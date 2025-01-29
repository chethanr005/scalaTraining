package com.chethan.spark

import java.util

import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, DataFrameReader, Row, SparkSession}

/**
  * Created by $CapName on Jan 16, 2025.
  */

object DataFramesBasics extends App {


  private val sparkSession: SparkSession = SparkSession.builder().appName("DataFrames Basics").config("spark.master", "local").getOrCreate()

  private val dataFrameReader: DataFrameReader = sparkSession.read

  private val dataFrame: DataFrame = dataFrameReader.format("json")
                                                    //in production inferschema - true should not be used but rather own schema should be defined
                                                    .options(Map("inferSchema" -> "true"))
                                                    .load("G:\\Scala_Training_Global\\scalaTraining\\src\\main\\resources\\data\\Cars.json")

  //  dataFrame.show()

  //  dataFrame.printSchema()

  //  dataFrame.take(2).foreach(println)


  //Creating Schema Structure
  val carSchema = StructType(Array(
    StructField("Model", StringType, true),
    StructField("Power", LongType, true),
    StructField("RegistrationDate", DateType, true),
    StructField("color", StringType, true),
  ))

  //      Obtaining the schema structure from dataframe
  println(dataFrame.schema)

  //Reading data by providing custom schema
  val customSchemaFrame = sparkSession.read.format("json").schema(carSchema).load("G:\\Scala_Training_Global\\scalaTraining\\src\\main\\resources\\data\\Cars.json")
  //  customSchemaFrame.show()
  //  customSchemaFrame.printSchema()


  val supraRow = Row("Supra", 375, "2025-01-01", "Red")

  val cars = Seq(
    ("Octavia VRS", 245, "2025-01-01", "electricBlue"),
    ("Mustang", 320, "2025-01-01", "Black"),
    ("Corvette", 420, "2025-01-01", "Yellow"),
    ("Godzilla", 625, "2025-01-01", "CopperBronze")
  )

  val manualCarsDF = sparkSession.createDataFrame(cars) // schema auto-inferred

  //  manualCarsDF.show()

  import sparkSession.implicits._

  val manualCarsWithImplicits = cars.toDF("Model", "Power", "RegistrationDate", "Color")
  manualCarsWithImplicits.show()

}

