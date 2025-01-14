package com.chethan.akka.actors.actorsTyped.Demo

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}
import Notifier._

/**
  * Created by Chethan on Jan 28, 2024.
  */
object Notifier {

  def apply(): Behavior[Command] = snooze()


  def snooze(): Behavior[Command] = {
    Behaviors.withStash(1){ stashBuffer =>


      Behaviors.receive[Command]{ (context, message) =>

        message match {
          case Alert =>
            stashBuffer.unstashAll(alert())
          case any   => context.log.info(s"Notifier is in Snooze mode. $any will be stashed")
            stashBuffer.stash(any)
            Behaviors.same

        }
      }

    }
  }

  def alert() = {
    Behaviors.receive[Command]{ (context, message) =>

      message match {
        case Snooze => snooze
        case Read   =>
          context.log.info(Read.toString)
          Behaviors.same
        case Write  =>
          context.log.info(Write.toString)
          Behaviors.same
      }
    }
  }


  trait Command

  case object Read extends Command

  case object Alert extends Command

  case object Snooze extends Command

  case object Write extends Command
}


object StashNotifierDemo extends App {


  val guard = Behaviors.setup[Unit]{ context =>
    val child = context.spawn(Notifier(), "stashObject")
//    child ! Alert
    child ! Read
    child ! Read
    child ! Read
    child ! Write
    child ! Write
//    child ! Snooze
//    child ! Read
//    child ! Read
//    child ! Read
//    child ! Write
//    child ! Write
//    child ! Alert
    Behaviors.empty

  }

  ActorSystem(guard, "guard")
}
