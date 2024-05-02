package com.chethan.akka.classicActor.test

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.chethan.akka.classicActor.test.MasterSlaveTesting._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

/**
  * Created by $CapName on Nov 03, 2023.
  */

class MasterSlaveTesting extends TestKit(ActorSystem("MasterSlave")) with AnyWordSpecLike with ImplicitSender with BeforeAndAfterAll {
  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  "MasterSlaveTesting" should {
    val actorSystem = ActorSystem("MasterSlaveTesting")
    "register a slave actor" in {

      val master = actorSystem.actorOf(Props[Master])
      val slave  = TestProbe("slave")
      master ! Register(slave.ref)
      expectMsg(RegistrationAck)
    }

    "send the work to the slave" in {
      val master = actorSystem.actorOf(Props[Master])
      val slave  = TestProbe("slave")
      master ! Register(slave.ref)
      expectMsg(RegistrationAck)

      val workLoadString = "I love akka"
      master ! Work(workLoadString)
      slave.expectMsg(SlaveWork(workLoadString, testActor))
      slave.reply(WorkCompleted(3, testActor))
      expectMsg(Report(3))
    }

    " " in {
      val master = actorSystem.actorOf(Props[Master])
      val slave  = TestProbe("slave")
      master ! Register(slave.ref)
      expectMsg(RegistrationAck)

      val workLoadString = "I love akka"
      master ! Work(workLoadString)

      master ! Work(workLoadString)
      master ! Work(workLoadString)
      slave.receiveWhile(){
        case SlaveWork(`workLoadString`, `testActor`) => slave.reply(WorkCompleted(3, testActor))
      }


      expectMsg(Report(3))
      expectMsg(Report(6))
      //      expectMsg(Report(9))

    }


  }


}


object MasterSlaveTesting {


  class Master extends Actor {
    override def receive: Receive = {
      case Register(slaveRef) =>
        sender() ! RegistrationAck
        context.become(registerted(slaveRef, 0))
      case _                  => ()

    }

    def registerted(slaveRef: ActorRef, totalWordCount: Int): Receive = {
      case Work(text)                              => slaveRef ! SlaveWork(text, sender())
      case WorkCompleted(count, originalRequester) =>

        val newCount = totalWordCount + count
        originalRequester ! Report(newCount)
        context.become(registerted(slaveRef, newCount))

    }
  }

  class Slave extends Actor {
    override def receive: Receive = ???
  }

  case class Register(slaveRef: ActorRef)

  case class SlaveWork(text: String, originalRequester: ActorRef)

  case class Work(text: String)

  case class WorkCompleted(count: Int, originalRequester: ActorRef)

  case class Report(totalCount: Int)

  case object RegistrationAck

}