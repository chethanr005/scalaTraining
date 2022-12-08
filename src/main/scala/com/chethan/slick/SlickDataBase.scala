package com.chethan.slick

import slick.jdbc.PostgresProfile.api._
import slick.jdbc.{GetResult, JdbcBackend, PostgresProfile}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Created by Chethan on Oct 10, 2022.
  */

object SlickDataBase {

  val query = sql"""select * from Student"""


  def databaseConnectionFromConfigFile: PostgresProfile.backend.Database = {
    Database.forConfig("postgres")
  }


  def databaseConnectionFromUrl: JdbcBackend.DatabaseDef = {
    JdbcBackend.Database.forURL("jdbc:postgresql://127.0.0.1/training", user = "postgres", password = "admin", driver = "org.postgresql.Driver")
  }

  def getAll(): Future[Seq[Student]] = {
    for {
      _ <- Future.unit
      db = databaseConnectionFromUrl
      studentTable: TableQuery[StudentTable] = TableQuery[StudentTable]
      query = studentTable
      result <- db.run[Seq[Student]](query.result)
    } yield result
  }

  def getByFilter(): Future[Seq[Student]] = {
    for {
      _ <- Future.unit
      db = databaseConnectionFromUrl
      studentTable: TableQuery[StudentTable] = TableQuery[StudentTable]
      query = studentTable.filter(_.gender === "female").filter(_.about === "athletic")
      result <- db.run[Seq[Student]](query.result)
    } yield result
  }

  def insert(studentsList: Seq[Student]): Future[Boolean] = {
    for {
      _ <- Future.unit
      db = databaseConnectionFromUrl
      studentTable: TableQuery[StudentTable] = TableQuery[StudentTable]
      //      query = studentTable += students (for one record)
      query = studentTable ++= studentsList
      result <- db.run(query)
    } yield if (result == 1) true else false
  }

  def updateById(studentId: Long): Future[Boolean] = {
    for {
      _ <- Future.unit
      db = databaseConnectionFromUrl
      studentTable: TableQuery[StudentTable] = TableQuery[StudentTable]
      query = studentTable.filter(_.id === studentId).map(_.name).update("three")
      result <- db.run(query)
    } yield if (result == 1) true else false
  }

  def deleteById(studentId: Long): Future[Boolean] = {
    for {
      _ <- Future.unit
      db = databaseConnectionFromUrl
      studentTable: TableQuery[StudentTable] = TableQuery[StudentTable]
      query = studentTable.filter(_.id === studentId).delete
      result <- db.run(query)
    }
    yield if (result == 1) true else false
  }

  def crudWithOneFunction: Future[Boolean] = {
    for {
      _ <- Future.unit
      db = databaseConnectionFromUrl
      studentTable: TableQuery[StudentTable] = TableQuery[StudentTable]
      insertQuery = studentTable ++= Seq(Student(4, "dddd", "male", Some("anything")), Student(5, "eeee", "male", None), Student(7, "eeee", "male", None))
      updateQuery = studentTable.filter(_.id === 5L).map(_.name).update("Five")
      deleteQuery = studentTable.filter(_.id === 7L).delete
      readQuery = studentTable.result
      combinedAction = DBIO.seq(insertQuery, updateQuery, deleteQuery)
      result <- db.run(combinedAction.transactionally) //combinedAction.transactionally (When there are multiple queries involved,if one fails other executed queries will be rolled back))
    } yield true
  }

  def getAllFromConfig(): Future[Seq[Student]] = {
    for {
      _ <- Future.unit
      db = databaseConnectionFromConfigFile
      studentTable: TableQuery[StudentTable] = TableQuery[StudentTable]
      query = studentTable
      result <- db.run[Seq[Student]](query.result)
    } yield result
  }

  def readStudentsFromQuery: Future[Vector[Student]] = {
    implicit val getResultStudent: GetResult[Student] = GetResult(positionResult => Student(positionResult.<<, positionResult.<<, positionResult.<<, positionResult.<<))
    for {
      _ <- Future.unit
      query = sql"""SELECT * From public."Student";""".as[Student]
      db = databaseConnectionFromConfigFile
      result <- db.run(query)
    } yield result
  }

  def readEmployeesFromQuery: Future[Vector[Employee]] = {
    implicit val getResultStudent: GetResult[Employee] = GetResult(positionResult => Employee(positionResult.<<, positionResult.<<, positionResult.<<))
    for {
      _ <- Future.unit
      query = sql"""SELECT * From public."Employee";""".as[Employee]
      db = databaseConnectionFromConfigFile
      result <- db.run(query)
    } yield result
  }

  def insertToDifferentTables: Unit = {
    val db             = databaseConnectionFromConfigFile
    val studentTable   = TableQuery[StudentTable]
    val student        = Student(10, "tenten", "female", None)
    val studentQuery   = studentTable += student
    val employeeTable  = TableQuery[EmployeeTable]
    val employee       = Employee("tenten", 10, "t10")
    val employeeQuery  = employeeTable += employee
    val combinedAction = DBIO.seq(studentQuery, employeeQuery)
    val result         = db.run(combinedAction)

    result.onComplete{
      case Success(result)    => println(result)
      case Failure(exception) => throw exception
    }
    Thread.sleep(5000)
  }

  def joins(studentId: Int): Unit = {
    val db            = databaseConnectionFromConfigFile
    val stuEmpTable   = TableQuery[StuEmpTable]
    val employeeTable = TableQuery[EmployeeTable]
    val query         = stuEmpTable.filter(_.studentId === studentId).join(employeeTable).on((a, b) => a.empId === b.empId)
    //val query= stuEmpTable.filter(_.studentId=== studentId).join(employeeTable).on((a,b) => a.empId === b.empId).map(_._2) // returns only employees
    val result        = db.run(query.result)

    result.onComplete{
      case Success(result)    => println(result)
      case Failure(exception) => throw exception
    }
    Thread.sleep(5000)
  }

  def likeOperator: Unit = {
    val studentTable = TableQuery[StudentTable]
    studentTable += new Student(15, "", "", None)
    studentTable.filter(_.name.like("%A%")).delete
  }

  def enumerations(id: Int): Unit = {
    val listOfStreams          = Seq(StreamingProvider(1, MyEnums.Hulu), StreamingProvider(2, MyEnums.Netflix))
    val streamingProviderTable = TableQuery[StreamingProviderTable]
    val insertQuery            = streamingProviderTable ++= listOfStreams
    val db                     = databaseConnectionFromConfigFile
    //insertion
    db.run(insertQuery)

    //Reading
    val streamingProviderTable2 = TableQuery[StreamingProviderTable]

    val readQuery = streamingProviderTable2.filter(_.id === id)
    db.run(readQuery.result)
  }

  def dataTypeOfTypeList() = {
    for {
      _ <- Future.unit
      db = databaseConnectionFromUrl
      insertQuery= TableQuery[Student1Table] += Student1("r12","eleven",10.14,"male",10,List("Nothing"))
      _ <- db.run(insertQuery)

      studentTable = TableQuery[Student1Table]
      result <- db.run(studentTable.result)
    } yield result
  }
}


