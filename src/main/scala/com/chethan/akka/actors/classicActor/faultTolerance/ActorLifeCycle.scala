package com.chethan.akka.actors.classicActor.faultTolerance

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Kill, Props}

/**
  * Created by $Chethan on Dec 05, 2023.
  */

object ActorLifeCycle extends App {

  object StartChild

  class LifeCycleActor extends Actor {

    override def preStart(): Unit = {
      println("I am starting " + self.path)
    }

    override def postStop(): Unit = {
      println("I am stopping " + self.path)
    }

    override def receive: Receive = {
      case StartChild => context.actorOf(Props[LifeCycleActor], "child")
    }
  }


  val actorSystem = ActorSystem("ActorLifeCycle")

  //    val actor = actorSystem.actorOf(Props[LifeCycleActor], "parent")
  //    actor ! StartChild
  //    actor ! Kill


  object Fail

  object FailChild

  class Parent extends Actor {

    val child = context.actorOf(Props[Child], "child")

    override def receive: Receive = {
      case FailChild => child ! Fail
    }
  }

  class Child extends Actor with ActorLogging {


    override def preStart(): Unit = println("pre start" + " " + self.path)

    override def postStop(): Unit = println("post stop " + " " + self.path)


    override def receive: Receive = {
      case Fail => log.warning("I am about to die")
        throw new Exception("I died")
    }
  }

  val supervisor = actorSystem.actorOf(Props[Parent], "superparent")
  supervisor ! FailChild
  //  supervisor ! FailChild

}

object Practice extends App {

  object CreateSub

  object FailSub

  class Super extends Actor with ActorLogging {

    override def preStart(): Unit = log.info(s"${context.self} => I am calling Pre Start")

    override def postStop(): Unit = log.info(s"${context.self} => I am calling Post Stop")


    override def preRestart(reason: Throwable, message: Option[Any]): Unit = log.info(s"${context.self} => I am calling Pre Restart")

    override def postRestart(reason: Throwable): Unit = log.info(s"${context.self} => I am calling Post Restart")


    var sub: ActorRef = null

    override def receive: Receive = {
      case CreateSub =>
        sub = context.actorOf(Props[Sub], "subClass")
      case FailSub   => throw new Exception("Intentionally failed")
    }

  }

  class Sub extends Actor with ActorLogging {

    override def preStart(): Unit = log.info(s"${context.self} => I am calling Pre Start")

    override def postStop(): Unit = log.info(s"${context.self} => I am calling Post Stop")

    override def preRestart(reason: Throwable, message: Option[Any]): Unit = log.info(s"${context.self} => I am calling Pre Restart")

    override def postRestart(reason: Throwable): Unit = log.info(s"${context.self} => I am calling Post Restart")

    override def receive: Receive = {
      case FailSub => log.info(s"${context.self} => I am failing")
        throw new Exception("I Failed")
    }
  }

  val actorSystem = ActorSystem("PrePost")
  val superClass  = actorSystem.actorOf(Props[Super], "superclass")
  superClass ! CreateSub
  superClass ! FailSub
  superClass ! FailSub
  superClass ! Kill
  //  superClass ! CreateSub
}