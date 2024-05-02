package com.chethan.akka.actorsTyped.Demo

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.chethan.akka.actorsTyped.Demo.HandlingBehavioursDemo.{Cappuccino, CoffeeSpecs, Initialize}

/**
  * Created by $CapName on Feb 14, 2024.
  */


//Creating an actor and handling its behaviour
object CoffeeMachine {


  def apply(): Behaviors.Receive[CoffeeSpecs] = {
    Behaviors.receive[CoffeeSpecs]{
      case (context, message) => //INITIAL BEHAVIOUR
        message match {
          case Initialize(beans, power) =>
            context.log.info("Machine is Ready")
            operational(beans, power)
          case any                      => context.log.info("Please Initialize the coffee machine")
            Behaviors.same
        }
    }
  }

  //CHANGE IN STATE OF THE ACTOR
  def operational(beans: String, power: String): Behaviors.Receive[CoffeeSpecs] = {
    Behaviors.receive[CoffeeSpecs]{
      case (context, message) => //SUBSEQUENT BEHAVIOUR
        message match {
          case Cappuccino => context.log.info(" Your Cappuccino is ready")
            Behaviors.same
          case any        => context.log.info("Unknown command")
            Behaviors.same
        }
    }

  }
}


object HandlingBehavioursDemo extends App {
  sealed trait CoffeeSpecs

  case class Initialize(beans: String, power: String) extends CoffeeSpecs

  case object Cappuccino extends CoffeeSpecs



//    val behaviour = Behaviors.setup[Unit]{context => Behaviors.same}


  val coffeeMachine: ActorSystem[CoffeeSpecs] = ActorSystem(CoffeeMachine(), "coffeeMachine")

//  coffeeMachine ! Cappuccino
  coffeeMachine ! Initialize("beans", "Power")
  coffeeMachine ! Cappuccino

}


