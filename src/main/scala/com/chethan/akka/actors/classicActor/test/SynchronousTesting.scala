package com.chethan.akka.actors.classicActor.test

import akka.actor.{Actor, ActorSystem, Props}
import akka.testkit.{CallingThreadDispatcher, TestActorRef, TestProbe}
import SynchronousTesting.{Counter, Inc, Read}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.duration.Duration

/**
  * Created by $CapName on Nov 08, 2023.
  */

class SynchronousTesting extends AnyWordSpecLike with BeforeAndAfterAll {

  override protected def afterAll(): Unit = actorSystem.terminate()

  implicit val actorSystem = ActorSystem("SynchronousTesting")

  "Synchronous testing" should {
    "synchronous increase in counter" in {

      val counter = TestActorRef[Counter](Props[Counter])
      counter ! Inc
      counter ! Inc
      counter ! Inc
      counter ! Inc
      counter ! Inc
      assert(counter.underlyingActor.count == 5)

    }

    "synchronous increase its count at the call of receive function" in {

      val counter = TestActorRef[Counter](Props[Counter])
      counter.receive(Inc)
      counter.receive(Inc)
      assert(counter.underlyingActor.count == 2)

    }

    "work on the calling thread dispatcher" in {

      val counter = actorSystem.actorOf(Props[Counter].withDispatcher(CallingThreadDispatcher.Id))
      val slave   = TestProbe()
      slave.send(counter, Read)
      slave.expectMsg(Duration.Zero, 0)
      slave.send(counter, Inc)
      slave.send(counter, Read)
      slave.expectMsg(Duration.Zero, 1)

    }
  }


}

object SynchronousTesting {

  case object Inc

  case object Read

  class Counter extends Actor {
    var count = 0

    override def receive: Receive = {
      case Inc  => Thread.sleep(1000); count += 1
      case Read => sender() ! count
    }
  }
}