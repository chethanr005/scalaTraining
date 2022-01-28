package com.rakesh.assignment1.employee;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmployeeDetails {

    private int getage(LocalDate dob)
    {
        Period p=Period.between(dob,LocalDate.now());
        return p.getYears();
    }

    public List<Employee> getEmployees()
    {
        return EmployeeDataBase.getAllEmployees();
    }


    public void AddEmployee(String name, String department, double salary, String gender, String joiningDate, String dob, String jobLevel)
    {
        DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate lddob=LocalDate.parse(dob,dtf);
        LocalDate ldjd=LocalDate.parse(joiningDate,dtf);
        int age=getage(lddob);
        if(age>=21)
        {
           EmployeeDataBase.AddEmployee(name,department,salary,gender,ldjd,lddob,jobLevel);
            System.out.println("Employee Added.. ");
        }
        else
        {
            System.out.println("Age of the Employee is below 21");
        }
    }
}
