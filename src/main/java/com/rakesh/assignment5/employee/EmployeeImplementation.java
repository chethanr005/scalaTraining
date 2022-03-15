package com.rakesh.assignment5.employee;


import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.unmarshalling.StringUnmarshallers;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static akka.http.javadsl.server.PathMatchers.integerSegment;
import static akka.http.javadsl.server.PathMatchers.segment;

/**
 * Created by Rakesh on Mar 14, 2022.
 */

public class EmployeeImplementation extends AllDirectives {
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
            List<String> deptList = employeeList.stream().map(Employee::getDepartment).distinct().collect(Collectors.toList());
            if (deptList.contains(department)) {
                return new DepartmentCountContainer(department, employeeList.stream().filter(employee -> employee.getDepartment().equals(department)).count());
            } else {
                return null;
            }
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
        Optional<String>                         dept           = Optional.ofNullable(department);
        Function<List<Employee>, List<Employee>> hikedEmployees = null;
        if (dept.isPresent()) {
            if (EmployeeDataBase.checkDepartment(department)) {
                hikedEmployees = (employeeList) -> {
                    //System.out.println("Employee List : "+ employeeList);
                    employeeList.stream().filter(employee -> employee.getDepartment().equals(department)).forEach(filteredEmployee -> {
                        String newSalary = String.valueOf((filteredEmployee.getSalary() + hike));
                        iEmployeeDataBaseEmployee.updateEmployeeData("Salary", newSalary, filteredEmployee.getEmpID());
                        filteredEmployee.setSalary(Double.parseDouble(newSalary));
                    });
                    return employeeList.stream().filter(employee -> employee.getDepartment().equals(department)).collect(Collectors.toList());
                };
            } else {
                return null;
            }
        } else {
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
            emp = iEmployeeDataBaseEmployee.getEmployeeByID(employee.getEmpID(), threadPool).get();
            emp.setJobLevel("Senior");
        }
        return emp;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        EmployeeImplementation app = new EmployeeImplementation(iEmployeeDataBaseEmployee);

        ActorSystem<Void> system = ActorSystem.create(Behaviors.empty(), "routes");

        Http http = Http.get(system);

        CompletionStage<ServerBinding> binding = http.newServerAt("localhost", 8085).bind(app.getRoutes());
        System.out.println("Server created at http://localhost:8085/");

    }

    public Route getRoutes() {
        return concat(
                // to get AllEmployee
                pathPrefix("employee", () -> path("getAllEmployee", () -> get(() -> {
                    List<Employee> employeeList = null;
                    try {
                        employeeList = iEmployeeDataBaseEmployee.getAllEmployee(threadPool).get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<Employee>>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(employeeList));
                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> {
                        return completeOK(employeesData, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                }))),

                //to get Employee by ID
                pathPrefix("employee", () -> pathPrefix("getEmployeeByID", () -> get(() -> path(integerSegment(), (Integer id) -> {
                    Employee fetchEmployee = null;
                    try {
                        fetchEmployee = iEmployeeDataBaseEmployee.getEmployeeByID(id, threadPool).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<Employee>> employeeData = CompletableFuture.completedFuture(Optional.ofNullable(fetchEmployee));
                    return onSuccess(employeeData, data -> data.map(employee -> completeOK(employee, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found")));
                })))),

                //to get Employee count by Department
                pathPrefix("employee", () ->
                        pathPrefix("employeesCountByDept", () -> get(() ->
                                path(segment(), (String departmentName) -> {
                                    DepartmentCountContainer count = null;
                                    try {
                                        count = employeeCountByDepartment(departmentName).get();
                                    } catch (ExecutionException | InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    CompletionStage<Optional<DepartmentCountContainer>> employeeCount = CompletableFuture.completedFuture(Optional.ofNullable(count));
                                    return onSuccess(employeeCount, data -> data.map(empCount -> completeOK(empCount, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found")));
                                })))),

                //to get Employee grouped by Departments
                pathPrefix("employee", () ->
                        path("employeeGroupByDept", () -> get(() ->
                        {
                            List<EmployeeGroupByDepartment> employeeGroup = null;
                            try {
                                employeeGroup = employeeGroupByDept().get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                            CompletionStage<Optional<List<EmployeeGroupByDepartment>>> group = CompletableFuture.completedFuture(Optional.ofNullable(employeeGroup));

                            return onSuccess(group, empGroup -> empGroup.map(data -> completeOK(data, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "No data found")));
                        }))),

                //to get promoted Employee
                pathPrefix("employee", () ->
                        path("promotedEmployees", () -> get(() ->
                        {
                            List<Employee> promotedEmployee = null;
                            try {
                                promotedEmployee = promoteEmployees().get();
                            } catch (Exception e) {
                            }

                            CompletionStage<Optional<List<Employee>>> employees = CompletableFuture.completedFuture(Optional.ofNullable(promotedEmployee));

                            return onSuccess(employees, employee -> employee.map(data -> completeOK(data, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "No data found")));
                        }))),


                // to hike employee by department.
                pathPrefix("employee", () ->
                        path("hikeEmployeeByDept", () -> concat(put(() ->
                                parameter("dept", dept ->
                                        parameter(StringUnmarshallers.DOUBLE, "hike", hike ->
                                        {
                                            List<Employee> hikedEmployee = null;
                                            try {
                                                hikedEmployee = hikeEmployees(dept, hike).get();
                                            } catch (ExecutionException | InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                            CompletionStage<Optional<List<Employee>>> employeeList = CompletableFuture.completedFuture(Optional.ofNullable(hikedEmployee));

                                            return onSuccess(employeeList, employees -> employees.map(data -> completeOK(data, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "No data found")));
                                        })))))),

                // to add new Employee
                pathPrefix("employee", () -> path("addEmployee", () -> post(() -> entity(Jackson.unmarshaller(Employee.class), employee -> {
                    // System.out.println(employee);
                    CompletionStage<Boolean> futureSaved = CompletableFuture.completedFuture(addEmployee(employee));
                    return onSuccess(futureSaved, done -> complete((done) ? StatusCodes.OK : StatusCodes.BAD_REQUEST));
                })))),

                //to delete a Employee
                pathPrefix("employee", () -> pathPrefix("deleteByID", () -> delete(() -> path(integerSegment(), (Integer id) -> {
                    CompletionStage<Boolean> isDeleted = CompletableFuture.completedFuture(iEmployeeDataBaseEmployee.deleteEmployeeByID(id));
                    return onSuccess(isDeleted, done -> complete((done) ? StatusCodes.OK : StatusCodes.BAD_REQUEST));
                })))),


                //to update a Employee data
                pathPrefix("employee", () ->
                        pathPrefix("update", () -> concat(put(() ->
                                parameter("columnName", columnName ->
                                        parameter("newValue", newValue ->
                                                parameter(StringUnmarshallers.INTEGER, "empID", empID -> {
                                                    CompletionStage<Boolean> isUpdated = CompletableFuture.completedFuture(iEmployeeDataBaseEmployee.updateEmployeeData(columnName, newValue, empID));
                                                    return onSuccess(isUpdated, done -> complete((done) ? StatusCodes.OK : StatusCodes.BAD_REQUEST));
                                                })))))))
        );
    }
}

