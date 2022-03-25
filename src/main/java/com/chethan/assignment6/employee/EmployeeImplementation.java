package com.chethan.assignment6.employee;


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
 * Created by Chethan on Mar 11, 2022.
 */

public class EmployeeImplementation {
    private static IEmployeeDatabase employeeDatabase;
    private final  ExecutorService   threadPool = Executors.newCachedThreadPool();

    EmployeeImplementation(IEmployeeDatabase employeeDatabase) {
        EmployeeImplementation.employeeDatabase = employeeDatabase;
    }

    // No of employees by given department
    public CompletableFuture<NoOfEmpByDeptContainer> getNoOfEmployeesByDept(String dept) throws ExecutionException, InterruptedException {

        CompletableFuture<List<Employee>> allEmployeesData = employeeDatabase.getEmployeeData();
        List<String>                      distinctDepts    = allEmployeesData.thenApplyAsync(employeesData -> employeesData.stream().map(employee -> employee.getDepartment()).distinct().collect(Collectors.toList())).get();
        if (distinctDepts.contains(dept))
            return allEmployeesData.thenApplyAsync(employeesData -> new NoOfEmpByDeptContainer(dept, employeesData.stream().filter(employee -> employee.getDepartment().equals(dept)).count()), threadPool);
        else return CompletableFuture.completedFuture(null);
    }

    // Group employees by department
    public CompletableFuture<List<GroupEmployeesByDepartment>> getGroupEmployeesByDept() {

        CompletableFuture<List<Employee>> allEmployeesData = employeeDatabase.getEmployeeData();

        CompletableFuture<List<String>> distinctDepts = allEmployeesData.thenApplyAsync(employeesData -> employeesData.stream().map(employee -> employee.getDepartment()).distinct().collect(Collectors.toList()));

        BiFunction<List<String>, List<Employee>, List<GroupEmployeesByDepartment>> listOfGroupEmployeesByDepartment = (listOfDistinctDept, listOfEmployees) -> listOfDistinctDept.stream().map(distinctDept -> new GroupEmployeesByDepartment(distinctDept,
                listOfEmployees.stream().filter(employee -> distinctDept.equals(employee.getDepartment())).collect(Collectors.toList()))).collect(Collectors.toList());

        return distinctDepts.thenCombineAsync(allEmployeesData, listOfGroupEmployeesByDepartment, threadPool);
    }

    // Increase salary for given department
    public CompletableFuture<List<Employee>> getIncreasedSalaryEmployees(Optional<String> dept, Optional<Double> hike) throws ExecutionException, InterruptedException {
        CompletableFuture<List<Employee>> allEmployeesData        = employeeDatabase.getEmployeeData();
        CompletableFuture<List<Employee>> incrementedEmployeeList = new CompletableFuture<>();
        List<String>                      distinctDepts           = allEmployeesData.thenApplyAsync(employeesData -> employeesData.stream().map(employee -> employee.getDepartment()).distinct().collect(Collectors.toList())).get();


        if (dept.isPresent() && distinctDepts.contains(dept.get())) {
            incrementedEmployeeList = allEmployeesData.thenApplyAsync(employeesData -> employeesData.stream().filter(employee -> employee.getDepartment().equals(dept.get())).map(incrementedEmployee ->
                    {
                        incrementedEmployee.setSalary(incrementedEmployee.getSalary() + incrementedEmployee.getSalary() * (hike.get() / 100));
                        Employee employee = null;
                        try {
                            employee = employeeDatabase.updateEmployeeDetailsById(incrementedEmployee).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        return employee;
                    }
            ).collect(Collectors.toList()), threadPool);
            return incrementedEmployeeList;
        } else return CompletableFuture.completedFuture(null);

    }

    // Promote employees based on experience
    public CompletableFuture<List<Employee>> getPromotedEmployees() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Employee>> allEmployeesData = employeeDatabase.getEmployeeData();
        CompletableFuture<List<Employee>> promotionList =
                allEmployeesData.thenApplyAsync(employeesData -> employeesData.stream().filter(employee -> Period.between(employee.getJoiningDate(), LocalDate.now()).getYears() >= 8 && !employee.getJobLevel().equals("senior")).collect(Collectors.toList()));
        BiFunction<List<Employee>, List<Employee>, List<Employee>> promotedEmployeesList = (listOfPromotionEmployees, listOfEmployees) -> listOfPromotionEmployees.stream().map(promotedEmployee ->
                {
                    promotedEmployee.setJobLevel("senior");
                    Employee promotedEmployees = null;
                    try {
                        promotedEmployees = employeeDatabase.updateEmployeeDetailsById(promotedEmployee).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return promotedEmployees;
                }
        ).collect(Collectors.toList());
        return promotionList.thenCombineAsync(allEmployeesData, promotedEmployeesList, threadPool);
    }

    // Filter employees based on condition
    public CompletableFuture<List<Employee>> getFilteredEmployees(FilterEmployees filterEmployees) {
        CompletableFuture<List<Employee>> allEmployeesData = employeeDatabase.getEmployeeData();

        return allEmployeesData.thenApply(e -> e.stream().filter(f1 -> {
                                                    if (filterEmployees.department != null) return f1.getDepartment().equals(filterEmployees.department);
                                                    else return true;
                                                })
                                                .filter(f2 -> {
                                                    if (filterEmployees.salary != null) return f2.getSalary() == filterEmployees.salary;
                                                    else return true;
                                                })
                                                .filter(f3 -> {
                                                    if (filterEmployees.gender != null) return f3.getGender().equals(filterEmployees.gender);
                                                    else return true;
                                                })
                                                .filter(f4 -> {
                                                    if (filterEmployees.jobLevel != null) return f4.getJobLevel().equals(filterEmployees.jobLevel);
                                                    else return true;
                                                })
                                                .filter(f5 -> {
                                                    if (filterEmployees.startDate != null) return f5.getJoiningDate().isAfter(filterEmployees.startDate) || f5.getJoiningDate().isEqual(filterEmployees.startDate);
                                                    else return true;
                                                })
                                                .filter(f5 -> {
                                                    if (filterEmployees.endDate != null) return f5.getJoiningDate().isBefore(filterEmployees.endDate) || f5.getJoiningDate().isEqual(filterEmployees.endDate);
                                                    else return true;
                                                })
                                                .collect(Collectors.toList()));

    }
}
