package com.chethan.akka.actors.classicActor.infrastucture

import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.actor.{Actor, ActorLogging, ActorSystem, OneForOneStrategy, Props, SupervisorStrategy}
import akka.http.scaladsl.model.DateTime
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

/**
  * Created by $Chethan on Jan 02, 2024.
  */

object CustomSupervisonStrategy extends  App{

  val config =ConfigFactory.load().getConfig("customStrategyConfigurator")
  val system = ActorSystem("dummy",config)
  val actor  = system.actorOf(Props[ChildActor])
  actor ! "one"
//  actor ! "two"

}

class ChildActor extends Actor with ActorLogging {
  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(){
    case _: Exception => SupervisorStrategy.Restart
  }


  override def preStart(): Unit = log.info("pre start " + DateTime.now.toString())

  override def postStop(): Unit = log.info("this actor has been stopped " + DateTime.now.toString())


  override def preRestart(reason: Throwable, message: Option[Any]): Unit = log.info("pre restart " + DateTime.now.toString())

  override def postRestart(reason: Throwable): Unit = log.info("post restart " + DateTime.now.toString())

  override def receive: Receive = {
    case "one" => throw new Exception()
    case "two" => throw new RuntimeException()
  }
}


//Note: Custom supervison strategy by inheriting akka.actor.SupervisorStrategyConfigurator and by and providing the same in
// akka configuration in application.conf ("guardian-supervisor-strategy" = "com.chethan.akka.actors.infrastucture.CustomStrategy")
final class CustomStrategy extends akka.actor.SupervisorStrategyConfigurator {
  def create(): SupervisorStrategy = OneForOneStrategy(){
    case _: Exception        => SupervisorStrategy.Stop
  }
}
