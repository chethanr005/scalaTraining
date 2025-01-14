package com.chethan.akka.actors.actorsTyped.Demo

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import ChildActorsHandlingDemo.{ChildSpecs, Initialize, SendMessage}
import RelationShip.Parent

/**
  * Created by $CapName on Feb 15, 2024.
  */

object RelationShip {
  object Parent { //2
    def apply(): Behavior[ChildSpecs] = {
      Behaviors.receive{ (context, message) =>

        message match {
          case Initialize(noOfChildren) =>
            context.log.info(s"Initialized $noOfChildren children")
            val children = (1 to noOfChildren).map(a => context.spawn(Child(), s"Child_${a}")).toList
            initialize(children)
        }
      }
    }

    def initialize(childrenList: List[ActorRef[String]]): Behavior[ChildSpecs] = {
      Behaviors.receive[ChildSpecs]{ (context, message) =>

        message match {
          case SendMessage(message) => childrenList.head ! message
            initialize(childrenList.tail :+ childrenList.head)
        }
      }
    }
  }
}


object Child {  //1
  def apply(): Behaviors.Receive[String] = {
    Behaviors.receive[String]{ (context, message) =>
      context.log.info(s"[${context.self.path.name}] : I got this message - $message")
      Behaviors.same
    }
  }
}


object ChildActorsHandlingDemo extends App {
  def apply() = {
    Behaviors.setup[Unit]{ context =>

      val parent = context.spawn(Parent(), "parent")
      parent ! Initialize(10)
      (1 to 50).foreach(a => parent ! SendMessage(s"Message_${a}"))
      Behaviors.same
    }
  }

  ActorSystem(apply(), "demo")


  trait ChildSpecs

  case class Initialize(children: Int) extends ChildSpecs

  case class CreateChild(name: String) extends ChildSpecs

  case class SendMessage(message: String) extends ChildSpecs

}
