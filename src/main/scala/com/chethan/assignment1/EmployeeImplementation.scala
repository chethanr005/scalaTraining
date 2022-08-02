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
    } yield EmployeesByDepartment("ec", employeesCount("ec")) +: EmployeesByDepartment("cs", employeesCount("cs")) +: EmployeesByDepartment("ee", employeesCount("ee")) +: Nil
  }

  def increaseSalaryOfEmployees(department: String, hikeInPercentage: Option[Double]): Future[List[Boolean]] = {

    for (employeeList <- employeesListInFuture)
      yield if (hikeInPercentage.isDefined)
              employeeList.filter(employee => employee.department.equals(department))
                          .map(hikedEmployee => new EmployeeDatabase().updateData(Employee(hikedEmployee.id, hikedEmployee.name, hikedEmployee.department, hikedEmployee.salary + (hikedEmployee.salary * (hikeInPercentage.get / 100)),
                            hikedEmployee.gender, hikedEmployee.joiningDate, hikedEmployee.dob, hikedEmployee.jobLevel)).value.get.get)

            else throw new RuntimeException("hike not found exception")


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
