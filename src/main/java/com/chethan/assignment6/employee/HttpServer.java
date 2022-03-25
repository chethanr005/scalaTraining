package com.chethan.assignment6.employee;

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

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

import static akka.http.javadsl.server.PathMatchers.integerSegment;
import static akka.http.javadsl.server.PathMatchers.segment;

/**
 * Created by Chethan on Mar 10, 2022.
 */

class HttpServer extends AllDirectives {
    private              IEmployeeDatabase      database;
    private              EmployeeImplementation employeeImplementation;
    private static final String                 SECRET_KEY = "e283dbb0-4ffb-4535-be21-e70b7056f444";


    HttpServer(IEmployeeDatabase database) {
        this.database = database;
        this.employeeImplementation = new EmployeeImplementation(database);
    }

    public static void main(String[] args) throws Exception {
        ActorSystem<Void>                    system  = ActorSystem.create(Behaviors.empty(), "routes");
        final Http                           http    = Http.get(system);
        HttpServer                           app     = new HttpServer(new EmployeeDatabase());
        final CompletionStage<ServerBinding> binding = http.newServerAt("localhost", 8008).bind(app.createRoute());
        System.out.println("Server online at http://localhost:8008/");
    }


    private Function<Optional<ProvidedCredentials>, Optional<Employee>> basicAuthentication = providedCredentials -> {
        try {
            List<Employee> students = database.getEmployeeData().get();
            if (providedCredentials.isPresent()) {
                String             username = providedCredentials.get().identifier();
                Optional<Employee> employee = students.stream().filter(user -> user.getName().equals(username)).findAny();
                if (employee.isPresent())
                    if (providedCredentials.get().verify(String.valueOf(employee.get().getId())))
                        return employee;
            }
        } catch (InterruptedException | ExecutionException e) {
            complete(StatusCodes.NOT_FOUND, "Username not found");
        }
        return Optional.empty();
    };


    private Supplier<Route> getNoOfEmployeesByDeptRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->

                path(segment(), (String department) -> {
                    CompletionStage<Optional<NoOfEmpByDeptContainer>> noOfEmpByDeptContainer = null;
                    try {
                        noOfEmpByDeptContainer = CompletableFuture.completedFuture(Optional.ofNullable(employeeImplementation.getNoOfEmployeesByDept(department).get()));
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    return onSuccess(noOfEmpByDeptContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                   .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "NoOfEmpByDeptContainer Not Found")));
                }));
    };


    private Supplier<Route> getGroupEmployeesByDeptRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user -> {
            CompletionStage<Optional<List<GroupEmployeesByDepartment>>> groupEmployeesByDepartment = null;
            try {
                groupEmployeesByDepartment = CompletableFuture.completedFuture(Optional.ofNullable(employeeImplementation.getGroupEmployeesByDept().get()));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return onSuccess(groupEmployeesByDepartment, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                               .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "GroupEmployeesByDepartment Not Found")));
        });
    };


    private Supplier<Route> increaseEmployeesSalaryRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                headerValueByName("apiKey", apikey ->
                        headerValueByName("secretKey", Key -> {
                            if (apikey.equals("employee") && Key.equals(SECRET_KEY)) {
                                return parameter(StringUnmarshallers.STRING, "department", department ->
                                        parameter(StringUnmarshallers.DOUBLE, "hike", hike -> {
                                            CompletionStage<Optional<List<Employee>>> employee = null;
                                            try {
                                                employee = CompletableFuture.completedFuture(Optional.ofNullable(employeeImplementation.getIncreasedSalaryEmployees(Optional.ofNullable(department), Optional.ofNullable(hike)).get()));
                                            } catch (InterruptedException | ExecutionException e) {
                                                e.printStackTrace();
                                            }
                                            return onSuccess(employee, success -> success.map(item -> completeOK(item, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND,
                                                    "Employees salary not incremented")));
                                        }));
                            } else return complete(StatusCodes.UNAUTHORIZED);
                        })));
    };


    private Supplier<Route> promoteEmployeesRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                headerValueByName("apiKey", apikey ->
                        headerValueByName("secretKey", Key -> {
                            if (apikey.equals("employee") && Key.equals(SECRET_KEY)) {
                                CompletionStage<Optional<List<Employee>>> employee = null;
                                try {
                                    employee = CompletableFuture.completedFuture(Optional.ofNullable(employeeImplementation.getPromotedEmployees().get()));
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                                return onSuccess(employee, success -> success.map(item -> completeOK(item, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND,
                                        "Employees not promoted")));
                            } else return complete(StatusCodes.UNAUTHORIZED);
                        })));

    };


    private Supplier<Route> getAllEmployeesRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user -> {
            CompletionStage<Optional<List<Employee>>> allEmployees = null;
            try {
                allEmployees = CompletableFuture.completedFuture(Optional.ofNullable(database.getEmployeeData().get()));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return onSuccess(allEmployees, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                 .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Employee Details Not Found")));
        });
    };


    private Supplier<Route> getEmployeesRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                path(integerSegment(), (Integer eid) -> {
                    CompletionStage<Optional<Employee>> employee = null;
                    try {
                        employee = CompletableFuture.completedFuture(Optional.ofNullable(database.getEmployeeById(eid).get()));
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    return onSuccess(employee, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                     .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Employee Not Found")));
                }));
    };

    private Supplier<Route> getFilteredEmployeesRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                entity(Jackson.unmarshaller(FilterEmployees.class), filterData -> {

                    CompletionStage<Optional<List<Employee>>> filteredEmployees = null;
                    try {
                        filteredEmployees = CompletableFuture.completedFuture(Optional.ofNullable(employeeImplementation.getFilteredEmployees(filterData).get()));
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return onSuccess(filteredEmployees, success -> success.map(item -> completeOK(item, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND,
                            "Employee data not updated")));
                }));
    };


    private Supplier<Route> addNewEmployeeRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                headerValueByName("apiKey", apikey ->
                        headerValueByName("secretKey", Key -> {
                            if (apikey.equals("employee") && Key.equals(SECRET_KEY)) {
                                return entity(Jackson.unmarshaller(Employee.class), employee -> {
                                    CompletionStage<Optional<Employee>> employeeSaved = null;
                                    try {
                                        employeeSaved = CompletableFuture.completedFuture(Optional.ofNullable(database.addEmployee(employee).get()));
                                    } catch (InterruptedException | ExecutionException | IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                    return onSuccess(employeeSaved, success -> success.map(item -> completeOK(item, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Employee data is not saved")));
                                });
                            } else return complete(StatusCodes.UNAUTHORIZED);
                        })));
    };


    private Supplier<Route> updateEmployeeRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                headerValueByName("apiKey", apikey ->
                        headerValueByName("secretKey", Key -> {
                            if (apikey.equals("employee") && Key.equals(SECRET_KEY)) {
                                return entity(Jackson.unmarshaller(Employee.class), employee -> {
                                    CompletionStage<Optional<Employee>> employeeSaved = null;
                                    try {
                                        employeeSaved = CompletableFuture.completedFuture(Optional.ofNullable(database.updateEmployeeDetailsById(employee).get()));
                                    } catch (InterruptedException | ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                    return onSuccess(employeeSaved, success -> success.map(item -> completeOK(item, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND,
                                            "Employee data not updated")));
                                });
                            } else return complete(StatusCodes.UNAUTHORIZED);
                        })));
    };


    private Supplier<Route> deleteEmployeeRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                headerValueByName("apiKey", apikey ->
                        headerValueByName("secretKey", Key -> {
                            if (apikey.equals("employee") && Key.equals(SECRET_KEY)) {
                                return path(integerSegment(), (Integer eid) -> {
                                    database.deleteEmployeeById(eid);
                                    CompletionStage<Optional<Done>> deleted = CompletableFuture.completedFuture(Optional.of(Done.getInstance()));
                                    return onSuccess(deleted, deleteSuccess -> complete(StatusCodes.NOT_FOUND, "Employee details successfully deleted"));
                                });
                            } else return complete(StatusCodes.UNAUTHORIZED);
                        })));
    };


    Route createRoute() {
        return pathPrefix("employee", () ->
                concat(
                        pathPrefix("noOfEmpByDeptContainer", () -> get(getNoOfEmployeesByDeptRoute)),

                        path("groupEmployeesByDepartment", () -> get(getGroupEmployeesByDeptRoute)),

                        path("increaseEmployeesSalary", () -> put(increaseEmployeesSalaryRoute)),

                        path("promoteEmployees", () -> put(promoteEmployeesRoute)),

                        get(getEmployeesRoute),

                        get(getAllEmployeesRoute),

                        path("addNewEmployee", () -> post(addNewEmployeeRoute)),

                        path("updateEmployee", () -> put(updateEmployeeRoute)),

                        pathPrefix("deleteEmployee", () -> delete(deleteEmployeeRoute)),

                        path("getFilteredEmployees", () -> post(getFilteredEmployeesRoute))
                ));
    }
}