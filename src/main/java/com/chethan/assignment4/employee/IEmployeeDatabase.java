package com.chethan.assignment4.employee;



import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Chethan on Feb 25, 2022.
 */

public interface IEmployeeDatabase {

    public CompletableFuture<List<Employee>> getEmployeeData();

    public CompletableFuture<Employee> getEmployeeById(int sid);

    public CompletableFuture<Employee> addEmployee(Employee employee) throws IllegalAccessException;

    public CompletableFuture<Employee> updateEmployeeDetailsById(int sid, String columnName, String value);

    public void deleteEmployeeById(int sid);

}
