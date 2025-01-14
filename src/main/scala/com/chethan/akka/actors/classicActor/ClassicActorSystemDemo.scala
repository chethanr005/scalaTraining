package com.chethan.akka.actors.classicActor

import java.util.concurrent.Executors

import akka.actor.setup.ActorSystemSetup
import akka.actor.{ActorSystem, BootstrapSetup}
import com.typesafe.config.Config

import scala.concurrent.ExecutionContext

/**
  * Created by Chethan on Sep 16, 2023.
  */

object ClassicActorSystemDemo extends App {

  // actor with default name
  val defaultActorSystem = ActorSystem.create()
  println(defaultActorSystem.name)


  //AC with custom Execution Context
  val exectuionContext = Executors.newFixedThreadPool(1)
  private val exec = ExecutionContext.fromExecutor(exectuionContext)
  val ac2 = ActorSystem.apply("AC2", None, None, Some(exec))
  println(ac2.name)


  //AC with custom config
  val config: Config = com.typesafe.config.impl.ConfigImpl.emptyConfig("")
  val ac3            = ActorSystem.apply("AC3", Some(config), None, Some(exec))
  println(ac3.name)


  //"A class loader is an object that is responsible for loading classes. The class ClassLoader is an abstract class. Given the binary name of a class, a class loader should attempt to locate or generate data that constitutes a
  // definition for the class. A typical strategy is to transform the name into a file name and then read a \"class file\" of that name from a file system."

  // Custom class loader
  val classLoader        = ClassLoader.getSystemClassLoader
  val contextClassLoader = Thread.currentThread().getContextClassLoader
  val ac4                = ActorSystem.apply("AC4", Some(config), Some(contextClassLoader), Some(exec))
  println(ac4.name)

  // Custom Actor system setup
  val bootStrap123     = BootstrapSetup.apply(Some(classLoader), Some(config), Some(exec))
  //  val settings = new Settings(classLoader, config, "abc",ActorSystemSetup.create(bootStrap123))
  val actorSystemSetup = ActorSystemSetup.create(bootStrap123)
  val ac5              = ActorSystem("AC5", actorSystemSetup)
  println(ac5.name)

  val bootStrap = BootstrapSetup.apply(Some(classLoader), Some(config), Some(exec))
  val ac6       = ActorSystem.apply("AC6", bootStrap)
  println(ac6.name)


  //Inaccessible outside akka
  //    new ActorSystemImpl("name",config, classLoader, Some(exec),None, actorSystemSetup)

  //Our own actor system can be created using ExtendedActorSystem
  defaultActorSystem.terminate()
}


//class MyAkka extends ExtendedActorSystem