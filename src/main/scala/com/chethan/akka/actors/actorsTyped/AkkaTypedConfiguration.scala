package com.chethan.akka.actors.actorsTyped

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.typesafe.config.ConfigFactory

/**
  * Created by $Chethan on Jan 24, 2024.
  */

object AkkaTypedConfiguration {

  object SimpleLoggingActor {
    def apply(): Behaviors.Receive[String] = {
      Behaviors.receive[String]{ (context, message) =>
        context.log.debug(message)
        Behaviors.same

      }
    }
  }


  def demoInlineConfig() = {
    //Human-Optimized Configuration Object Notation(HOCON)
    // HOCON is a superset of JavaScript Object Notation (JSON), enhanced for readability
    val configString =
      """
        |akka{
        | loglevel = "DEBUG"
        |}
        |""".stripMargin


    //Method 1
    //    val config = ConfigFactory.parseString(configString)
    //    val acs    = ActorSystem(SimpleLoggingActor(), "SimpleLoggingActor", config)

    //Method2
    val config = ConfigFactory.load().getConfig("typedAkkaConfig")
    val acs    = ActorSystem(SimpleLoggingActor(), "SimpleLoggingActor", config)


    acs ! "this is a message"
    Thread.sleep(1000)
    acs.terminate()
  }

  def main(args: Array[String]): Unit = {
    demoInlineConfig()

  }

}
