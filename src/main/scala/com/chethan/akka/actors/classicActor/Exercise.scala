package com.chethan.akka.actors.classicActor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.util.Random

/**
  * Created by $CapName on Oct 27, 2023.
  */

object Exercise extends App {
  case class Initializer(children: Int)

  case class Task(message: String)

  case class SlaveReply(message: String)

  class Master extends Actor {


    override def receive: Receive = {

      case Initializer(slaveCount: Int) => {
        val slavesGroup: List[ActorRef] = (1 to slaveCount).map(n => context.actorOf(Props[Slave], "slave" + n)).toList
        context.become(workAssigner(slavesGroup))
      }
    }

    def workAssigner(slavesGroup: List[ActorRef]): Receive = {

      case Task(message) => if (slavesGroup.isEmpty) {
        println("Waiting for a worker to get freedom")
        Thread.sleep(200)
        context.self ! Task(message)
      }
                            else {
                              val slave = slavesGroup.head
                              println("Assigning work to this slave => " + slave.path)
                              slave ! message
                              context.become(workAssigner(slavesGroup.tail))
                            }


      case SlaveReply(message) =>
        println(slavesGroup.size)
        println(message)
        context.become(workAssigner(slavesGroup :+ sender()))
    }
  }


  class Slave extends Actor {
    override def receive: Receive = {
      case message: String =>
        val time = Random.nextInt(1999)
        Thread.sleep(time)
        sender() ! SlaveReply(s"Hello Master. I ${self.path} will accept the given task => $message")
    }
  }

  val actorSystem = ActorSystem.create("ComplexExercise")
  val master      = actorSystem.actorOf(Props[Master], "Master")

  master ! Initializer(3)
  Thread.sleep(100)
  master ! Task("do anything")
  (1 to 20).foreach{ _ => master ! Task("do anything") }
}
