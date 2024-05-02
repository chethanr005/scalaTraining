package com.chethan.akka.assignment.classisActor

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.chethan.akka.assignment.classisActor.Excavator.{DoneLoading, StartLoading}
import com.chethan.akka.assignment.classisActor.MineManagerSpec.UpdateAsset
import com.chethan.akka.assignment.classisActor.MiningSpecs.getAssetName

import scala.util.Random

/**
  * Created by $Chethan on Jan 16, 2024.
  */

class Excavator extends Actor with ActorLogging {
  override def receive: Receive = {
    case StartLoading(truckRef) =>
      log.info(s"${Console.BLUE}[${getAssetName(self)}]${Console.GREEN} : started loading to ${Console.BLUE}[${getAssetName(truckRef)}].${Console.RED} ")
      Thread.sleep(5000)

      //Note: Represents failure(Break down of asset)
      val ram = Random.nextInt(40)
      if (ram % 4 == 0) {
        sender() ! UpdateAsset(truckRef)
        throw new Exception()
      }

      //Note:Represent Completion of Task
      else {
        log.info(s"${Console.BLUE}[${getAssetName(self)}]${Console.GREEN}  : ${Console.BLUE} [${getAssetName(truckRef)}]${Console.GREEN}  has been loaded.${Console.RED} ")
        sender() ! DoneLoading(truckRef)
      }
  }


}

object Excavator {
  case class StartLoading(truckRef: ActorRef)

  case class DoneLoading(truckRef: ActorRef)
}

