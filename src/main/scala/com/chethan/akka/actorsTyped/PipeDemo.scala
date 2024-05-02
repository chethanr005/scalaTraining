package com.chethan.akka.actorsTyped

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Created by Chethan on Jan 28, 2024.
  */

object PipeDemo {
  val database = Map("chethan" -> 8005)

  def getInformation(name: String): Future[Int] = Future(database(name))


  def pipedActor() = {
    Behaviors.receive[DataProtocal]{ (context, message) =>
      message match {
        case FindInformation(name) =>
          val futureData = getInformation(name)
          context.pipeToSelf(futureData){
            case Failure(exception) =>
              UserError(name)
            case Success(num)       =>
              UserData(name, num)
          }
          Behaviors.same


        case UserData(name, num) => context.log.info(s"User Data found for $num : $num")
          Behaviors.same

        case UserError(name) => context.log.info(s"User Data Not found $name")
          Behaviors.same
      }
    }

  }


  def main(args: Array[String]): Unit = {

    val guard=Behaviors.setup[Unit]{
      context =>
        val pipedAct = context.spawn(pipedActor(), "pipedActor")

        pipedAct ! FindInformation("chthan")
        Behaviors.empty


    }
    ActorSystem(guard,"guard")

    Thread.sleep(1000)
  }


  trait DataProtocal

  case class FindInformation(name: String) extends DataProtocal

  case class UserError(name: String) extends DataProtocal

  case class UserData(name: String, num: Int) extends DataProtocal


}
