package com.chethan.akka.actors.classicActor

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by $CapName on Oct 19, 2023.
  */

object CounterChangingBehaviour extends App {

  val INCREMENT = "INCREMENT"
  val DECREMENT = "DECREMENT"
  val COUNT     = "COUNT"

  class UpdatedCounter extends Actor {

    override def receive: Receive = counterReceive(0)

    def counterReceive(count: Int): Receive = {
      case INCREMENT => println(s"Incrementing count - $count"); context.become(counterReceive(count + 1),false)
      case DECREMENT => println(s"decrementing count - $count"); context.become(counterReceive(count - 1),false)
      case COUNT     => println(s"the current count is $count")
    }
  }


  val actorSystem = ActorSystem("counter")
  val counter     = actorSystem.actorOf(Props[UpdatedCounter], "counter")

//  counter ! INCREMENT
//  counter ! INCREMENT
//  counter ! INCREMENT
//  counter ! COUNT
//  counter ! DECREMENT
//  counter ! DECREMENT
//  counter ! COUNT


  (1 to 100000000).foreach(_ => counter ! INCREMENT)
  (1 to 10000).foreach(_ => counter ! DECREMENT)
  counter ! COUNT


//  actorSystem.terminate()
}
