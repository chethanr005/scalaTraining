package com.chethan.akka.assignment.classisActor

import akka.actor.{Actor, ActorLogging, ActorRef, Status}
import com.chethan.akka.assignment.classisActor.MiningSpecs.getAssetName
import com.chethan.akka.assignment.classisActor.TruckSpec.{DumpSuccess, GoTo, InitiateDumping, ReachedDest}

import scala.util.Random

/**
  * Created by $Chethan on Jan 16, 2024.
  */

class Truck extends Actor with ActorLogging {

  override def receive: Receive = {
    case GoTo(asset: ActorRef) =>
      log.info(s"${Console.YELLOW}[${getAssetName(self)}]${Console.BLUE} : is heading towards ${Console.YELLOW}[${getAssetName(asset)}]${Console.RED}")
      Thread.sleep(5000) // todo use scheduler

      //Note: Represents failure(Break down of asset)
      val ram = Random.nextInt(40)
      if (ram % 4 == 0) {
        sender() ! Status.Failure(new Exception())
        throw new Exception()
      }

      //Note:Represent Completion of Task
      else {
        log.info(s"${Console.YELLOW}[${getAssetName(self)}]${Console.BLUE} :  has reached ${Console.YELLOW}[${getAssetName(asset)}]${Console.RED}")
        sender() ! ReachedDest
      }

    case InitiateDumping =>
      log.info(s"${Console.YELLOW}[${getAssetName(self)}]${Console.BLUE} : is heading towards DumpingSite${Console.RED}")
      Thread.sleep(5000)
      log.info(s"${Console.YELLOW}[${getAssetName(self)}]${Console.BLUE} : has dumped the load.${Console.RED}")
      sender() ! DumpSuccess
  }
}

object TruckSpec {

  case class GoTo(asset: ActorRef)

  case object ReachedDest

  case object InitiateDumping

  case object DumpSuccess
}
