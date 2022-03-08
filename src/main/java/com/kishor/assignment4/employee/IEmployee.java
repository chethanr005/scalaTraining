package com.kishor.assignment4.employee;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Kishor on Feb 28, 2022.
 */

public interface IEmployee {
    public CompletableFuture<List<Employee>> getAllEmployees() throws SQLException;

    public CompletableFuture<Employee> getEmployeeById(int empId) throws SQLException;

    public CompletableFuture<Employee> updateValueThroughEmpId(String columnName, String value, Integer empId) throws Exception;

    public CompletableFuture<Employee> insertNewRecord(Employee employee) throws SQLException;

    public CompletableFuture<List<Employee>> deleteEmployeeById(int empId) throws SQLException;
}
