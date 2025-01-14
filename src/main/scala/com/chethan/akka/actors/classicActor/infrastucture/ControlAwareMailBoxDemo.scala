package com.chethan.akka.actors.classicActor.infrastucture

import akka.actor.{ActorSystem, Props}
import akka.dispatch.ControlMessage
import com.typesafe.config.ConfigFactory

/**
  * Created by $Chethan on Jan 10, 2024.
  */

object ControlAwareMailBoxDemo extends App {
  case object ManagementTicket extends ControlMessage

  val acs = ActorSystem("ControlAwareMailBoxDemo", ConfigFactory.load().getConfig("customMailBox"))

  //Note:Method 1
  //  val controlAwareActor = acs.actorOf(Props[SimpleActor].withMailbox("control-aware-mailbox"))
  //  controlAwareActor ! "[P3] this is a new feature"
  //  controlAwareActor ! "[P0] this is an urgent message"
  //  controlAwareActor ! "[P0] this is an urgent message"
  //  (1 to 1000).foreach(a => controlAwareActor ! s"[P10] this is an urgent message ${a}")
  //  controlAwareActor ! ManagementTicket


  //Note:Method 2 using deployment config
  val controlAwareActor2 = acs.actorOf(Props[SimpleActor], "altControlAwareActor")
  controlAwareActor2 ! "[P3] this is a new feature"
  controlAwareActor2 ! "[P0] this is an urgent message"
  controlAwareActor2 ! "[P0] this is an urgent message"
  controlAwareActor2 ! ManagementTicket
}