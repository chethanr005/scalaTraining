package com.chethan.akka.actors.classicActor

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by $CapName on Oct 18, 2023.
  */

object ChangingBehaviour extends App {
  final val VEGETABLE = "Vegetable"
  final val CHOCOLATE = "Chocolate"
  final val ANY       = "Any"

  final val HAPPY = "Happy"
  final val SAD   = "Sad"

  final val ACCEPT = "Accept"
  final val REJECT = "Reject"


  class PersonChangingBehaviour extends Actor {

    override def receive: Receive = happyReceive

    def happyReceive: Receive = {
      case Food(VEGETABLE) => context.become(sadReceive)
      case Food(CHOCOLATE) =>
      case Ask(message)    => sender ! ACCEPT
    }

    def sadReceive: Receive = {
      case Food(VEGETABLE) => context.become(sadReceive)
      case Food(CHOCOLATE) => context.become(happyReceive)
      case Food(ANY)       => context.unbecome()
      case Ask(message)    => sender ! REJECT
    }
  }


  class NormalPerson extends Actor {
    override def receive: Receive = {
      case ACCEPT => println("weird person ACCEPTED")
      case REJECT => println("Weird person REJECTED")
    }
  }


  val actorSystem  = ActorSystem("BehaviourChnagingSystem")
  val weirdPerson  = actorSystem.actorOf(Props[PersonChangingBehaviour], "WeirdPerson")
  val normalPerson = actorSystem.actorOf(Props[NormalPerson], "NormalPerson")


  weirdPerson.!(Ask("hello"))(normalPerson)
  weirdPerson.!(Food(VEGETABLE))(normalPerson)
  weirdPerson.!(Food(VEGETABLE))(normalPerson)
  weirdPerson.!(Food(VEGETABLE))(normalPerson)
  weirdPerson.!(Ask("hello"))(normalPerson)
  weirdPerson.!(Food(ANY))(normalPerson)
  weirdPerson.!(Ask("hello"))(normalPerson)
    weirdPerson.!(Food(ANY))(normalPerson)
  weirdPerson.!(Ask("hello"))(normalPerson)
    weirdPerson.!(Food(ANY))(normalPerson)
  weirdPerson.!(Ask("hello"))(normalPerson)
  //  weirdPerson.!(Food(CHOCOLATE))(normalPerson)
  //  weirdPerson.!(Ask("hello"))(normalPerson)


  case class Food(name: String)

  case class Ask(message: String)

  actorSystem.terminate()
}
