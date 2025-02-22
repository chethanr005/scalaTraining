package com.kishor.assignment5.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public class EmployeeMockDatabase {
    ExecutorService service = Executors.newCachedThreadPool();

    public CompletableFuture<Employee> addNewEmployee() {
        return CompletableFuture.supplyAsync(() -> new Employee(6, "Geetha", "Administration", 40000.0, "female", "2020-07-03", "1994-12-13", "junior"));
    }

    public CompletableFuture<EmployeeCountByDepartmentContainer> getMockNoOfEmployeeByCount() {
        return CompletableFuture.supplyAsync(() -> new EmployeeCountByDepartmentContainer("IT Development", 2l), service);
    }

    public CompletableFuture<List<GroupByDepartmentContainer>> getMockGroupByDepartment() {
        ArrayList<GroupByDepartmentContainer> grpByDept = new ArrayList<>();
        grpByDept.add(new GroupByDepartmentContainer(Arrays.asList("Jyoti", "Sunil"), "IT Development"));
        grpByDept.add(new GroupByDepartmentContainer(Arrays.asList("Rohit", "Jessi"), "Business Analysis"));
        grpByDept.add(new GroupByDepartmentContainer(Arrays.asList("Edwin"), "Administration"));
        return CompletableFuture.supplyAsync(() -> {
            return grpByDept;
        }, service);
    }

    public CompletableFuture<List<IncreaseSalaryContainer>> getMockIncreaseSalaryForDepartment() {
        ArrayList<IncreaseSalaryContainer> increaseSalaryContainers = new ArrayList<>();
        increaseSalaryContainers.add(new IncreaseSalaryContainer("Jyoti", 50000.0));
        increaseSalaryContainers.add(new IncreaseSalaryContainer("Sunil", 45000.0));
        return CompletableFuture.supplyAsync(() -> {
            return increaseSalaryContainers;
        }, service);
    }

    public CompletableFuture<List<GetPromoteEmployeeContainer>> getMockPromoteEmployee() {
        ArrayList<GetPromoteEmployeeContainer> getPromoteEmployeeContainers = new ArrayList<>();
        getPromoteEmployeeContainers.add(new GetPromoteEmployeeContainer("Jyoti", "Senior"));
        getPromoteEmployeeContainers.add(new GetPromoteEmployeeContainer("Edwin", "Senior"));
        return CompletableFuture.supplyAsync(() -> getPromoteEmployeeContainers, service);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EmployeeMockDatabase e = new EmployeeMockDatabase();
        System.out.println(e.getMockPromoteEmployee().get());
    }
}
