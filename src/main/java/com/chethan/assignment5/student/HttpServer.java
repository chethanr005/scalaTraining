package com.chethan.assignment5.student;

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

import static akka.http.javadsl.server.PathMatchers.integerSegment;
import static akka.http.javadsl.server.PathMatchers.segment;

/**
 * Created by Chethan on Mar 10, 2022.
 */

class HttpServer extends AllDirectives {
    private IStudentDatabase      database;
    private StudentImplementation studentImplementation;

    HttpServer(IStudentDatabase database) {
        this.database = database;
        this.studentImplementation=new StudentImplementation(database);
    }

    public static void main(String[] args) throws Exception {
        ActorSystem<Void>                    system  = ActorSystem.create(Behaviors.empty(), "routes");
        final Http                           http    = Http.get(system);
        HttpServer                           app     = new HttpServer(new StudentDatabase());
        final CompletionStage<ServerBinding> binding = http.newServerAt("localhost", 8888).bind(app.createRoute());
        System.out.println("Server online at http://localhost:8888/\nPress RETURN to stop...");
        System.in.read();
        binding.thenCompose(ServerBinding::unbind).thenAccept(unbound -> system.terminate());
    }


    private CompletableFuture<Student> updateStudentDetails(String sid, String column, String value ){
        if(column.equals("activities"))
           return database.updateStudentDetails(sid,column,"Array["+value+"]");
        else
            return database.updateStudentDetails(sid,column,value);
    }


     Route createRoute() {
        return
                concat(
                        path("student", () ->
                                get(() ->
                                        complete("  Welcome to Student Database  "))),

                        pathPrefix("student", () -> path("maleAndFemaleContainer", () -> {
                            return get(() -> {
                                CompletionStage<Optional<MaleAndFemaleContainer>> maleAndFemaleContainer = null;
                                try {
                                    maleAndFemaleContainer = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getMaleAndFemaleContainer().get()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return onSuccess(maleAndFemaleContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                               .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "maleAndFemaleContainer Not Found")));
                            });
                        })),

                        pathPrefix("student", () -> path("prefixNames", () -> {
                            return get(() -> {
                                CompletionStage<Optional<List<String>>> prefixNames = null;
                                try {
                                    prefixNames = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getPrefixStudentsName().get()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return onSuccess(prefixNames, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                    .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "prefixNames Not Found")));
                            });
                        })),

                        pathPrefix("student", () ->
                                pathPrefix("gradeLevelContainer", () -> path(integerSegment(), (Integer gradeLevel) -> {
                                    return get(() -> {
                                        CompletionStage<Optional<GradeLevelContainer>> gradeLevelContainer = null;
                                        try {
                                            gradeLevelContainer = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getGradeLevelContainer(gradeLevel).get()));
                                        } catch (ExecutionException | InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        return onSuccess(gradeLevelContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                                    .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "gradeLevelContainer Not Found")));
                                    });
                                }))),

                        pathPrefix("student", () -> path("gradeLevelContainer2", () -> {
                            return get(() -> {
                                CompletionStage<Optional<List<GradeLevelContainer>>> gradeLevelContainer = null;
                                try {
                                    gradeLevelContainer = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getGradeLevelContainer2().get()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return onSuccess(gradeLevelContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                            .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "gradeLevelContainer Not Found")));
                            });
                        })),

                        pathPrefix("student", () ->
                                pathPrefix("activityContainer", () -> path(segment(), (String activity) -> {
                                    return get(() -> {
                                        CompletionStage<Optional<ActivityContainer>> activityContainer = null;
                                        try {
                                            activityContainer = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getActivityContainer(activity).get()));
                                        } catch (ExecutionException | InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        return onSuccess(activityContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                                  .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "activityContainer Not Found")));
                                    });
                                }))),

                        pathPrefix("student", () -> path("activityContainer2", () -> {
                            return get(() -> {
                                CompletionStage<Optional<List<ActivityContainer>>> activityContainer = null;
                                try {
                                    activityContainer = CompletableFuture.completedFuture(Optional.of(studentImplementation.getActivityContainer2().get()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return onSuccess(activityContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                          .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "activityContainer Not Found")));
                            });
                        })),

                        pathPrefix("student", () ->
                                pathPrefix("performanceContainer", () -> path(segment(), (String performance) -> {
                                    return get(() -> {
                                        CompletionStage<Optional<PerformanceContainer>> PerformanceContainer = null;
                                        try {
                                            PerformanceContainer = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getPerformanceContainer(performance).get()));
                                        } catch (ExecutionException | InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        return onSuccess(PerformanceContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                                     .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "PerformanceContainer Not Found")));
                                    });
                                }))),

                        pathPrefix("student", () -> path("performanceContainer2", () -> {
                            return get(() -> {
                                CompletionStage<Optional<List<PerformanceContainer>>> PerformanceContainer = null;
                                try {
                                    PerformanceContainer = CompletableFuture.completedFuture(Optional.ofNullable(studentImplementation.getPerformanceContainer2().get()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return onSuccess(PerformanceContainer, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                             .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "PerformanceContainer2 Not Found")));
                            });
                        })),

                        pathPrefix("student", () -> path("getAllStudents", () -> {
                            return get(() -> {
                                CompletionStage<Optional<List<Student>>> allStudents = null;
                                try {
                                    allStudents = CompletableFuture.completedFuture(Optional.ofNullable(database.getStudentsData().get()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return onSuccess(allStudents, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                    .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Student Details Not Found")));
                            });
                        })),
                        pathPrefix("student", () -> path(segment(), (String sid) -> {
                            return get(() -> {
                                CompletionStage<Optional<Student>> student = null;
                                try {
                                    student = CompletableFuture.completedFuture(Optional.ofNullable(database.getStudent(sid).get()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return onSuccess(student, maybeItem -> maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                                                .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Student Not Found")));
                            });
                        })),

                        pathPrefix("student", () ->
                                path("addNewStudent", () -> {
                                    return post(() -> {
                                        return entity(Jackson.unmarshaller(Student.class), student -> {
                                            CompletionStage<Optional<Student>> futureSaved = null;
                                            try {
                                                futureSaved = CompletableFuture.completedFuture(Optional.ofNullable(database.addStudent(student).get()));
                                            } catch (InterruptedException | ExecutionException e) {
                                                e.printStackTrace();
                                            }
                                            return onSuccess(futureSaved, success ->success.map(item -> completeOK(item,Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Student data is not saved")));
                                        });
                                    });
                                })),

                        pathPrefix("student", () ->
                                path("updateStudent", () -> put(() ->
                                                parameter(StringUnmarshallers.STRING, "sid", sid ->
                                                        parameter(StringUnmarshallers.STRING, "column", column ->
                                                                parameter("value", value -> {
                                                                    CompletionStage<Optional<Student>> student=null;
                                                                    try {
                                                                        student=CompletableFuture.completedFuture(Optional.ofNullable(updateStudentDetails(sid,column,value).get()));
                                                                    } catch (InterruptedException | ExecutionException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    return onSuccess(student, success-> success.map(item -> completeOK(item,Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND,
                                                                            "Student data not updated")));
                                                                })
                                                        ))))),


                        pathPrefix("student", () ->
                                pathPrefix("deleteStudent", () -> path(segment(), (String sid) -> {
                                    return delete(() -> {
                                        database.deleteStudentsById(sid);
                                        CompletionStage<Optional<Done>> deleted = CompletableFuture.completedFuture(Optional.of(Done.getInstance()));
                                        return onSuccess(deleted, deleteSuccess -> complete(StatusCodes.OK, "Student details successfully deleted"));
                                    });
                                }))));
    }
}