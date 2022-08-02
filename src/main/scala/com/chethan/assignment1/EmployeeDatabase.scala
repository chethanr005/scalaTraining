package com.chethan.assignment1

import java.sql._
import java.time.format.DateTimeFormatter
import java.time.{LocalDate, Period}

import scala.concurrent._
import scala.util.{Failure, Success, Try}


/**
  * Created by Chethan on Jun 13, 2022.
  */

class EmployeeDatabase extends DataBase[Employee] {

  private def getConnection(host: String, username: String, password: String): Connection = {
    val driver  = "org.postgresql.Driver"
    val jdbcUrl = s"jdbc:postgresql://$host:5432/training?user=$username&password=$password"
    Class forName driver
    DriverManager getConnection jdbcUrl
  }

  override def getAllData: Future[List[Employee]] = {
    val getAllQuery = s""" SELECT * FROM "public"."Employee" """
    val format      = DateTimeFormatter ISO_LOCAL_DATE

    def getAllFromResultSet(resultSet: ResultSet): List[Employee] = {
      def resultSetLoop(resultSetData: ResultSet, listOfEmployees: List[Employee]): List[Employee] = {
        if (!resultSet.next())
          listOfEmployees
        else {
          val id          = resultSet getInt "id"
          val name        = resultSet getString 2
          val department  = resultSet getString 3
          val salary      = resultSet getDouble 4
          val gender      = resultSet getString 5
          val joiningDate = LocalDate parse(resultSet getString 6, format)
          val dob         = LocalDate parse(resultSet getString 7, format)
          val jobLevel    = resultSet getString 8
          resultSetLoop(resultSetData, Employee(id, name, department, salary, gender, joiningDate, dob, jobLevel) :: listOfEmployees)
        }
      }

      resultSetLoop(resultSet, Nil)
    }

    val result = Try[List[Employee]]{
      val connection      = new EmployeeDatabase() getConnection("127.0.0.1", "postgres", "admin")
      val statement       = connection createStatement()
      val resultSet       = statement executeQuery getAllQuery
      val listOfEmployees = getAllFromResultSet(resultSet)
      resultSet close()
      statement close()
      connection close()
      listOfEmployees
    }

    result match {
      case Success(value)     => Future successful value
      case Failure(exception) => Future failed exception
    }
  }

  override def getDataById(id: Any): Future[Employee] = {
    val format           = DateTimeFormatter ISO_LOCAL_DATE
    val getDataByIdQuery = s"""SELECT * FROM "public"."Employee" WHERE id = $id """

    val result = Try[Employee]{
      val connection = new EmployeeDatabase() getConnection("127.0.0.1", "postgres", "admin")
      val statement  = connection createStatement()
      val resultSet  = statement executeQuery getDataByIdQuery
      resultSet next()
      val dataId      = resultSet getInt "id"
      val name        = resultSet getString 2
      val department  = resultSet getString 3
      val salary      = resultSet getDouble 4
      val gender      = resultSet getString 5
      val joiningDate = LocalDate parse(resultSet getString 6, format)
      val dob         = LocalDate parse(resultSet getString 7, format)
      val jobLevel    = resultSet getString 8
      resultSet close()
      statement close()
      connection close()
      Employee(dataId, name, department, salary, gender, joiningDate, dob, jobLevel)
    }
    result match {
      case Success(value)     => Future successful value
      case Failure(exception) => Future failed exception
    }
  }

  override def addData(employee: Employee): Future[Boolean] = {
    val getInsertQuery =
      s""" INSERT INTO "public"."Employee" (id, name, department, salary, gender, \"joiningDate\", dob, \"jobLevel\") values ('${employee.id}','${employee.name}','${employee.department}','${employee.salary}','${employee.gender}','${employee.joiningDate}','${employee.dob}','${employee.jobLevel}')"""

    val result = Try[Boolean]{
      val connection = new EmployeeDatabase() getConnection("127.0.0.1", "postgres", "admin")
      val statement  = connection createStatement()
      val isSuccess  = if (Period.between(employee.dob, LocalDate.now).getYears >= 21 && !Period.between(employee.joiningDate, LocalDate.now).isNegative && Period.between(employee.joiningDate, employee.dob).isNegative) {
        statement executeUpdate getInsertQuery
        true
      } else throw new RuntimeException
      statement close()
      connection close()
      isSuccess
    }
    result match {
      case Success(value)     => Future successful value
      case Failure(exception) => Future failed exception
    }
  }

  override def updateData(employee: Employee): Future[Boolean] = {
    val getInsertQuery =
      s""" UPDATE "public"."Employee" SET id = '${employee.id}', name = '${employee.name}' , department = '${employee.department}' , salary = '${employee.salary}', gender = '${employee.gender}', \"joiningDate\" = '${employee.joiningDate}',dob = '${employee.dob}', \"jobLevel\" = '${employee.jobLevel}' WHERE id = '${employee.id}'"""
    val result         = Try[Boolean]{
      val connection = new EmployeeDatabase() getConnection("127.0.0.1", "postgres", "admin")
      val statement  = connection createStatement()
      val isSuccess  = if (Period.between(employee.dob, LocalDate.now).getYears >= 21 && !Period.between(employee.joiningDate, LocalDate.now).isNegative && Period.between(employee.joiningDate, employee.dob).isNegative) {
        statement executeUpdate getInsertQuery
        true
      } else false
      statement close()
      connection close()
      isSuccess
    }

    result match {
      case Success(value)     => Future successful value
      case Failure(exception) => Future failed exception
    }
  }

  override def deleteData(id: Any): Future[Boolean] = {
    val getInsertQuery = s""" DELETE FROM "public"."Employee" WHERE id = '$id'"""
    val result         = Try[Boolean]{
      val connection = new EmployeeDatabase() getConnection("127.0.0.1", "postgres", "admin")
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

case class Employee(id: Int, name: String, department: String, salary: Double, gender: String, joiningDate: LocalDate, dob: LocalDate, jobLevel: String)




