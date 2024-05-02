package com.chethan.akka.classicActor.infrastucture

import java.time.LocalDateTime

import akka.actor.{Actor, ActorLogging, ActorSystem, Cancellable, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random

/**
  * Created by $Chethan on Dec 31, 2023.
  */

class SelfClosingActorDemo extends TestKit(ActorSystem("SelfClosingDemo")) with AnyWordSpecLike with ImplicitSender {

  "SelfClosingActorDemo" should {

    "SelfClosingActorDemo" in {
      val selfClosingActor = system.actorOf(Props[SelfClosingActor], "selfClosingActor")

      selfClosingActor ! Ping

//            system.scheduler.schedule(100 millis, 1000 millis){
//              selfClosingActor ! "message 1 " + LocalDateTime.now
//            }

      for (i <- 1 to 100) {
        val time = Random.nextInt(1100)
        Thread.sleep(time)
        selfClosingActor ! "message " + s" $i " + LocalDateTime.now
      }
      Thread.sleep(50000)

    }
  }
}


object Stop

object TimeOut

object Ping

class SelfClosingActor extends Actor with ActorLogging {


  override def preStart(): Unit = log.info("Autonomous actor has been started " + LocalDateTime.now)

  override def postStop(): Unit = log.info("Sorry : Autonomous actor has been stopped due to no messages " + LocalDateTime.now)

  var timeout: Cancellable = timeOutWindow()

  def timeOutWindow(): Cancellable = context.system.scheduler.scheduleOnce(1 second){
    self ! Stop
  }

  def startActor: Receive = {
    case Stop => context.stop(self)

    case TimeOut => timeout = timeOutWindow()

    case message: String =>
      timeout.cancel()
      log.info(message)
      self ! TimeOut
  }

  override def receive: Receive = {
    case Ping => context.become(startActor)

    case _ => log.error("""Error 007 :- Unable to start the Autonomous Actor XXX. Please send a "Ping"!!!""")
  }
}
