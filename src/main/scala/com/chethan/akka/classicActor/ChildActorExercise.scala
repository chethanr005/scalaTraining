package com.chethan.akka.classicActor

import java.util.concurrent.Executors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext

/**
  * Created by $CapName on Oct 26, 2023.
  */

object ChildActorExercise extends App {


  case class Initializer(children: Int)

  case class Task(message: String)

  case class SlaveReply(message: String)


  //  Master-Slave JK flipflop
  //    class Master extends Actor {
  //
  //      var slavesGroup: List[ActorRef] = null
  //
  //      override def receive: Receive = {
  //
  //        case Initializer(slaveCount: Int) => {
  //          slavesGroup = (1 to 10).map(n => context.actorOf(Props[Slave], "slave" + n)).toList
  //          context.become(workAssigner(slavesGroup))
  //        }
  //
  //          def workAssigner(slaves: List[ActorRef]): Receive = {
  //
  //            case Task(message) =>
  //              val slaveRef = slaves.head
  //              println(s"Assigning the task to slave ${slaveRef.path}")
  //              slaveRef ! message
  //              if (slaves.tail.isEmpty) context.become(workAssigner(slavesGroup))
  //              else context.become(workAssigner(slaves.tail))
  //
  //            case SlaveReply(message) =>
  //              println("My Behaviour :- " + slaves.head.path)
  //              println(message)
  //          }
  //      }
  //    }
  //
  //    class Slave extends Actor {
  //      override def receive: Receive = {
  //        case message: String => sender() ! SlaveReply(s"Hello Master. I ${self.path} will accept the given task")
  //      }
  //    }

  //  class Master extends Actor {
  //
  //    override def receive: Receive = {
  //
  //      case Initializer(slaveCount: Int) => {
  //        val slavesGroup = List(1 to 10).map(n => context.actorOf(Props[Slave], "slave" + n))
  //        context.become(workAssigner(slavesGroup, slavesGroup))
  //      }
  //
  //        def workAssigner(availableSlaves: List[ActorRef], slavesGroup: List[ActorRef]): Receive = {
  //
  //          case Task(message) =>
  //            val slaveRef = availableSlaves.head
  //            println(s"Assigning the task to slave ${slaveRef.path}")
  //            slaveRef ! message
  //            if (availableSlaves.tail.isEmpty) context.become(workAssigner(slavesGroup, slavesGroup))
  //            else context.become(workAssigner(availableSlaves.tail, slavesGroup))
  //
  //          case SlaveReply(message) =>
  //            println("My Behaviour :- " + slavesGroup.head.path)
  //            println(message)
  //        }
  //    }
  //  }
  //
  //  class Slave extends Actor {
  //    override def receive: Receive = {
  //      case message: String => sender() ! SlaveReply(s"Hello Master. I ${self.path} will accept the given task")
  //    }
  //  }

  class Master extends Actor {

    override def receive: Receive = {

      case Initializer(slaveCount: Int) => {
        val slavesGroup: List[ActorRef] = (1 to slaveCount).map(n => context.actorOf(Props[Slave], "slave" + n)).toList
        context.become(workAssigner(slavesGroup, slavesGroup))
      }

        def workAssigner(availableSlaves: List[ActorRef], slavesGroup: List[ActorRef]): Receive = {

          case Task(message) =>
            println(self)
            val slaveRef = availableSlaves.head
            println(s"Assigning the task to slave ${slaveRef.path}")
            Thread.sleep(1000)
            slaveRef ! message
            if (availableSlaves.tail.isEmpty) context.become(workAssigner(slavesGroup, slavesGroup))
            else context.become(workAssigner(availableSlaves.tail, slavesGroup))

          case SlaveReply(message) =>
            println("My Behaviour :- " + availableSlaves.head.path)
            println(message)
        }
    }
  }

  class Slave extends Actor {
    override def receive: Receive = {
      case message: String =>
        println(sender())
        sender() ! SlaveReply(s"Hello Master. I ${self.path} will accept the given task => $message")
    }
  }


  val executor         = Executors.newFixedThreadPool(10)
  val executionContext = ExecutionContext.fromExecutor(executor)
  //  val actorSystem = ActorSystem("ComplexExercise", defaultExecutionContext = Some(executionContext))

  val actorSystem = ActorSystem("ComplexExercise")
  val master      = actorSystem.actorOf(Props[Master], "Master")

  master ! Initializer(10)
  Thread.sleep(100)
  master ! Task("do anything")
//  Thread.sleep(10)
  master ! Task("run run run run run run run run run")
        (1 to 20).foreach{ _ => master ! Task("do anything") }
}


