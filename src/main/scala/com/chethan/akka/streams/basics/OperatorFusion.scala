package com.chethan.akka.streams.basics

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

/**
  * Created by Chethan on Jan 30, 2025.
  */

object OperatorFusion extends App {

  //Akka streams components are based on actors
  // an graph completely runs on single actor -  operator/Component fusion

  implicit private val system      : ActorSystem       = ActorSystem("MaterializingStreams")
  private          val materializer: ActorMaterializer = ActorMaterializer()


  val source = Source(90 to 100)
  val flow1  = Flow[Int].map{ a =>
    Thread.sleep(1000)
    a + 1
  }
  val flow2  = Flow[Int].map{ a =>
    Thread.sleep(1000)
    a * 10
  }

  val sink = Sink.foreach(println)

  //Will be executed by one actor hence runs synchronous
  //  source.via(flow1).via(flow2).to(sink).run()


  // async boundaries
  //Will be executed by multiple actors hence runs asynchronous
  source.via(flow1).async.via(flow2).async.to(sink).run()


//  source
//    .map{ a => println(s"Flow : A ${a}"); a }
//    .map{ a => println(s"Flow : B ${a}"); a }
//    .map{ a => println(s"Flow : C ${a}"); a }
//    .runWith(Sink.ignore)


  //  Relative order of elements within the components are guaranteed
  source
    .map{ a => println(s"Flow : A ${a}"); a }.async
    .map{ a => println(s"Flow : B ${a}"); a }.async
    .map{ a => println(s"Flow : C ${a}"); a }.async
    .runWith(Sink.ignore)
}
