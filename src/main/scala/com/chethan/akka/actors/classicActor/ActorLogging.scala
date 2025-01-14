package com.chethan.akka.actors.classicActor

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by $CapName on Oct 31, 2023.
  */

object ActorLogging extends App {

  class SimplelogActor extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.debug(message.toString)
        log.info(message.toString)
        log.warning(message.toString)
        log.error(message.toString)
    }
  }


  class SimpleActor extends Actor {

    //Behaviour
    override def receive: Receive = {
      case 123         =>
      case "AnyString" =>
      case any         => updatedBehaviour
    }

    //Behaviour
    private def updatedBehaviour: Receive = {
      case any =>
    }
  }



  //1 - Line configuration
  val configString =
    """
      |akka{
      | loglevel = DEBUG
      |}
      |""".stripMargin

  val config      = ConfigFactory.parseString(configString)
  val actorSystem = ActorSystem("akkaConfigDemo", ConfigFactory.load(config))


  val actor1 = actorSystem.actorOf(Props[SimplelogActor])
  actor1 ! "A message to print in logger"

  //  //2. Configuration file
  //
  //  // this will apply the configuration with respect to conf file present in the resources folder
  //  val confActorSystem = ActorSystem("ConfActorSystem")
  //  val actor2          = confActorSystem.actorOf(Props[SimplelogActor])
  //  actor2 ! "this is a message"
  //
  //
  //  //  3. separate configs in the same file
  //  val separateConfig = ConfigFactory.load().getConfig("mySeparateConfig")
  //  val as2            = ActorSystem("ConfActorSystem", separateConfig)
  //  val actor3         = as2.actorOf(Props[SimplelogActor])
  //  actor3 ! "this is a custom separate config"
  //
  //
  //  //4. configuration from separate file
  //  val separateFileConfig = ConfigFactory.load("secretFolder/secretConfiguration.conf")
  //  val as4                = ActorSystem("ConfActorSystem", separateFileConfig)
  //  val actor4             = as4.actorOf(Props[SimplelogActor])
  //  actor4 ! "this is a custom separate config from separate config file"
  //
  //
  //  //5. separate configuration from separate file
  //  val separateConfigSeparateFile = ConfigFactory.load("secretFolder/secretConfiguration.conf").getConfig("sepFileAkkaConfig")
  //  val as5                        = ActorSystem("ConfActorSystem1", separateConfigSeparateFile)
  //  val actor5                    = as5.actorOf(Props[SimplelogActor])
  //  actor5 ! "this is a custom separate config from separate config file and custom configuration"
  //
  //
  //  //6. separate configuration from JSON type of file
  //  val jsonConfig = ConfigFactory.load("jsonConfig.json")
  //  println(jsonConfig.getString("customJsonProp"))
  //  println(jsonConfig.getString("akka.loglevel "))
  //
  //  val as6                        = ActorSystem("ConfActorSystem1", jsonConfig)
  //  val actor6                     = as6.actorOf(Props[SimplelogActor])
  //  actor6 ! "this is a custom separate config from JSON file"
  //
  //
  //
  //
  //  //7. separate configuration from Properties type of file
  //  val propsConfig = ConfigFactory.load("propsConfiguration.properties")
  //    println(propsConfig.getString("my.customProperty"))
  //    println(propsConfig.getString("akka.loglevel "))
  //
  //  val as7    = ActorSystem("ConfActorSystem1", propsConfig)
  //  val actor7 = as7.actorOf(Props[SimplelogActor])
  //  actor7 ! "this is a custom separate config from Properties file"


}
