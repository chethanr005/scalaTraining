package com.chethan.akka.assignment.enhancement

import akka.actor.{Actor, ActorLogging, ActorRef, Status}
import com.chethan.akka.assignment.enhancement.MiningSpecs.getAssetName
import com.chethan.akka.assignment.enhancement.TruckSpec.{DumpSuccessV2, GoToV2, InitiateDumpingV2, ReachedDestV2}

import scala.util.Random

/**
  * Created by $Chethan on Jan 16, 2024.
  */

class TruckV2 extends Actor with ActorLogging {

  override def receive: Receive = {
    case GoToV2(asset) =>
      log.info(s"${Console.YELLOW}[${getAssetName(self)}]${Console.BLUE} : is heading towards ${Console.YELLOW}[${asset}]${Console.RED}")
      Thread.sleep(5000)

      //Note: Represents failure(Break down of asset)
      val ram = Random.nextInt(40)
      if (false) {
        sender() ! Status.Failure(new Exception())
        throw new Exception()
      }

      //Note:Represent Completion of Task
      else {
        log.info(s"${Console.YELLOW}[${getAssetName(self)}]${Console.BLUE} :  has reached ${Console.YELLOW}[${asset}]${Console.RED}")
        sender() ! ReachedDestV2
      }

    case InitiateDumpingV2 =>
      log.info(s"${Console.YELLOW}[${getAssetName(self)}]${Console.BLUE} : is heading towards DumpingSite${Console.RED}")
      Thread.sleep(5000)
      log.info(s"${Console.YELLOW}[${getAssetName(self)}]${Console.BLUE} : has dumped the load.${Console.RED}")
      sender() ! DumpSuccessV2
  }
}

object TruckSpec {

  case class GoToV2(asset: String)

  case object ReachedDestV2

  case object InitiateDumpingV2

  case object DumpSuccessV2
}
