package com.chethan.mongo

import java.util.concurrent.{Executor, ExecutorService}

import com.mongodb.client.{MongoClient, MongoClients}

import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * Created by Chethan on Dec 22, 2022.
  */

class MongoConnection {

  def getMongoClient(username: String, password: String, host: String, port: Int): MongoClient = {
    val connectionUri = s"""mongo:db//$username:$password@$host:$port"""
    MongoClients.create("mongodb://localhost:27017/")
  }
}

object Test extends App {

  println("done")


  val flat = List(List(1, 2, 3, List(1, 1, 1)), List(7, 8, 9), List(4, 5, 6))
  println(flat.flatMap(a => a))
  println(flat.flatten)


  val e = new Executor {
    override def execute(command: Runnable): Unit = 10
  }

 val a=  ExecutionContext.fromExecutor(e)
  val future = Future(10)(a)

//  def promise(implicit context: ExecutionContext): Future[Int] = {
//    val pro = Promise[Int]()
//    context.execute{ () =>
//      pro.success(10)
//    }
//    pro.future
//  }

}