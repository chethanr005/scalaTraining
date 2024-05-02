package com.chethan.akka.classicActor.infrastucture

import akka.actor.{Actor, ActorLogging, ActorSystem, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import akka.routing._
import com.chethan.akka.classicActor.infrastucture.Items._
import com.typesafe.config.ConfigFactory

/**
  * Created by $Chethan on Jan 05, 2024.
  */

object Routers extends App {

  val acs = ActorSystem("routerDemo", ConfigFactory.load().getConfig("customStrategyConfigurator"))


  val master = acs.actorOf(Props[Master], "master")
//  //  Note:Method 1 of creating router pool
  for (i <- 1 to 10) {
    master ! i
  }


//  Note:Method 2 of creating router pool
//    val masterPool2 = acs.actorOf(RoundRobinPool(5).props(Props[Slave]), "simplePoolMaster")
//    for (i <- 1 to 10) {
//      masterPool2 ! i
//    }



  //Note:Method 3 of creating router pool
  //   val poolMaster3 = acs.actorOf(FromConfig.props(Props[Slave]),"poolMaster3")
  //        for (i <- 1 to 10) {
  //          poolMaster3 ! " this is a message " + i
  //        }


  //Note:Method 4 of creating router pool
//    val slaveList  = (1 to 5).map(a => acs.actorOf(Props[Slave], s"slave_$a")).toList
//    val slavePaths = slaveList.map(_.path.toString)
//    //this is an actor  ref of type "akka.routing.RoutedActorRef"
//    val masterPool4: ActorRef = acs.actorOf(RoundRobinGroup(slavePaths).props())
//    println(masterPool4.getClass.getName)
//    for (i <- 1 to 10) {
//      masterPool4 !  i
//    }

  //Note:Method 5 of creating router pool
  // (1 to 5).foreach(a => acs.actorOf(Props[Slave], s"slave_$a"))
  ////  val slavePaths            = slaveList.map(_.path.toString)
  ////  val masterPool4: ActorRef = acs.actorOf(RoundRobinGroup(slavePaths).props())
  //
  //  val groupedMaster = acs.actorOf(FromConfig.props(), "groupedMaster")
  //    for (i <- 1 to 10) {
  //      groupedMaster ! i
  //    }
  //
  //  groupedMaster ! Broadcast("Hello all this is a broad casting to all actors")

}


object Items {


  class Slave extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info(message.toString + " " + self.path)
//              throw new Exception()
        Thread.sleep(5000)
      //        if (message == 6 || message == 7)
      //          context.stop(self)

    }
  }

  class Master extends Actor {

    override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(){
      case _: Exception => SupervisorStrategy.Stop
    }


    //Method 1
    private val slaves = for (i <- 1 to 5) yield {
      val slave = context.actorOf(Props[Slave], s"slave_${i}")
      context.watch(slave)
      ActorRefRoutee(slave)
    }

    var router = Router(RoundRobinRoutingLogic(), slaves)

    override def receive: Receive = {


      case Terminated(actor) =>
        val newSlave = context.actorOf(Props[Slave])
        println(s"${actor.path} has failed : This is the new actor ${newSlave.path}")
        context.watch(newSlave)
        router = router.removeRoutee(actor).addRoutee(newSlave)


      case message =>
        router.route(message, sender())
      case "an"    =>
    }
  }
}
