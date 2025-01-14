package com.chethan.akka.actors.actorsTyped

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

/**
  * Created by $Chethan on Jan 19, 2024.
  */

object Exercise1 extends App {

  object HappyPerson {
    def apply(): Behavior[String] = {
      Behaviors.receive{ case (context, message) => context.log.info(s"I have received $message That's great!.")
        Behaviors.same
      }
    }
  }

  object SadPerson {
    def apply(): Behavior[String] = {
      Behaviors.receive{ case (context, message) => context.log.info(s"I have received $message That Sucks!.")
        HappyPerson()
      }
    }
  }

  object Person {
    def apply(): Behavior[String] = {
      Behaviors.receiveMessage{
        case "akka is bad" => SadPerson()
        case "message"     => HappyPerson()
      }
    }
  }


  //  val person = ActorSystem(SadPerson(), "Person")
  //  person ! """"I am learning Typed Actors""""
  //  person ! """"I am learning Typed Actors""""

  //  val person = ActorSystem(Person(), "Person")
  //  person ! """"I am learning Typed Actors""""
  //  person ! """"I am learning Typed Actors2""""

  val wordCounter = ActorSystem(WordCounter(),"WordCounter")
  wordCounter ! "this is akka typed actors testing"
  wordCounter ! "I love akka"
  wordCounter ! "akka is awesome "
}


object WordCounter {
  def apply(totalCount: Int = 0): Behavior[String] = {
    Behaviors.setup[String]{ (context) =>
      Behaviors.receiveMessage{ message =>
        val wordCount = message.split(" ").length
        context.log.info(s"""this word has = $wordCount and Total Word Count is : ${totalCount + wordCount}""")
        apply(totalCount + wordCount)
      }
    }
  }
}
