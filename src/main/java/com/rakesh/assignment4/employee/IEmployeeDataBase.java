package com.rakesh.assignment4.employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by Rakesh on Feb 25, 2022.
 */

public interface IEmployeeDataBase {
    public CompletableFuture<List<Employee>> getAllEmployee(ExecutorService service);

    public CompletableFuture<Employee> getEmployeeByID(int empID, ExecutorService service);

    public boolean addNewEmployee(Employee employee);

    public boolean updateEmployeeData(String columnName, String newValue, int empID);

    public boolean deleteAllEmployee();

    public boolean deleteEmployeeByID(int empID);
}
