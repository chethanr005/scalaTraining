package com.chethan.assignment6.employee;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * Created by Chethan on Mar 04, 2022.
 */

public class MockitoEmployeeDataBase {
    IEmployeeDatabase i = new EmployeeDatabase();

    public static CompletableFuture<List<Employee>> getEmployeesList() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList(
                new Employee(1, "marry", "cs", 260000, "female", LocalDate.of(2020, 10, 15), LocalDate.of(2000, 4, 15), "junior"),
                new Employee(2, "rose", "ee", 250000, "female", LocalDate.of(2015, 12, 25), LocalDate.of(1993, 6, 21), "mid"),
                new Employee(3, "julie", "ec", 350000, "female", LocalDate.of(2014, 7, 24), LocalDate.of(1993, 3, 10), "mid"),
                new Employee(4, "tony", "cs", 250000, "male", LocalDate.of(2010, 4, 4), LocalDate.of(1989, 8, 23), "mid"),
                new Employee(5, "kail", "cs", 450000, "female", LocalDate.of(2012, 12, 28), LocalDate.of(1990, 9, 10), "mid"),
                new Employee(6, "kate", "ee", 450000, "female", LocalDate.of(2015, 10, 1), LocalDate.of(1994, 1, 26), "junior"),
                new Employee(7, "joe", "ee", 250000, "male", LocalDate.of(2021, 12, 1), LocalDate.of(2000, 10, 25), "junior"),
                new Employee(8, "anthony", "ec", 380000, "male", LocalDate.of(2019, 5, 12), LocalDate.of(1995, 3, 21), "junior"),
                new Employee(9, "andrew", "ec", 300000, "male", LocalDate.of(2021, 6, 26), LocalDate.of(1997, 7, 19), "junior"),
                new Employee(10, "swift", "cs", 500000, "female", LocalDate.of(2020, 7, 12), LocalDate.of(1996, 5, 2), "junior"),
                new Employee(11, "cooper", "ee", 470000, "male", LocalDate.of(2020, 10, 18), LocalDate.of(1996, 5, 5), "junior"),
                new Employee(12, "mary", "cs", 260000, "female", LocalDate.of(2021, 10, 15), LocalDate.of(2000, 4, 15), "junior"),
                new Employee(13, "hailey", "cs", 420000, "female", LocalDate.of(2016, 11, 9), LocalDate.of(1994, 2, 21), "mid")
        ));
    }

    public static CompletableFuture<Employee> getEmployee(int eid) {
        return getEmployeesList().thenApply(studentsList -> {
            return studentsList.stream().filter(student -> student.getId() == eid).findAny().get();
        });
    }

    public static CompletableFuture<Employee> updateEmployeeByIdInMockito(Employee employee) {
        return getEmployeesList().thenApply(employeesList -> employeesList.stream().filter(employees -> employees.getId() == employee.getId()).map(IdEmployee -> {

            IdEmployee.setName(employee.getName());
            IdEmployee.setSalary(employee.getSalary());
            IdEmployee.setGender(employee.getGender());
            IdEmployee.setDepartment(employee.getDepartment());
            IdEmployee.setJoiningDate(employee.getJoiningDate());
            IdEmployee.setDob(employee.getDob());
            IdEmployee.setJobLevel(employee.getJobLevel());

            return IdEmployee;
        }).findAny().get());
    }

    public static CompletableFuture<Employee> addMockitoEmployee(Employee employee) throws IllegalAccessException, ExecutionException, InterruptedException {
        List<Employee> employeesList = new ArrayList<>();
        employeesList.addAll(getEmployeesList().get());

        if (Period.between(employee.getDob(), LocalDate.now()).getYears() >= 21 && !Period.between(employee.getJoiningDate(), LocalDate.now()).isNegative()) {
            employeesList.add(employee);
        } else throw new IllegalAccessException("error adding an employee: check if the details of the employee is valid");
        return CompletableFuture.supplyAsync(() -> employee);
    }
}
