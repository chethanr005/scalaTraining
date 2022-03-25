package com.chethan.assignment6.student;

import akka.Done;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.http.javadsl.Http;
import akka.http.javadsl.OutgoingConnection;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpMethod;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.model.headers.BasicHttpCredentials;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.http.scaladsl.model.HttpMethods;
import akka.stream.javadsl.Flow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    private              IStudentDatabase      database;
    private              StudentImplementation studentImplementation;
    private static final String                SECRET_KEY = "e283dbb0-4ffb-4535-be21-e70b7056f444";

    HttpServer(IStudentDatabase database) {
        this.database = database;
        this.studentImplementation = new StudentImplementation(database);
    }

    public static void main(String[] args) throws Exception {
        ActorSystem<Void>                    system  = ActorSystem.create(Behaviors.empty(), "routes");
        final Http                           http    = Http.get(system);
        HttpServer                           app     = new HttpServer(new StudentDatabase());
        final CompletionStage<ServerBinding> binding = http.newServerAt("localhost", 8888).bind(app.createRoute());
        System.out.println("Server online at http://localhost:8888/");

    }


    private Function<Optional<ProvidedCredentials>, Optional<Student>> basicAuthentication = providedCredentials -> {
        try {
            List<Student> students = database.getStudentsData().get();
            if (providedCredentials.isPresent()) {
                String            username = providedCredentials.get().identifier();
                Optional<Student> student  = students.stream().filter(user -> user.getName().equals(username)).findAny();
                if (student.isPresent())
                    if (providedCredentials.get().verify(student.get().getId()))
                        return student;
            }
        } catch (InterruptedException | ExecutionException e) {
            complete(StatusCodes.NOT_FOUND, "Username not found");
        }
        return Optional.empty();
    };

    private Supplier<Route> getAllStudentsRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user -> {
            if (user != null) {
                CompletionStage<Optional<List<Student>>> allStudents = null;
                try {
                    allStudents = CompletableFuture.completedFuture(Optional.ofNullable(database.getStudentsData().get()));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return onSuccess(allStudents, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                    .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Student Details Not Found")));
            } else return complete(StatusCodes.NOT_FOUND, "Username not found");
        });
    };

    private Supplier<Route> getMaleAndFemaleContainerRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user -> {

            CompletionStage<Optional<MaleAndFemaleContainer>> maleAndFemaleContainer = null;
            try {
                maleAndFemaleContainer = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getMaleAndFemaleContainer().get()));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return onSuccess(maleAndFemaleContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                           .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "maleAndFemaleContainer Not Found")));
        });
    };

    private Supplier<Route> getPrefixNamesRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user -> {

            CompletionStage<Optional<List<String>>> prefixNames = null;
            try {
                prefixNames = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getPrefixStudentsName().get()));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return onSuccess(prefixNames, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "prefixNames Not Found")));
        });
    };

    private Supplier<Route> getGradeLevelContainer2Route = () -> {

        return authenticateBasic("secure", basicAuthentication, user -> {
            CompletionStage<Optional<List<GradeLevelContainer>>> gradeLevelContainer = null;
            try {
                gradeLevelContainer = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getGradeLevelContainer2().get()));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return onSuccess(gradeLevelContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                        .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "gradeLevelContainer Not Found")));
        });

    };


    private Supplier<Route> getActivityContainer2Route = () -> {

        return authenticateBasic("secure", basicAuthentication, user -> {

            CompletionStage<Optional<List<ActivityContainer>>> activityContainer = null;
            try {
                activityContainer = CompletableFuture.completedFuture(Optional.of(studentImplementation.getActivityContainer2().get()));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return onSuccess(activityContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                      .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "activityContainer Not Found")));
        });

    };


    private Supplier<Route> getPerformanceContainer2Route = () -> {

        return authenticateBasic("secure", basicAuthentication, user -> {
            CompletionStage<Optional<List<PerformanceContainer>>> PerformanceContainer = null;
            try {
                PerformanceContainer = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getPerformanceContainer2().get()));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return onSuccess(PerformanceContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                         .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "PerformanceContainer2 Not Found")));
        });

    };

    private Supplier<Route> getDeleteRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                headerValueByName("apiKey", apikey ->
                        headerValueByName("secretKey", Key -> {
                            if (apikey.equals("student") && Key.equals(SECRET_KEY)) {
                                return path(segment(), (String sid) ->
                                        delete(() -> {
                                            database.deleteStudentsById(sid);
                                            CompletionStage<Optional<Done>> deleted = CompletableFuture.completedFuture(Optional.of(Done.getInstance()));
                                            return onSuccess(deleted, deleteSuccess -> complete(StatusCodes.OK, "Student details successfully deleted"));
                                        }));
                            } else
                                return complete(StatusCodes.UNAUTHORIZED);
                        })));
    };


    private Supplier<Route> getGradeLevelContainerRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                path(integerSegment(), (Integer gradeLevel) -> {
                    CompletionStage<Optional<GradeLevelContainer>> gradeLevelContainer = null;
                    try {
                        gradeLevelContainer = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getGradeLevelContainer(gradeLevel).get()));
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    return onSuccess(gradeLevelContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "gradeLevelContainer Not Found")));
                }));
    };


    private Supplier<Route> getActivityContainerRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->

                path(segment(), (String activity) -> {

                    CompletionStage<Optional<ActivityContainer>> activityContainer = null;
                    try {
                        activityContainer = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getActivityContainer(activity).get()));
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    return onSuccess(activityContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                              .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "activityContainer Not Found")));
                }));


    };


    private Supplier<Route> getPerformanceContainerRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->

                path(segment(), (String performance) -> {

                    CompletionStage<Optional<PerformanceContainer>> PerformanceContainer = null;
                    try {
                        PerformanceContainer = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getPerformanceContainer(performance).get()));
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    return onSuccess(PerformanceContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                 .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "PerformanceContainer Not Found")));
                }));


    };


    private Supplier<Route> getStudentRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
        {
            return path(segment(), (String sid) -> {
                CompletionStage<Optional<Student>> student = null;
                try {
                    student = CompletableFuture.completedFuture(Optional.ofNullable(database.getStudent(sid).get()));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return onSuccess(student, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Student Not Found")));
            });
        });

    };


    private Supplier<Route> getAddNewStudentRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                headerValueByName("apiKey", apikey ->
                        headerValueByName("secretKey", Key -> {
                            if (apikey.equals("student") && Key.equals(SECRET_KEY)) {
                                return entity(Jackson.unmarshaller(Student.class), student -> {
                                    CompletionStage<Optional<Student>> futureSaved = null;
                                    try {
                                        futureSaved = CompletableFuture.completedFuture(Optional.ofNullable(database.addStudent(student).get()));
                                    } catch (InterruptedException | ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                    return onSuccess(futureSaved, success -> success.map(item -> completeOK(item, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Student data is not saved")));
                                });
                            } else return complete(StatusCodes.UNAUTHORIZED);
                        })));
    };

    private Supplier<Route> getUpdateStudentRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                headerValueByName("apiKey", apikey ->
                        headerValueByName("secretKey", Key -> {
                            if (apikey.equals("student") && Key.equals(SECRET_KEY)) {
                                return entity(Jackson.unmarshaller(Student.class), rawStudent -> {
                                    CompletionStage<Optional<Student>> student = null;
                                    try {
                                        student = CompletableFuture.completedFuture(Optional.ofNullable(database.updateStudentDetails(rawStudent).get()));
                                    } catch (InterruptedException | ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                    return onSuccess(student, success -> success.map(item -> completeOK(item, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND,
                                            "Student data not updated")));
                                });
                            } else return complete(StatusCodes.UNAUTHORIZED);

                        })));
    };


    private Supplier<Route> getFilterRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                entity(Jackson.unmarshaller(FilterStudents.class), filterObject -> {
                    CompletionStage<Optional<List<Student>>> students = null;
                    try {
                        students = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getFilteredStudents(filterObject).get()));
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return onSuccess(students, success -> success.map(item -> completeOK(item, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Ooops something went wrong ")));
                }));

    };


    Route createRoute() {
        return
                pathPrefix("student", () ->

                        concat(
                                path("maleAndFemaleContainer", () -> get(getMaleAndFemaleContainerRoute)),

                                path("prefixNames", () -> get(getPrefixNamesRoute)),

                                pathPrefix("gradeLevelContainer", () -> get(getGradeLevelContainerRoute)),

                                path("gradeLevelContainer2", () -> get(getGradeLevelContainer2Route)),

                                pathPrefix("activityContainer", () -> get(getActivityContainerRoute)),

                                path("activityContainer2", () -> get(getActivityContainer2Route)),

                                pathPrefix("performanceContainer", () -> get(getPerformanceContainerRoute)),

                                path("performanceContainer2", () -> get(getPerformanceContainer2Route)),

                                get(getStudentRoute),

                                get(getAllStudentsRoute),

                                path("addNewStudent", () -> post(getAddNewStudentRoute)),

                                path("updateStudent", () -> put(getUpdateStudentRoute)),

                                pathPrefix("deleteStudent", getDeleteRoute),

                                path("filterStudents", () -> post(getFilterRoute))));
    }
}