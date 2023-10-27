package com.chethan.akka.actors

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by $CapName on Oct 16, 2023.
  */

object ActorIntro extends App {

  val actorSystem = ActorSystem("FirstActorSystem")

  class WordCounterActor extends Actor {
    var totalWords = 0

    def receive: PartialFunction[Any, Unit] = {
      case messsage: String => totalWords += messsage.split(" ").length; println(totalWords)
      case msg              => println(s"[Word Counter] I can't understand ${msg.toString}")
    }
  }

  val wordCounterActor = actorSystem.actorOf(Props[WordCounterActor], "WordCounter")
  wordCounterActor ! "the quick brown fox jumps over the lazy dog"


  class Person(name: String) extends Actor {
    override def receive: Receive = {
      case message => println(s"this is the message i received ${message.toString}")
    }
  }

  val personActor = actorSystem.actorOf(Props(new Person("Rocky")), "Rocky")
  val womenActor  = actorSystem.actorOf(Person("womenActor"), "womenActor")

  object Person {
    def apply(name: String): Props = Props(new Person(name))
  }


}
