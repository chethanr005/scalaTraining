package com.chethan.akka.actors.classicActor.askPattern

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, Stash}

/**
  * Created by $Chethan on Jan 10, 2024.
  */

object StashDemo extends App {

  val acs        = ActorSystem("StashDemo")
  val stashActor = acs.actorOf(Props[ResourceActor],"stashActor")
  stashActor ! Write
  stashActor ! Write
  stashActor ! Read
  stashActor ! Read
  stashActor ! Open
  stashActor ! Read
  stashActor ! Write




  class ResourceActor extends Actor with ActorLogging with Stash {
    override def receive: Receive = close

    def close: Receive = {
      case Open                =>
        log.info("Opening Resource Actor")
        context.become(open)
        unstashAll()
      case Read | Write =>
        log.info("unable to process the data before opening the actor. Stashing the message")
        //note: Same message cant be stashed twice.
        stash()
      case _                   => log.info("Unrecognizable data found before the actor is opened")
    }

    def open: Receive = {
      case Close       =>
        log.info("Closing Resource Actor")
        context.become(close)
      case a@(Read | Write) => log.info(a.toString)
      case _           => log.info("Unrecognizable data found")
    }
  }

  case object Open

  case object Close

  case object Read

  case class Write(data: String)

}
