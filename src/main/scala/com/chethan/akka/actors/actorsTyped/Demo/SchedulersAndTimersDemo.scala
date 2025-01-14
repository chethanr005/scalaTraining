package com.chethan.akka.actors.actorsTyped.Demo

import akka.actor.Cancellable
import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import SchedulersAndTimers.StartTimer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
  * Created by $CapName on Feb 20, 2024.
  */


object SchedulersAndTimers {

  var timer: Cancellable = null

  def schedulers() = Behaviors.receive[SchedulersAndTimersSpecs]{
    (context, message) =>


      message match {
        case StartTimer =>
          timer = context.system.scheduler.scheduleWithFixedDelay(0.1 second, 1 second){
            () => println("Timer is running")
          }
          context.scheduleOnce(5 seconds, context.self, Cancel)
          Behaviors.same
        case Cancel     =>
          println("Timer has been stopped")
          timer.cancel()
          Behaviors.same

      }
  }

  val key = "abc123"

  def timers: Behavior[String] = Behaviors.withTimers[String]{
    timerScheduler =>
      timerScheduler.startSingleTimer(key, "hello", 10 seconds)
      timerScheduler.startSingleTimer(key, "another message", 10 seconds) // Old timer will be cancelled
      timerScheduler.cancel(key)
      Behaviors.same
  }


  trait SchedulersAndTimersSpecs

  case object StartTimer extends SchedulersAndTimersSpecs

  case object Cancel extends SchedulersAndTimersSpecs

}


object SchedulersAndTimersDemo extends App {

  val timer = ActorSystem(SchedulersAndTimers.schedulers(), "SchedulersAndTimersDemo")
  timer ! StartTimer

}
