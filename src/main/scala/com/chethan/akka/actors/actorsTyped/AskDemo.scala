package com.chethan.akka.actors.actorsTyped

import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.util.Timeout
import utils._

import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by Chethan on Jan 28, 2024.
  */

object AskDemo {

  trait WorkProtocol

  case class ComputationalTask(text: String, replyTo: ActorRef[WorkProtocol]) extends WorkProtocol

  case class ComputationalResult(result: Int) extends WorkProtocol

  object Worker {
    def apply() = {
      Behaviors.receive[WorkProtocol]{ (context, message) =>

        message match {
          case ComputationalTask(text, destination) =>
            throw new Exception()
            context.log.info(s"[Worker] : Crunching data for $text")
            destination ! ComputationalResult(text.split(" ").length)
            Behaviors.same
          case _                                    => Behaviors.same
        }
      }
    }
  }

  def askSimple() = {

    val actorSystem = ActorSystem(Worker(), "DemoAskSimple").withFiniteLifeSpan(5 seconds)

    implicit val timeout = Timeout(3 seconds)
    implicit val exec    = actorSystem.scheduler

    val future = actorSystem.ask(ref => ComputationalTask("this is a akka typed ask pattern", ref))
    implicit val exe = actorSystem.executionContext

    future.foreach(println)

  }

  def askWithinAnotherActor = {
    val userGuardian = Behaviors.setup[WorkProtocol]{ context =>

      case class ReturnFromTheFuture(result: WorkProtocol, text: String) extends WorkProtocol
      val worker = context.spawn(Worker(), "worker")
      implicit val timeout = Timeout(3 seconds)


      context.ask(worker, selfRef => ComputationalTask("this is a convoluted ask pattern", selfRef)){
        case Failure(exception) => ReturnFromTheFuture(ComputationalResult(-1),"this computation failed due to exception")
        case Success(comPre)    => ReturnFromTheFuture(comPre, "this is a convoluted ask pattern")

      }


      Behaviors.receiveMessage{
        case ReturnFromTheFuture(comPre, text) => context.log.info(s"this is the result $comPre for input $text")
          Behaviors.same
      }
    }

    ActorSystem(userGuardian, "conAsk").withFiniteLifeSpan(5 seconds)
  }


  def main(args: Array[String]): Unit = {
    //    askSimple()
    askWithinAnotherActor
  }
}


