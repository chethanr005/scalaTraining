package com.chethan.akka.others

import scala.collection.mutable
import scala.util.Random

/**
  * Created by Chethan on Mar 28, 2023.
  */

object ConPro extends App {

  def proConBuffer = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]()
    val capacity                   = 3
    val random                     = new Random()

    val producer = new Thread(() => {
      var value = 0
      while (true) {
        buffer.synchronized{
          if (buffer.size == capacity) {
            println("[Producer] - Container is full !!!")
            buffer.wait()
          }
          println("[Producer] -  Producing the value...")
          value += 1
          buffer.enqueue(value)
          buffer.notify()
        }
        Thread.sleep(random.nextInt(3000))
      }
    })


    val consumer = new Thread(() => {
      while (true) {
        buffer.synchronized{
          if (buffer.isEmpty) {
            println("[Consumer] - Container is Empty !!! I am waiting")
            buffer.wait()
          }
          val value = buffer.dequeue()
          println("[Consumer] - Consuming the value \"" + value + "\"")
          buffer.notify()
        }
        Thread.sleep(random.nextInt(3000))
      }
    })

    producer.start()
    consumer.start()
    // Thread.sleep(3000)
  }

  proConBuffer
}


object ConProLevel3 extends App {

  def proConBuffer = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]()
    val capacity                   = 3
    val random                     = new Random()

    val producer1 = new Thread(() => {
      var value = 0
      while (true) {
        buffer.synchronized{
          if (buffer.size == capacity) {
            println("[Pro 111] - Container is full !!!")
            buffer.wait()
          }
          println("[Pro 111] -  Producing the value...")
          value += 1
          buffer.enqueue(value)
          buffer.notify()
        }
        Thread.sleep(random.nextInt(3000))
      }
    })

    val producer2 = new Thread(() => {
      var value = 0
      while (true) {
        buffer.synchronized{
          if (buffer.size == capacity) {
            println("[Pro 222] - Container is full !!!")
            buffer.wait()
          }
          println("[Pro 222] -  Producing the value...")
          value += 1
          buffer.enqueue(value)
          buffer.notify()
        }
        Thread.sleep(random.nextInt(3000))
      }
    })

    val producer3 = new Thread(() => {
      var value = 0
      while (true) {
        buffer.synchronized{
          if (buffer.size == capacity) {
            println("[Pro 333] - Container is full !!!")
            buffer.wait()
          }
          println("[Pro 333] -  Producing the value...")
          value += 1
          buffer.enqueue(value)
          buffer.notify()
        }
        Thread.sleep(random.nextInt(3000))
      }
    })


    val consumer1 = new Thread(() => {
      while (true) {
        buffer.synchronized{
          if (buffer.isEmpty) {
            println("[Cons 111] - Container is Empty !!! I am waiting")
            buffer.wait()
          }
          val value = buffer.dequeue()
          println("[Cons 111] - Consuming the value \"" + value + "\"")
          buffer.notify()
        }
        Thread.sleep(random.nextInt(3000))
      }
    })

    val consumer2 = new Thread(() => {
      while (true) {
        buffer.synchronized{
          if (buffer.isEmpty) {
            println("[Cons 222] - Container is Empty !!! I am waiting")
            buffer.wait()
          }
          val value = buffer.dequeue()
          println("[Cons 222] - Consuming the value \"" + value + "\"")
          buffer.notify()
        }
        Thread.sleep(random.nextInt(3000))
      }
    })

    val consumer3 = new Thread(() => {
      while (true) {
        buffer.synchronized{
          if (buffer.isEmpty) {
            println("[Cons 333] - Container is Empty !!! I am waiting")
            buffer.wait()
          }
          val value = buffer.dequeue()
          println("[Cons 333] - Consuming the value \"" + value + "\"")
          buffer.notify()
        }
        Thread.sleep(random.nextInt(3000))
      }
    })


    producer1.start()
    producer2.start()
    producer3.start()
    consumer1.start()
    consumer2.start()
    consumer3.start()
    // Thread.sleep(3000)
  }

  proConBuffer
}
