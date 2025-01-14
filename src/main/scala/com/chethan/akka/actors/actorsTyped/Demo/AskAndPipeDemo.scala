package com.chethan.akka.actors.actorsTyped.Demo

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.util.Timeout
import AskAndPipe.{Ask, Borrower, Lender, Return}

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by $CapName on Feb 21, 2024.
  */

object AskAndPipe {

  implicit val timeout = Timeout(4 seconds)

  object Borrower {

    var debit = 0

    def apply(): Behaviors.Receive[AskPipeSpec] = {
      Behaviors.receive[AskPipeSpec]{
        (context, message) =>

          message match {
            case Ask(amount, lender)    =>
              context.log.info(s"[Borrower] : Requesting $amount rupees from the lender")
              context.ask(lender, self => MoneyRequest(amount, self)){
                case Failure(exception) => throw new Exception()
                case Success(value)     =>
                  context.log.info(s"Received ($amount) rupees from the lender")
                  Debit(amount)
              }
            case Return(amount, lender) =>
              context.log.info(s"[Borrower] : Returning $amount to the Lender")
              lender ! ReturnRequest(amount, context.self)

            case Debit(amount)          => debit += amount
              context.log.info(s"The current debit is : $debit")
            case Credit(amount, lender) => debit -= amount
              context.log.info(s"The current debit is : $debit")
              lender ! TransactionComplete
          }

          Behaviors.same
      }
    }

  }


  object Lender {
    def apply(): Behaviors.Receive[AskPipeSpec] = {
      Behaviors.receive[AskPipeSpec]{
        (context, message) =>

          message match {
            case MoneyRequest(amount, borrower)  =>
              borrower ! Debit(amount)
            case ReturnRequest(amount, borrower) => context.ask(borrower, self => Credit(amount, self)){
              case Failure(exception) => throw new Exception()
              case Success(value)     => value
            }
            case TransactionComplete             =>
              context.log.info("[Lender] : Borrowed has returned the money successful")
          }
          Behaviors.same
      }
    }
  }


  trait AskPipeSpec

  case class MoneyRequest(amount: Int, borrower: ActorRef[AskPipeSpec]) extends AskPipeSpec

  case class ReturnRequest(amount: Int, borrower: ActorRef[AskPipeSpec]) extends AskPipeSpec

  case class Ask(amount: Int, lender: ActorRef[AskPipeSpec]) extends AskPipeSpec

  case class Return(amount: Int, lender: ActorRef[AskPipeSpec]) extends AskPipeSpec

  case class Debit(amount: Int) extends AskPipeSpec

  case class Credit(amount: Int, lender: ActorRef[AskPipeSpec]) extends AskPipeSpec

  case object TransactionComplete extends AskPipeSpec

}


object AskAndPipeDemo extends App {

  val demo: Behavior[Unit] = Behaviors.setup[Unit]{ context =>

    val borrower = context.spawn(Borrower(), "borrower")
    val lender   = context.spawn(Lender(), "lender")

    borrower ! Ask(100000, lender)
    Thread.sleep(1000)
    borrower ! Return(20000, lender)

    Behaviors.empty
  }

  val ac = ActorSystem(demo, "AspSpecsDemo")

  def futureCall = Future("A FUTURE CALL")
}
