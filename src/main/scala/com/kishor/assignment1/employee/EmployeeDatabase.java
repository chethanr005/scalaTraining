package com.kishor.assignment1.employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. Don't allow child labours while taking employee // 21 years
 */
public class EmployeeDatabase {
    public static List<Employee> employees = new ArrayList<>();

    public static List<Employee> getEmployee() {
        return employees;
    }

    public static void addEmployee(String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel) {
        ;
        int p = Period.between(dob, LocalDate.now()).getYears();
        if (p > 21) {
            Employee emp = new Employee(name, department, salary, gender, joiningDate, dob, jobLevel);
            employees.add(emp);
            System.out.println("Data added Successfully");
        } else {
            System.out.println("Age is " + p + " and which is less than 21. So Data Cannot be added to Database");
        }

    }

}
