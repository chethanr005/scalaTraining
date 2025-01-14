package com.chethan.akka.actors.assignment.enhancement

import java.util.UUID

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorLogging, ActorRef, Cancellable, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import akka.pattern.ask
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}
import akka.util.Timeout
import com.chethan.akka.actors.assignment.classisActor.Excavator.{DoneLoading, StartLoading}
import MineManagerSpec._
import MiningSpecs.{Shift, getAssetName}
import TruckSpec._
import com.chethan.akka.actors.assignment.classisActor.{Excavator, WeighBridge}
import com.chethan.akka.actors.assignment.classisActor.WeighBridge.{DoneWeighing, StartWeighing}

import scala.collection.immutable._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by $Chethan on Jan 16, 2024.
  */

class MineManagerV2 extends Actor with ActorLogging {


  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(){
    case _: Exception => Stop
  }

  //  implicit val executionContext: ExecutionContextExecutor = context.system.dispatcher
  implicit val timeout = Timeout(6 seconds)


  override def receive: Receive = {
    case InitiateShift(shift: Shift) =>
      val trucks      : IndexedSeq[ActorRef]       =
        (1 to shift.trucks).map{ a =>
          val asset = context.actorOf(Props[TruckV2], s"${"Truck"}_${a}")
          context.watch(asset)
        }
      val excavators  : IndexedSeq[ActorRefRoutee] = getMonitoredAssets(shift.excavators, Props[Excavator])
      val weighBridges: IndexedSeq[ActorRefRoutee] = getMonitoredAssets(shift.weighBridges, Props[WeighBridge])

      val excavatorRouter   = Router(RoundRobinRoutingLogic(), excavators)
      val weighBridgeRouter = Router(RoundRobinRoutingLogic(), weighBridges)

      context.become(operational(trucks, excavatorRouter, weighBridgeRouter, startMiningProcess, shift.shiftHours))
      shiftConcludeAlarm(shift.shiftHours)
  }

  private def operational(trucks: IndexedSeq[ActorRef], excavators: Router, weighBridges: Router, workReceiver: Cancellable, shiftHours: Int): Receive = {

    case Work =>
      if (trucks.isEmpty) log.warning("No Trucks available. Waiting for the Trucks to return.")
      else {
        val truck = trucks.head
        context.become(operational(trucks.tail, excavators, weighBridges, workReceiver, shiftHours))
        val future = truck ? GoToV2("excavator")
        future.onComplete{
          case Success(value)     => excavators.route(StartLoading(truck), self)
          case Failure(exception) =>
        }
      }

    case UpdateTruckRouter(truckRef) =>
      // Note: This block updates the asset availability list.
      val assetName = getAssetName(truckRef)
      if (assetName.startsWith("T")) context.become(operational(trucks :+ truckRef, excavators, weighBridges, workReceiver, shiftHours))


    case DoneLoading(truckRef: ActorRef) =>
      //Note:Reply from Excavator and WeighBridge
      val future = truckRef ? GoToV2("weighBridge")
      future.onComplete{
        case Success(value)     =>
          println(truckRef + " " + " is waiting")
          weighBridges.route(StartWeighing(truckRef), self)
        case Failure(exception) =>
      }


    case DoneWeighing(truckRef) =>
      truckRef ! InitiateDumpingV2

    case DumpSuccessV2 =>
      log.info("Mining Cycle Successfully Completed [" + getAssetName(sender()) + "]")
      context.become(operational(trucks :+ sender(), excavators, weighBridges, workReceiver, shiftHours))


    case Terminated(asset) =>
      //Note: Routers
      val assetName = getAssetName(asset)
      if (assetName.startsWith("T")) {
        val backUpTruck = context.actorOf(Props[TruckV2], s"Truck_BackUp_${UUID.randomUUID()}")
        context.watch(backUpTruck)
        context.become(operational(trucks :+ backUpTruck, excavators, weighBridges, workReceiver, shiftHours))
      }
      else if (assetName.startsWith("E")) {
        val backUpExcavator = context.actorOf(Props[Excavator], s"Excavator_BackUp_${UUID.randomUUID()}")
        context.watch(backUpExcavator)
        val updatedRouter = excavators.removeRoutee(asset).addRoutee(backUpExcavator)
        context.become(operational(trucks, updatedRouter, weighBridges, workReceiver, shiftHours))
      }
           else if (assetName.startsWith("W")) {
             val backUpWeighBridge = context.actorOf(Props[WeighBridge], s"WeighBridge_BackUp_${UUID.randomUUID()}")
             context.watch(backUpWeighBridge)
             val updatedRouter = weighBridges.removeRoutee(asset).addRoutee(backUpWeighBridge)
             context.become(operational(trucks, excavators, updatedRouter, workReceiver, shiftHours))
           }


    case ConcludeShift =>
      println(Console.WHITE_B + Console.BLACK + "Shift is going to end" + Console.BLACK_B + Console.RED)
      workReceiver.cancel()
      terminateShift(shiftHours)

    case ShiftTermination =>
      println(Console.WHITE_B + Console.BLACK + "Stop All The Assets" + Console.BLACK_B + Console.RED)
      context.stop(self)

  }

  def startMiningProcess: Cancellable = {
    context.system.scheduler.scheduleWithFixedDelay(0 second, 1 seconds){ () => {
      self ! Work
    }
    }
    //    context.system.scheduler.scheduleOnce(3 seconds){
    //      self ! Work
    //    }
  }

  def shiftConcludeAlarm(shiftHours: Int): Cancellable = {
    val duration = (shiftHours * 0.95).round
    context.system.scheduler.scheduleOnce(duration minutes){
      self ! ConcludeShift
    }
  }

  def terminateShift(shiftHours: Int) = {
    val duration = (shiftHours * 0.05).round
    context.system.scheduler.scheduleOnce(duration minutes){
      self ! ShiftTermination
    }
  }


  private def getMonitoredAssets(assetsRequired: Int, props: Props): IndexedSeq[ActorRefRoutee] = {
    val assetType = props.clazz.getName
    (1 to assetsRequired).map{ a =>
      val asset = context.actorOf(props, s"${assetType}_${a}")
      context.watch(asset)
      ActorRefRoutee(asset)
    }
  }
}

object MineManagerSpec {
  case class InitiateShift(shift: Shift)

  case class ConcludeShift(shift: Shift)

  case class ShiftTermination(shift: Shift)

  case class UpdateTruckRouter(asset: ActorRef)

  case object Work

  def assetAvailabilityDashboard(trucks: Seq[ActorRef], excavators: Seq[ActorRef], weighBridges: Seq[ActorRef]) = {

    val truckNames     = trucks.map(a => getAssetName(a)).mkString(" , ")
    val excavatorNames = excavators.map(a => getAssetName(a)).mkString(" , ")
    val weighBridges1  = weighBridges.map(a => getAssetName(a)).mkString(" , ")
    println()
    println(Console.BLUE + "Assets Availability")
    Console.print(Console.RED + "Trucks")
    print(Console.GREEN + " => ")
    println(Console.CYAN + truckNames)
    Console.print(Console.RED + "Excavators")
    print(Console.GREEN + " => ")
    println(Console.CYAN + excavatorNames)
    Console.print(Console.RED + "WeighBridges")
    print(Console.GREEN + " => ")
    println(Console.CYAN + weighBridges1)
    println()

  }
}
