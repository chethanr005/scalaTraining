package com.rakesh.assignment4.employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Created by Rakesh on Feb 25, 2022.
 */

public class EmployeeImplementation {
    static IEmployeeDataBase iEmployeeDataBaseEmployee;
    static ExecutorService   threadPool = Executors.newFixedThreadPool(10);

    EmployeeImplementation(IEmployeeDataBase iEmployeeDataBase) {
        this.iEmployeeDataBaseEmployee = iEmployeeDataBase;
    }

    /**
     * 1. Don't allow child labours while taking employee // 21 years
     */
    public boolean addEmployee(Employee employee) {
        return iEmployeeDataBaseEmployee.addNewEmployee(employee);
    }

    /**
     * 2. Get no of employees by given department
     */
    public CompletableFuture<DepartmentCountContainer> employeeCountByDepartment(String department) {
        Function<List<Employee>, DepartmentCountContainer> getCount = (employeeList) -> {
            return new DepartmentCountContainer(department, employeeList.stream().filter(employee -> employee.getDepartment().equals(department)).count());
        };
        return iEmployeeDataBaseEmployee.getAllEmployee(threadPool).thenApplyAsync(getCount, threadPool);
    }

    /**
     * 3. group employees by department
     */
    public CompletableFuture<List<EmployeeGroupByDepartment>> employeeGroupByDept() {
        Function<List<Employee>, List<EmployeeGroupByDepartment>> getGroup = (employeeList) -> {
            List<EmployeeGroupByDepartment> result = new ArrayList<>();

            employeeList.stream().map(Employee::getDepartment).distinct().sorted().collect(Collectors.toList()).stream().forEach(department ->
                    result.add(new EmployeeGroupByDepartment(department,
                            employeeList.stream().filter(employee -> employee.getDepartment().equals(department)).collect(Collectors.toList()))));

            return result;
        };
        return iEmployeeDataBaseEmployee.getAllEmployee(threadPool).thenApplyAsync(getGroup, threadPool);
    }

    public CompletableFuture<EmployeeGroupByDepartment> getEmployeeGroupByDept(String department) {
        Function<List<Employee>, EmployeeGroupByDepartment> getGroup = (employeeList) -> {
            return new EmployeeGroupByDepartment(department,
                    employeeList.stream().filter(emloyee -> emloyee.getDepartment().equalsIgnoreCase(department)).collect(Collectors.toList()));
        };

        return iEmployeeDataBaseEmployee.getAllEmployee(threadPool).thenApplyAsync(getGroup, threadPool);
    }

    /**
     * 4. Increase salary of employees for given Department , not necessary that there will be hike!
     */
    public CompletableFuture<List<Employee>> hikeEmployees(String department, double hike) {
        Optional<String>                         dept = Optional.ofNullable(department);
        Function<List<Employee>, List<Employee>> hikedEmployees;
        if (dept.isPresent()) {
            if (EmployeeDataBase.checkDepartment(department)) {
                hikedEmployees = (employeeList) -> {
                    //System.out.println("Employee List : "+ employeeList);
                    employeeList.stream().filter(employee -> employee.getDepartment().equals(department)).forEach(filteredEmployee -> {
                        String newSalary = String.valueOf((filteredEmployee.getSalary() + hike));
                        iEmployeeDataBaseEmployee.updateEmployeeData("Salary", newSalary, filteredEmployee.getEmpID());
                    });
                    return employeeList.stream().filter(employee -> employee.getDepartment().equals(department)).collect(Collectors.toList());
                };
            } else {
                throw new RuntimeException("No Department found in DataBase with name : \'" + department + "'");
            }
        } else {
            hikedEmployees = (employeeList -> {
                return null;
            });
        }
        return iEmployeeDataBaseEmployee.getAllEmployee(threadPool).thenApplyAsync(hikedEmployees, threadPool);
    }

    /**
     * 5. Promote employees having 8 years experience to Senior position
     */
    public CompletableFuture<List<Employee>> promoteEmployees() {

        Function<List<Employee>, List<Employee>> promoteJobLevel = (employeeList) -> {
            return employeeList.stream().filter(employee -> getYears(employee) >= 8 && checkJobLevel(employee))
                               .map(employee -> {
                                   Employee emp = null;
                                   try {
                                       emp = promoteEmployee(employee);
                                   } catch (ExecutionException e) {
                                       e.printStackTrace();
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }
                                   //System.out.println(emp);
                                   return emp;
                               })
                               .collect(Collectors.toList());
        };
        return iEmployeeDataBaseEmployee.getAllEmployee(threadPool).thenApplyAsync(promoteJobLevel, threadPool);
    }

    /**
     * Helper Methods
     */
    private static long getYears(Employee employee) {
        return Period.between(employee.getJoiningDate(), LocalDate.now()).getYears();
    }

    public static boolean checkJobLevel(Employee employee) {
        return !employee.getJobLevel().equals("Senior");
    }

    private static Employee promoteEmployee(Employee employee) throws ExecutionException, InterruptedException {
        Employee emp = null;
        if (iEmployeeDataBaseEmployee.updateEmployeeData("JobLevel", "Senior", employee.getEmpID())) {
            emp = iEmployeeDataBaseEmployee.getEmployeeByID(1004, threadPool).get();
            emp.setJobLevel("Senior");
        }
        return emp;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        IEmployeeDataBase      employeeDataBase = new EmployeeDataBase();
        EmployeeImplementation implementation   = new EmployeeImplementation(employeeDataBase);


        System.out.println("---- Employee Assignment ----");
        System.out.println();

        System.out.println("Employee Count By Department : " + implementation.employeeCountByDepartment("IT Development").get());
        Thread.sleep(1000);
        System.out.println();

        System.out.print("Employee GroupByDept : ");
        implementation.employeeGroupByDept().get().stream().forEach(System.out::println);
        Thread.sleep(1000);
        System.out.println();

        System.out.println("Hike Employees : " + implementation.hikeEmployees("Administration", 0).get());
        Thread.sleep(1000);
        System.out.println();

        System.out.println("Promote Employees : " + implementation.promoteEmployees().get());
        Thread.sleep(1000);

        if (!threadPool.isShutdown())
            threadPool.shutdown();
    }
}
