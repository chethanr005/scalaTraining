package com.chethan.akka.classicActor

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by $CapName on Oct 17, 2023.
  */

class Counter extends Actor {

  var counter = 0

  override def receive: Receive = {
    case Increment => counter += 1
    case Decrement => counter += -1
    case Count     => println(counter)
    case _         => println("unknown error")
  }
}

case object Increment

case object Decrement

case object Count

object Application extends App {
  val actorSystem = ActorSystem("counterActorSystem")
  val counter     = actorSystem.actorOf(Props[Counter], "counter")
  counter ! Increment
  counter ! Increment
  counter ! Increment
  counter ! Increment
  counter ! Count
  counter ! Increment
  counter ! Decrement
  counter ! Decrement
  counter ! Decrement
  counter ! Count
  actorSystem.terminate()
}



