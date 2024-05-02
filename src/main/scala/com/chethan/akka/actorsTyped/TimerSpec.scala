package com.chethan.akka.actorsTyped

import akka.actor.testkit.typed.scaladsl.{ManualTime, ScalaTestWithActorTestKit}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.chethan.akka.actorsTyped.TimerSpec.{Command, Reporter,Report}
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.duration._

/**
  * Created by $Chethan on Jan 24, 2024.
  */

class TimerSpec extends ScalaTestWithActorTestKit(ManualTime.config) with AnyWordSpecLike {

  "A reporter" should {
    "trigger report in an hour" in {
      val probe = testKit.createTestProbe[Command]()

      val time: ManualTime = ManualTime()

      testKit.spawn(Reporter(probe.ref))
      probe.expectNoMessage(1 seconds)
      time.timePasses(1 hour)
      probe.expectMessage(Report)

    }
  }

}

object TimerSpec {

  trait Command

  case object Timeout extends Command

  case object Report extends Command

  object Reporter {

    def apply(destination: ActorRef[Command]): Behavior[Command] = Behaviors.withTimers{
      timer =>
        timer.startSingleTimer(Timeout, 10 seconds)

        Behaviors.receiveMessage{
          case Timeout => destination ! Report
            Behaviors.same
        }
    }
  }

}
