package com.chethan.slick

import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter

import scala.math.BigDecimal.RoundingMode

/**
  * Created by Chethan on Nov 06, 2024.
  */

object HackerR {
  def print(arr: Array[Int]): Unit = {
    val (negatives, others) = arr.partition(_ < 0)
    val (positives, zeros)  = others.partition(_ > 0)
    println(negatives.length.toDouble / arr.length.toDouble)
    println(arr.length.toDouble)
    println("positives : " + BigDecimal.decimal(positives.length.toDouble / arr.length.toDouble).setScale(5, RoundingMode.DOWN))
    println("negatives : " + BigDecimal.decimal(negatives.length.toDouble / arr.length.toDouble).setScale(5, RoundingMode.DOWN))
    println("zeros : " + BigDecimal.decimal(zeros.length.toDouble / arr.length.toDouble).setScale(5, scala.math.BigDecimal.RoundingMode.DOWN))


    //    val totalSize = arr.length
    //    val (negatives, others) = arr.partition(_ < 0)
    //    val (positives, zeros) = others.partition(_ > 0)
    //    println(scala.math.BigDecimal.decimal(positives.length.toDouble / totalSize.toDouble)
    //                 .setScale(totalSize,scala.math.BigDecimal.RoundingMode.HALF_UP))
    //    println(scala.math.BigDecimal.decimal(negatives.length.toDouble / totalSize.toDouble)
    //                 .setScale(totalSize,scala.math.BigDecimal.RoundingMode.HALF_UP))
    //    println(scala.math.BigDecimal. decimal(zeros.length.toDouble / totalSize.toDouble)
    //                 .setScale(totalSize,scala.math.BigDecimal.RoundingMode.HALF_UP))

  }


}

//object Application extends App {
//
//  val n = StdIn.readLine.trim.toInt
//
//  val arr = StdIn.readLine.replaceAll("\\s+$", "").split(" ").map(_.trim.toInt)
//  HackerR.print(arr)
//}


object MinMax extends App {

  def get(arr: Array[Int]) = {
    val head: Long        = arr.head
    val tail: Array[Long] = arr.tail.map(_.toLong)
    val tailSum           = tail.sum

    val listWithIndex = tail.zipWithIndex

    val result = listWithIndex.map{ case (_, index) =>
      val sum = listWithIndex.collect{ case (a, i) if i != index => a }.sum
      sum + head
    } :+ tailSum

    print(result.min + " " + result.max)

  }

  get(Array(256741038, 623958417, 467905213, 714532089, 938071625))

  //  println(Array(256741038, 623958417, 467905213, 714532089, 938071625).sum)
}


object TimeConversion extends App {

  def get(time: String) = {
    val isPM = time.toLowerCase.endsWith("pm")
    if(isPM){
        LocalTime.parse("",DateTimeFormatter.ofPattern("HH:mm:ss"))
      println()
      }
    else println(time.toUpperCase)
  }

 println( new SimpleDateFormat("hh:mm").format("03:30"))
//  println(LocalTime.parse("03:30:20a",DateTimeFormatter.ofPattern("HH:mm:ss")))
}



