package com.chethan.akka.actors.classicActor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * Created by $CapName on Oct 16, 2023.
  */

object ActorCapablities extends App {
  var messagesCount = 0

  class SimpleActor extends Actor {
    override def receive: Receive = {

      case message: String =>
        println(context.sender())
        println(context.self)
        println(context.self.path)
      //        println(s"I have received this message $message")
      //        messagesCount += 1
      //        println(messagesCount)
      //        context.sender() ! "hello there"
      case SenderCheck(ref) => println(s"this is a sender check ${context.sender()} ${ref}")
        ref.!("sending to sender")

      case DeadLetters       => println(context.sender())
        sender() ! "dead messages"
      case CustomSender(ref) =>
        println(s"we are sending custom message from $self to $ref")
        //        implicit val  anonys = anonymous
        ref.!("hello")(null)


      case MessageForwarding(message, actor) => actor forward   message
      case number: Int                       => println(s"I have received this message $number")
      case SimpleObject                      => self ! "I am greeting myself"
      case sayHi: SayHiTo                    => println(s"this is $self saying hi to ${sayHi.ref}")
      case any                               => println(s"I have received this message $any")
    }
  }


  val actorSystem = ActorSystem("ActorCapabilitiesDemo")
  val simpleActor = actorSystem.actorOf(Props[SimpleActor], "SimpleActor")

  //  simpleActor ! SimpleObject
  //    simpleActor ! " hello"
  //  simpleActor ! 420

  case object SimpleObject

  val alice     = actorSystem.actorOf(Props[SimpleActor], "alice")
  val bob       = actorSystem.actorOf(Props[SimpleActor], "bob")
  val anonymous = actorSystem.actorOf(Props[SimpleActor], "anonymous")

  case class SayHiTo(ref: ActorRef)

  //  alice ! SayHiTo(bob)

  case class SenderCheck(ref: ActorRef)

  //  alice ! SenderCheck(bob)

  case class CustomSender(ref: ActorRef)

  //  alice ! CustomSender(bob)

  case object DeadLetters

  //  alice ! DeadLetters

  case class MessageForwarding(message: String, actor: ActorRef)

  alice ! MessageForwarding("I said his", bob)

  actorSystem.terminate()
}
