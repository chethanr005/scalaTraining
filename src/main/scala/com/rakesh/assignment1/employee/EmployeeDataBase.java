package com.rakesh.assignment1.employee;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeDataBase {
    static List<Employee> employeedata=new ArrayList<>();
    public static List<Employee> getAllEmployees()
    {
        return employeedata;
    }
    public static void AddEmployee(String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel)
    {
        employeedata.add(new Employee(name,department,salary,gender,joiningDate,dob,jobLevel));
    }
}
