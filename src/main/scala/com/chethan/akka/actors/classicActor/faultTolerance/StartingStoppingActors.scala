package com.chethan.akka.actors.classicActor.faultTolerance

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Kill, PoisonPill, Props, Terminated}

/**
  * Created by $CapName on Nov 09, 2023.
  */

object StartingStoppingActors extends App {

  val actorSystem = ActorSystem("StartingStopping")

  case class StartChild(name: String)

  case class StopChild(name: String)

  case object Stop


  class Parent extends Actor with ActorLogging {
    override def receive: Receive = withChildren(Map.empty)

    def withChildren(children: Map[String, ActorRef]): Receive = {
      case StartChild(name) => log.info("[Starting child] - " + name)
        context.become(withChildren(children + (name -> context.actorOf(Props[Child], name))))

      case StopChild(name) => log.info("[Stopping child] - " + name)
        val childOption = children.get(name)
        childOption.foreach(childRef => context.stop(childRef))

      case Stop =>
        println("Stopping myself")
        context.stop(self)

      case message => println(message.toString)
    }
  }

  class Child extends Actor with ActorLogging {
    override def receive: Receive = {
      case PoisonPill => println("I have been saved")
      case message    => log.info(message.toString)
    }
  }


  val parent = actorSystem.actorOf(Props[Parent], "parent")
  //  parent ! StartChild("child1")
  //  val child  = actorSystem.actorSelection("user/parent/child1")
  //  child ! "this is a message "
  //  parent ! StopChild("child1")
  //  (1 to 50).foreach(a =>  child ! "this is a message "+a)

  //  parent ! StartChild("child2")
  //  val child2 = actorSystem.actorSelection("user/parent/child2")
  //  child2 ! "this is a message "
  //  parent ! Stop
  //  (1 to 50).foreach{a => parent ! "this is parent actor "+a}
  ////  parent ! StopChild("child2")
  //
  //  (1 to 100).foreach{a => child2 ! "this is a message "+a}


  //  parent ! StartChild("child3")
  //  val child3 = actorSystem.actorSelection("user/parent/child3")
  //  child3 ! PoisonPill
  //  (1 to 4).foreach(_ =>  child3 ! ("this message is for child 3 "+ _ ))

  //  parent ! StartChild("child4")
  //  val child4 = actorSystem.actorSelection("user/parent/child4")
  //  child4 ! Kill
  //  (1 to 4).foreach(_ => child4 ! ("this message is for child 3 " + _))


  class Watcher extends Actor {
    var child: ActorRef = null

    override def receive: Receive = {


      case msg: String      => println("unwatching"); context.watch(child)
      case Terminated(ref)  => println(s"this actor $ref has been stopped")
      case StartChild(name) => println("creating child => " + name)
        child = context.actorOf(Props[Child], name)
        println(child)
//        context.watch(child)
    }
  }


  val watcher = actorSystem.actorOf(Props[Watcher], "watcher")



  //Dead Watch
  watcher ! StartChild("watcherChild")
  val watcherChild = actorSystem.actorSelection("user/watcher/watcherChild")
  watcherChild ! PoisonPill
  //Watching can be done on any type of actor not only on child.
  //Terminated message will be received if the context.watch() is called even after the actor is dead.
  Thread.sleep(5000)
  watcher ! "huhuhuh"
  //  watcherChild ! Kill
//  watcherChild ! Kill



}
