package com.rakesh.assignment5.employee;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


/**
 * Created by Rakesh on Mar 15, 2022.
 */

public class EmployeeMockData {
    public static List<Employee> employeeData=new ArrayList<>();
    public CompletableFuture<List<Employee>> getAllEmployees() {
        Employee emp1 = new Employee(1001, "John", "IT Development", 35000.0, "male", LocalDate.of(2021, 8, 11), LocalDate.of(1998, 5, 12), "Junior");
        Employee emp2 = new Employee(1002, "Sunil", "IT Development", 35000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(1997, 4, 20), "Junior");
        Employee emp3 = new Employee(1003, "Rohit", "Business Analysis", 45000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(1995, 8, 17), "Mid-Term");
        Employee emp4 = new Employee(1004, "Edwin", "Administration", 55000.0, "male", LocalDate.of(2010, 8, 11), LocalDate.of(1994, 2, 21), "Junior");

        return CompletableFuture.supplyAsync(() -> {
            return Arrays.asList(emp1, emp2, emp3, emp4);
        });

    }

    public boolean updateData(String columnName, String newValue, int empID) throws ExecutionException, InterruptedException {
        // System.out.print("Inside UpdateData Mockito :    ");
        boolean  isUpdated = false;
        Employee employee  = getEmployeeByID(empID).get();
        // System.out.println(employee);
        //Employee employee  = getAllEmployees().get().stream().filter(employeeData -> employeeData.getEmpID() == empID).collect(Collectors.toList()).get(0);
        if (employee != null) {
            if (columnName.equalsIgnoreCase("Name")) {
                employee.setName(newValue);
                isUpdated = true;
            } else if (columnName.equalsIgnoreCase("Department")) {
                employee.setDepartment(newValue);
                isUpdated = true;
            } else if (columnName.equalsIgnoreCase("salary")) {
                double salary = Double.parseDouble(newValue);
                employee.setSalary(salary);
                isUpdated = true;
            } else if (columnName.equalsIgnoreCase("Gender")) {
                employee.setGender(newValue);
                isUpdated = true;
            } else if (columnName.equalsIgnoreCase("joiningDate")) {
                DateTimeFormatter dtf       = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate         joingdate = LocalDate.parse(newValue, dtf);
                int               days      = Period.between(joingdate, LocalDate.now()).getDays();
                if (days > -1) {
                    employee.setJoiningDate(joingdate);
                    isUpdated = true;
                } else {
                    throw new RuntimeException("Invalid Joining date");
                }
            } else if (columnName.equalsIgnoreCase("DOB")) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate         dob = LocalDate.parse(newValue, dtf);
                int               age = Period.between(dob, LocalDate.now()).getYears();
                if (age >= 21) {
                    employee.setDob(dob);
                    isUpdated = true;
                } else {
                    throw new RuntimeException("Employee age cannot be under 21");
                }
            } else if (columnName.equalsIgnoreCase("jobLevel")) {
                employee.setJobLevel(newValue);
                isUpdated = true;
            } else {
                return false;
            }
        } else {
            isUpdated = false;
        }

        return isUpdated;
    }

    public CompletableFuture<Employee> getEmployeeByID(int empID) throws ExecutionException, InterruptedException {
        Employee employee = getAllEmployees().get().stream().filter(emp -> emp.getEmpID() == empID).collect(Collectors.toList()).get(0);
        //System.out.println("From mockito..");
        return CompletableFuture.supplyAsync(() -> employee);
    }
    public boolean addNewEmployee(Employee employee) {
        boolean isAdded = false;
        int     age     = Period.between(employee.getDob(), LocalDate.now()).getYears();
        int     empID   = employee.getEmpID();
        if (age >= 21) {
            DateTimeFormatter dtf       = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String            joingDate = dtf.format(employee.getJoiningDate());
            String            dob       = dtf.format(employee.getDob());
            employeeData.add(employee);
            isAdded = true;
        } else {
           return false;
        }
        return isAdded;
    }

//    public static void main(String[] args) {
//        EmployeeMockData db=new EmployeeMockData();
//        System.out.println(db.addNewEmployee(new Employee(1001, "John", "IT Development", 35000.0, "male", "2021-08-11", "2018-05-12", "Junior")));
//    }
}
