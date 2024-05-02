package com.chethan.akka.classicActor

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.event.Logging

/**
  * Created by $CapName on Oct 31, 2023.
  */

object ActorLoggerDemo extends App {

  class SimpleActorWithLogs extends Actor {
    val logger = Logging(context.system, this)

    override def receive: Receive = {
      case message: String =>
        println(message)
        logger.error(message)
    }
  }

  val actorSystem = ActorSystem("LoggingDemo")
  val simpleLoggerActor = actorSystem.actorOf(Props[SimpleActorWithLogs])
  simpleLoggerActor ! "this is an info logger"


  class LoggerActor extends Actor with ActorLogging {
    override def receive: Receive = {

      case (a,b,c)=>log.warning("this is warning logs {} {} {}",a,b,c)
      case message: String =>
        println(message)
        log.error(message)
    }
  }

  val selfLogger = actorSystem.actorOf(Props[LoggerActor])
  selfLogger ! "this is an self logger actor"
  selfLogger ! (123,654,312)

}
