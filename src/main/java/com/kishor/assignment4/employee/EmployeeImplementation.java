package com.kishor.assignment4.employee;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Kishor on Feb 28, 2022.
 */

public class EmployeeImplementation {
    private IEmployee iEmployee;

    EmployeeImplementation(IEmployee database) {
        this.iEmployee = database;
    }

    ExecutorService service = Executors.newCachedThreadPool();

    public CompletableFuture<EmployeeCountByDepartmentContainer> getNoOfEmployeeByCount(String dept) throws SQLException, ExecutionException, InterruptedException {
        List<Employee> employees1 = iEmployee.getAllEmployees().get();
        Supplier<EmployeeCountByDepartmentContainer> employeeSupplier = () -> {
            Long countOFDept = employees1.stream().filter(f -> f.getDepartment().equals(dept)).count();
            return new EmployeeCountByDepartmentContainer(dept, countOFDept);
        };
        return CompletableFuture.supplyAsync(employeeSupplier, service);
    }

    public CompletableFuture<List<GroupByDepartmentContainer>> groupByDepartment() throws SQLException, ExecutionException, InterruptedException {
        List<Employee> employees = iEmployee.getAllEmployees().get();
        Supplier<List<GroupByDepartmentContainer>> containerSupplier = () -> {
            return employees.stream().map(employee -> employee.getDepartment()).distinct().collect(Collectors.toList()).stream()
                            .map(department -> {
                                return new GroupByDepartmentContainer(employees.stream()
                                                                               .filter(employee1 -> employee1.getDepartment().equals(department))
                                                                               .map(employee2 -> employee2.getName()).collect(Collectors.toList()), department);
                            })
                            .collect(Collectors.toList());
        };
        return CompletableFuture.supplyAsync(containerSupplier, service);
    }

    public CompletableFuture<List<IncreaseSalaryContainer>> increaseSalaryForDept(String department, double hike) throws SQLException, ExecutionException, InterruptedException {
        List<Employee> employees = iEmployee.getAllEmployees().get();
        Supplier<List<IncreaseSalaryContainer>> listSupplier = () -> {
            return employees.stream().filter(employee -> employee.getDepartment().equals(department)).collect(Collectors.toList())
                            .stream().map(employee -> {
                        double increasedSalary = employee.getSalary() + hike;
                        try {
                            iEmployee.updateValueThroughEmpId("Salary", String.valueOf(increasedSalary), employee.getEmpId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return new IncreaseSalaryContainer(employee.getName(), increasedSalary);
                    }).collect(Collectors.toList());
        };
        return CompletableFuture.supplyAsync(listSupplier, service);
    }

    public CompletableFuture<List<GetPromoteEmployeeContainer>> getPromoteEmployee() throws SQLException, ExecutionException, InterruptedException {
        List<Employee> employees = iEmployee.getAllEmployees().get();
        Supplier<List<GetPromoteEmployeeContainer>> listSupplier = () -> {
            return employees.stream().filter(employee -> (Period.between(employee.getJoiningDate(), LocalDate.now()).getYears()) > 8)
                            .map(employee -> {
                                try {
                                    iEmployee.updateValueThroughEmpId("JobLevel", "Senior", employee.getEmpId());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return new GetPromoteEmployeeContainer(employee.getName(), employee.getJobLevel());
                            })
                            .collect(Collectors.toList());
        };
        return CompletableFuture.supplyAsync(listSupplier, service);
    }

    public static void main(String[] args) throws SQLException, ExecutionException, InterruptedException {
        EmployeeDatabase       d = new EmployeeDatabase();
        EmployeeImplementation e = new EmployeeImplementation(d);
        //System.out.println(e.getNoOfEmployeeByCount("IT Development").get());
        //System.out.println(e.groupByDepartment().get());
        //System.out.println(e.increaseSalaryForDept("IT Development",5000).get());
        //System.out.println(e.getPromoteEmployee().get());
    }
}
