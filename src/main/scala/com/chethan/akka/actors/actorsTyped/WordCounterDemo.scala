package com.chethan.akka.actors.actorsTyped

import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors

/**
  * Created by $Chethan on Jan 20, 2024.
  */

object WordCounterDemo {

}

object WordCountSpecs {

  trait CounterSpecs

  case class Work(message: String) extends CounterSpecs

  case class Initialize(noOfChildren: Int) extends CounterSpecs

  case class WorkReply() extends CounterSpecs

  object WordCountMaster {

    def apply(): Behaviors.Receive[CounterSpecs] = initialize(Nil)

    def initialize(workers: Seq[ActorRef[String]]): Behaviors.Receive[CounterSpecs] = {
      Behaviors.receive[CounterSpecs]{ case (context, message) =>
        message match {
          case Initialize(noOfChildren) => context.log.info("Initializing Children")
            val workers = (1 to noOfChildren).map(a => context.spawn(WordCountWorker(), s"Worker_$a"))
            workAssigner(workers)
        }
      }
    }

    def workAssigner(workers: Seq[ActorRef[String]]): Behaviors.Receive[CounterSpecs] = {
      Behaviors.receive[CounterSpecs]{ case (context, message) =>
        message match {
          case Work(message) =>
            if (workers.isEmpty) {
              context.log.info("Workers are not Available. Please Wait.")
              Behaviors.same
            }
            else {
              workers.head ! message
              workAssigner(workers.tail)
            }
          case WorkReply()   =>Behaviors.empty
        }
      }
    }
  }

  object WordCountWorker {
    def apply() = {
      Behaviors.receive[String]{ case (context, message) =>
        message match {
          case message => context.log.info(s"[${context.self.path.name}] I have received Work : $message")
        }
        Behaviors.same
      }
    }
  }
}


