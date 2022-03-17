package com.kishor.assignment5.student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public class StudentMockDatabase {
    ExecutorService service = Executors.newCachedThreadPool();

    public CompletableFuture<MaleAndFemaleContainer> getMockMaleAndFemale() {

        return CompletableFuture.supplyAsync(() -> new MaleAndFemaleContainer(4, 2), service);
    }

    public CompletableFuture<List<String>> getMockStudentNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("Mr Alex");
        names.add("Mr Mahesh");
        names.add("Mr James");
        names.add("Mr Kenny");
        names.add("Ms Lisba");
        names.add("Ms Emma");
        return CompletableFuture.supplyAsync(() -> {
            return names;
        }, service);
    }

    public CompletableFuture<List<GradeLevelContainer>> getMockAllGradeLevel() {
        ArrayList<GradeLevelContainer> gradesList = new ArrayList<>();
        gradesList.add(new GradeLevelContainer(2, 1l));
        gradesList.add(new GradeLevelContainer(3, 2l));
        gradesList.add(new GradeLevelContainer(4, 2l));
        gradesList.add(new GradeLevelContainer(5, 1l));
        return CompletableFuture.supplyAsync(() -> {
            return gradesList;
        }, service);
    }

    public CompletableFuture<List<ActivityContainer>> getMockAllActivitiesCount() {
        ArrayList<ActivityContainer> allActivities = new ArrayList<>();
        allActivities.add(new ActivityContainer("Painting", 2l));
        allActivities.add(new ActivityContainer("Swimming", 4l));
        allActivities.add(new ActivityContainer("Basketball", 2l));
        allActivities.add(new ActivityContainer("Volleyball", 1l));
        allActivities.add(new ActivityContainer("Baseball", 1l));
        allActivities.add(new ActivityContainer("Football", 2l));
        allActivities.add(new ActivityContainer("Running", 1l));
        allActivities.add(new ActivityContainer("Soccer", 1l));
        allActivities.add(new ActivityContainer("Dancing", 1l));
        allActivities.add(new ActivityContainer("Gymnastics", 1l));
        allActivities.add(new ActivityContainer("Aerobics", 1l));
        return CompletableFuture.supplyAsync(() -> {
            return allActivities;
        }, service);
    }

    public CompletableFuture<List<PerformanceContainer>> getMockAllPerformance() {
        ArrayList<PerformanceContainer> allPerformance = new ArrayList<>();
        allPerformance.add(new PerformanceContainer("poor", Arrays.asList(new Student(1004, "James", 4, 3.9, "male", Arrays.asList("Swimming", "Basketball", "Baseball", "Football")))));
        allPerformance.add(new PerformanceContainer("average", Arrays.asList(
                new Student(1001, "Alex", 2, 4.6, "male", Arrays.asList("Painting")),
                new Student(1003, "Mahesh", 3, 5.9, "male", Arrays.asList("Swimming", "Basketball", "Volleyball")),
                new Student(1011, "Kenny", 5, 5.6, "male", Arrays.asList("Painting", "Running", "Soccer")),
                new Student(1048, "Emma", 3, 6.0, "female", Arrays.asList("Swimming", "Gymnastics", "Aerobics"))
        )));
        allPerformance.add(new PerformanceContainer("excellent", Arrays.asList(new Student(1012, "Lisba", 4, 7.5, "female", Arrays.asList("Swimming", "Dancing", "Football")))));
        return CompletableFuture.supplyAsync(() -> {
            return allPerformance;
        }, service);
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        StudentMockDatabase d = new StudentMockDatabase();
        d.getMockAllPerformance().get().stream().forEach(System.out::println);
    }
}
