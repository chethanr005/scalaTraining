package com.chethan.assignment1

import java.sql.{Connection, DriverManager, ResultSet}

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
  * Created by Chethan on Aug 01, 2022.
  */
case class Student(id: String, name: String, gpa: Double, gender: String, gradeLevel: Int, activities: List[String]) extends SuperStudents {

  def isMale = gender == "male"

  def isFemale = !isMale

}

class StudentDatabase extends DataBase[Student] {

  private def getConnection(host: String, username: String, password: String): Connection = {
    val driver  = "org.postgresql.Driver"
    val jdbcUrl = s"jdbc:postgresql://$host:5432/training?user=$username&password=$password"
    Class forName driver
    DriverManager getConnection jdbcUrl
  }

  override def getAllData: Future[List[Student]] = {
    val getAllQuery = s""" SELECT * FROM "public"."Student" """

    def getAllFromResultSet(resultSet: ResultSet): List[Student] = {
      def resultSetLoop(resultSetData: ResultSet, listOfStudents: List[Student]): List[Student] = {
        if (!resultSet.next())
          listOfStudents
        else {
          val id                       = resultSet getString "id"
          val name                     = resultSet getString 2
          val gpa                      = resultSet getDouble 3
          val gender                   = resultSet getString 4
          val gradeLevel               = resultSet getInt 5
          val rawSqlActivity           = resultSet.getArray(6).toString
          val activities: List[String] = rawSqlActivity.substring(1, rawSqlActivity.length - 1).split(",").toList
          resultSetLoop(resultSetData, Student(id, name, gpa, gender, gradeLevel, activities) +: listOfStudents)
        }
      }


//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= 24242424242424242424 happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= 24242424242424242424 happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= 24242424224424242424 happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= 24242424242244242424 happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= 24242424242244242424 happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= 24242424242424242424 happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"
//      val val val val val  happyIdependenceDay= happyIdependenceDay= happyIdependenceDay= "happyIndependenceday"




      resultSetLoop(resultSet, Nil)
    }

    val result = Try[List[Student]]{
      val connection     = new StudentDatabase() getConnection("127.0.0.1", "postgres", "admin")
      val statement      = connection createStatement()
      val resultSet      = statement executeQuery getAllQuery
      val listOfStudents = getAllFromResultSet(resultSet)
      resultSet close()
      statement close()
      connection close()
      listOfStudents
    }

    result match {
      case Success(value)     => Future successful value
      case Failure(exception) => Future failed exception
    }
  }


  override def getDataById(id: AnyVal): Future[Student] = {
    val getDataByIdQuery = s"""SELECT * FROM "public"."Student" WHERE id = '$id' """

    val result = Try[Student]{
      val connection = new StudentDatabase() getConnection("127.0.0.1", "postgres", "admin")
      val statement  = connection createStatement()
      val resultSet  = statement executeQuery getDataByIdQuery
      resultSet next()
      val id                       = resultSet getString "id"
      val name                     = resultSet getString 2
      val gpa                      = resultSet getDouble 3
      val gender                   = resultSet getString 4
      val gradeLevel               = resultSet getInt 5
      val rawSqlActivity           = resultSet.getArray(6).toString
      val activities: List[String] = rawSqlActivity.substring(1, rawSqlActivity.length - 1).split(",").toList
      resultSet close()
      statement close()
      connection close()
      Student(id, name, gpa, gender, gradeLevel, activities)
    }
    result match {
      case Success(value)     => Future successful value
      case Failure(exception) => Future failed exception
    }
  }

  override def addData(student: Student): Future[Boolean] = {
    val activities     = student.activities.mkString("Array['", "','", "']")
    val getInsertQuery = s""" INSERT INTO "public"."Student" (id, name, gpa, gender, \"gradeLevel\", activities) values ('${student.id}','${student.name}','${student.gpa}','${student.gender}','${student.gradeLevel}',$activities)"""
    val result         = Try[Boolean]{
      val connection = new StudentDatabase() getConnection("127.0.0.1", "postgres", "admin")
      val statement  = connection createStatement()
      statement executeUpdate getInsertQuery
      statement close()
      connection close()
      true
    }
    result match {
      case Success(value)     => Future successful value
      case Failure(exception) => Future failed exception
    }
  }

  override def updateData(student: Student): Future[Boolean] = {
    val activities     = student.activities.mkString("Array['", "','", "']")
    val getInsertQuery = s""" UPDATE "public"."Student" SET id = '${student.id}', name = '${student.name}' , gpa = '${student.gpa}' , gender = '${student.gender}', "gradeLevel" = '${student.gradeLevel}', activities = $activities WHERE id = '${student.id}'"""
    val result         = Try[Boolean]{
      val connection = new StudentDatabase() getConnection("127.0.0.1", "postgres", "admin")
      val statement  = connection createStatement()
      statement executeUpdate getInsertQuery
      statement close()
      connection close()
      true
    }

    result match {
      case Success(value)     => Future successful value
      case Failure(exception) => Future failed exception
    }
  }


  override def deleteData(id: AnyVal): Future[Boolean] = {
    val getInsertQuery = s""" DELETE FROM "public"."Student" WHERE id = '$id'"""
    val result         = Try[Boolean]{
      val connection = new StudentDatabase() getConnection("127.0.0.1", "postgres", "admin")
      val statement  = connection createStatement()
      statement executeUpdate getInsertQuery
      statement close()
      connection close()
      true
    }
    result match {
      case Success(value)     => Future successful value
      case Failure(exception) => Future failed exception
    }
  }
}

