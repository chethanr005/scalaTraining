//package com.chethan.assignment1
//
//import java.time.LocalDate
//
//import org.scalatest.concurrent.ScalaFutures
//import org.scalatest.matchers.should.Matchers
//import org.scalatest.wordspec.AnyWordSpec
//
//
///**
//  * Created by Chethan on Jul 25, 2022.
//  */
//
//class EmployeeDatabaseTesting extends AnyWordSpec with ScalaFutures with Matchers {
//  "Employee database testing" must {
//    "getAllData testing " in {
//      val getAllData = new EmployeeDatabase().getAllData
//      assert(getAllData.value.get.get.size == 13)
//    }
//
//    "get employee by id testing " in {
//      val getById = new EmployeeDatabase().getDataById(1)
//      assert(getById.value.get.get == Employee(1, "marry", "cs", 260000.0, "female", LocalDate.of(2020, 10, 15), LocalDate.of(2000, 4, 15), "junior"))
//    }
//  }
//
//  "addData testing" in {
//    val tuple = new EmployeeDatabase().addData(Employee(15, "ino", "cs", 260000.0, "female", LocalDate.of(2020, 10, 15), LocalDate.of(2000, 4, 15), "junior"))
//    Thread.sleep(1000)
//    tuple.futureValue shouldBe true
//    assert(tuple.value.get.get == true)
//  }
//
//  "updateData testing" in {
//    val updateData = new EmployeeDatabase().updateData(Employee(15, "haku", "cs", 260000.0, "female", LocalDate.of(2020, 10, 15), LocalDate.of(2000, 4, 15), "junior"))
//    assert(updateData.value.get.get)
//  }
//
//  "deleteData testing" in {
//    val deleteData = new EmployeeDatabase().deleteData(15)
//    assert(deleteData.value.get.get)
//  }
//
//  "Employee Implementation testing" must {
//    "getNoOfEmployeesByDept testing" in{
//      val getNoOfEmployeesByDept=EmployeeImplementation.getNoOfEmployeesByDept("ec")
//      Thread.sleep(1000)
//      assert(getNoOfEmployeesByDept.value.get.get == ("ec",3))
//    }
//
//    "groupEmployeesByDepartment" in {
//      val groupEmployeesByDepartment=EmployeeImplementation.groupEmployeesByDepartment
//      Thread.sleep(1000)
//      groupEmployeesByDepartment.value.get.get.foreach(println)
//    }
//
//    "increaseSalaryOfEmployees" in {
//      val increaseSalaryOfEmployees= EmployeeImplementation.increaseSalaryOfEmployees("ec", Option(10))
//      Thread.sleep(1000)
//      assert(increaseSalaryOfEmployees.value.get.get == List(true, true, true))
//    }
//
//    "promoteEmployees testing" in {
//      val promoteEmployees=EmployeeImplementation.promoteEmployees()
//      Thread.sleep(1000)
//      assert(promoteEmployees.value.get.get.size == 3)
//    }
//  }
//}
//
//
