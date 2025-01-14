package com.chethan.akka.actors.classicActor

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by Chethan on Aug 10, 2023.
  */

object BackToActors extends App {

  val actorSystem = ActorSystem("Acting_Company")
  println(actorSystem.name)


  class WorldClassActor extends Actor {
    //    override def receive: Receive = ???


    def receive: PartialFunction[Any, Unit] = {
      case dialogue: String => println("this is world class acting")
      case _                => println("I can not understand the dialogue")
    }


  }


  val worldClassActor = actorSystem.actorOf(Props[WorldClassActor], "WorldClassActor")

  worldClassActor ! "hello I am BSH"


  class Developer(noOfTasks: Int) extends Actor {
    def receive: PartialFunction[Any, Unit] = {
      case "Hi"       =>
        println(sender())
        sender() ! "hello there"
      case tasks: Int => println(s"I have $tasks no of tasks.")
      case _          => println("this task is not possible for me")
    }
  }

  val feDeveloper = actorSystem.actorOf(Props(new Developer(10)), "FEDeveloper")
  feDeveloper ! "10"

  object Developer {
    def getActor(tasks: Int): Props = {
      Props(new Developer(tasks))
    }
  }

  val beDeveloper = actorSystem.actorOf(Developer.getActor(10), "BE")
  beDeveloper ! 10

  beDeveloper ! new Object()

  beDeveloper ! "Hi"

  println("done done done")
System.exit(1)
}
