package com.chethan.akka.actorsTyped

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

/**
  * Created by Chethan on Jan 28, 2024.
  */

object StashDemo {
  trait Command

  case object Read extends Command

  case object Open extends Command

  case object Close extends Command

  case object Write extends Command


  object StashObject {

    def apply(): Behavior[Command] = closed


    def closed: Behavior[Command] = {
      Behaviors.withStash(1024){ stashBuffer =>


        Behaviors.receive[Command]{ (context, message) =>

          message match {
            case Open =>
              stashBuffer.unstashAll(open)
            case any  => context.log.info(s"IO Device is closed. $any will be stashed")
              stashBuffer.stash(any)
              Behaviors.same

          }
        }

      }
    }

    def open = {
      Behaviors.receive[Command]{ (context, message) =>

        message match {
          case Close => closed
          case Read  =>
            context.log.info("Read Command")
            Behaviors.same
          case Write =>
            context.log.info("Write Command")
            Behaviors.same
        }
      }
    }

  }


  def main(args: Array[String]): Unit = {
    val guard = Behaviors.setup[Unit]{ context =>
      val child = context.spawn(StashObject(), "stashObject")
      child ! Open
      child ! Read
      child ! Read
      child ! Read
      child ! Write
      child ! Write
      child ! Close
      child ! Read
      child ! Read
      child ! Read
      child ! Write
      child ! Write
      child ! Open
      Behaviors.empty

    }

    ActorSystem(guard,"guard")
  }

}
