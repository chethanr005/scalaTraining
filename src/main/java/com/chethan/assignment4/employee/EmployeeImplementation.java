package com.chethan.assignment4.employee;


import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Feb 25, 2022.
 */

public class EmployeeImplementation {
   private static IEmployeeDatabase employeeDatabase;
    ExecutorService threadPool= Executors.newCachedThreadPool();

    EmployeeImplementation(IEmployeeDatabase employeeDatabase){
        this.employeeDatabase=employeeDatabase;
    }

    // No of employees by given department
    public CompletableFuture<NoOfEmpByDeptContainer> getNoOfEmployeesByDept(String dept) {

        CompletableFuture<List<Employee>> allEmployeesData =employeeDatabase.getEmployeeData();

        return allEmployeesData.thenApplyAsync(employeesData -> new NoOfEmpByDeptContainer(dept, employeesData.stream().filter(employee -> employee.getDepartment().equals(dept)).count()),threadPool);
    }

    // Group employees by department
    public  CompletableFuture<List<GroupEmployeesByDepartment>> getGroupEmployeesByDept() {

        CompletableFuture<List<Employee>> allEmployeesData =employeeDatabase.getEmployeeData();

        CompletableFuture<List<String>>      distinctDepts = allEmployeesData.thenApplyAsync(employeesData -> employeesData.stream().map(employee -> employee.getDepartment()).distinct().collect(Collectors.toList()));

        BiFunction<List<String>,List<Employee>,List<GroupEmployeesByDepartment>> listOfGroupEmployeesByDepartment=(listOfDistinctDept,listOfEmployees)->listOfDistinctDept.stream().map(distinctDept -> new GroupEmployeesByDepartment(distinctDept,
                listOfEmployees.stream().filter(employee -> distinctDept.equals(employee.getDepartment())).collect(Collectors.toList()))).collect(Collectors.toList());

        return distinctDepts.thenCombineAsync(allEmployeesData,listOfGroupEmployeesByDepartment,threadPool);
    }

    // Increase salary for given department
    public CompletableFuture<List<Employee>> getIncreasedSalaryEmployees(Optional<String> dept, Optional<Double> hike) {
        CompletableFuture<List<Employee>> allEmployeesData =employeeDatabase.getEmployeeData();
        CompletableFuture<List<Employee>> incrementedEmployeeList=new CompletableFuture<>();

        if (dept.isPresent()) {
            incrementedEmployeeList= allEmployeesData.thenApplyAsync(employeesData->employeesData.stream().filter(employee -> employee.getDepartment().equals(dept.get())).map(incrementedEmployee ->
                    {   Employee employee=null;
                        try {
                             employee=employeeDatabase.updateEmployeeDetailsById(incrementedEmployee.getId(), "salary", "" + (incrementedEmployee.getSalary() + incrementedEmployee.getSalary() * (hike.get() / 100))).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        return employee;
                    }
            ).collect(Collectors.toList()),threadPool);
        }
        return incrementedEmployeeList;
    }

    // Promote employees based on experience
    public CompletableFuture<List<Employee>> getPromotedEmployees() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Employee>> allEmployeesData =employeeDatabase.getEmployeeData();
        CompletableFuture<List<Employee>>     promotionList       =
                allEmployeesData.thenApplyAsync(employeesData -> employeesData.stream().filter(employee -> Period.between(employee.getJoiningDate(), LocalDate.now()).getYears() >= 8).collect(Collectors.toList()));
        BiFunction<List<Employee>,List<Employee>,List<Employee>> promotedEmployeesList=(listOfPromotionEmployees, listOfEmployees)-> listOfPromotionEmployees.stream().map(promotedEmployee ->
                { Employee promotedEmployees=null;
                    try {
                        promotedEmployees= employeeDatabase.updateEmployeeDetailsById(promotedEmployee.getId(), "jobLevel", "senior").get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return promotedEmployees;
                }
        ).collect(Collectors.toList());
        return promotionList.thenCombineAsync(allEmployeesData,promotedEmployeesList,threadPool);
    }
}
