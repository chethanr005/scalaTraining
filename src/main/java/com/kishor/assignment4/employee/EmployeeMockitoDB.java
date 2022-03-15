package com.kishor.assignment4.employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by Kishor on Mar 04, 2022.
 */

public class EmployeeMockitoDB {
    public static ArrayList<Employee> employees = new ArrayList<Employee>();

    public CompletableFuture<List<Employee>> getMockAllEmployees() {
        ArrayList<Employee> ee = new ArrayList<Employee>();
        ee.add(new Employee(1, "Jyoti", "IT Development", 45000.0, "female", LocalDate.of(2000, 8, 11), LocalDate.of(1998, 5, 12), "Junior"));
        ee.add(new Employee(2, "Sunil", "IT Development", 40000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(2010, 4, 20), "Junior"));
        ee.add(new Employee(3, "Rohit", "Business Analysis", 35000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(1995, 8, 17), "Mid-Term"));
        ee.add(new Employee(4, "Edwin", "Administration", 35000.0, "male", LocalDate.of(1990, 8, 11), LocalDate.of(1980, 2, 21), "Junior"));
        ee.add(new Employee(5, "Jessi", "Business Analysis", 35000.0, "female", LocalDate.of(2018, 8, 11), LocalDate.of(1998, 11, 19), "Junior"));
        return CompletableFuture.supplyAsync(() -> ee);
    }

    public CompletableFuture<Employee> getMockEmployeeById() {
        return null;
    }

    public CompletableFuture<Employee> insertMockEmployee(int empId, String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel) throws Exception {
        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age > 21) {
            Employee emp = new Employee(empId, name, department, salary, gender, joiningDate, dob, jobLevel);
            employees.add(emp);
            return CompletableFuture.supplyAsync(() -> emp);
        } else
            throw new IllegalAccessException();
    }

    public CompletableFuture<Employee> updateMockValueThroughEmpId(String columnName, String value, Integer empId) throws ExecutionException, InterruptedException {
        Employee employee = getMockAllEmployees().get().stream().filter(employeedata -> employeedata.getEmpId() == empId).collect(Collectors.toList()).get(0);
        //System.out.println("Mock    "+employee);
        // System.out.println("Mock   "+value);
        if (columnName == "Name")
            employee.setName(value);
        else if (columnName == "Department")
            employee.setDepartment(value);
        else if (columnName == "Salary")
            employee.setSalary(Double.parseDouble(value));
        else if (columnName == "Gender")
            employee.setGender(value);
        else if (columnName == "JoiningDate")
            employee.setJoiningDate(LocalDate.parse(value));
        else if (columnName == "Dob")
            employee.setDob(LocalDate.parse(value));
        else if (columnName.equals("JobLevel"))
            employee.setJobLevel(value);

        //System.out.println("Mock Updated  "+employee);
        return CompletableFuture.supplyAsync(() -> employee);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EmployeeMockitoDB db = new EmployeeMockitoDB();
        System.out.println(db.updateMockValueThroughEmpId("JobLevel", "Senior", 2).get());
    }
}