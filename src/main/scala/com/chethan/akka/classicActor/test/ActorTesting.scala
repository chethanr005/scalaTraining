package com.chethan.akka.classicActor.test

import akka.actor.{Actor, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.chethan.akka.classicActor.test.ActorTesting.LabTestActor
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.duration._

/**
  * Created by $CapName on Oct 31, 2023.
  */

class ActorTesting extends TestKit(ActorSystem("testAC")) with AnyWordSpecLike with ImplicitSender with BeforeAndAfterAll {

  val sleep = Thread.sleep(1000)

  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  "ActorTesting" should {
    val actorSystem = ActorSystem("actorSystem")
    "will receive message" in {
      val actor   = actorSystem.actorOf(Props[ActorTesting.TestActor])
      val message = "hello this is the test area"
      println(testActor.path)
      actor ! message
      expectMsg(message)
    }

    "will receive empty behaviour" in {
      val actor   = actorSystem.actorOf(Props[ActorTesting.DummyActor])
      val message = "hello this is the test area"
      println(testActor.path)
      actor.!(message)(testActor)
      expectNoMessage(3.6 seconds)
      sleep
    }
    "a lab test " in {

      val labTest = actorSystem.actorOf(Props[LabTestActor])
      labTest ! "i love akka"
      expectMsg("I LOVE AKKA")
      sleep

    }

    "lab test 2" in {
      val labTest = actorSystem.actorOf(Props[LabTestActor])
      labTest ! "i love akka"
      val expectedMessage = expectMsgType[String]
      assert(expectedMessage == "I LOVE AKKA")
      sleep
    }

    "lab test 3" in {
      val labTest = actorSystem.actorOf(Props[LabTestActor])
      labTest ! "SendAnything"
      expectMsgAnyOf[String]("this is an even number", "this an odd number")
      sleep
    }


    "lab test 4" in {
      val labTest = actorSystem.actorOf(Props[LabTestActor])
      labTest ! "Multi"
      expectMsg("OneMsg")
      expectMsg("TwoMsg")
      sleep
    }

    "lab test 5" in {
      val labTest = actorSystem.actorOf(Props[LabTestActor])
      labTest ! "Multi"
      expectMsgAllOf("ThirdOne")
      sleep
    }


    "lab test 6" in {
      val labTest = actorSystem.actorOf(Props[LabTestActor])
      labTest ! "Multi"
      val result = receiveN(3)
      println(result.size)
      sleep
    }

    "lab test 7 :- expect partial function" in {
      val labTest = actorSystem.actorOf(Props[LabTestActor])
      labTest ! "Multi"
      expectMsgPF(){
        case "OneMsg"   =>
        case "TwoMsg"   =>
        case "ThirdOne" =>
      }

    }
  }
}


object ActorTesting {

  class LabTestActor extends Actor {
    override def receive: Receive = {


      case "Multi"         =>
        sender() ! "OneMsg"
        sender() ! "TwoMsg"
        sender() ! "ThirdOne"
      case "SendAnything"  => sender ! (if (System.currentTimeMillis() % 2 == 0) "this is an even number" else "this an odd number")
      case message: String => sender ! message.toUpperCase
    }
  }

  class TestActor extends Actor {

    val as: ActorSystem = ActorSystem()
    val actor           = as.actorOf(Props[LabTestActor])

    //    override val sender:ActorRef = actor
    override def receive: Receive = {
      case message =>
        println(sender().path)
        sender() ! message


    }
  }


  class DummyActor extends Actor {
    override def receive: Receive = {
      println(sender().path);
      Actor.emptyBehavior
      // receiving dead letters as sender???? HOW
    }
  }

}
