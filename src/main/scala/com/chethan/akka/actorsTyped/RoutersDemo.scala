package com.chethan.akka.actorsTyped

import akka.actor.typed.ActorSystem
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.{Behaviors, Routers}
import com.chethan.akka.actorsTyped.utils._

import scala.concurrent.duration._

/**
  * Created by $Chethan on Jan 24, 2024.
  */

object RoutersDemo {

  def demoPoolRouters(): Unit = {
    val workerBehaviour = LoggerActor[String]()
    //Round robin is the default pool
    //    val poolRouter      = Routers .pool(5)(workerBehaviour)

    // Broadcasting - will broad cast the message which satisfies the predicate condition to all the routees.
    val poolRouter = Routers.pool(5)(workerBehaviour).withBroadcastPredicate(_.length > 11)

    val userGuardian = Behaviors.setup[Unit]{ context =>
      val poolActor = context.spawn(poolRouter, "pool")

      (1 to 10).foreach{ a => poolActor ! s"work_Task ${a}"
        //        Thread.sleep(1000)
      }
      Behaviors.empty
    }
    ActorSystem(userGuardian, "DemoPoolRouter").withFiniteLifeSpan(2 seconds)
  }

  def demoGroupRoutee() = {

    val serviceKey = ServiceKey[String]("logWorker")

    val userGuardian = Behaviors.setup[Unit]{
      context =>
        val workers  = (1 to 3).map(a => context.spawn(LoggerActor[String](), s"WorkerGroup_${a}"))
        val regActor = context.spawn(actReg(), "actorReg")
        workers.foreach(w => context.system.receptionist ! Receptionist.register(serviceKey, w, regActor))


        // Default is Random Pool
        val groupedRouter = Routers.group(serviceKey).withRoundRobinRouting()
        val groupRoutee   = context.spawn(groupedRouter, "workerGroup")
//        (1 to 10).foreach(a => groupRoutee ! s"work_Task ${a}")
//        Thread.sleep(1000)

        println("\n")

        //Adding an extra actor by registering it to the actor pool
        val extraWorker = context.spawn(LoggerActor[String](), "extraWorker")
//        context.system.receptionist ! Receptionist.register(serviceKey, extraWorker)
//        (1 to 10).foreach(a => groupRoutee ! s"work_Task ${a}")
//        Thread.sleep(100)
//        println("\n")
//
//
//        // De registering an extra actor from the pool
        val deRegisterActor = context.spawn(deActor(), "deActor")
        context.system.receptionist ! Receptionist.deregister(serviceKey, extraWorker, deRegisterActor)
        context.system.receptionist ! Receptionist.deregister(serviceKey, workers(2), deRegisterActor)
        Thread.sleep(1000)
//        (1 to 10).foreach(a => groupRoutee ! s"work_Task ${a}")

        Behaviors.empty
    }

    ActorSystem(userGuardian, "DemoGroupRouter").withFiniteLifeSpan(10 seconds)

  }

  def main(args: Array[String]): Unit = {
    //    demoPoolRouters()
    demoGroupRoutee()

  }


  def deActor(): Behaviors.Receive[Receptionist.Deregistered] = {
    Behaviors receive[Receptionist.Deregistered] {
      (context, message) =>
        context.log.info(message.toString)
        Behaviors.same
    }
  }

  def actReg(): Behaviors.Receive[Receptionist.Registered] = {
    Behaviors receive[Receptionist.Registered] {
      (context, message) =>
        context.log.info(message.toString)
        Behaviors.same
    }
  }


}
