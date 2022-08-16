package com.chethan.assignment1

import java.time.{LocalDate, Period}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Chethan on Jul 26, 2022.
  */

object EmployeeImplementation {
  val employeesListInFuture: Future[List[Employee]] = new EmployeeDatabase().getAllData

  def getNoOfEmployeesByDept(dept: String): Future[(String, Int)] =
    employeesListInFuture.map(listOfEmployees => (dept, listOfEmployees.count(employee => employee.department.equals(dept))))


  def groupEmployeesByDepartment: Future[List[EmployeesByDepartment]] = {
    for {
      employeeList <- employeesListInFuture
      employeesCount = employeeList.groupBy(employee => employee.department)
      employeesCount2 = employeeList.groupBy(employee => employee.department).map{ case (departmentName, employees) =>
        EmployeesByDepartment(departmentName, employees)
      }.toList
    } yield {
      //      EmployeesByDepartment("ec", employeesCount("ec")) +: EmployeesByDepartment("cs", employeesCount("cs")) +: EmployeesByDepartment("ee", employeesCount("ee")) +: Nil

      employeesCount2
    }
  }

  def increaseSalaryOfEmployees(department: String, hikeInPercentage: Option[Double]): Future[List[Boolean]] = {
//
//    for (employeeList <- employeesListInFuture)
//      yield if (hikeInPercentage.isDefined)
//              employeeList.filter(employee => employee.department.equals(department))
//                          .map(hikedEmployee => {
//                            val updatedEmployeeRecord = Employee(hikedEmployee.id, hikedEmployee.name, hikedEmployee.department, hikedEmployee.salary + (hikedEmployee.salary * (hikeInPercentage.get / 100)),
//                              hikedEmployee.gender, hikedEmployee.joiningDate, hikedEmployee.dob, hikedEmployee.jobLevel)
//                            new EmployeeDatabase().updateData(updatedEmployeeRecord).value.get.get // todo not recommended
//                          })
//
//            else throw new RuntimeException("hike not found exception")
    for {
      employeeList <- employeesListInFuture
      result: List[Boolean] <- if (hikeInPercentage.isEmpty) {
        Future.successful(Nil)
      } else {
        val filteredEmployees              = employeeList.filter(employee => employee.department.equals(department))
        val records: List[Future[Boolean]] = filteredEmployees.map{ employee =>
          val updatedSalary   = employee.salary + (employee.salary * (hikeInPercentage.get / 100))
          val updatedEmployee = employee.copy(salary = updatedSalary)
          new EmployeeDatabase().updateData(updatedEmployee)
        }
        Future.sequence(records)

      }
    } yield result
  }

  def promoteEmployees(): Future[List[Employee]] = {
    employeesListInFuture.map(listOfEmployees =>
      listOfEmployees.filter(employee => Period.between(employee.joiningDate, LocalDate.now()).getYears >= 8).map(promotedEmployee => {
        new EmployeeDatabase().updateData(Employee(promotedEmployee.id, promotedEmployee.name, promotedEmployee
          .department, promotedEmployee.salary, promotedEmployee.gender, promotedEmployee.joiningDate, promotedEmployee.dob, "senior"))
        new EmployeeDatabase().getDataById(promotedEmployee.id).value.get.get
      }))
  }
}


case class EmployeesByDepartment(department: String, listOfEmployees: List[Employee])
