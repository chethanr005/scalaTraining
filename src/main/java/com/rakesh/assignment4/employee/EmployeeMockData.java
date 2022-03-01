package com.rakesh.assignment4.employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rakesh on Feb 25, 2022.
 */

public class EmployeeMockData {

    public List<EmployeeGroupByDepartment> employeeGroupMockData() {
        List<EmployeeGroupByDepartment> result = new ArrayList<>();

        result.add(new EmployeeGroupByDepartment("Administration", Arrays.asList(new Employee(1004, "Edwin", "Administration", 55000.0, "male", LocalDate.of(2010, 8, 11), LocalDate.of(1994, 2, 21), "Junior"))));
        result.add(new EmployeeGroupByDepartment("Business Analysis", Arrays.asList(new Employee(1003, "Rohit", "Business Analysis", 45000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(1995, 8, 17), "Mid-Term"))));
        result.add(new EmployeeGroupByDepartment("IT Development", Arrays.asList(
                new Employee(1001, "John", "IT Development", 45000.0, "male", LocalDate.of(2021, 8, 11), LocalDate.of(1998, 5, 12), "Junior"),
                new Employee(1002, "Sunil", "IT Development", 45000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(1997, 4, 20), "Junior"))));

        return result;
    }

    public EmployeeGroupByDepartment employeeGroupByDept(String department) {
        if (department.equalsIgnoreCase("Administration")) {
            return new EmployeeGroupByDepartment("Administration", Arrays.asList(new Employee(1004, "Edwin", "Administration", 55000.0, "male", LocalDate.of(2010, 8, 11), LocalDate.of(1994, 2, 21), "Junior")));
        } else if (department.equalsIgnoreCase("Business Analysis")) {
            return new EmployeeGroupByDepartment("Business Analysis", Arrays.asList(new Employee(1003, "Rohit", "Business Analysis", 45000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(1995, 8, 17), "Mid-Term")));
        } else {
            return new EmployeeGroupByDepartment("IT Development", Arrays.asList(
                    new Employee(1001, "John", "IT Development", 45000.0, "male", LocalDate.of(2021, 8, 11), LocalDate.of(1998, 5, 12), "Junior"),
                    new Employee(1002, "Sunil", "IT Development", 45000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(1997, 4, 20), "Junior")));
        }
    }

    public List<Employee> hikeEmployeeMockData() {
        return Arrays.asList(new Employee(1004, "Edwin", "Administration", 55000.0, "male", LocalDate.of(2010, 8, 11), LocalDate.of(1994, 2, 21), "Junior"));
    }

    public List<Employee> promotedEmployeeMockData() {
        return Arrays.asList(new Employee(1004, "Edwin", "Administration", 55000.0, "male", LocalDate.of(2010, 8, 11), LocalDate.of(1994, 2, 21), "Senior"));
    }
}
