package com.chethan.akka.classicActor.supervision

import java.io.File
import java.time.LocalDateTime

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorLogging, ActorSystem, OneForOneStrategy, Props, SupervisorStrategy}
import akka.pattern.{BackoffOpts, BackoffSupervisor}

import scala.concurrent.duration._
import scala.io.Source

/**
  * Created by $Chethan on Dec 28, 2023.
  */

object BackOffSuperVision extends App {

  val acs = ActorSystem("BackOffSuperVisorActorSystem")
  //  val fileBasedActor: ActorRef = acs.actorOf(Props[FileBasedPersistence], "fileActor")
  //
  //    fileBasedActor ! ReadFile


  //  //Note:back off failure super visor
  //  val backOffProps = BackoffSupervisor.props(
  //    BackoffOpts.onFailure(Props[FileBasedPersistence],
  //      "simpleBackOffActor", 3 seconds, 30 seconds, 0.2)
  //  )
  //
  //  val simpleBackOffSupervisor = acs.actorOf(backOffProps, "simpleBackOffSuperVisor")
  //  simpleBackOffSupervisor ! ReadFile
  //  Thread.sleep(4000)
  //  simpleBackOffSupervisor ! ReadFile


  //  //  Note:back off stop super visor
  //  val backOffStopProps = BackoffSupervisor.props(
  //    BackoffOpts.onStop(Props[FileBasedPersistence],
  //      "simpleBackOffActor", 3 seconds, 30 seconds, 0.2).withDefaultStoppingStrategy
  //  )
  //
  //  val simpleBackOffStopSupervisor = acs.actorOf(backOffStopProps, "simpleBackOffSuperVisor")
  //  simpleBackOffStopSupervisor ! ReadFile
  //  Thread.sleep(4000)
  //  simpleBackOffStopSupervisor ! ReadFile

  //  //  Note:actor initialization failure demo
  //    val actorFail = acs.actorOf(Props[ActorFail], "actorFail")
  //    actorFail ! ReadFile

  //  //  Note:back off supervisor for actor initialization failure
  val backOffSupervisor = BackoffSupervisor.props(BackoffOpts.onStop(Props[ActorFail],
    "actorFailed",
    1 second,
    30 second,
    0.1).withSupervisorStrategy(OneForOneStrategy(){case _:Exception => Stop}))
  val stopSupervisor    = acs.actorOf(backOffSupervisor, "actorFail")
  stopSupervisor ! ReadFile

}

object ReadFile

class FileBasedPersistence extends Actor with ActorLogging {


  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(){
    case _: Exception => Stop
  }

  var dataSource: Source = null


  override def preStart(): Unit = log.info("Persistence actor starting " + LocalDateTime.now().toString)

  override def postStop(): Unit = log.info("Persistence actor stopping " + LocalDateTime.now().toString)

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = log.info("Persistence actor restarting " + LocalDateTime.now().toString)

  override def receive: Receive = {
    case ReadFile =>
      if (dataSource == null) {
        dataSource = Source.fromFile(new File(""))
        log.info("I have read some important message : " + dataSource.getLines().toList)
      }
  }

}

class ActorFail extends FileBasedPersistence {
  override def preStart(): Unit = {
    log.info("prestart");
    Source.fromFile(new File("G:\\Sources\\scala-training\\src\\main\\resources\\import.txt")).close()
  }
}


class CustomError extends VirtualMachineError