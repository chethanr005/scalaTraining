package com.chethan.akka.classicActor.infrastucture

import akka.actor.{Actor, ActorLogging, ActorSystem, Cancellable, Props}
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop

import scala.concurrent.duration._
/**
  * Created by $Chethan on Dec 31, 2023.
  */

object SchedulersAndTimers extends App{

  val system = ActorSystem("schedulersAndTimers")
  val simpleActor = system.actorOf(Props[SimpleActor])

  system.log.info("scheduling reminder for simple actor")

//  implicit val executor = system.dispatcher
  import system._


  //reminder once
//  system.scheduler.scheduleOnce(1 second){simpleActor ! "reminder"}

 val cycle: Cancellable =

   system.scheduler.scheduleWithFixedDelay(1 second, 1 second){
   new Runnable {
     override def run(): Unit = {
       simpleActor ! "heartbeat"
     }
   }
  }

  // Cancelling a cycle
  system.scheduler.scheduleOnce(10 seconds)(performeSomeTask())

  def performeSomeTask() ={

  }



}


class SimpleActor extends Actor with ActorLogging{
  override def receive: Receive = {
    case message => log.info(message.toString)
  }
}