package com.chethan.akka.streams.basics

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, RunnableGraph, Sink, Source}
import akka.{Done, NotUsed}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Chethan on Jan 29, 2025.
  */

object FirstPrinciples extends App {

  private implicit val actorSystem      : ActorSystem       = ActorSystem("FirstPrinciples")
  private          val actorMaterializer: ActorMaterializer = ActorMaterializer()

  private val source: Source[Int, NotUsed]    = Source(1 to 10)
  private val sink  : Sink[Int, Future[Done]] = Sink.foreach[Int](a => println(a * 10))

  //Graph is a blueprint for a stream
  val graph: RunnableGraph[NotUsed] = source.to(sink)

  //  graph.run()

  // Running a graph allocates the right resources
  // actors, thread pools
  // sockets, connections
  // etc - everything is transparent

  // Materialization - Execution of graph
  //Running the graph is also called materializing the graph which returns materialized values.
  // materializing graph == materializing all the components
  // each component produces a materialized value when run
  // the graph produce a single materialized value
  // A materialized value can be anything
  // A component can materialize multiple item
  // You can reuse the same component in different graphs
  // Different runs = different materialization.

  private val flow: Flow[Int, Int, NotUsed] = Flow[Int].map(_ + 1)

  private val sourceWithFlow: Source[Int, NotUsed] = source.via(flow)

  private val flowToSink: Sink[Int, NotUsed] = flow.to(sink)

  //  sourceWithFlow.to(sink).run()
  //  source.to(flowToSink).run()
  //  source.via(flow).to(sink).run()

  //  nulls are not allowed.
  //  private val illegalSource: Source[String, NotUsed] = Source.single[String](null)

  private val infiniteStream: Source[Int, NotUsed] = Source(Stream.from(1))

  private val futureSource: Source[Int, NotUsed] = Source.future(Future(42))

  private val boringSink: Sink[Any, Future[Done]] = Sink.ignore

  private val forEachSink: Sink[String, Future[Done]] = Sink.foreach[String](println)

  private val headSink: Sink[Int, Future[Int]] = Sink.head[Int] // Only retrieves head and closed the stream

  private val foldSink: Sink[Int, Future[Int]] = Sink.fold[Int, Int](0)((a, b) => a + b)

  private val mapFlow: Flow[Int, Int, NotUsed] = Flow[Int].map(a => a + 1)

  private val takeFlow: Flow[Int, Int, NotUsed] = Flow[Int].take(5)
  // There is no flatMap


  source.via(mapFlow).via(takeFlow).via(flow).to(sink)

  source.map(x => x + 1) //Syntactic sugar equivalent to source.via(Flow[Int].map(x => x+1))

  //  source.runForeach(println) //Syntactic sugar equivalent to source.to(Sink[Int].foreach(println)).run()


  //Assignment
  //  Source[String](Stream("Tokyo", "Burlin", "Rio", "Denver", "Helsynky")).via(Flow[String].filter(_.length > 5)).via(Flow[String].take(2)).to(Sink.foreach(println)).run()
  Source[String](Stream("Tokyo", "Burlin", "Rio", "Denver", "Helsynky")).filter(_.length > 5).take(2).runForeach(println)
}
