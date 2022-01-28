package com.rakesh.assignment1.employee;

import java.time.LocalDate;
import java.util.List;

public class Output {
    public static List<Employee> employeeList=EmployeeDataBase.getAllEmployees();
public static void addEmployee()
{
    Employee emp1=new Employee("John","IT Development",35000.0,"male", LocalDate.of(2021,8,11),LocalDate.of(1998,5,12),"Junior");
    Employee emp2=new Employee("Sunil","IT Development",35000.0,"male", LocalDate.of(2020,8,11),LocalDate.of(1997,4,20),"Junior");
    Employee emp3=new Employee("Rohit","Business Analysis",45000.0,"male", LocalDate.of(2020,8,11),LocalDate.of(1995,8,17),"Mid-Term");
    Employee emp4=new Employee("Edwin","Administration",55000.0,"male", LocalDate.of(2010,8,11),LocalDate.of(1994,2,21),"Junior");


    EmployeeDataBase.employeeData.add(emp1);
    EmployeeDataBase.employeeData.add(emp2);
    EmployeeDataBase.employeeData.add(emp3);
    EmployeeDataBase.employeeData.add(emp4);
}

    public static void main(String[] args) {
        EmployeeDetails empDetails=new EmployeeDetails();
        EmployeeCount empCount=new EmployeeCount();
        EmployeeGroup empGroup=new EmployeeGroup();
        SalaryHike empHike=new SalaryHike();
        EmployeePromotion empPromotion=new EmployeePromotion();

        System.out.println();
        addEmployee();

        System.out.println("List of Employee before process: ");
        System.out.println(employeeList);

        System.out.println("Adding Employees : ");
        System.out.println("Employee Pramod of dob(1998-05-12)");
        empDetails.addEmployee("Pramod","IT Development",35000.0,"male", "2021-08-01","1998-05-12","Junior");
        System.out.println("Employee Raj of dob(2016-08-29)");
        empDetails.addEmployee("Raj","IT Development",45000.0,"male","2018-08-11","2016-08-29","Junior");


        System.out.println();
        System.out.println("Employee Count by Departments : ");
        System.out.println(empCount.departmentCount(employeeList));

        System.out.println();
        System.out.println("Employee Name based on Department : ");
        System.out.println(empGroup.empGroupList(employeeList));

        System.out.println();
        System.out.println("Hike List of Employees based on Department : ");
        System.out.println(empHike.hike(empDetails.getEmployees(),"IT Development"));


        System.out.println();
        System.out.println("Promoted Employee with experience of 8 plus years ");
        System.out.println(empPromotion.promoteEmployee(employeeList));


        System.out.println();
        System.out.println("List of Employee After process: ");
        System.out.println(employeeList);

    }

}
