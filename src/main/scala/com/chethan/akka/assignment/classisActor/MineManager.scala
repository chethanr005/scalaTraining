package com.chethan.akka.assignment.classisActor

import java.util.UUID

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorLogging, ActorRef, Cancellable, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import akka.pattern.ask
import akka.util.Timeout
import com.chethan.akka.assignment.classisActor.Excavator.{DoneLoading, StartLoading}
import com.chethan.akka.assignment.classisActor.MineManagerSpec._
import com.chethan.akka.assignment.classisActor.MiningSpecs.{Shift, getAssetName}
import com.chethan.akka.assignment.classisActor.TruckSpec._
import com.chethan.akka.assignment.classisActor.WeighBridge.{DoneWeighing, StartWeighing}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by $Chethan on Jan 16, 2024.
  */

class MineManager extends Actor with ActorLogging {


  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(){
    case _: Exception => Stop
  }

  //  implicit val executionContext: ExecutionContextExecutor = context.system.dispatcher
  implicit val timeout = Timeout(6 seconds)


  override def receive: Receive = {
    case InitiateShift(shift: Shift) =>
      val trucks      : Seq[ActorRef] = getMonitoredAssets(shift.trucks, Props[Truck])
      val excavators  : Seq[ActorRef] = getMonitoredAssets(shift.excavators, Props[Excavator])
      val weighBridges: Seq[ActorRef] = getMonitoredAssets(shift.weighBridges, Props[WeighBridge])
      context.become(operational(trucks, excavators, weighBridges, startMiningProcess, shift.shiftHours))
      shiftConcludeAlarm(shift.shiftHours)
  }

  private def operational(trucks: Seq[ActorRef], excavators: Seq[ActorRef], weighBridges: Seq[ActorRef], workReceiver: Cancellable, shiftHours: Int): Receive = {

    case Work               =>
      assetAvailabilityDashboard(trucks, excavators, weighBridges)
      if (trucks.isEmpty) log.warning("No Trucks available. Waiting for the Trucks to return.")
      else {
        val truck = trucks.head
        if (excavators.isEmpty) log.warning(s"Excavators are busy Loading!!!, ${Console.YELLOW} ${getAssetName(truck)} ${Console.RED}  is waiting.")
        else {
          val excavator = excavators.head
          context.become(operational(trucks.tail, excavators.tail, weighBridges, workReceiver, shiftHours))
          val future = truck ? GoTo(excavator)
          future.onComplete{
            case Success(value)     => value match {
              case _ => excavator ! StartLoading(truck)
            }
            case Failure(exception) =>
              self ! UpdateAsset(excavator)
          }
        }
      }
    case UpdateAsset(asset) =>
      // Note: This block updates the asset availability list.
      val assetName = getAssetName(asset)
      if (assetName.startsWith("T")) context.become(operational(trucks :+ asset, excavators, weighBridges, workReceiver, shiftHours))
      else if (assetName.startsWith("E")) context.become(operational(trucks, excavators :+ asset, weighBridges, workReceiver, shiftHours))
           else if (assetName.startsWith("W")) context.become(operational(trucks, excavators, weighBridges :+ asset, workReceiver, shiftHours))


    case DoneLoading(truckRef: ActorRef) =>
      //Note:Reply from Excavator and WeighBridge
      val senderAsset = getAssetName(sender())
      if (senderAsset.startsWith("E") && !excavators.contains(sender())) context.become(operational(trucks, excavators :+ sender(), weighBridges, workReceiver, shiftHours))

      if (weighBridges.isEmpty) {
        log.info(s"[${getAssetName(truckRef)}] : Weighing Bridges are full!!!, Waiting...")
        Thread.sleep(2000)
        self.forward(DoneLoading(truckRef))
      }
      else {
        val wb = weighBridges.head
        if (senderAsset.startsWith("E") && excavators.contains(sender())) context.become(operational(trucks, excavators, weighBridges.tail, workReceiver, shiftHours))
        else context.become(operational(trucks, excavators :+ sender(), weighBridges.tail, workReceiver, shiftHours))
        val future = truckRef ? GoTo(wb)
        future.map{
          case ReachedDest => wb ! StartWeighing(truckRef)
          case _           => log.info("Nothing")
        }
      }


    case DoneWeighing(truckRef) =>
      context.become(operational(trucks, excavators, weighBridges :+ sender(), workReceiver, shiftHours))
      truckRef ! InitiateDumping

    case DumpSuccess =>
      log.info("Mining Cycle Successfully Completed")
      context.become(operational(trucks :+ sender(), excavators, weighBridges, workReceiver, shiftHours))

    case Terminated(asset) =>
      //Note: Routers
      val assetName = getAssetName(asset)
      if (assetName.startsWith("T")) {
        val backUpTruck = context.actorOf(Props[Truck], s"Truck_BackUp_${UUID.randomUUID()}")
        context.watch(backUpTruck)
        context.become(operational(trucks :+ backUpTruck, excavators, weighBridges, workReceiver, shiftHours))
      }
      else if (assetName.startsWith("E")) {
        val backUpExcavator = context.actorOf(Props[Excavator], s"Excavator_BackUp_${UUID.randomUUID()}")
        context.watch(backUpExcavator)
        context.become(operational(trucks, excavators :+ backUpExcavator, weighBridges, workReceiver, shiftHours))
      }
           else if (assetName.startsWith("W")) {
             val backUpWeighBridge = context.actorOf(Props[WeighBridge], s"WeighBridge_BackUp_${UUID.randomUUID()}")
             context.watch(backUpWeighBridge)
             context.become(operational(trucks, excavators, weighBridges :+ backUpWeighBridge, workReceiver, shiftHours))
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


  private def getMonitoredAssets(assetsRequired: Int, props: Props): Seq[ActorRef] = {
    val assetType = props.clazz.getName
    (1 to assetsRequired).map{ a =>
      val asset = context.actorOf(props, s"${assetType}_${a}")
      context.watch(asset)
      asset
    }
  }
}

object MineManagerSpec {
  case class InitiateShift(shift: Shift)

  case class ConcludeShift(shift: Shift)

  case class ShiftTermination(shift: Shift)

  case class UpdateAsset(asset: ActorRef)

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
