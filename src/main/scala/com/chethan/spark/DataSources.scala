package com.chethan.spark

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types._

/**
  * Created by $CapName on Jan 20, 2025.
  */

object DataSources extends App {

  private val sparkSession: SparkSession = SparkSession.builder().appName("Data Sources and Formats").config("spark.master", "local").getOrCreate()


  val carSchema = StructType(Array(
    StructField("Model", StringType, true),
    StructField("Power", LongType, true),
    StructField("RegistrationDate", DateType, true),
    StructField("color", StringType, true),
  ))


  val carsDataFrame = sparkSession.read
                                  .format("json")
                                  .schema(carSchema)
                                  .options(Map("path" -> "G:\\Scala_Training_Global\\scalaTraining\\src\\main\\resources\\data\\Cars.json",
                                    //                                    "mode" -> "failFast",
                                    //                                    "mode" -> "dropMalformed",
                                    //                                    "mode" -> "permessive",
                                    //                                     inferSchema -> true
                                  ))
                                  .load()


  //  carsDataFrame.show()

  carsDataFrame.write
               .format("json")
               .mode(SaveMode.Overwrite)
               .save("G:\\Scala_Training_Global\\scalaTraining\\src\\main\\resources\\data\\WriteCars.json")



}
