package com.chethan.akka.classicActor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * Created by $CapName on Oct 23, 2023.
  */

object ChildActors extends App {

  def pleaseWait = Thread.sleep(100)

  case class CreateChild(name: String)

  case class TellChild(message: String)

  class Parent extends Actor {
    override def receive: Receive = {
      case CreateChild(name) =>
        println("******** Creating child.........")
        val child = context.actorOf(Props[Child], name)
        context.become(withChild(child))

      case anyOther => println("404 error >>> Wrong way")
    }

    def withChild(child: ActorRef): Receive = {
      case TellChild(message) => child forward message
    }
  }

  class Child extends Actor {
    override def receive: Receive = {
      case message: String => println("I got this message :- " + message)
      case anything        => println("Nothing to do with the child")
    }
  }


  val actorSystem = ActorSystem("ParentChildRelationShip")

  val parent = actorSystem.actorOf(Props[Parent], "ParentOne")
  val child  = actorSystem.actorOf(Props[Child], "ChildOne")

  parent ! CreateChild("Json")
  pleaseWait

  parent ! TellChild("hello json")
  pleaseWait

  child ! new Object
  pleaseWait

  child ! "Hiii kiddo"
  pleaseWait


  val selectedParent = actorSystem.actorSelection("/user/ParentOne")
  selectedParent ! new Object
  pleaseWait

  val selectedChild = actorSystem.actorSelection("/user/ParentOne/Json")
  selectedChild ! "Yay! I got selected"

  val jsonChild = actorSystem.actorSelection("user/ChildOne")
  jsonChild ! "Hello i am json"
}
