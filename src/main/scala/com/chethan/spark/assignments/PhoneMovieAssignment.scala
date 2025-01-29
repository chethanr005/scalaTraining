package com.chethan.spark.assignments

import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by $CapName on Jan 20, 2025.
  */

object PhoneMovieAssignment extends App {

  val sparkSession = SparkSession.builder().appName("Phone Movie Assignment").config("spark.master", "local").getOrCreate()

  val phoneStructure = StructType(Seq(
    StructField("Make", StringType, true),
    StructField("Model", StringType, true),
    StructField("Screen", DoubleType, true),
    StructField("Camera", LongType, true)
  ))

  val rows = Seq(
    ("Samsung", "S25 Ultra", 7.1, 100),
    ("Apple", "SamePhone17", 6.8, 50),
    ("Vivo", "Find100x", 6.9, 200),
    ("Oppo", "Reno30Pro", 6.8, 200),
    ("Asus", "ZenPhone3", 6.2, 48),
    ("Nokia", "3110", 1.2, 0),
  )

  //    private val dataFrame     : DataFrame = sparkSession.createDataFrame(rows)
  //    private val phoneDataFrame: DataFrame = dataFrame.toDF("Make", "Model", "Screen", "Camera")
  //    phoneDataFrame.show()


  private val moviesDataFrame: DataFrame = sparkSession.read.format("json").options(Map("inferSchema" -> "true")).load("G:\\Scala_Training_Global\\scalaTraining\\src\\main\\resources\\data\\movies.json")

//  moviesDataFrame.show()
  println(moviesDataFrame.count())
  moviesDataFrame.printSchema()


}
