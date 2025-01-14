package com.chethan.akka.actors.classicActor.test

import akka.actor.{Actor, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import TimerTest.{NoTask, Task, TimeActor}
import com.typesafe.config.ConfigFactory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.duration._
import scala.util.Random


/**
  * Created by $CapName on Nov 06, 2023.
  */

class TimerTest extends TestKit(ActorSystem("jiji", ConfigFactory.load().getConfig("specialAkkaTimeConfig"))) with AnyWordSpecLike with ImplicitSender with BeforeAndAfterAll {
  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  "Time test" should {
    val actorSystem = ActorSystem("TimeTester")
    "time actor" in {
      val actor = actorSystem.actorOf(Props[TimeActor])
      actor ! Task
      within(1000 millis, 1100 millis)(expectMsg("1000"))
    }

    "time actor 2" in {
      val actor = actorSystem.actorOf(Props[TimeActor])
      //      actor ! Task
      actor ! NoTask
      Thread.sleep(1000)
      within(10 millis){
        val result = receiveWhile(1000 millis, 100 millis, 100){
          case res => res
        }
        assert(result.size == 10)
      }
    }

    "using test probe as implicit sender pass" in {
      val actor = actorSystem.actorOf(Props[TimeActor])
      within(1000 millis){
        // Manually Custom Config is passed to Testkit and time out is set to 0.3 secs

        val testProbe = TestProbe()
        testProbe.send(actor, Task)
        testProbe.expectMsg("1000")
      }
    }

    "using test probe as implicit sender fail" in {
      val actor = actorSystem.actorOf(Props[TimeActor])
      within(100 millis){
        // Manually Custom Config is passed to Testkit and time out is set to 0.3 secs

        val testProbe = TestProbe()
        testProbe.send(actor, Task)
       val result = intercept[Throwable]( testProbe.expectMsg("1000"))
        assert(result.isInstanceOf[Exception])
      }
    }
  }
}

object TimerTest {

  case object Task

  case object NoTask

  class TimeActor extends Actor {
    override def receive: Receive = {
      case Task   =>
        println("Tasking")
        Thread.sleep(1000)
        sender() ! "1000"
      case NoTask =>
        for (i <- 1 to 10) {
          println("No Tasking")
          val int = Random.nextInt(100)
          Thread.sleep(int)
          sender() ! "100"
        }
    }
  }
}

