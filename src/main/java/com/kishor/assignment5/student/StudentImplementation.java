package com.kishor.assignment5.student;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static akka.http.javadsl.server.PathMatchers.integerSegment;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public class StudentImplementation extends AllDirectives {
    private IStudent iStudent;

    StudentImplementation(IStudent database) {
        this.iStudent = database;
    }

    ExecutorService service = Executors.newCachedThreadPool();

    public CompletableFuture<MaleAndFemaleContainer> maleAndFemaleCount() throws SQLException, ExecutionException, InterruptedException {
        List<Student> students = iStudent.getAllStudents().get();
        Supplier<MaleAndFemaleContainer> maleAndFemale = () -> {
            long maleCount   = students.stream().filter(student -> student.getGender().equals("male")).count();
            long femaleCount = students.stream().filter(student -> student.getGender().equals("female")).count();
            return new MaleAndFemaleContainer(maleCount, femaleCount);
        };
        return CompletableFuture.supplyAsync(maleAndFemale, service);
    }

    public CompletableFuture<List<String>> addPrefixToStudents() throws SQLException, ExecutionException, InterruptedException {
        List<Student> students = iStudent.getAllStudents().get();
        Supplier<List<String>> prefix = () -> {
            return students.stream().map(student -> {
                if (student.getGender().equals("male"))
                    return "Mr " + student.getName();
                else
                    return "Ms " + student.getName();
            }).collect(Collectors.toList());
        };
        return CompletableFuture.supplyAsync(prefix, service);
    }

    public CompletableFuture<List<GradeLevelContainer>> getAllGradeLevel() throws SQLException, ExecutionException, InterruptedException {
        List<Student> students = iStudent.getAllStudents().get();
        Supplier<List<GradeLevelContainer>> getGradeLeve = () -> {
            return students.stream().map(student -> student.getGradeLevel()).distinct().collect(Collectors.toList()).stream()
                           .map(distinctGrade -> {
                               Long count = students.stream().filter(student -> student.getGradeLevel() == distinctGrade).count();
                               return new GradeLevelContainer(distinctGrade, count);
                           }).collect(Collectors.toList());
        };
        return CompletableFuture.supplyAsync(getGradeLeve, service);
    }


    public CompletableFuture<List<ActivityContainer>> getNoOfStudentsBelongsToEachActivities() throws SQLException, ExecutionException, InterruptedException {
        List<Student> students = iStudent.getAllStudents().get();
        Supplier<List<ActivityContainer>> allActivities = () -> {
            return students.stream().flatMap(student -> student.getActivities().stream()).distinct().collect(Collectors.toList()).stream()
                           .map(activity -> {
                               Long count = students.stream().map(student -> student.getActivities()).flatMap(activities -> activities.stream()).filter(activities -> activities.equals(activity)).count();
                               return new ActivityContainer(activity, count);
                           }).collect(Collectors.toList());
        };
        return CompletableFuture.supplyAsync(allActivities, service);
    }

    public CompletableFuture<List<PerformanceContainer>> getPerformanceOfStudents() throws SQLException, ExecutionException, InterruptedException {
        List<Student> students = iStudent.getAllStudents().get();
        Supplier<List<PerformanceContainer>> getPerformance = () -> {
            ArrayList<PerformanceContainer> result    = new ArrayList<>();
            List<Student>                   poor      = students.stream().filter(student -> student.getGpa() >= 0.0 && student.getGpa() <= 4.0).collect(Collectors.toList());
            List<Student>                   average   = students.stream().filter(student -> student.getGpa() >= 4.1 && student.getGpa() <= 7.0).collect(Collectors.toList());
            List<Student>                   excellent = students.stream().filter(student -> student.getGpa() >= 7.1).collect(Collectors.toList());
            result.add(new PerformanceContainer("poor", poor));
            result.add(new PerformanceContainer("average", average));
            result.add(new PerformanceContainer("excellent", excellent));
            return result;
        };
        return CompletableFuture.supplyAsync(getPerformance, service);
    }

    public static void main(String[] args) throws SQLException, ExecutionException, InterruptedException {
        StudentDatabase       database              = new StudentDatabase();
        StudentImplementation studentImplementation = new StudentImplementation(database);
        StudentMockDatabase   mockDatabase          = new StudentMockDatabase();
//        System.out.println("1.1 Males Count     : " + s.maleAndFemaleCount().get().getmales());
//        System.out.println("1.2 Females Count     : " + s.maleAndFemaleCount().get().getmales() + "\n\n");
//        System.out.println("2. Student Names with Prefix     : " + s.addPrefixToStudents().get() + "\n\n");
//        System.out.println("3. All Grade Level's     :");
//        s.getAllGradeLevel().get().stream().forEach(System.out::println);
//        System.out.println("\n\n4. No of Student's belonging to particular Activity     :");
//        s.getNoOfStudentsBelongsToEachActivities().get().stream().forEach(System.out::println);
//        System.out.println("\n\n5. All Student's Performance     :");
//        s.getPerformanceOfStudents().get().stream().forEach(System.out::println);

        ActorSystem<Void> system = ActorSystem.create(Behaviors.empty(), "routes");
        final Http        http   = Http.get(system);
        final CompletionStage<ServerBinding> binding =
                http.newServerAt("localhost", 8080)
                    .bind(studentImplementation.createRoute());
        System.out.println("Server online at http://localhost:8080/\n");
    }

    private CompletionStage<Optional<Student>> fetchStudentByRegNo(final int regNo) throws SQLException, ExecutionException, InterruptedException {
        Student student = iStudent.getStudentById(regNo).get();
        return CompletableFuture.completedFuture(Optional.of(student));
    }

    private CompletionStage<List<Student>> saveStudent(final Student student) throws SQLException, ExecutionException, InterruptedException {
        Supplier<List<Student>> addStudents = () -> {
            List<Student> studentList = null;
            try {
                iStudent.insertNewRecord(student); //int regNo, String name, int gradeLevel, double gpa, String gender, List<String,activities
                studentList = iStudent.getAllStudents().get();
            } catch (SQLException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return studentList;
        };
        return CompletableFuture.supplyAsync(addStudents);
    }

    private CompletionStage<Student> modifyStudent(int RegNo, String columnName, String value) {
        Supplier<Student> updateStudents = () -> {
            Student student = null;
            try {
                student = iStudent.updateValueThroughRegNo(columnName, value, RegNo).get();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return student;
        };
        return CompletableFuture.supplyAsync(updateStudents);
    }

    private CompletionStage<List<Student>> deleteItems(int regNo) {
        Supplier<List<Student>> deleteStudents = () -> {
            List<Student> studentList = null;
            try {
                studentList = iStudent.deleteStudentById(regNo).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return studentList;
        };
        return CompletableFuture.supplyAsync(deleteStudents);
    }

    public Route createRoute() {
        return concat(
                pathPrefix("student", () -> pathPrefix("getStudentByRegNo", () ->
                        path(integerSegment(), (Integer regNo) -> {
                            return get(() -> {
                                Student futureMaybeStudent = null;
                                try {
                                    futureMaybeStudent = iStudent.getStudentById(regNo).get();
                                } catch (SQLException | ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                CompletionStage<Optional<Student>> studentData = CompletableFuture.completedFuture(Optional.ofNullable(futureMaybeStudent));
                                return onSuccess(studentData, maybeStudent -> {
                                    return maybeStudent.map(student -> completeOK(student, Jackson.marshaller()))
                                                       .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found"));
                                });
                            });
                        }))),
                pathPrefix("student", () -> path("getAllStudents", () -> get(() -> {
                    List<Student> studentList = null;
                    try {
                        studentList = iStudent.getAllStudents().get();
                    } catch (InterruptedException | ExecutionException | SQLException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<Student>>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(studentList));
                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> {
                        return completeOK(employeesData, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                }))),
                pathPrefix("student", () -> path("insert", () ->
                        post(() -> {
                            return entity(Jackson.unmarshaller(Student.class), student ->
                            {
                                Student futureSaved = null;
                                try {
                                    futureSaved = iStudent.insertNewRecord(student).get();
                                } catch (SQLException | ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                CompletionStage<Optional<Student>> studentData = CompletableFuture.completedFuture(Optional.ofNullable(futureSaved));
                                return onSuccess(studentData, maybeStudent -> maybeStudent.map(students -> completeOK(students, Jackson.marshaller()))
                                                                                          .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found")));
                            });
                        }))),
                pathPrefix("student", () -> path("update", () ->
                        put(() -> {
                            return entity(Jackson.unmarshaller(Student.class), item -> {
                                return parameter(StringUnmarshallers.INTEGER, "RegNo", RegNo -> parameter(StringUnmarshallers.STRING, "columnName", columnName -> parameter(StringUnmarshallers.STRING, "value", value -> {
                                    Student futureUpdated = null;
                                    try {
                                        futureUpdated = iStudent.updateValueThroughRegNo(columnName, value, RegNo).get();
                                    } catch (SQLException | InterruptedException | ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                    CompletionStage<Optional<Student>> studentData = CompletableFuture.completedFuture(Optional.ofNullable(futureUpdated));
                                    return onSuccess(studentData, maybeStudent -> maybeStudent.map(students -> completeOK(students, Jackson.marshaller()))
                                                                                              .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found")));
                                })));
                            });
                        }))),
                pathPrefix("student", () -> pathPrefix("delete", () -> path(integerSegment(), (Integer regNo) -> delete(() -> {
                    List<Student> futureMaybeItem = null;
                    try {
                        futureMaybeItem = iStudent.deleteStudentById(regNo).get();
                    } catch (InterruptedException | SQLException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<Student>>> studentData = CompletableFuture.completedFuture(Optional.ofNullable(futureMaybeItem));
                    return onSuccess(studentData, maybeStudent -> maybeStudent.map(students -> completeOK(students, Jackson.marshaller()))
                                                                              .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found")));
                })))),
                pathPrefix("student", () -> path("maleAndFemaleCount", () -> get(() -> {
                    MaleAndFemaleContainer studentList = null;
                    try {
                        studentList = maleAndFemaleCount().get();
                    } catch (InterruptedException | ExecutionException | SQLException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<MaleAndFemaleContainer>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(studentList));
                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> {
                        return completeOK(employeesData, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                }))),
                pathPrefix("student", () -> path("addPrefixToStudents", () -> get(() -> {
                    List<String> studentList = null;
                    try {
                        studentList = addPrefixToStudents().get();
                    } catch (InterruptedException | ExecutionException | SQLException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<String>>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(studentList));
                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> {
                        return completeOK(employeesData, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                }))),
                pathPrefix("student", () -> path("getAllGradeLevel", () -> get(() -> {
                    List<GradeLevelContainer> studentList = null;
                    try {
                        studentList = getAllGradeLevel().get();
                    } catch (InterruptedException | ExecutionException | SQLException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<GradeLevelContainer>>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(studentList));
                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> {
                        return completeOK(employeesData, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                }))),
                pathPrefix("student", () -> path("getNoOfStudentsBelongsToEachActivities", () -> get(() -> {
                    List<ActivityContainer> studentList = null;
                    try {
                        studentList = getNoOfStudentsBelongsToEachActivities().get();
                    } catch (InterruptedException | ExecutionException | SQLException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<ActivityContainer>>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(studentList));
                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> {
                        return completeOK(employeesData, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                }))),
                pathPrefix("student", () -> path("getPerformanceOfStudents", () -> get(() -> {
                    List<PerformanceContainer> studentList = null;
                    try {
                        studentList = getPerformanceOfStudents().get();
                    } catch (InterruptedException | ExecutionException | SQLException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<PerformanceContainer>>> optionalEmployeeList = CompletableFuture.completedFuture(Optional.ofNullable(studentList));
                    return onSuccess(optionalEmployeeList, list -> list.map(employeesData -> {
                        return completeOK(employeesData, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                })))

        );
    }
}
