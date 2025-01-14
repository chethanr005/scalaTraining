package com.chethan.akka.actors.classicActor.infrastucture

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, Timers}

import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Created by $Chethan on Jan 05, 2024.
  */

object TimerBasedActorDemo extends App {
  val acs = ActorSystem("timing")

  val timerActor = acs.actorOf(Props[TimerBasedHeartBeatActor], "HeartActor")
  Thread.sleep(3000)
  println("waiting")
      timerActor ! Intruder
    Thread.sleep(3000)
  timerActor ! "another"
  Thread.sleep(3000)
  timerActor ! Cancel


}


class TimerBasedHeartBeatActor extends Actor with ActorLogging with Timers {
  //Note:Start a timer that will send msg once to the self actor after the given timeout.
  timers.startSingleTimer(TimerKey, Intruder, 200 millis)



  timers.startPeriodicTimer(1, Start, 200 millis)

  override def receive: Receive = {
    case Start => log.info("Received a start message")


      timers.startTimerWithFixedDelay(2, Reminder, 1 second)

    case Intruder  => log.info("Unwanted message")
      timers.startPeriodicTimer(3, Reminder, 200 millis)
    case "any"     => log.info("any message")
    case "another" => timers.startPeriodicTimer(TimerKey, "any", 200 millis)

    //    case Restart  =>
    //    log.info("called restart")
    //      timers.cancelAll()
    case Cancel   => log.info("Received a stop message")
      timers.cancelAll()
    case Reminder => log.info("I have not killed the timer: Timer is still running")
  }
}

case object Intruder

case object TimerKey

case object Cancel

case object Restart

case object Start

case object Reminder

