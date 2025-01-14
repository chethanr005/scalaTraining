package com.chethan.akka.actors.actorsTyped

import akka.actor.typed.ActorSystem
import akka.actor.typed.receptionist.Receptionist
import akka.actor.typed.scaladsl.Behaviors
import MailBoxSpecs.{CasualMessage, VIPMessage}

import scala.concurrent.duration.FiniteDuration
/**
  * Created by $Chethan on Jan 24, 2024.
  */

package object utils {

  object LoggerActor {
    def apply[A](): Behaviors.Receive[A] = {

      Behaviors.receive[A]{ (context, message) =>

        message match {
          case CasualMessage => context.log.info("i have received casual message")
          case VIPMessage    => context.log.info("i have received VIP message")
          case _: String     => context.log.info(s"[${context.self}] : $message")
          case de@Receptionist.Deregistered => context.log.error(s"this actor has been de registered : $de")
        }
        Behaviors.same
      }
    }
  }


  implicit class ActorSystemEnhancement[A](actorSystem: ActorSystem[A]) {
    def withFiniteLifeSpan(duration: FiniteDuration): ActorSystem[A] = {
      import actorSystem.executionContext
      actorSystem.scheduler.scheduleOnce(duration, () => actorSystem.terminate())
      actorSystem
    }
  }
}
