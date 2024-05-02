package com.chethan.akka.actorsTyped

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior, PostStop}

/**
  * Created by $Chethan on Jan 18, 2024.
  */

object TypeIntro extends App {


  object SensistiveActor {
    def apply() = {
      Behaviors.receive[String]{ (context, message) =>
                 if (message == "I am ugly") {
                   context.log.info("I am shutting down")
                   Behaviors.stopped
                   //                   (() => context.log.info("I have been stopped132"))
                 }
                 else {
                   context.log.info(s"I received a message $message")
                   Behaviors.same
                 }
               }
               .receiveSignal{ case (context, PostStop) =>
                 context.log.info("this is receive signal block")
                 Behaviors.same
               }
    }
  }


  val sensitiveActor = ActorSystem(SensistiveActor(), "SensitiveActor")
  sensitiveActor ! "Hello howw are you"
  sensitiveActor ! "I am ugl"
  sensitiveActor ! "I am ugly"
  Thread.sleep(2000)
  println("this is a demo message")
  sensitiveActor ! "I am lily"
  //  sensitiveActor ! "I am ugly"
  //  sensitiveActor ! "I am ugly"
  //

  object SimpleBehaviour {
    def apply(): Behavior[String] = Behaviors.receive{ (context, message) =>
      if (message == "") {
        context.log.info("this is if block")
        Behaviors.stopped(() => context.log.info("stopped message"))
      } else {
        println(message)
        Behaviors.same
      }

      //      Behaviors.setup{
      //        context =>
      //
      ////          Behaviors.supervise(SupervisorStrategy.stop).onFailure()
      //      }
    }

    object SimpleBehaviourV2 {
      def apply(): Behavior[String] =
        Behaviors.receive{

          case (context, message) => Behaviors.same
        }
    }
  }

  object SimpleBehaviourV3 {
    def apply(): Behavior[String] = {
      Behaviors.setup{ context =>
        Behaviors.receiveMessage(message => Behaviors.same)
      }
    }
  }

  //  val actorSystem: ActorSystem[String] = ActorSystem(SimpleBehaviour(), "SimpleActor")
  //
  //  actorSystem.!("this is a message to typed actor system")

  //    Option("").fold(println(""))(a => "")}
}



