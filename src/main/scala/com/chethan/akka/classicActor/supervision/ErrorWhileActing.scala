package com.chethan.akka.classicActor.supervision

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

/**
  * Created by $Chethan on Dec 29, 2023.
  */

class ErrorWhileActing extends Actor with ActorLogging {


  override def preStart(): Unit = log.info("this is pre start")

  override def postStop(): Unit = log.info("this is post start")

  override def preRestart(reason: Throwable, message: Option[Any]): Unit =
    log.info("this is pre re start")


  override def postRestart(reason: Throwable): Unit = log.info("this is post restart")

  override def receive: Receive = {
    case _ => log.info("this is the message")
      throw new Exception()
  }
}

object ErrorTesting extends App {
  val acs        = ActorSystem("ErrorActorSystem")
  val errorActor = acs.actorOf(Props[ErrorWhileActing], "ErrorActor")
  errorActor ! ""

}
