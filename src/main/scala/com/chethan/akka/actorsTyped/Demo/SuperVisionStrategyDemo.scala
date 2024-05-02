package com.chethan.akka.actorsTyped.Demo

import akka.actor.typed._
import akka.actor.typed.scaladsl.Behaviors
import com.chethan.akka.actorsTyped.Demo.SuperVisionDemo.{Count, Message, WordCounter}

/**
  * Created by $CapName on Feb 20, 2024.
  */

object SuperVisionDemo {

  object WordCounter {

    def apply(count: Int): Behavior[SuperVisionSpecs] =
      Behaviors.receive[SuperVisionSpecs]{
        (context, message) =>
          message match {
            case Count         => context.log.info(s"Current count : $count")
              Behaviors.same
            case Message(text) =>
              if (text.size > 30) throw new Exception()
              apply(text.split(" ").size)
          }
      }.receiveSignal{
        case (context, PostStop) => context.log.info(s"[${context.self.path.name}] : This is a receive signal")
          Behaviors.same
      }
  }


  trait SuperVisionSpecs

  case class Message(text: String) extends SuperVisionSpecs

  case object Count extends SuperVisionSpecs
}

object SuperVisionStrategyDemo extends App {

  val parent = Behaviors.receive[String]{
    (context, message) =>
      message match {
        case "Start" =>
          val counter = Behaviors.supervise(WordCounter(0)).onFailure[Exception](SupervisorStrategy.resume)

          val wordCountActor = context.spawn(counter, "WordCounter")
          context.watch(wordCountActor)


          wordCountActor ! Count
          wordCountActor ! Message("Hello")
          wordCountActor ! Count
          wordCountActor ! Message("this is a greeting message")
          wordCountActor ! Count
          wordCountActor ! Message("this is a greeting message that Hii everybody good morning how are you")
          wordCountActor ! Count
          wordCountActor ! Message("this is a greeting message")
          wordCountActor ! Count
          Behaviors.same
      }
  }.receiveSignal{
    case (context, Terminated(value)) =>
      context.log.info("Word Count Actor Terminated")
      Behaviors.same
  }


  val ac = ActorSystem(parent, "master")
  ac ! "Start"
  Thread.sleep(2000)
}
