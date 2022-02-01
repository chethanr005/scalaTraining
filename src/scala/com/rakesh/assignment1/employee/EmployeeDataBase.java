package com.rakesh.assignment1.employee;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDataBase {
    static List<Employee> employeeData=new ArrayList<>();
    public static List<Employee> getAllEmployees()
    {
        return employeeData;
    }
    public static void addEmployee(String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel)
    {
        employeeData.add(new Employee(name,department,salary,gender,joiningDate,dob,jobLevel));
    }
}
