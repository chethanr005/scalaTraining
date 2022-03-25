package com.kishor.assignment5.employee;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public class EmployeeMockitoDB {
    public static ArrayList<Employee> employees = new ArrayList<Employee>();
    static        DateTimeFormatter   formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CompletableFuture<List<Employee>> getMockAllEmployees() {
        ArrayList<Employee> ee = new ArrayList<Employee>();
        ee.add(new Employee(1, "Jyoti", "IT Development", 45000.0, "female", "2000-08-11", "1998-05-12", "Senior"));
        ee.add(new Employee(2, "Sunil", "IT Development", 40000.0, "male", "2020-08-11", "1996-04-12", "Junior"));
        ee.add(new Employee(3, "Rohit", "Business Analysis", 35000.0, "male", "2015-08-11", "1995-08-17", "Mid-Term"));
        ee.add(new Employee(4, "Edwin", "Administration", 35000.0, "male", "2002-02-18", "1996-08-11", "Senior"));
        ee.add(new Employee(5, "Jessi", "Business Analysis", 35000.0, "female", "2018-08-11", "1998-11-19", "Junior"));
        ee.add(new Employee(6, "Geetha", "Administration", 45000.0, "female", "2020-07-03", "1994-12-13", "Junior"));
        return CompletableFuture.supplyAsync(() -> ee);
    }

    public CompletableFuture<Employee> getMockEmployeById(int i) {
        return CompletableFuture.supplyAsync(() -> new Employee(1, "Jyoti", "IT Development", 70000.0, "female", "2000-08-11", "1998-05-12", "Senior"));
    }


    public CompletableFuture<Employee> insertMockEmployee(int empId, String name, String department, double salary, String gender, String joiningDate, String dob, String jobLevel) throws Exception {

        int age = Period.between(LocalDate.parse(dob, formatter), LocalDate.now()).getYears();
        if (age > 21) {
            Employee emp = new Employee(empId, name, department, salary, gender, joiningDate, dob, jobLevel);
            employees.add(emp);
            return CompletableFuture.supplyAsync(() -> emp);
        } else
            throw new IllegalAccessException();
    }

    public CompletableFuture<Employee> updateMockValueThroughEmpId(String columnName, String value, Integer empId) throws ExecutionException, InterruptedException {
        Employee employee = getMockAllEmployees().get().stream().filter(employeedata -> employeedata.getEmpId() == empId).collect(Collectors.toList()).get(0);
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

        return CompletableFuture.supplyAsync(() -> employee);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EmployeeMockitoDB db = new EmployeeMockitoDB();

        //System.out.println(db.getMockEmployeById(1).get());
        //System.out.println(db.getMockAllEmployees().get());
        System.out.println(db.updateMockValueThroughEmpId("JobLevel", "Senior", 4).get());
    }
}
