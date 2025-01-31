package com.chethan.akka.streams.basics

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.stream.{ActorMaterializer, OverflowStrategy}

import scala.concurrent.duration.DurationInt

/**
  * Created by Chethan on Jan 30, 2025.
  */

object BackPressureBasics extends App {

  implicit private val system      : ActorSystem       = ActorSystem("MaterializingStreams")
  private          val materializer: ActorMaterializer = ActorMaterializer()

  /** o.Element flow as a response to demand from the consumer.
    *o. BackPressure is synchronization of speed between asynchronous components.
    *
    *o. When the consumers are slowing in processing the elements then the producer producing -
    * - a backpressure protocol will be raised and a signal will be sent upstream to slow down the production.
    */

  val fastSource = Source(1 to 1000)
  val slowSink   = Sink.foreach[Int]{
    a =>
      println("Slow sink : " + a)
      Thread.sleep(1000)
  }

  //Not back pressure
  //  fastSource.to(slowSink).run()

  //Back pressure
  //  fastSource.async.to(slowSink).async.run()

  val simpleFlow = Flow[Int].map{ a =>
    println("Simple flow : " + a)
    a
  }

  //Back Pressure in action
  //  fastSource.via(simpleFlow).async.to(slowSink).run()

  val failFlow = simpleFlow.buffer(10, overflowStrategy = OverflowStrategy.dropHead)
  // The sink has a default buffer size i.e 16 in this case. This can be seen while the whole graph is asynchronous.
  //  fastSource.async.via(failFlow).async.to(slowSink).run()


  //Throttling
  fastSource.throttle(2, 1.seconds).to(Sink.foreach(println)).run()


}
