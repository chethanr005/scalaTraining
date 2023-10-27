package com.chethan.assignment7.student;

import akka.Done;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

import static akka.http.javadsl.server.PathMatchers.segment;

/**
 * Created by Chethan on Mar 30, 2022.
 */

public class HttpServer extends AllDirectives {
    private              IStudentDatabase      database;
    private              StudentImplementation studentImplementation;
    private static final String                SECRET_KEY = "e283dbb0-4ffb-4535-be21-e70b7056f444";
    private static final String                FILENAME   = "UpdateDatabase.csv";

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

    private Supplier<Route> getUpdateDataFromCSVRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                headerValueByName("apiKey", apikey ->
                        headerValueByName("fileName", fileName -> {
                                    if (apikey.equals("student") && fileName.equals(FILENAME)) {
                                        return entity(Jackson.unmarshaller(String.class), path -> {
                                            CompletionStage<Optional<Map<String, List<String>>>> futureSaved = null;
                                            try {
                                                futureSaved = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.updateFromCSVToDatabase(path).get()));
                                            } catch (InterruptedException | ExecutionException e) {
                                                e.printStackTrace();
                                            }
                                            return onSuccess(futureSaved,
                                                    success -> success.map(item -> complete("Succesfully added : " + item.get("added") + "\nSuccessfully updated :" + item.get("updated"))).orElseGet(() -> complete(StatusCodes.NOT_FOUND,
                                                    "Student data is not saved")));
                                        });
                                    } else return complete(StatusCodes.UNAUTHORIZED,"invalid headers");
                                }
                        )));
    };


    private Supplier<Route> getExportCSVFileRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                headerValueByName("apiKey", apikey ->
                        headerValueByName("fileName", fileName -> {
                                    if (apikey.equals("student") && fileName.equals(FILENAME)) {
                                        return entity(Jackson.unmarshaller(String.class), path -> {
                                            CompletionStage<Optional<Done>> futureSaved = null;
                                            try {
                                                futureSaved = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.csvFileWriter(path).get()));
                                            } catch (InterruptedException | ExecutionException e) {
                                                e.printStackTrace();
                                            }
                                            return onSuccess(futureSaved,
                                                    success -> success.map(item -> complete("Data successfully exported")).orElseGet(() -> complete(StatusCodes.NOT_FOUND,
                                                            "Unable to export the data")));
                                        });
                                    } else return complete(StatusCodes.UNAUTHORIZED);
                                }
                        )));
    };


    private Supplier<Route> getDeleteStudentsFromCSVRoute = () -> {

        return authenticateBasic("secure", basicAuthentication, user ->
                headerValueByName("apiKey", apikey ->
                        headerValueByName("fileName", fileName -> {
                                    if (apikey.equals("student") && fileName.equals(FILENAME)) {
                                        return entity(Jackson.unmarshaller(String.class), path -> {
                                            CompletionStage<Optional<List<String>>> futureSaved = null;
                                            try {
                                                futureSaved = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.deleteStudentFromCSV(path).get()));
                                            } catch (InterruptedException | ExecutionException e) {
                                                e.printStackTrace();
                                            }
                                            return onSuccess(futureSaved,
                                                    success -> success.map(item -> complete("Student data with ID as mentioned below are successfully deleted : \n" + item)).orElseGet(() -> complete(StatusCodes.NO_CONTENT)));
                                        });
                                    } else return complete(StatusCodes.UNAUTHORIZED);
                                }
                        )));
    };


    Route createRoute() {
        return
                pathPrefix("student", () ->
                        concat(
                                get(getStudentRoute),

                                get(getAllStudentsRoute),

                                path("addNewStudent", () -> post(getAddNewStudentRoute)),

                                path("updateStudent", () -> put(getUpdateStudentRoute)),

                                pathPrefix("deleteStudent", getDeleteRoute),

                                path("updateDataFromCSV", () -> post(getUpdateDataFromCSVRoute)),

                                path("deleteStudentsFromCSV", () -> delete(getDeleteStudentsFromCSVRoute)),

                                path("exportCSVFile", () -> post(getExportCSVFileRoute))));
    }
}