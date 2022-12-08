package com.chethan.slick

import java.io.File

import slick.jdbc.PostgresProfile

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Chethan on Dec 07, 2022.
  */

object Codegen extends App {
  slick.jdbc.PostgresProfile

  val path=new File(".").getCanonicalPath

  val url = "jdbc:postgresql://127.0.0.1/training"
  val db  = slick.codegen.SourceCodeGenerator.main(
    Array("slick.jdbc.PostgresProfile",
      "org.postgresql.ds.PGSimpleDataSource",
      url,
      path,
      "com.chethan.slick.Codegen",
      "postgres",
      "admin"
    ))


  val a= PostgresProfile.createModel(Some(PostgresProfile.defaultTables))
  println(a)


}
