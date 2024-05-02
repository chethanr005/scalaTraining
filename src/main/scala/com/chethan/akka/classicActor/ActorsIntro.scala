package com.chethan.akka.classicActor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.chethan.akka.classicActor.ActorsIntro.SayHiTo

/**
  * Created by Chethan on Mar 28, 2023.
  */

object ActorsIntro extends App {

  val actorSystem = ActorSystem("FirstActorSystem")
  println(actorSystem)


  val actor : ActorRef = actorSystem.actorOf(Props[FirstActorClass], "FirstActor")
  val actor1: ActorRef = actorSystem.actorOf(Props[FirstActorClass], "SecondActor")

  actor ! "hello world"
  actor ! "hello earth"
  actor1 ! "hello alllll"
  actor.tell("hello msg", actor)
  // actor ! 100

  actor
  // val a = new FirstActorClass

  val createdActor = actorSystem.actorOf(Props(new FirstActorClass))
  createdActor ! "this is a created actor"
  actorSystem.actorOf(FirstActorClass.props)

  val alice = actorSystem.actorOf(Props[FirstActorClass], "alice")
  val bob   = actorSystem.actorOf(Props[FirstActorClass], "bob")

  case class SayHiTo(actorRef: ActorRef)

  alice ! SayHiTo(bob)

  alice ! "hiii"

  alice ! SelfMessage("testing self message")
  alice ! new Object()
  alice ! "null"


}

class FirstActorClass extends Actor {

  override def receive: PartialFunction[Any, Unit] = {
    case "null"         => sender() ! "this is a null message"
    case "hiii"         => println(context.sender().path.name)
    case msg: String    => println("this is the actual message " + msg)
    case SelfMessage(a) => self ! a
    case SayHiTo(ref)   => println(self.path.name + " is Saying hi to " + ref.path.name)
    case msg            => println("this message cannot be read" + msg.toString)
    //    case _              => self ! "this is a self message"
    case _ => sender()
  }

  println(context.self)
  println(self)
  self ! "this is a self message"
  println(context.sender())
}

object FirstActorClass {
  def props = {
    Props(new FirstActorClass)
  }
}


case class SelfMessage(msg: String)

object Execut extends App {

  val actorSystem = ActorSystem.create("new4ctor8ystem")

  val actor1 = actorSystem.actorOf(Props[Actor1], "actor1")
  println(actor1.toString())

  val actor2 = actorSystem.actorOf(Props(new Actor1), "actor2")
  println(actor2.toString())

  val actor3 = actorSystem.actorOf(Actor1.props, "actor3")
  println(actor3)

  println(actorSystem.name)
  println(actorSystem.startTime)
  println(actorSystem.deadLetters)
  println(actorSystem.child("child"))
  println(actorSystem)
}

class Actor1 extends Actor {
  override def receive: Receive = {
    case message: String => println(message)
    case num: Int        => println(num)
  }
}

object Actor1 {
  def props = {
    Props(new Actor1)
  }
}


