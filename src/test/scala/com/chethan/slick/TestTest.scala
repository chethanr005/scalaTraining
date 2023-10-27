package com.chethan.slick

import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.duration._

/**
  * Created by Chethan on Nov 15, 2022.
  */

class TestTest extends AnyWordSpecLike with ScalaFutures with Matchers {

  val timeout = Timeout(10.seconds)

  "ScalaSlickTesting" should {

    "get all from database" in {
      val test         = SlickDataBase.getAll().futureValue(timeout)
      val expectedData = Vector(Student(1, "aaaa", "male", Some("nothing")), Student(2, "bbbb", "female", None), Student(3, "cccc", "female", Some("athletic")))
      assert(test.size == expectedData.size)
      assert(test == expectedData)

      expectedData shouldBe true
    }


    "get using filter from database" in {
      val test = SlickDataBase.getByFilter().futureValue(timeout)
      assert(test.size == 1)
      assert(test(0) == Student(3, "cccc", "female", Some("athletic")))
    }


    "insert to database" in {
      val student1 = Student(4, "dddd", "male", Some("anything"))
      val student2 = Student(5, "eeee", "male", None)
      val test     = SlickDataBase.insert(Seq(student1, student2)).futureValue(timeout)
      assert(test == true)
    }

    "update to database" in {
      val test = SlickDataBase.updateById(3).futureValue(timeout)
      assert(test == true)
    }

    "delete to database" in {
      val test = SlickDataBase.deleteById(4).futureValue(timeout)
      assert(test == true)
    }

    "crud with one function" in {
      val test = SlickDataBase.crudWithOneFunction.futureValue(timeout)
      assert(test == true)
    }


    "testing config connection" in {
      val test = SlickDataBase.getAllFromConfig().futureValue(timeout)
      println(test)
    }

    "read students from query" in {
      val test = SlickDataBase.readStudentsFromQuery.futureValue(timeout)
      println(test)
    }

    "read Employees from query" in {
      val test = SlickDataBase.readEmployeesFromQuery.futureValue(timeout)
      println(test)
    }

    "insert to multiple tables" in {
      val test = SlickDataBase.insertToDifferentTables
    }

    "joins" in {
      val test = SlickDataBase.joins(10)
    }

    "dataTypeOfTypeList" in {
      val test = SlickDataBase.dataTypeOfTypeList().futureValue(timeout)
      println(test)
    }

  }
}



