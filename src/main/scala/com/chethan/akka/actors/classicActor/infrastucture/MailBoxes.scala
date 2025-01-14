package com.chethan.akka.actors.classicActor.infrastucture

import akka.actor.ActorSystem.Settings
import akka.actor.{Actor, ActorLogging, ActorSystem, PoisonPill, Props}
import akka.dispatch.{PriorityGenerator, UnboundedPriorityMailbox}
import com.typesafe.config.Config

import scala.util.Random

/**
  * Created by $Chethan on Jan 09, 2024.
  */

object MailBoxes extends App {

  val acs         = ActorSystem("MailBoxDemo")
  val simpleActor = acs.actorOf(Props[SimpleActor].withDispatcher("support-ticket-dispatcher"))
  //  simpleActor ! "[P1] this is the normal message"
  //  simpleActor ! "[P3] this is the least urgent message"
  //  simpleActor ! "[P0] this is an urgent message"

  val random = new Random()

  (1 to 1000).foreach{ i =>
    val sleep = random.nextInt(11)
    simpleActor ! s"[P$sleep] this is a message ${i} with priority $sleep"
  }
  simpleActor ! PoisonPill


  class SimpleActor extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info(message.toString)
    }
  }

  class SupportTicketPriorityMailbox(settings: Settings, config: Config) extends UnboundedPriorityMailbox(
    PriorityGenerator{
      case PoisonPill                                     => 0
      case message: String if message.startsWith("[P1]")  => 1
      case message: String if message.startsWith("[P2]")  => 2
      case message: String if message.startsWith("[P3]")  => 3
      case message: String if message.startsWith("[P4]")  => 4
      case message: String if message.startsWith("[P5]")  => 5
      case message: String if message.startsWith("[P6]")  => 6
      case message: String if message.startsWith("[P7]")  => 7
      case message: String if message.startsWith("[P8]")  => 8
      case message: String if message.startsWith("[P9]")  => 9
      case message: String if message.startsWith("[P10]") => 10
      case _                                              => 10
    }
  )
}

