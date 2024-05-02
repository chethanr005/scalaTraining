package com.chethan.akka.classicActor

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by $CapName on Oct 17, 2023.
  */

class Bank extends Actor {
  var account = 0

  override def receive: Receive = {

    case Deposit(amount) => account += amount
      self ! Success(s"An amount of $amount has been deposited to your bank account")


    case Withdraw(amount) => if (amount < account) {
      account -= amount
      self ! Success(s"The amount of $amount has been withdrawn from your bank account")
    }
                             else self ! Failure("Your withdraw transcation has been bounced back due to less account balance")


    case Statement        => self ! s"Your bank balance is $account"
    case message :String         => println(message)
    case Success(message) => println(message)
    case Failure(message) => println(message)
    case _                => println("Unknown error")
  }
}

case class Deposit(amount: Int)

case class Withdraw(amount: Int)

case object Statement


case class Success(message: String)

case class Failure(message: String)


object BankApplication extends App {

  val actorSystem = ActorSystem("BankSystem")
  val bank        = actorSystem.actorOf(Props[Bank], "Bank")
  bank ! Deposit(1000000)
  bank ! Statement
  bank ! Withdraw(4756131)
  bank ! Statement
  bank ! Withdraw(566831)
  bank ! Statement

  actorSystem.terminate()
}