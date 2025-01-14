package com.chethan.akka.actors.assignment.classisActor

import akka.actor.{Actor, ActorLogging, ActorRef}
import Excavator.DoneLoading
import MiningSpecs._
import WeighBridge._

import scala.util.Random

/**
  * Created by $Chethan on Jan 16, 2024.
  */


class WeighBridge extends Actor with ActorLogging {
  override def receive: Receive = {
    case StartWeighing(truckRef) =>
      log.info(s"${Console.MAGENTA}[${getAssetName(self)}]${Console.YELLOW}  started weighing ${Console.MAGENTA}[${getAssetName(truckRef)}].${Console.RED}")
      Thread.sleep(5000)

      //Note: Represents failure(Break down of assest)
      val ram = Random.nextInt(40)
      if (ram % 4 == 0) {
        sender() ! DoneLoading(truckRef)
        throw new Exception()
      }

      //Note:Represent Completion of Task
      else {
        log.info(s"${Console.MAGENTA}[${getAssetName(self)}]${Console.YELLOW} : ${Console.MAGENTA}[${getAssetName(truckRef)}] ${Console.YELLOW}has been Weighed.${Console.RED}")
        sender() ! DoneWeighing(truckRef)
      }
  }
}

object WeighBridge {
  case class StartWeighing(truckRef: ActorRef)

  case class DoneWeighing(truckRef: ActorRef)

}