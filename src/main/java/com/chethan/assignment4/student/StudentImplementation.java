package com.chethan.assignment4.student;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Feb 24, 2022.
 */

public class StudentImplementation {
    private static IStudentDatabase studentDatabase;
    ExecutorService threadPool = Executors.newCachedThreadPool();

    StudentImplementation(IStudentDatabase studentDatabase) {
        StudentImplementation.studentDatabase = studentDatabase;
    }

    // 1. No of male and female student implementation
    public CompletableFuture<MaleAndFemaleContainer> getMaleAndFemaleContainer() {
        CompletableFuture<List<Student>> allStudentsData = studentDatabase.getStudentsData();

        return allStudentsData.thenApplyAsync(studentsData -> new MaleAndFemaleContainer(studentsData.stream().filter(student -> student.getGender().equals("male")).count(),
                studentsData.stream().filter(student -> student.getGender().equals("female")).count()), threadPool);
    }

    // 2. Adds Prefix to student names
    public CompletableFuture<List<String>> getPrefixStudentsName() {
        CompletableFuture<List<Student>> allStudentsData = studentDatabase.getStudentsData();

        return allStudentsData.thenApplyAsync(studentsData -> studentsData.stream().map(student -> {
            if (student.getGender().equals("male")) return "Mr." + student.getName();
            else return "Ms." + student.getName();
        }).collect(Collectors.toList()), threadPool);
    }

    // 3. No of Students according to Grade level - 1
    public CompletableFuture<GradeLevelContainer> getGradeLevelContainer(int gradeLevel) {
        CompletableFuture<List<Student>> allStudentsData = studentDatabase.getStudentsData();

        return allStudentsData.thenApply(studentsData -> new GradeLevelContainer(gradeLevel, studentsData.stream().filter(student -> student.getGradeLevel() == gradeLevel).distinct().count()));
    }

    // 3. No of Students according to Grade level - 2
    public CompletableFuture<List<GradeLevelContainer>> getGradeLevelContainer2() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Student>> allStudentsData = studentDatabase.getStudentsData();
        List<Integer>                    distinctGrade   = allStudentsData.get().stream().map(student -> student.getGradeLevel()).distinct().collect(Collectors.toList());

        return allStudentsData.thenApplyAsync(studentsData -> distinctGrade.stream().map(distinctGrad ->
                                                                                   new GradeLevelContainer(distinctGrad, studentsData.stream().filter(student -> distinctGrad == student.getGradeLevel()).count()))
                                                                           .collect(Collectors.toList()), threadPool);

    }

    // 4. No of students participating in each type of activity-1
    public CompletableFuture<ActivityContainer> getActivityContainer(String activity) {
        CompletableFuture<List<Student>> allStudentsData = studentDatabase.getStudentsData();
        return allStudentsData.thenApplyAsync(studentsData -> new ActivityContainer(activity, studentsData.stream().filter((student -> student.getActivities().contains(activity))).count()), threadPool);
    }

    // 4. No of students participating in each type of activity-2
    public CompletableFuture<List<ActivityContainer>> getActivityContainer2() {
        CompletableFuture<List<Student>> allStudentsData      = studentDatabase.getStudentsData();
        CompletableFuture<List<String>>  distinctActivityList = allStudentsData.thenApplyAsync(studentsData -> studentsData.stream().flatMap(student -> student.getActivities().stream()).distinct().collect(Collectors.toList()));
        BiFunction<List<String>, List<Student>, List<ActivityContainer>> listOfActivityContainer =
                (listOfActivity, StudentsData) -> listOfActivity.stream().map(m -> new ActivityContainer(m, StudentsData.stream().filter(student -> student.getActivities().contains(m)).count())).collect(Collectors.toList());

        return distinctActivityList.thenCombineAsync(allStudentsData, listOfActivityContainer, threadPool);
    }

    // 5. Group students based on GPA - 1
    public CompletableFuture<PerformanceContainer> getPerformanceContainer(String gpa) {
        CompletableFuture<List<Student>> allStudentsData = studentDatabase.getStudentsData();
        return allStudentsData.thenApplyAsync(studentsData -> {
            if (gpa == "poor") return new PerformanceContainer(gpa, studentsData.stream().filter(student -> 0 <= student.getGpa() && student.getGpa() <= 4.0).collect(Collectors.toList()));
            else if (gpa == "average") return new PerformanceContainer(gpa, studentsData.stream().filter(student -> 4.1 <= student.getGpa() && student.getGpa() <= 7.0).collect(Collectors.toList()));
            else return new PerformanceContainer(gpa, studentsData.stream().filter(student -> 7.1 <= student.getGpa()).collect(Collectors.toList()));
        }, threadPool);
    }

    // 5. Group students based on GPA - 2
    public CompletableFuture<List<PerformanceContainer>> getPerformanceContainer2() {
        CompletableFuture<List<Student>> allStudentsData     = studentDatabase.getStudentsData();
        List<String>                     distinctPerformance = Arrays.asList("Average", "Excellent", "Poor");

        return allStudentsData.thenApplyAsync(studentsData -> distinctPerformance.stream().map(distintPerf -> {
            if (distintPerf.equals("Average"))
                return new PerformanceContainer(distintPerf, studentsData.stream().filter(student -> 4.1 <= student.getGpa() && student.getGpa() <= 7.0).collect(Collectors.toList()));
            else if (distintPerf.equals("Excellent"))
                return new PerformanceContainer(distintPerf, studentsData.stream().filter(student -> 7.1 <= student.getGpa()).collect(Collectors.toList()));
            else
                return new PerformanceContainer(distintPerf, studentsData.stream().filter(student -> 4.0 >= student.getGpa() && student.getGpa() <= 7.0).collect(Collectors.toList()));
        }).collect(Collectors.toList()), threadPool);
    }
}



