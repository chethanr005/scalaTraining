package com.chethan.akka.classicActor.infrastucture

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory

/**
  * Created by $Chethan on Jan 08, 2024.
  */

object DispatchersDemo extends App {


//  Note:Method1
//    val acs = ActorSystem("DispatcherDemo")
//    val slaves = (1 to 10).map(a => acs.actorOf(Props[Dispatchers].withDispatcher("my-dispatcher"), s"Disp_${a}"))
//    val random = new Random()
//    for (i <- 1 to 1000) {
//      slaves(random.nextInt(10)) ! i
//    }

  //Note:Method2
  val acs                    = ActorSystem("DispatcherDemoSystem", ConfigFactory.load().getConfig("Dispatcher-Demo"))
  val customConfigDispatcher = acs.actorOf(Props[Dispatchers], "customDispatcher")


  class Dispatchers extends Actor with ActorLogging {
    var count = 0

    override def receive: Receive = {
      case message =>
        count += 1
        log.info(s"""${self.path} [$count] ${message.toString}""")
    }
  }


}
