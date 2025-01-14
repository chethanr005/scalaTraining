package com.chethan.akka.actors.assignment.typedActors

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

/**
  * Created by Chethan on Feb 05, 2024.
  */

object Runner extends App {

  //  val authenticator = Behaviors.setup[Unit]{
  //    context =>
  //      val tacoBall = context.spawn(TacoBall(context.self), "tacoball")
  //      //      tacoBall ! ProceedToPayment("123456789", "770")
  //      tacoBall ! Cancel
  //      //      tacoBall ! ConfirmOrder("cake")
  //      //      tacoBall ! ConfirmOrder("cake")
  //
  //      Behaviors.empty
  //  }

  val authenticator = Behaviors.setup[Unit]{
    context =>


      val tacoBall = context.spawn(Zomato(), "tacoball")
      //      tacoBall ! Login("chethan", "chethan2021")
      //      tacoBall ! SignUp("shyam", "shyam2021")
      //        tacoBall ! Login("shyam", "shyam2021")
      Thread.sleep(7000)
      //        tacoBall ! ConfirmOrder(1)
      Thread.sleep(3000)
      //        tacoBall ! ProceedToPayment("123456789","007")

      Thread.sleep(3000)
      //      tacoBall ! Cancel
      Behaviors.empty
  }

  //  val authenticator = Behaviors.setup[Unit]{
  //    context =>
  //
  //
  //      val tacoBall = context.spawn(TacoBall(), "tacoball")
  //      val resto    = context.spawn(Restaurant(tacoBall), "resto")
  //      val delivery = context.spawn(Delivery(tacoBall), "delivery")
  //
  //      delivery ! DeliveryRequest("idli",resto, "location")
  //      delivery ! DeliveryRequest("dosa",resto, "location")
  //
  //
  ////      tacoBall ! Login("shyam", "shyam2021")
  ////      Thread.sleep(7000)
  ////      tacoBall ! ConfirmOrder(1)
  ////      Thread.sleep(3000)
  ////      tacoBall ! ProceedToPayment("123456789", "007")
  //
  //      Thread.sleep(3000)
  //      //      tacoBall ! Cancel
  //      51
  //      Behaviors.empty
  //  }


  ActorSystem(authenticator, "demo")

  Thread.sleep(10000)
}
