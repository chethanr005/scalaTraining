package com.chethan.akka.streams.basics

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Keep, RunnableGraph, Sink, Source}
import akka.{Done, NotUsed}

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Created by Chethan on Jan 30, 2025.
  */

object MaterializingStreams extends App {

  implicit private val system      : ActorSystem       = ActorSystem("MaterializingStreams")
  private          val materializer: ActorMaterializer = ActorMaterializer()


  //  private val graph      : RunnableGraph[NotUsed] = Source(1 to 10).to(Sink.foreach(println))
  //  private val unUsedGraph: NotUsed                = graph.run()

  private val source                       = Source(1 to 100)
  private val sink: Sink[Int, Future[Int]] = Sink.reduce[Int]((a, b) => a + b)

  //    private val graphValue = source.runWith(sink)

  //  graphValue.onComplete{
  //    case Success(value) => println(value)
  //    case Failure(ex)    => ???
  //  }

  private val simpleSource = Source(1 to 10)
  private val simpleFlow   = Flow[Int].map(_ + 1)
  private val simpleSink   = Sink.foreach[Int](println)

  // viaMat() takes the component that needs to be connected with the source and returns the materialized values of the source and flow
  simpleSource.viaMat(simpleFlow)((sourceMatVal, flowMatVal) => flowMatVal)
  private val value : Source[Int, NotUsed]            = simpleSource.viaMat(simpleFlow)(Keep.right)
  private val value1: Source[Int, NotUsed]            = simpleSource.viaMat(simpleFlow)(Keep.left)
  private val value2: Source[Int, (NotUsed, NotUsed)] = simpleSource.viaMat(simpleFlow)(Keep.both)
  private val value3: Source[Int, NotUsed]            = simpleSource.viaMat(simpleFlow)(Keep.none)


  // Done - Typically used together with Future to signal completion but there is no actual value completed.
  private val doneGraph: RunnableGraph[Future[Done]] = simpleSource.viaMat(simpleFlow)(Keep.right).toMat(simpleSink)(Keep.right)

  //  doneGraph.run().onComplete{
  //    case Failure(exception) => println("Stream processing failure")
  //    case Success(value)     =>
  //      println("Stream processing success")
  //      println(value)
  //  }

  //sugars
  // val sumFuture =  Source(1 to 10).runWith(Sink.reduce[Int](_ + _)) // ~ Source(1 to 10).toMat(Sink.reduce[Int](_ + _))(Keep.right).run()
  val sumFuture = Source(1 to 10).runReduce[Int](_ + _) // Syntactical sugar

  //  sumFuture.onComplete{
  //    case Failure(exception) => ???
  //    case Success(value)     => println("Sum : " + value)
  //  }

  // Backwards
  //  Sink.foreach[Int](println).runWith(Source(27 to 36))
  //  Source(27 to 36).runWith(Sink.foreach[Int](println)) // All are same
  //  Flow[Int].map(a => a).runWith(Source(27 to 36), Sink.foreach[Int](println))

  // Assignment
  private val lastInt: RunnableGraph[Future[Int]] = Source(1 to 10).toMat(Sink.last[Int])(Keep.right)
  //  lastInt.run().onComplete{
  //    case Failure(exception) => ???
  //    case Success(value)     => println("Sum : " + value)
  //  }

  //  val wordCount = Source(List("this is boss" ,"this is my pc", "this is streams demo code")).viaMat(Flow[String].map(a => a.split(" ").size))(Keep.right).toMat(Sink.fold(0)(_ +_))(Keep.right)
  //  val wordCount = Source(List("this is boss" ,"this is my pc", "this is streams demo code")).viaMat(Flow[String].map(a => a.split(" ").size))(Keep.right).toMat(Sink.reduce[Int](_ +_))(Keep.right)
  //  val wordCount = Source(List("this is boss", "this is my pc", "this is streams demo code")).viaMat(Flow[String].map(a => a.split(" ").size))(Keep.right)
  //                                                                                            .viaMat(Flow[Int].reduce(_ + _))(Keep.right)
  //
  //
  //                                                                                            .toMat(Sink.head)(Keep.right)

  import scala.concurrent.ExecutionContext.Implicits.global

//  val wordCount = Source (List("this is boss", "this is my pc", "this is streams demo code")).runWith(Sink.fold[Int, String](0)((count, sentence) => count + sentence.split(" ").size))
    val wordCount = Flow[String].map(a => a.split(" ").size).runWith(Source(List("this is boss" ,"this is my pc", "this is streams demo code")),Sink.fold(0)(_ +_))._2
    wordCount.onComplete{
    case Failure(exception) => ???
    case Success(value)     => println("Count : " + value)
  }
}
