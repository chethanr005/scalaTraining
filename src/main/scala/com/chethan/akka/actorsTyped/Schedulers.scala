package com.chethan.akka.actorsTyped

import akka.actor.Cancellable
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.duration._

/**
  * Created by $Chethan on Jan 24, 2024.
  */

object Schedulers {

  object SchedulerActor {
    def apply(): Behaviors.Receive[String] = {

      Behaviors.receive[String]{ (context, message) =>
        context.log.info(message)
        Behaviors.same
      }
    }
  }

  //  def demoScheduler() = {
  //    val guardian = Behaviors.setup[String]{ context =>
  //      val scheduledActor = context.spawn(SchedulerActor(), "schedulerDemo")
  //      context.scheduleOnce(2 seconds, scheduledActor, "this is a message")
  //      implicit val ex = context.system.executionContext
  //      context.system.scheduler.scheduleAtFixedRate(Duration.Zero, 1 second){
  //        () =>
  //          scheduledActor ! " timer"
  //      }
  //      Behaviors.empty
  //    }
  //
  //    val acs = ActorSystem(guardian, "acs")
  //    //    Thread.sleep(5000)
  //    acs.terminate()
  //  }


  //  def timeoutActor = {
  //    Behaviors.receive[String]{ (context, message) =>
  //      val schedulerActor = context.spawn(SchedulerActor(), "schedulerActor1")
  //
  //      def getTimer: Cancellable = context.scheduleOnce(1 second, schedulerActor, "Stop")
  //
  //      var timer = getTimer
  //      message match {
  //        case "Stop"  => Behaviors.stopped
  //        case "Reset" =>
  //          timer.cancel()
  //          timer = getTimer
  //          Behaviors.same
  //      }
  //    }
  //  }

  object TimeoutActorV2 {
    def apply(): Behaviors.Receive[String] = {
      Behaviors.receive[String]{ (context, message) =>
        timer(context.scheduleOnce(1 second, context.self, "Stop"))
      }
    }

    def timer(stoppingTimer: Cancellable): Behaviors.Receive[String] = {

      Behaviors.receive[String]{ (context, message) =>

        message match {
          case "Stop"  =>
            context.log.info("Stop")
            Behaviors.stopped
          case "Reset" =>
            context.log.info("Reset")
            stoppingTimer.cancel()
            val restedTimer = context.scheduleOnce(1 second, context.self, "Stop")
            timer(restedTimer)
        }
      }
    }
  }


  def timerGuard = {

    val guard = Behaviors.setup[Unit]{ context =>
      val timeoutActor = context.spawn(Schedulers.TimeoutActorV2(), "toac")

      //      (1 to 50).foreach{ _ => timeoutActor ! "Reset"; Thread.sleep(500) }
      timeoutActor ! "Reset"
      Thread.sleep(500)
      timeoutActor ! "Reset"
      Thread.sleep(500)
      timeoutActor !"Reset"
      Thread.sleep(500)
      timeoutActor !"Reset"
      Thread.sleep(500)
      timeoutActor !"Reset"
      Thread.sleep(500)
      timeoutActor !"Reset"
      Thread.sleep(1000)
      timeoutActor !"Reset"

    Behaviors.empty
  }

  ActorSystem(guard, "anysystem")
}


def main(args: Array[String]): Unit = {
  //    demoScheduler()
  timerGuard
  Thread.sleep(50000)
}
}
