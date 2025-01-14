package com.chethan.akka.actors.assignment.typedActors

import akka.actor.Cancellable
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import Zomato.{PaymentFailure, PaymentSuccess, TacoBallSpecs}

import scala.concurrent.duration._

/**
  * Created by $Chethan on Jan 22, 2024.
  */

object Bank {

  def apply(): Behaviors.Receive[BankSpecs] = {
    Behaviors.receive[BankSpecs]{ (context, message) =>
      message match {

        case PaymentRequest(cardNo, cvv, requester: ActorRef[TacoBallSpecs]) =>
          val paymentStatus: TacoBallSpecs = verifyPayment(cardNo, cvv)
          nextProcedure(context, requester, paymentStatus)
          Behaviors.same

      }
    }
  }


  private def verifyPayment(cardNo: String, cvv: String): TacoBallSpecs = {
    val bankDatabase = Map("123456789" -> "007", "456871332" -> "123", "+916561131" -> "654")
    bankDatabase.get(cardNo).fold[TacoBallSpecs](PaymentFailure("Incorrect Card Details")){ userCvv =>
      if (userCvv == cvv) PaymentSuccess
      else PaymentFailure("Incorrect CVV")
    }
  }

  trait BankSpecs

  case class PaymentRequest(cardNo: String, cvv: String, requester: ActorRef[TacoBallSpecs]) extends BankSpecs

  private def nextProcedure(context: ActorContext[BankSpecs],
                            requester: ActorRef[TacoBallSpecs],
                            message: TacoBallSpecs): Cancellable = context.scheduleOnce(2 seconds, requester, message)
}