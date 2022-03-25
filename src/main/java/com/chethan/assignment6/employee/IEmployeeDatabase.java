package com.chethan.assignment6.employee;


import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Chethan on Mar 11, 2022.
 */

public interface IEmployeeDatabase {

    CompletableFuture<List<Employee>> getEmployeeData();

    CompletableFuture<Employee> getEmployeeById(int sid);

    CompletableFuture<Employee> addEmployee(Employee employee) throws IllegalAccessException;

    CompletableFuture<Employee> updateEmployeeDetailsById(Employee employee);

    void deleteEmployeeById(int sid);

}
