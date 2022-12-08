package com.chethan.slick

import slick.jdbc.PostgresProfile.api._


/**
  * Created by Chethan on Nov 11, 2022.
  */

class StudentTable(tag: slick.lifted.Tag) extends Table[Student](tag, Some("public"), "Student") {

  val id    : Rep[Long]           = column[Long]("id", O.PrimaryKey)
  val name  : Rep[String]         = column[String]("name")
  val gender: Rep[String]         = column[String]("gender")
  val about : Rep[Option[String]] = column[Option[String]]("about")

  override def * = (id, name, gender, about) <> (Student.tupled, Student.unapply)

}


case class Student(id: Long, name: String, gender: String, about: Option[String])


//Employee class

case class Employee(name: String, rollNo: Int, empId: String)

class EmployeeTable(tag: slick.lifted.Tag) extends Table[Employee](tag, Some("public"), "Employee") {

  val name  : Rep[String] = column[String]("name")
  val rollNo: Rep[Int]    = column[Int]("rollno")
  val empId : Rep[String] = column[String]("empId", O.PrimaryKey)

  override def * = (name, rollNo, empId) <> (Employee.tupled, Employee.unapply)
}

case class StuEmp(id: Int, studentId: Int, empId: String)

class StuEmpTable(tag: slick.lifted.Tag) extends Table[StuEmp](tag, Some("public"), "StuEmp") {

  val id       : Rep[Int]    = column[Int]("id", O.PrimaryKey)
  val studentId: Rep[Int]    = column[Int]("studentId")
  val empId    : Rep[String] = column[String]("empId")

  override def * = (id, studentId, empId) <> (StuEmp.tupled, StuEmp.unapply)
}

//Department Class
case class Student1(id: String, name: String, gpa: Double, gender: String, gradeLevel: Int, activities: List[String])


class Student1Table(tag: slick.lifted.Tag) extends Table[Student1](tag, Some("public"), "Student") {

  import CustomPostgresProfile.api._

  val id        : Rep[String]       = column[String]("id", O.PrimaryKey)
  val name      : Rep[String]       = column[String]("name")
  val gpa       : Rep[Double]       = column[Double]("gpa")
  val gender    : Rep[String]       = column[String]("gender")
  val gradeLevel: Rep[Int]       = column[Int]("gradeLevel")
  val activities: Rep[List[String]] = column[List[String]]("activities")

  override def * = (id, name, gpa, gender, gradeLevel, activities) <> (Student1.tupled, Student1.unapply)
}

object MyEnums extends Enumeration {
  type Provider = Value
  val Netflix = Value("Netflix")
  val Disney  = Value("Disney")
  val Prime   = Value("Prime")
  val Hulu    = Value("Hulu")
}

//Enumerations
case class StreamingProvider(id: Int, streamingProvider: MyEnums.Provider)

class StreamingProviderTable(tag: Tag) extends Table[StreamingProvider](tag, Some("public"), "StreamingProvider") {
  implicit val enumsConverter = MappedColumnType.base[MyEnums.Provider, String](provider => provider.toString, string => MyEnums.withName(string))

  val id               : Rep[Int]              = column[Int]("id", O.PrimaryKey)
  val streamingProvider: Rep[MyEnums.Provider] = column[MyEnums.Provider]("StreamingProvider")

  override def * = (id, streamingProvider) <> (StreamingProvider.tupled, StreamingProvider.unapply)
}


object aa {
  def main(args: Array[String]): Unit = {
    println(classOf[Seq[String]])
    println()
    val db      = SlickDataBase.databaseConnectionFromConfigFile
    val student = TableQuery[StudentTable]
    val ddl     = List(student).map(_.schema).reduce(_ ++ _)
    println(student.schema.createIfNotExistsStatements.mkString(""))
  }
}
