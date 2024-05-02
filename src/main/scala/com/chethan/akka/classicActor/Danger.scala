package com.chethan.akka.classicActor

import akka.actor.{Actor, Props}
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop

/**
  * Created by $CapName on Oct 26, 2023.
  */

object Danger extends App {
  case class Withdraw(amount: Int)

  case class Deposit(amount: Int)

  case object InitializeAccount

  case class AttachToAccount(bankAccount: BankAccount)

  case object CheckStatus


  class BankAccount extends Actor {
    var amount = 0

    override def receive: Receive = {
      case InitializeAccount =>
        val creditCradRef = context.actorOf(Props[CreditCard])
        creditCradRef ! AttachToAccount(this)
      case Withdraw(funds)   => amount -= funds
      case Deposit(funds)    => amount += funds
    }
  }

  class CreditCard extends Actor {
    override def receive: Receive = {
      case AttachToAccount(bankAccount) => context.become(attachedTo(bankAccount))
    }


    def attachedTo(bankAccount: BankAccount): Receive = {
      case CheckStatus =>
        println(s"${self.path} your message has been processed")
      //        bankAccount . Withdraw(100)
    }
  }


  /** *
    *
    *
    *Danger
    *
    *
    ** */

}
