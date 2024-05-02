package com.chethan.akka.classicActor.test

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.testkit.{EventFilter, ImplicitSender, TestKit}
import com.chethan.akka.classicActor.test.IntegrationTesting.{CheckOut, CheckOutActor}
import com.typesafe.config.ConfigFactory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

/**
  * Created by $CapName on Nov 07, 2023.
  */

class IntegrationTesting extends TestKit(ActorSystem("Integration")) with ImplicitSender with AnyWordSpecLike with BeforeAndAfterAll {
  //  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)


  "Integration testing" should {
    "test 1" in {

      EventFilter.info(message = "").intercept{

        val actor = system.actorOf(Props[CheckOutActor])
        actor ! CheckOut("phone", "5656 5654 5646 4546")
      }
    }
  }
}

object IntegrationTesting {
  case class CheckOut(item: String, card: String)

  case object AuthorizeCard

  case object PaymentAccepted

  case object PaymentDenied

  case class DispatchOrder(item: String)

  case class OrderedConfirmed(item: String)

  case class DeniedOrder(item: String)

  case class OrderCancelled(item: String)

  case object InvalidCard

  case class InitiatePayment(card: String)
  //  case object

  class CheckOutActor extends Actor with ActorLogging {
    val paymentManager  = context.actorOf(Props[PaymentManager])
    val dispatchManager = context.actorOf(Props[DispatchManager])
    val cardManager     = context.actorOf(Props[CardManager])

    override def receive: Receive = {
      case CheckOut(item, card) =>
        println("Verifying Card.....")
        cardManager ! card
        context.become(cardConfirmation(item, card))
    }

    def cardConfirmation(item: String, card: String): Receive = {
      case AuthorizeCard =>
        println("[Card Manager] => Authorized card!!!")
        paymentManager ! InitiatePayment(card)
        context.become(paymentPending(item))
      case InvalidCard   =>
        println("[Card Manager] => Invalid card!!!")
        self ! PaymentDenied
        context.become(paymentPending(item))

      case _ => println("here here here here")

    }


    def paymentPending(item: String): Receive = {

      case PaymentAccepted =>
        println("[Payment Manager] => Payment Accepted!!!")
        dispatchManager ! DispatchOrder(item)
        context.become(dispatchPending)
      case PaymentDenied   =>
        println("[Payment Manager] => Payment Denied!!!")
        dispatchManager ! DeniedOrder(item)
        context.become(dispatchPending)
    }


    def dispatchPending: Receive = {
      case OrderedConfirmed(item) => log.info("[Dispatch Manager] => order has been confirmed " + item)
      case OrderCancelled(item)   => log.info("[Dispatch Manager] => order cancelled " + item)
    }
  }


  class CardManager extends Actor {
    override def receive: Receive = {
      case card: String => if (card.startsWith("0")) sender ! InvalidCard else sender ! AuthorizeCard
    }
  }


  class PaymentManager extends Actor {
    override def receive: Receive = {
      case InitiatePayment(card) => sender ! PaymentAccepted
    }
  }

  class DispatchManager extends Actor {
    override def receive: Receive = {
      case DispatchOrder(item) => sender() ! OrderedConfirmed(item)
      case DeniedOrder(item)   => sender() ! OrderCancelled(item)
    }
  }
}