package com.chethan.slick.Codegen
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends Tables {
  val profile = slick.jdbc.PostgresProfile
}

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Employee.schema ++ Student.schema ++ Test.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Employee
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(varchar)
   *  @param department Database column department SqlType(varchar)
   *  @param salary Database column salary SqlType(numeric)
   *  @param gender Database column gender SqlType(varchar)
   *  @param joiningdate Database column joiningDate SqlType(date)
   *  @param dob Database column dob SqlType(date)
   *  @param joblevel Database column jobLevel SqlType(varchar) */
  case class EmployeeRow(id: Long, name: String, department: String, salary: scala.math.BigDecimal, gender: String, joiningdate: java.sql.Date, dob: java.sql.Date, joblevel: String)
  /** GetResult implicit for fetching EmployeeRow objects using plain SQL queries */
  implicit def GetResultEmployeeRow(implicit e0: GR[Long], e1: GR[String], e2: GR[scala.math.BigDecimal], e3: GR[java.sql.Date]): GR[EmployeeRow] = GR{
    prs => import prs._
    EmployeeRow.tupled((<<[Long], <<[String], <<[String], <<[scala.math.BigDecimal], <<[String], <<[java.sql.Date], <<[java.sql.Date], <<[String]))
  }
  /** Table description of table Employee. Objects of this class serve as prototypes for rows in queries. */
  class Employee(_tableTag: Tag) extends profile.api.Table[EmployeeRow](_tableTag, "Employee") {
    def * = (id, name, department, salary, gender, joiningdate, dob, joblevel).<>(EmployeeRow.tupled, EmployeeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(name), Rep.Some(department), Rep.Some(salary), Rep.Some(gender), Rep.Some(joiningdate), Rep.Some(dob), Rep.Some(joblevel))).shaped.<>({r=>import r._; _1.map(_=> EmployeeRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar) */
    val name: Rep[String] = column[String]("name")
    /** Database column department SqlType(varchar) */
    val department: Rep[String] = column[String]("department")
    /** Database column salary SqlType(numeric) */
    val salary: Rep[scala.math.BigDecimal] = column[scala.math.BigDecimal]("salary")
    /** Database column gender SqlType(varchar) */
    val gender: Rep[String] = column[String]("gender")
    /** Database column joiningDate SqlType(date) */
    val joiningdate: Rep[java.sql.Date] = column[java.sql.Date]("joiningDate")
    /** Database column dob SqlType(date) */
    val dob: Rep[java.sql.Date] = column[java.sql.Date]("dob")
    /** Database column jobLevel SqlType(varchar) */
    val joblevel: Rep[String] = column[String]("jobLevel")
  }
  /** Collection-like TableQuery object for table Employee */
  lazy val Employee = new TableQuery(tag => new Employee(tag))

  /** Entity class storing rows of table Student
   *  @param id Database column id SqlType(varchar), PrimaryKey
   *  @param name Database column name SqlType(varchar)
   *  @param gpa Database column gpa SqlType(numeric)
   *  @param gender Database column gender SqlType(varchar)
   *  @param gradelevel Database column gradeLevel SqlType(int4)
   *  @param activities Database column activities SqlType(_text), Length(2147483647,false), Default(None) */
  case class StudentRow(id: String, name: String, gpa: scala.math.BigDecimal, gender: String, gradelevel: Int, activities: Option[String] = None)
  /** GetResult implicit for fetching StudentRow objects using plain SQL queries */
  implicit def GetResultStudentRow(implicit e0: GR[String], e1: GR[scala.math.BigDecimal], e2: GR[Int], e3: GR[Option[String]]): GR[StudentRow] = GR{
    prs => import prs._
    StudentRow.tupled((<<[String], <<[String], <<[scala.math.BigDecimal], <<[String], <<[Int], <<?[String]))
  }
  /** Table description of table Student. Objects of this class serve as prototypes for rows in queries. */
  class Student(_tableTag: Tag) extends profile.api.Table[StudentRow](_tableTag, "Student") {
    def * = (id, name, gpa, gender, gradelevel, activities).<>(StudentRow.tupled, StudentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(name), Rep.Some(gpa), Rep.Some(gender), Rep.Some(gradelevel), activities)).shaped.<>({r=>import r._; _1.map(_=> StudentRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(varchar), PrimaryKey */
    val id: Rep[String] = column[String]("id", O.PrimaryKey)
    /** Database column name SqlType(varchar) */
    val name: Rep[String] = column[String]("name")
    /** Database column gpa SqlType(numeric) */
    val gpa: Rep[scala.math.BigDecimal] = column[scala.math.BigDecimal]("gpa")
    /** Database column gender SqlType(varchar) */
    val gender: Rep[String] = column[String]("gender")
    /** Database column gradeLevel SqlType(int4) */
    val gradelevel: Rep[Int] = column[Int]("gradeLevel")
    /** Database column activities SqlType(_text), Length(2147483647,false), Default(None) */
    val activities: Rep[Option[String]] = column[Option[String]]("activities", O.Length(2147483647,varying=false), O.Default(None))
  }
  /** Collection-like TableQuery object for table Student */
  lazy val Student = new TableQuery(tag => new Student(tag))

  /** Entity class storing rows of table Test
   *  @param counts Database column counts SqlType(varchar), PrimaryKey
   *  @param name Database column name SqlType(varchar)
   *  @param bdata Database column bdata SqlType(bytea), Default(None) */
  case class TestRow(counts: String, name: String, bdata: Option[Array[Byte]] = None)
  /** GetResult implicit for fetching TestRow objects using plain SQL queries */
  implicit def GetResultTestRow(implicit e0: GR[String], e1: GR[Option[Array[Byte]]]): GR[TestRow] = GR{
    prs => import prs._
    TestRow.tupled((<<[String], <<[String], <<?[Array[Byte]]))
  }
  /** Table description of table Test. Objects of this class serve as prototypes for rows in queries. */
  class Test(_tableTag: Tag) extends profile.api.Table[TestRow](_tableTag, "Test") {
    def * = (counts, name, bdata).<>(TestRow.tupled, TestRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(counts), Rep.Some(name), bdata)).shaped.<>({r=>import r._; _1.map(_=> TestRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column counts SqlType(varchar), PrimaryKey */
    val counts: Rep[String] = column[String]("counts", O.PrimaryKey)
    /** Database column name SqlType(varchar) */
    val name: Rep[String] = column[String]("name")
    /** Database column bdata SqlType(bytea), Default(None) */
    val bdata: Rep[Option[Array[Byte]]] = column[Option[Array[Byte]]]("bdata", O.Default(None))
  }
  /** Collection-like TableQuery object for table Test */
  lazy val Test = new TableQuery(tag => new Test(tag))
}
