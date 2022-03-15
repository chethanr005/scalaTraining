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

    static final ExecutorService service = Executors.newCachedThreadPool();

    public CompletableFuture<Employee> addEmployee(int empId, String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel) throws Exception {
        return iEmployee.insertNewRecord(new Employee(empId, name, department, salary, gender, joiningDate, dob, jobLevel));
    }

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
                                Employee updatedEmployee = null;
                                try {
                                    updatedEmployee = iEmployee.updateValueThroughEmpId("JobLevel", "Senior", employee.getEmpId()).get();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return new GetPromoteEmployeeContainer(updatedEmployee.getName(), updatedEmployee.getJobLevel());
                            })
                            .collect(Collectors.toList());
        };
        return CompletableFuture.supplyAsync(listSupplier, service);
    }

    public static void main(String[] args) throws Exception {
        IEmployee              d = new EmployeeDatabase();
        EmployeeImplementation e = new EmployeeImplementation(d);
        System.out.println("1. New Employee Added   ==> \n" + e.addEmployee(7, "Geetha", "Administration", 40000.0, "female", LocalDate.of(2020, 07, 03), LocalDate.of(1994, 12, 13), "junior").get() + "\n\n");
        Thread.sleep(2000);
        System.out.println("2. Employees by Count   ==> \n " + e.getNoOfEmployeeByCount("IT Development").get() + "\n\n");
        Thread.sleep(2000);
        System.out.println("3. Grouping employees by Department   ==> \n " + e.groupByDepartment().get() + "\n\n");
        Thread.sleep(2000);
        System.out.println("4. Increase Salary by Department   ==> \n " + e.increaseSalaryForDept("IT Development", 5000).get() + "\n\n");
        Thread.sleep(2000);
        System.out.println("5. Promote Employee to Senior   ==> \n " + e.getPromoteEmployee().get());

    }
}
