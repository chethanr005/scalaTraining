package com.chethan.akka.actors.classicActor.supervision

import akka.actor.SupervisorStrategy.{Directive, Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, AllForOneStrategy, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import akka.testkit.{EventFilter, ImplicitSender, TestKit}
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}

/**
  * Created by $Chethan on Dec 26, 2023.
  */

class SuperVisionStrategy extends TestKit(ActorSystem()) with AnyWordSpecLike with BeforeAndAfterAll with ImplicitSender with BeforeAndAfterEach {

  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)


  "SuperVisionStrategy" should {

    "1" in {
      val supervisor = system.actorOf(Props[SuperVisor], "supervisor")
      val props      = Props[ThrowEx]
      supervisor ! props
      val throwEx = expectMsgType[ActorRef]
      throwEx ! 1
      expectMsg(10)
    }

    "stop" in {
      val supervisor = system.actorOf(Props[SuperVisor], "supervisor")
      val props      = Props[ThrowEx]
      supervisor ! props
      val throwEx = expectMsgType[ActorRef]

      //            watch(throwEx)                                //Way 1
      //      throwEx ! 2
      //      expectTerminated(throwEx)


      //      throwEx ! 2
      //      throwEx ! 1
      //      expectNoMessage()
      //      val excep = intercept[Exception](TestActorRef[ThrowEx](throwEx, "any"))
      //      assert(excep.isInstanceOf[Exception])
      //      assertResult(excep.getMessage)("cannot reserve actor name 'any': already terminated")

      watch(throwEx) //Way 3
      throwEx ! 2
      val terminated = expectMsgType[Terminated]
      assert(terminated.actor == throwEx)
      throwEx ! 6

    }

    "restart" in {
      val supervisor = system.actorOf(Props[SuperVisor], "supervisor")
      val props      = Props[ThrowEx]
      supervisor ! props
      val throwEx = expectMsgType[ActorRef]
      throwEx ! 1
      expectMsg(10)

      throwEx ! 6
      throwEx ! 1
      expectMsg(16)

      throwEx ! 6
      throwEx ! 1
      expectMsg(22)

      //      val actualEx = TestActorRef[ThrowEx](throwEx, "any")
      //      assert(actualEx.underlyingActor.balance == 1) // It must be 22 right? Why 10?

            throwEx ! 3
            throwEx ! 1
            expectMsg(10)
    }

    "Resume" in {
      val supervisor = system.actorOf(Props[SuperVisor], "supervisor")
      val props      = Props[ThrowEx]
      supervisor ! props
      val throwEx = expectMsgType[ActorRef]
      throwEx ! 1
      expectMsg(10)

      throwEx ! 6
      throwEx ! 1
      expectMsg(16)

      throwEx ! 5
      throwEx ! 1
      expectMsg(16)


      throwEx ! 6
      throwEx ! 1
      expectMsg(22)

      throwEx ! 5
      throwEx ! 1
      expectMsg(22)

      //      val actualEx = TestActorRef[ThrowEx](throwEx, "any")
      //      assert(actualEx.underlyingActor.balance == 1) // It must be 22 right? Why 10?
    }

    "Escalate" in {
      val supervisor = system.actorOf(Props[SuperVisor], "supervisor")
      val props      = Props[ThrowEx]
      supervisor ! props
      val throwEx = expectMsgType[ActorRef]
      watch(throwEx)
      throwEx ! 4
      val terminated = expectMsgType[Terminated]
      println(terminated.actor.path)
      assert(terminated.actor == throwEx)

    }
  }

  "No Death super visior" should {
    "not kill any children" in {
      OneForOneStrategy
      val supervisor = system.actorOf(Props[NoDeathSupervisor], "supervisor")
      val props      = Props[ThrowEx]
      supervisor ! props
      val throwEx = expectMsgType[ActorRef]
      watch(throwEx)
      throwEx ! 4
      throwEx ! 1
      expectMsg(10)
    }

    "will kill all the children if the supervisor didnt override preRestart() method" in {
      val supervisor = system.actorOf(Props[NoDeathSupervisor], "supervisor")
      val props      = Props[ThrowEx]
      supervisor ! props
      val throwEx = expectMsgType[ActorRef]
      watch(throwEx)
      throwEx ! 4
      val terminated = expectMsgType[Terminated]
      println(terminated.actor.path)
      assert(terminated.actor == throwEx)
    }
  }

  "All for one strategy" should {
    "OFOS 1" in {
      val supervisor = system.actorOf(Props[SuperVisor], "supervisor")
      val props      = Props[ThrowEx]

      supervisor ! props
      val throwEx1 = expectMsgType[ActorRef]
      watch(throwEx1) //Way 3
      throwEx1 ! 2
      val terminated1 = expectMsgType[Terminated]
      assert(terminated1.actor == throwEx1)

      supervisor ! props
      val throwEx2 = expectMsgType[ActorRef]
      watch(throwEx2) //Way 3
      throwEx2 ! 2
      val terminated2 = expectMsgType[Terminated]
      assert(terminated2.actor == throwEx2)
    }

    "OFOS 2" in {
      val supervisor = system.actorOf(Props[SuperVisor], "supervisor")
      val props      = Props[ThrowEx]

      supervisor ! props
      val throwEx1 = expectMsgType[ActorRef]
      watch(throwEx1) //Way 3
      throwEx1 ! 2
      val terminated1 = expectMsgType[Terminated]
      assert(terminated1.actor == throwEx1)

      supervisor ! props
      val throwEx2 = expectMsgType[ActorRef]
      watch(throwEx2) //Way 3
      val terminated2 = expectMsgType[Terminated]
      assert(terminated2.actor == throwEx2)
    }
    "AFOS" in {
      val supervisor = system.actorOf(Props[AllForOneSupervisor], "supervisor")
      val props      = Props[ThrowEx]

      supervisor ! props
      val throwEx1 = expectMsgType[ActorRef]

      supervisor ! props
      val throwEx2 = expectMsgType[ActorRef]


      watch(throwEx1) //Way 3
      throwEx1 ! 2
      val terminated1 = expectMsgType[Terminated]
      assert(terminated1.actor == throwEx1)

      watch(throwEx2) //Way 3
      val terminated2 = expectMsgType[Terminated]
      assert(terminated2.actor == throwEx2)
    }

    "AFOS 2" in {
      val supervisor = system.actorOf(Props[AllForOneSupervisor], "supervisor")
      val props      = Props[ThrowEx]

      supervisor ! props
      val throwEx1 = expectMsgType[ActorRef]

      supervisor ! props
      val throwEx2 = expectMsgType[ActorRef]


      throwEx1 ! 6
      throwEx1 ! 1
      expectMsg(16)

      throwEx2 ! 6
      throwEx2 ! 1
      expectMsg(16)

      throwEx1 ! 3
      Thread.sleep(500)
      throwEx2 ! 1
      expectMsg(10)

    }
  }

}

class NoDeathSupervisor extends SuperVisor {
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = ()
}

class AllForOneSupervisor extends SuperVisor {
  override def supervisorStrategy: SupervisorStrategy = AllForOneStrategy()(decider)
}

class SuperVisor extends Actor {

  val decider: PartialFunction[Throwable, Directive] = {
    case _: NullPointerException  => Escalate
    case _: IllegalStateException => Restart
    case _: RuntimeException      => Resume
    case _: Exception             => Stop
  }

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy()(decider)


  override def receive: Receive = {
    case props: Props =>
      val ac = context.actorOf(props)
      sender() ! ac
    case 123          => throw new NullPointerException("kokokokokokkoko")
  }
}

class ThrowEx extends Actor with ActorLogging {

  var balance = 10

  override def receive: Receive = {
    case 1 => sender() ! balance
    case 2 => throw new Exception("1")
    case 3 => throw new IllegalStateException("2")
    case 4 => throw new NullPointerException("3")
    case 5 => throw new RuntimeException("4")
    case 6 => balance += 6
  }
}
