package com.kishor.assignment5.employee;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.unmarshalling.StringUnmarshallers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static akka.http.javadsl.server.PathMatchers.integerSegment;
import static akka.http.javadsl.server.PathMatchers.segment;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public class EmployeeImplementation extends AllDirectives {
    private final IEmployee         iEmployee;
    public        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    EmployeeImplementation(IEmployee database) {
        this.iEmployee = database;
    }

    static final ExecutorService service = Executors.newCachedThreadPool();

    public CompletableFuture<Employee> addEmployee(int empId, String name, String department, double salary, String gender, String joiningDate, String dob, String jobLevel) throws Exception {
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
        IEmployee d = new EmployeeDatabase();

        DateTimeFormatter      formatter              = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        EmployeeImplementation employeeImplementation = new EmployeeImplementation(d);
//        System.out.println(employeeImplementation.fetchEmployeeById(1));


//        System.out.println("1. New Employee Added   ==> \n" + employeeImplementation.addEmployee(7, "Geetha", "Administration", 40000.0, "female", LocalDate.of(2020, 07, 03), LocalDate.of(1994, 12, 13), "junior").get() + "\n\n");
//        Thread.sleep(2000);
//        System.out.println("2. Employees by Count   ==> \n " + employeeImplementation.getNoOfEmployeeByCount("IT Development").get() + "\n\n");
//        Thread.sleep(2000);
//        System.out.println("3. Grouping employees by Department   ==> \n " + employeeImplementation.groupByDepartment().get() + "\n\n");
//        Thread.sleep(2000);
//        System.out.println("4. Increase Salary by Department   ==> \n " + employeeImplementation.increaseSalaryForDept("IT Development", 5000).get() + "\n\n");
//        Thread.sleep(2000);
        //    System.out.println("5. Promote Employee to Senior   ==> \n " + employeeImplementation.getPromoteEmployee().get());


        ActorSystem<Void> system = ActorSystem.create(Behaviors.empty(), "routes");
        final Http        http   = Http.get(system);
        final CompletionStage<ServerBinding> binding =
                http.newServerAt("localhost", 8080)
                    .bind(employeeImplementation.createRoute());
        System.out.println("Server online at http://localhost:8080/\n");
    }

    Route createRoute() {
        return concat(
                pathPrefix("employee", () -> path("getAllEmployee", () -> get(() -> {
                    List<Employee> employeeList = null;
                    try {
                        employeeList = iEmployee.getAllEmployees().get();
                    } catch (InterruptedException | ExecutionException | SQLException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<Employee>>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(employeeList));
                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> {
                        return completeOK(employeesData, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                }))),
                pathPrefix("employee", () -> pathPrefix("getAllEmployeeById", () -> get(() -> path(integerSegment(), (Integer empid) -> {
                    Employee employeeData = null;
                    try {
                        employeeData = iEmployee.getEmployeeById(empid).get();
                    } catch (InterruptedException | ExecutionException | SQLException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<Employee>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(employeeData));
                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> {
                        return completeOK(employeesData, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                })))),
                pathPrefix("employee", () -> path("insert", () ->
                        post(() -> {
                            return entity(Jackson.unmarshaller(Employee.class), employee ->
                            {
                                Employee futureSaved = null;
                                try {
                                    futureSaved = iEmployee.insertNewRecord(employee).get();
                                } catch (SQLException | InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                                CompletionStage<Optional<Employee>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(futureSaved));
                                return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> {
                                    return completeOK(employeesData, Jackson.marshaller());
                                }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                            });
                        }))),
                pathPrefix("employee", () -> path("update", () ->
                        put(() -> {
                            return entity(Jackson.unmarshaller(Employee.class), item -> {
                                return parameter(StringUnmarshallers.INTEGER, "EmpId", empId -> parameter(StringUnmarshallers.STRING, "columnName", columnName -> parameter(StringUnmarshallers.STRING, "value", value -> {
                                    Employee futureUpdated = null;
                                    try {
                                        futureUpdated = iEmployee.updateValueThroughEmpId(columnName, value, empId).get();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    CompletionStage<Optional<Employee>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(futureUpdated));
                                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> completeOK(employeesData, Jackson.marshaller())).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                                })));
                            });
                        }))),
                pathPrefix("employee", () -> pathPrefix("delete", () -> path(integerSegment(), (Integer empId) -> delete(() -> {
                    List<Employee> futureMaybeItem = null;
                    try {
                        futureMaybeItem = iEmployee.deleteEmployeeById(empId).get();
                    } catch (InterruptedException | ExecutionException | SQLException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<Employee>>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(futureMaybeItem));
                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> completeOK(employeesData, Jackson.marshaller())).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                })))),
                pathPrefix("employee", () ->
                        pathPrefix("getNoOfEmployeeByCount", () -> get(() ->
                                path(segment(), (String departmentName) -> {
                                    EmployeeCountByDepartmentContainer count = null;
                                    try {
                                        count = getNoOfEmployeeByCount(departmentName).get();
                                    } catch (ExecutionException | InterruptedException | SQLException e) {
                                        e.printStackTrace();
                                    }
                                    CompletionStage<Optional<EmployeeCountByDepartmentContainer>> employeeCount = CompletableFuture.completedFuture(Optional.ofNullable(count));
                                    return onSuccess(employeeCount, data -> data.map(empCount -> completeOK(empCount, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found")));
                                })))),
                pathPrefix("employee", () -> path("getGroupByDepartment", () -> get(() -> {
                    List<GroupByDepartmentContainer> employeeList = null;
                    try {
                        employeeList = groupByDepartment().get();
                    } catch (InterruptedException | ExecutionException | SQLException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<GroupByDepartmentContainer>>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(employeeList));
                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> {
                        return completeOK(employeesData, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                }))),
                pathPrefix("employee", () ->
                        path("increaseSalaryForDept", () -> concat(put(() ->
                                parameter("department", department ->
                                        parameter(StringUnmarshallers.DOUBLE, "hike", hike ->
                                        {
                                            List<IncreaseSalaryContainer> hikedEmployee = null;
                                            try {
                                                hikedEmployee = increaseSalaryForDept(department, hike).get();
                                            } catch (ExecutionException | InterruptedException | SQLException e) {
                                                e.printStackTrace();
                                            }
                                            CompletionStage<Optional<List<IncreaseSalaryContainer>>> employeeList = CompletableFuture.completedFuture(Optional.ofNullable(hikedEmployee));
                                            return onSuccess(employeeList, employees -> employees.map(data -> completeOK(data, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "No data found")));
                                        })))))),
                pathPrefix("employee", () -> path("getPromoteEmployee", () -> get(() -> {
                    List<GetPromoteEmployeeContainer> employeeList = null;
                    try {
                        employeeList = getPromoteEmployee().get();
                    } catch (InterruptedException | ExecutionException | SQLException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<GetPromoteEmployeeContainer>>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(employeeList));
                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> {
                        return completeOK(employeesData, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                })))
        );
    }

}
