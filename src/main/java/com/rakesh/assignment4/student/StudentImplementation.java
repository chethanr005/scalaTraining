package com.rakesh.assignment4.student;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Rakesh on Feb 25, 2022.
 */

public class StudentImplementation {
    IStudentDatabase iStudent;
    ExecutorService  threadPool = Executors.newFixedThreadPool(10);

    StudentImplementation(IStudentDatabase iStudent) {
        this.iStudent = iStudent;
    }

    /**
     * 1. get no of male and female students , Return result in class MaleAndFemaleContainer(int males, int females)
     */
    public CompletableFuture<MaleAndFemaleContainer> getMaleAndFemaleCount() {
        Function<List<Student>, MaleAndFemaleContainer> getCounts = (studentList) -> {
            return new MaleAndFemaleContainer(studentList.stream().filter(student -> student.getGender().equals("male")).count(),
                    studentList.stream().filter(student -> student.getGender().equals("female")).count());
        };

        return iStudent.getAllStudents(threadPool)
                       .thenApplyAsync(getCounts, threadPool);
    }

    /**
     * 2. Add Prefix to each student's name ,  Mr. or Ms. and return
     */
    public CompletableFuture<PrefixContainer> addPrefixToName() {
        Function<List<Student>, PrefixContainer> addPrefix = studentList -> {
            return new PrefixContainer(studentList.stream().map((s) -> updateName(s.getGender(), s.getName()))
                                                  .sorted().collect(Collectors.toList()));
        };
        return iStudent.getAllStudents(threadPool)
                       .thenApplyAsync(addPrefix, threadPool);
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
            return new GradeLevelContainer(gradeLevel, studentList.stream().filter(student -> student.getGradeLevel() == gradeLevel).count());
        };
        return iStudent.getAllStudents(threadPool)
                       .thenApplyAsync(getCount, threadPool);
    }

    // If we don't provide gradeLevel.
    public CompletableFuture<List<GradeLevelContainer>> getCountOfAllGradeLevel() {
        Function<List<Student>, List<GradeLevelContainer>> getCounts = (studentList) -> {
            List<GradeLevelContainer> result = new ArrayList<>();
            studentList.stream().map(student -> student.getGradeLevel()).distinct().forEach(grade ->
                    result.add(new GradeLevelContainer(grade, studentList.stream().filter(student -> student.getGradeLevel() == grade).count()))
            );
            return result;
        };
        return iStudent.getAllStudents(threadPool)
                       .thenApplyAsync(getCounts, threadPool);
    }


    /**
     * 4. get no of students participating in each type of activity , Return result in class ActivityContainer(String activity, int students)
     */
    //If we provide the activity.
    public CompletableFuture<ActivityContainer> getCountBasedOnActivity(String activity) {
        Function<List<Student>, ActivityContainer> getCount = (studentList) -> {
            return new ActivityContainer(activity, studentList.stream().filter(s -> s.getActivities().contains(activity)).count());
        };
        return iStudent.getAllStudents(threadPool).thenApplyAsync(getCount, threadPool);
    }

    //If we don't provide activity
    public CompletableFuture<List<ActivityContainer>> getActivityCountList() {
        Function<List<Student>, List<ActivityContainer>> getCounts = (studentList) -> {
            List<ActivityContainer> result = new ArrayList<>();
            //System.out.println("Thread"+Thread.currentThread().getName());
            studentList.stream().flatMap(s -> s.getActivities().stream()).distinct().collect(Collectors.toList())
                       .stream().forEach(activity ->
                               result.add(new ActivityContainer(activity, studentList.stream().filter(s -> s.getActivities().contains(activity)).count())));
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
            if (level.equals("Average")) {
                filteredList = studentList.stream().filter(s -> s.getGpa() >= 4.1 && s.getGpa() <= 7.1).collect(Collectors.toList());
                return new PerformanceContainer(level, filteredList);
            } else if (level.equals("Excellent")) {
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
        IStudentDatabase      i     = new StudentDatabase();
        StudentImplementation si    = new StudentImplementation(i);
        LocalTime             start = LocalTime.now();
//        for(int j=0;j<10;j++)
//        System.out.println(si.getActivityCountList());

        si.threadPool.shutdown();
        System.out.println("TIme taken to complete " + Duration.between(start, LocalTime.now()).toMillis());
    }
}