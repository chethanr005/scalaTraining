package com.chethan.assignment5.employee;

import akka.Done;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.unmarshalling.StringUnmarshallers;
import com.chethan.assignment5.student.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static akka.http.javadsl.server.PathMatchers.integerSegment;
import static akka.http.javadsl.server.PathMatchers.segment;

/**
 * Created by Chethan on Mar 10, 2022.
 */

class HttpServer extends AllDirectives {
    private IEmployeeDatabase      database             ;
    private EmployeeImplementation employeeImplementation ;

    HttpServer(IEmployeeDatabase database) {
        this.database = database;
        this.employeeImplementation = new EmployeeImplementation(database);
    }

    public static void main(String[] args) throws Exception {
        ActorSystem<Void>                    system  = ActorSystem.create(Behaviors.empty(), "routes");
        final Http                           http    = Http.get(system);
        HttpServer                           app     = new HttpServer(new EmployeeDatabase());
        final CompletionStage<ServerBinding> binding = http.newServerAt("localhost", 8008).bind(app.createRoute());
        System.out.println("Server online at http://localhost:8008/\nPress RETURN to stop...");
        System.in.read();
        binding.thenCompose(ServerBinding::unbind).thenAccept(unbound -> system.terminate());
    }

     Route createRoute() {
        return
                concat(
                        path("employee", () ->
                                get(() ->
                                        complete("  Welcome to Employee Database  "))),


                        pathPrefix("employee", () ->
                                pathPrefix("noOfEmpByDeptContainer", () -> path(segment(), (String department) -> {
                                    return get(() -> {
                                        CompletionStage<Optional<NoOfEmpByDeptContainer>> noOfEmpByDeptContainer = null;
                                        try {
                                            noOfEmpByDeptContainer = CompletableFuture.completedFuture(Optional.ofNullable(employeeImplementation.getNoOfEmployeesByDept(department).get()));
                                        } catch (ExecutionException | InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        return onSuccess(noOfEmpByDeptContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                                    .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "NoOfEmpByDeptContainer Not Found")));
                                    });
                                }))),



                        pathPrefix("employee", () -> path("groupEmployeesByDepartment", () -> {
                            return get(() -> {
                                CompletionStage<Optional<List<GroupEmployeesByDepartment>>> groupEmployeesByDepartment = null;
                                try {
                                    groupEmployeesByDepartment = CompletableFuture.completedFuture(Optional.ofNullable(employeeImplementation.getGroupEmployeesByDept().get()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return onSuccess(groupEmployeesByDepartment, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                               .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "GroupEmployeesByDepartment Not Found")));
                            });
                        })),

                        pathPrefix("employee", () ->
                                path("increaseEmployeesSalary", () -> put(() ->
                                        parameter(StringUnmarshallers.STRING, "department", department ->
                                                        parameter(StringUnmarshallers.DOUBLE,"hike", hike -> {
                                                            CompletionStage<Optional<List<Employee>>> employee=null;
                                                            try {
                                                                employee=CompletableFuture.completedFuture(Optional.ofNullable(employeeImplementation.getIncreasedSalaryEmployees(Optional.ofNullable(department),Optional.ofNullable(hike)).get()));
                                                            } catch (InterruptedException | ExecutionException e) {
                                                                e.printStackTrace();
                                                            }
                                                            return onSuccess(employee, success-> success.map(item -> completeOK(item,Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND,
                                                                    "Employees salary not incremented")));
                                                        })
                                                )))),




                        pathPrefix("employee", () ->
                                path("promoteEmployees", () -> put(() ->{
                                                    CompletionStage<Optional<List<Employee>>> employee=null;
                                                    try {
                                                        employee=CompletableFuture.completedFuture(Optional.ofNullable(employeeImplementation.getPromotedEmployees().get()));
                                                    } catch (InterruptedException | ExecutionException e) {
                                                        e.printStackTrace();
                                                    }
                                                    return onSuccess(employee, success-> success.map(item -> completeOK(item,Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND,
                                                            "Employees not promoted")));
                                                })
                                        )),

                        pathPrefix("employee", () -> path("getAllEmployees", () -> {
                            return get(() -> {
                                CompletionStage<Optional<List<Employee>>> allEmployees = null;
                                try {
                                    allEmployees = CompletableFuture.completedFuture(Optional.ofNullable(database.getEmployeeData().get()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return onSuccess(allEmployees, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                    .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Employee Details Not Found")));
                            });
                        })),
                        pathPrefix("employee", () -> path(integerSegment(), (Integer eid) -> {
                            return get(() -> {
                                CompletionStage<Optional<Employee>> employee = null;
                                try {
                                    employee = CompletableFuture.completedFuture(Optional.ofNullable(database.getEmployeeById(eid).get()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return onSuccess(employee, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Employee Not Found")));
                            });
                        })),

                        pathPrefix("employee", () ->
                                path("addNewEmployee", () -> {
                                    return post(() -> {
                                        return entity(Jackson.unmarshaller(Employee.class), employee -> {
                                            CompletionStage<Optional<Employee>> employeeSaved = null;
                                            try {
                                                employeeSaved = CompletableFuture.completedFuture(Optional.ofNullable(database.addEmployee(employee).get()));
                                            } catch (InterruptedException | ExecutionException | IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                            return onSuccess(employeeSaved, success ->success.map(item -> completeOK(item,Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Employee data is not saved")));
                                        });
                                    });
                                })),

                        pathPrefix("employee", () ->
                                path("updateEmployee", () -> put(() ->
                                                parameter(StringUnmarshallers.INTEGER, "eid", eid ->
                                                        parameter(StringUnmarshallers.STRING, "column", column ->
                                                                parameter("value", value -> {
                                                                    CompletionStage<Optional<Employee>> employee=null;
                                                                    try {
                                                                        employee=CompletableFuture.completedFuture(Optional.ofNullable(database.updateEmployeeDetailsById(eid,column,value).get()));
                                                                    } catch (InterruptedException | ExecutionException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    return onSuccess(employee, success-> success.map(item -> completeOK(item,Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND,
                                                                            "Employee data not updated")));
                                                                })
                                                        ))))),


                        pathPrefix("employee", () ->
                                pathPrefix("deleteEmployee", () -> path(integerSegment(), (Integer eid) -> {
                                    return delete(() -> {
                                        database.deleteEmployeeById(eid);
                                        CompletionStage<Optional<Done>> deleted = CompletableFuture.completedFuture(Optional.of(Done.getInstance()));
                                        return onSuccess(deleted, deleteSuccess -> complete(StatusCodes.NOT_FOUND, "Employee details successfully deleted"));
                                    });
                                }))));
    }
}