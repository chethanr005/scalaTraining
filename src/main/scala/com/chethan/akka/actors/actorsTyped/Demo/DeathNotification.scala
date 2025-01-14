package com.chethan.akka.actors.actorsTyped.Demo

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior, Terminated}
import DeathNotification.{DeathSpecs, StartTrip}
import DeathWatch.KSRTC

import scala.util.Random

/**
  * Created by $CapName on Feb 19, 2024.
  */
object DeathWatch {

  object KSRTC {
    def apply(): Behavior[DeathSpecs] =
      Behaviors.receive[DeathSpecs]{ (context, message) =>
                 val busList = (1 to 10).map{ a =>
                   val bus = context.spawn(Bus(), s"Bus_${a}")
                   if (a / 6 == 0) context.watch(bus) // Registered buses
                   bus
                 }.toList

                 message match {
                   case StartTrip => busList.foreach(bus => bus ! StartTrip)
                     Behaviors.same
                 }
               }
               .receiveSignal{ case (context, Terminated(bus)) =>
                 context.log.info(Console.BLUE + s"[KSRTC] : ${bus.path.name} has been crashed" + Console.RED)
                 Behaviors.same
               }
  }


  object Bus {
    def apply(): Behaviors.Receive[DeathSpecs] = {

      Behaviors.receive[DeathSpecs]{ (context, message) =>

        message match {
          case StartTrip =>
            if (Random.nextInt(11) % 5 == 0) {
              context.log.info(Console.MAGENTA + s"[${context.self.path.name}] : I am crashed" + Console.RED)
              throw new Exception()
            }
            Behaviors.same
        }
      }
    }
  }
}


object DeathNotification extends App {

  val demo = Behaviors.setup[Unit]{ context =>
    val ksrtc = context.spawn(KSRTC(), "ksrtc")
    ksrtc ! DeathNotification.StartTrip
    Behaviors.empty
  }

  ActorSystem(demo, "demo")

  Thread.sleep(3000)

  trait DeathSpecs

  object StartTrip extends DeathSpecs

}
