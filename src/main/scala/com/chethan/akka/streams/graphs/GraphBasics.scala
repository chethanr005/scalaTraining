package com.chethan.akka.streams.graphs

import akka.NotUsed
import akka.stream.scaladsl.{Broadcast, GraphDSL, RunnableGraph, Source, Zip}

/**
  * Created by Chethan on Jan 30, 2025.
  */

object GraphBasics extends App {

  val source: Source[Int, NotUsed] = Source(1 to 10)

  val graph = RunnableGraph.fromGraph{
    GraphDSL.create(){ builder: GraphDSL.Builder[NotUsed] =>


      val broadcast = builder.add(Broadcast[Int](2))
      val zip       = builder.add(Zip[Int, Int])

      ???
    }
  }

  List(1, 2, 3, 4).map{ a =>


  }
}
