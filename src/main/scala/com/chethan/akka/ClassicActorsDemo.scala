package com.chethan.akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * Created by Chethan on Sep 19, 2023.
  */

object ClassicActorsDemo extends App {


  //Instantiation
  val actorSystem = ActorSystem.apply("Custom_Actors")
  val actor1      = actorSystem.actorOf(Props[Actor10])
  //  actor1 ! ""


  //properties
  //1. messages handler will work asynchronously.
  //2. guaranteed that message will be delivered. But only once
  //3. message will be delivered in the same order as it was published.


  val kidActor = actorSystem.actorOf(Props[Kid], "kid")

  val momActor = actorSystem.actorOf(Props[Mom], "mom")


  momActor ! kidActor
}

//Declaration
class Actor10 extends Actor {
  override def receive: PartialFunction[Any, Unit] = {
    case message: String => println("received message")
      // self messaging
      self ! Map()
    case _               => println("received Any")
  }
}

class Mom extends Actor {
  override def receive: Receive = {

    // actorReply
    case actor: ActorRef => println(s"I received this actor => $actor")
      println(sender())
      actor ! ""
    case _               => Thread.sleep(100); println("this is the default")
  }
}

class Kid extends Actor {
  override def receive: Receive = {
    case mes: String => println("this is the message kid received")
      println(sender())
    case _           => println("Kid received default message")
  }
}


class ActorWay3(a: Int, b: String) extends Actor {
  override def receive: Receive = ???
}

object ActorWay3 {

  def getThirdActor() = {

    Props(new ActorWay3(1, "1"))
  }

}

object Dummy extends App {


  def anyMethod(abc: SuperClass): Unit = {

    println(abc.getClass)
  }

  val superClass = new SuperClass
  val subClaSS   = new SubClass

  anyMethod(superClass:SuperClass)


  class SuperClass

  class SubClass extends SuperClass

}


