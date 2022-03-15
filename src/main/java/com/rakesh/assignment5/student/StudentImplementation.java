package com.rakesh.assignment5.student;


import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.unmarshalling.StringUnmarshallers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static akka.http.javadsl.server.PathMatchers.integerSegment;
import static akka.http.javadsl.server.PathMatchers.segment;

/**
 * Created by Rakesh on Mar 10, 2022.
 */

public class StudentImplementation extends AllDirectives {
    static IStudentDatabase iStudent;
    static ExecutorService  threadPool = Executors.newFixedThreadPool(10);

    StudentImplementation(IStudentDatabase iStudent) {
        this.iStudent = iStudent;
    }

    /**
     * 1. get no of male and female students , Return result in class MaleAndFemaleContainer(int males, int females)
     */
    public CompletableFuture<MaleAndFemaleContainer> getMaleAndFemaleCount() throws ExecutionException, InterruptedException {
        Function<List<Student>, MaleAndFemaleContainer> getCounts = (studentList) -> {
            //System.out.println(" Student Implementation ;"+ studentList);
            return new MaleAndFemaleContainer(studentList.stream().filter(student -> student.getGender().equals("male")).count(), studentList.stream().filter(student -> student.getGender().equals("female")).count());
        };

        // System.out.println("Student Imp Get count "+iStudent.getAllStudents(threadPool).get());
        return iStudent.getAllStudents(threadPool).thenApplyAsync(getCounts, threadPool);
    }

    /**
     * 2. Add Prefix to each student's name ,  Mr. or Ms. and return
     */
    public CompletableFuture<PrefixContainer> addPrefixToName() {
        Function<List<Student>, PrefixContainer> addPrefix = studentList -> {
            return new PrefixContainer(studentList.stream().map((s) -> updateName(s.getGender(), s.getName())).sorted().collect(Collectors.toList()));
        };
        return iStudent.getAllStudents(threadPool).thenApplyAsync(addPrefix, threadPool);
    }

    // Helper method to add prefix.
    private static String updateName(String gender, String name) {
        if (gender.equals("male")) {
            name = "Mr." + name;
        }
        if (gender.equals("female")) {
            name = "Ms." + name;
        }
        return name;
    }

    /**
     * 3. get no of students according to Grade level , Return result in class GradeLevelContainer(int gradeLevel, int students)
     */
    // If we provide gradeLevel.
    public CompletableFuture<GradeLevelContainer> getCountBasedOnGradeLevel(int gradeLevel) {
        Function<List<Student>, GradeLevelContainer> getCount = (studentList) -> {
            List<Integer> gradeList = studentList.stream().map(Student::getGradeLevel).distinct().collect(Collectors.toList());
            if (gradeList.contains(gradeLevel)) return new GradeLevelContainer(gradeLevel, studentList.stream().filter(student -> student.getGradeLevel() == gradeLevel).count());
            else return null;
        };
        return iStudent.getAllStudents(threadPool).thenApplyAsync(getCount, threadPool);
    }

    // If we don't provide gradeLevel.
    public CompletableFuture<List<GradeLevelContainer>> getCountOfAllGradeLevel() {
        Function<List<Student>, List<GradeLevelContainer>> getCounts = (studentList) -> {
            List<GradeLevelContainer> result = new ArrayList<>();
            studentList.stream().map(student -> student.getGradeLevel()).distinct().forEach(grade -> result.add(new GradeLevelContainer(grade, studentList.stream().filter(student -> student.getGradeLevel() == grade).count())));
            return result;
        };
        return iStudent.getAllStudents(threadPool).thenApplyAsync(getCounts, threadPool);
    }


    /**
     * 4. get no of students participating in each type of activity , Return result in class ActivityContainer(String activity, int students)
     */
    //If we provide the activity.
    public CompletableFuture<ActivityContainer> getCountBasedOnActivity(String activity) {
        Function<List<Student>, ActivityContainer> getCount = (studentList) -> {
            List<String> activityList = studentList.stream().flatMap(student -> student.getActivities().stream()).distinct().collect(Collectors.toList());
            if (activityList.contains(activity)) return new ActivityContainer(activity, studentList.stream().filter(s -> s.getActivities().contains(activity)).count());
            else return null;
        };
        return iStudent.getAllStudents(threadPool).thenApplyAsync(getCount, threadPool);
    }

    //If we don't provide activity
    public CompletableFuture<List<ActivityContainer>> getActivityCountList() {
        Function<List<Student>, List<ActivityContainer>> getCounts = (studentList) -> {
            List<ActivityContainer> result = new ArrayList<>();
            //System.out.println("Thread"+Thread.currentThread().getName());
            studentList.stream().flatMap(s -> s.getActivities().stream()).distinct().collect(Collectors.toList()).stream().forEach(activity -> result.add(new ActivityContainer(activity, studentList.stream().filter(s -> s.getActivities().contains(activity)).count())));
            return result;
        };
        return iStudent.getAllStudents(threadPool).thenApplyAsync(getCounts, threadPool);
    }

    /**
     * 5. group students based on GPA with below criteria, Return result in class PerformanceContainer(String level, List<Student> students)
     */
    //If Level is passed
    public CompletableFuture<PerformanceContainer> getStudentsByLevel(String level) {
        Function<List<Student>, PerformanceContainer> getStudents = (studentList) -> {
            List<Student> filteredList;
            long          count = 0L;
            if (level.equalsIgnoreCase("Average")) {
                filteredList = studentList.stream().filter(s -> s.getGpa() >= 4.1 && s.getGpa() <= 7.1).collect(Collectors.toList());
                return new PerformanceContainer(level, filteredList);
            } else if (level.equalsIgnoreCase("Excellent")) {
                filteredList = studentList.stream().filter(s -> s.getGpa() > 7.1).collect(Collectors.toList());
                return new PerformanceContainer(level, filteredList);
            } else {
                filteredList = studentList.stream().filter(s -> s.getGpa() < 4.1).collect(Collectors.toList());
                return new PerformanceContainer(level, filteredList);
            }
        };

        return iStudent.getAllStudents(threadPool).thenApplyAsync(getStudents, threadPool);
    }

    //If level is not passed
    public CompletableFuture<List<PerformanceContainer>> getAllStudentsByLevel() {
        Function<List<Student>, List<PerformanceContainer>> getStudents = (studentList) -> {
            List<PerformanceContainer> result = new ArrayList<>();
            result.add(new PerformanceContainer("Average", studentList.stream().filter(s -> s.getGpa() >= 4.1 && s.getGpa() <= 7.1).collect(Collectors.toList())));
            result.add(new PerformanceContainer("Excellent", studentList.stream().filter(s -> s.getGpa() > 7.1).collect(Collectors.toList())));
            result.add(new PerformanceContainer("Poor", studentList.stream().filter(s -> s.getGpa() < 4.1).collect(Collectors.toList())));
            return result;
        };

        return iStudent.getAllStudents(threadPool).thenApplyAsync(getStudents, threadPool);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //IStudentDatabase database = new StudentDatabase();

        ActorSystem system = ActorSystem.create(Behaviors.empty(), "routes");

        Http http = Http.get(system);

        StudentImplementation app = new StudentImplementation(iStudent);

        final CompletionStage<ServerBinding> binding = http.newServerAt("localhost", 8080).bind(app.getRoutes());
        System.out.println("Server created at http://localhost:8080");

    }

    public Route getRoutes() {
        return concat(


                // to get AllStudents
                pathPrefix("student", () -> path("getAllStudents", () -> get(() -> {
                    List<Student> studentList = null;
                    try {
                        studentList = iStudent.getAllStudents(threadPool).get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<Student>>> optionalStudentList = CompletableFuture.completedFuture(Optional.ofNullable(studentList));
                    return onSuccess(optionalStudentList, list -> list.map(studentsData -> {
                        return completeOK(studentsData, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found in database")));
                }))),


                //to get Student by ID
                pathPrefix("student", () -> pathPrefix("getStudentByID", () -> get(() -> path(integerSegment(), (Integer id) -> {
                    Student fetchStudent = null;
                    try {
                        fetchStudent = iStudent.getStudentByID(id, threadPool).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<Student>> studentData = CompletableFuture.completedFuture(Optional.ofNullable(fetchStudent));
                    return onSuccess(studentData, data -> data.map(student -> completeOK(student, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found")));
                })))),


                // to get Male and Female count
                pathPrefix("student", () -> path("maleFemaleCount", () -> get(() -> {
                    MaleAndFemaleContainer count = null;
                    try {
                        count = getMaleAndFemaleCount().get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                    CompletionStage<Optional<MaleAndFemaleContainer>> result = CompletableFuture.completedFuture(Optional.ofNullable(count));
                    return onSuccess(result, data -> data.map(student -> completeOK(student, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found")));
                }))),


                //get prefix name
                pathPrefix("student", () -> path("prefixName", () -> get(() -> {
                    PrefixContainer nameList = null;
                    try {
                        nameList = addPrefixToName().get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<PrefixContainer>> result = CompletableFuture.completedFuture(Optional.ofNullable(nameList));
                    return onSuccess(result, data -> data.map(student -> completeOK(student.nameList, Jackson.marshaller())).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found")));
                }))),


                //to get all students count in respective grade.
                pathPrefix("student", () -> path("countInAllGrade", () -> get(() -> {
                    List<GradeLevelContainer> items = null;
                    try {
                        items = getCountOfAllGradeLevel().get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<List<GradeLevelContainer>>> gradeList = CompletableFuture.completedFuture(Optional.ofNullable(items));
                    return onSuccess(gradeList, list -> list.map(data -> {
                        return completeOK(data, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found")));
                }))),


                //student count by grade level
                pathPrefix("student", () -> pathPrefix("countByGrade", () -> get(() -> path(integerSegment(), (Integer id) -> {
                    GradeLevelContainer count = null;
                    try {
                        count = getCountBasedOnGradeLevel(id).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<GradeLevelContainer>> data = CompletableFuture.completedFuture(Optional.ofNullable(count));
                    return onSuccess(data, stdCount -> stdCount.map(item -> {
                        //System.out.println(item);
                        return completeOK(item, Jackson.marshaller());
                    }).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found")));
                })))),


                //to get all students count in respective of Activities.
                pathPrefix("student", () -> path("countInAllActivities", () -> get(() -> {
                    List<ActivityContainer> items = null;
                    try {
                        items = getActivityCountList().get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    CompletionStage<Optional<List<ActivityContainer>>> gradeList = CompletableFuture.completedFuture(Optional.ofNullable(items));

                    return onSuccess(gradeList, list -> list.map(data -> {
                        return completeOK(data, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found")));
                }))),


                //student count by Activity
                pathPrefix("student", () -> pathPrefix("countByActivity", () -> get(() -> path(segment(), (String id) -> {
                    ActivityContainer count = null;
                    try {
                        count = getCountBasedOnActivity(id).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<ActivityContainer>> data = CompletableFuture.completedFuture(Optional.ofNullable(count));
                    return onSuccess(data, stdCount -> stdCount.map(item -> {
                        //System.out.println(item);
                        return completeOK(item, Jackson.marshaller());
                    }).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found")));
                })))),


                //to get all students count in respective of Activities.
                pathPrefix("student", () -> path("performance", () -> get(() -> {
                    List<PerformanceContainer> items = null;
                    try {
                        items = getAllStudentsByLevel().get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    CompletionStage<Optional<List<PerformanceContainer>>> gradeList = CompletableFuture.completedFuture(Optional.ofNullable(items));

                    return onSuccess(gradeList, list -> list.map(data -> {
                        return completeOK(data, Jackson.marshaller());
                    }).orElse(complete(StatusCodes.NOT_FOUND, "No data found")));
                }))),


                //student count by Activity
                pathPrefix("student", () -> pathPrefix("performanceByLevel", () -> get(() -> path(segment(), (String id) -> {
                    PerformanceContainer count = null;
                    try {
                        count = getStudentsByLevel(id).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CompletionStage<Optional<PerformanceContainer>> data = CompletableFuture.completedFuture(Optional.ofNullable(count));
                    return onSuccess(data, stdCount -> stdCount.map(item -> {
                        //System.out.println(item);
                        return completeOK(item, Jackson.marshaller());
                    }).orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found")));
                })))),


                //to add new Student
                pathPrefix("student", () -> pathPrefix("addStudent", () -> post(() -> entity(Jackson.unmarshaller(Student.class), student -> {
                    CompletionStage<Boolean> futureSaved = addStudent(student);
                    return onSuccess(futureSaved, done -> complete((done) ? StatusCodes.OK : StatusCodes.BAD_REQUEST));
                })))),


                //to delete a Student
                pathPrefix("student", () -> pathPrefix("deleteByID", () -> delete(() -> path(integerSegment(), (Integer id) -> {
                    CompletionStage<Boolean> isDeleted = CompletableFuture.completedFuture(iStudent.deleteStudentByID(id));
                    return onSuccess(isDeleted, done -> complete((done) ? StatusCodes.OK : StatusCodes.BAD_REQUEST));
                })))),


                //to update a Student data
                pathPrefix("student", () ->
                        pathPrefix("update", () -> concat(put(() ->
                                parameter("columnName", columnName ->
                                        parameter("newValue", newValue ->
                                                parameter(StringUnmarshallers.INTEGER, "regNo", regNo -> {
                                                    CompletionStage<Boolean> isUpdated = CompletableFuture.completedFuture(iStudent.updateStudent(columnName, newValue, regNo));
                                                    return onSuccess(isUpdated, done -> complete((done) ? StatusCodes.OK : StatusCodes.BAD_REQUEST));
                                                }))))))));
    }

    public CompletionStage<Boolean> addStudent(Student student) {
        return CompletableFuture.completedFuture(iStudent.addNewStudent(student));
    }

}
