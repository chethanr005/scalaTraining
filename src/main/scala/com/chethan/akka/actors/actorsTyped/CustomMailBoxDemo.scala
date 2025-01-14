package com.chethan.akka.actors.actorsTyped

import akka.actor.ActorSystem.Settings
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, MailboxSelector}
import akka.dispatch.{ControlMessage, PriorityGenerator, UnboundedPriorityMailbox}
import com.chethan.akka.actors.actorsTyped.utils.LoggerActor
import MailBoxSpecs.{CasualMessage, ImpMessage, VIPMessage}
import com.typesafe.config.{Config, ConfigFactory}

/**
  * Created by Chethan on Jan 28, 2024.
  */

object CustomMailBoxDemo {

  def customMailBoxDemo() =

    Behaviors.setup[Unit]{
      context =>
        val priorityActor = context.spawn(LoggerActor[String](), "customMailBoxActor", MailboxSelector.defaultMailbox())//.fromConfig("custom-mailbox"))
        priorityActor ! "[P10] : this is a normal message"
        priorityActor ! "[P1] : this is very important message"
        priorityActor ! "[P0] : this is a highly prioritized message"
        priorityActor ! "[P2] : this is an important message"
        Behaviors.same
    }


  def controlMailboxDemo() = {
    Behaviors.setup[Unit]{
      context =>
        val controlledActor = context.spawn(LoggerActor[ImpMessage](), "controlMailBoxActor")//, MailboxSelector.fromConfig("control-mailbox"))


        (1 to 100).foreach(num => if (num == 100) controlledActor ! VIPMessage else controlledActor ! CasualMessage)

        Behaviors.same
    }


  }

  def main(args: Array[String]): Unit = {
    val customMailDemo = ActorSystem(controlMailboxDemo(), "cmbd", ConfigFactory.load().getConfig("customTypedMailBoxSettings"))

  }
}

object MailBoxSpecs {
  trait ImpMessage

  case object VIPMessage extends ControlMessage with ImpMessage

  case object CasualMessage extends ImpMessage

  class CustomMailBoxPrioritizer(settings: Settings, config: Config) extends UnboundedPriorityMailbox(PriorityGenerator{
    case message: String if message.startsWith("[P0]") => 0
    case message: String if message.startsWith("[P1]") => 1
    case message: String if message.startsWith("[P2]") => 2
    case message: String if message.startsWith("[P3]") => 3
    case _                                             => 4
  })
}
