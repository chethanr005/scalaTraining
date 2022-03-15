package com.chethan.assignment5.student;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Chethan on Mar 14, 2022.
 */

public class MockStudentDatabase {

    public CompletableFuture<MaleAndFemaleContainer> getExpectedMaleAndFemaleContainer() {
        return CompletableFuture.supplyAsync(() -> new MaleAndFemaleContainer(4, 6));
    }

    public CompletableFuture<List<String>> getExpectedPrefixNames() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList(
                "Ms.julie", "Mr.tony", "Ms.kail", "Mr.anthony", "Mr.andrew", "Ms.swift", "Mr.cooper", "Ms.hailey", "Ms.kate", "Ms.rose"));
    }

    public CompletableFuture<List<String>> getExpectedMockitoPrefixNames() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList(
                "Ms.rose","Ms.julie", "Mr.tony", "Ms.kail", "Ms.kate", "Mr.anthony", "Mr.andrew", "Ms.swift", "Mr.cooper", "Ms.hailey"));
    }

    public CompletableFuture<GradeLevelContainer> getExpectedGradeLevelContainer() {
        return CompletableFuture.supplyAsync(() -> new GradeLevelContainer(10, 5));
    }

    public CompletableFuture<List<GradeLevelContainer>> getExpectedGradeLevelContainerList() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList(new GradeLevelContainer(9, 3), new GradeLevelContainer(8, 2), new GradeLevelContainer(10, 5)));
    }

    public CompletableFuture<List<GradeLevelContainer>> getExpectedMockitoGradeLevelContainerList() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList(new GradeLevelContainer(10, 5),new GradeLevelContainer(9, 3), new GradeLevelContainer(8, 2)));
    }

    public CompletableFuture<ActivityContainer> getExpectedActivityContainer() {
        return CompletableFuture.supplyAsync(() -> new ActivityContainer("cycling", 5));

    }

    public CompletableFuture<List<ActivityContainer>> getExpectedActivityContainer2() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList(new ActivityContainer("hiking", 5), new ActivityContainer("running", 2), new ActivityContainer("travelling", 3), new ActivityContainer("swimming", 3),
                new ActivityContainer("walking", 6), new ActivityContainer("cycling", 5)));

    }

    public CompletableFuture<List<ActivityContainer>> getExpectedMockitoActivityContainer2() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList( new ActivityContainer("swimming", 3)
                , new ActivityContainer("cycling", 5),new ActivityContainer("hiking", 5), new ActivityContainer("running", 2), new ActivityContainer("travelling", 3),new ActivityContainer("walking", 6)));

    }

    public CompletableFuture<PerformanceContainer> getExpectedPerformanceContainer() {
        return CompletableFuture.supplyAsync(() -> new PerformanceContainer("excellent", Arrays.asList(
                new Student("r006", "anthony", 8.1, "male", 10, Arrays.asList("swimming"))
                , new Student("r008", "swift", 9.1, "female", 9, Arrays.asList("hiking", "travelling", "walking"))
                , new Student("r009", "cooper", 8.7, "male", 10, Arrays.asList("travelling", "cycling", "hiking", "walking")),
                new  Student("r001", "rose", 7.5, "female", 10, Arrays.asList("swimming", "cycling")))));
    }

    public CompletableFuture<PerformanceContainer> getExpectedMockitoPerformanceContainer() {
        return CompletableFuture.supplyAsync(() -> new PerformanceContainer("excellent", Arrays.asList(
                  new Student("r001", "rose", 7.5, "female", 10, Arrays.asList("swimming", "cycling")),
                  new Student("r006", "anthony", 8.1, "male", 10, Arrays.asList("swimming"))
                , new Student("r008", "swift", 9.1, "female", 9, Arrays.asList("hiking", "travelling", "walking"))
                , new Student("r009", "cooper", 8.7, "male", 10, Arrays.asList("travelling", "cycling", "hiking", "walking"))
                )));
    }


    public CompletableFuture<List<PerformanceContainer>> getExpectedPerformanceContainer2() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList((new PerformanceContainer("Average", Arrays.asList(
                          new Student("r002", "julie", 7.0, "female", 9, Arrays.asList("hiking", "running"))
                        , new Student("r004", "kail", 6.1, "female", 10, Arrays.asList("hiking", "walking"))
                        , new Student("r007", "andrew", 5.4, "male", 8, Arrays.asList("cycling", "running"))
                        , new Student("r010", "hailey", 5.0, "female", 9, Arrays.asList("walking", "cycling"))
                        , new Student("r005", "kate", 6.9, "female", 10, Arrays.asList("hiking", "cycling", "walking")))))
                ,
                (         new PerformanceContainer("Excellent", Arrays.asList(
                          new Student("r006", "anthony", 8.1, "male", 10, Arrays.asList("swimming"))
                        , new Student("r008", "swift", 9.1, "female", 9, Arrays.asList("hiking", "travelling", "walking"))
                        , new Student("r009", "cooper", 8.7, "male", 10, Arrays.asList("travelling", "cycling", "hiking", "walking")),
                          new Student("r001", "rose", 7.5, "female", 10, Arrays.asList("swimming", "cycling"))))),
                (         new PerformanceContainer("Poor", Arrays.asList(new Student("r003", "tony", 3.6, "male", 8, Arrays.asList("travelling", "swimming", "walking")))))));
    }

    public CompletableFuture<List<PerformanceContainer>> getExpectedMockitoPerformanceContainer2() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList((new PerformanceContainer("Average", Arrays.asList(
                          new Student("r002", "julie", 7.0, "female", 9, Arrays.asList("hiking", "running"))
                        , new Student("r004", "kail", 6.1, "female", 10, Arrays.asList("hiking", "walking"))
                        , new Student("r005", "kate", 6.9, "female", 10, Arrays.asList("hiking", "cycling", "walking"))
                        , new Student("r007", "andrew", 5.4, "male", 8, Arrays.asList("cycling", "running"))
                        , new Student("r010", "hailey", 5.0, "female", 9, Arrays.asList("walking", "cycling")))))
                ,
                (new PerformanceContainer("Excellent", Arrays.asList(
                          new Student("r001", "rose", 7.5, "female", 10, Arrays.asList("swimming", "cycling")),
                          new Student("r006", "anthony", 8.1, "male", 10, Arrays.asList("swimming"))
                        , new Student("r008", "swift", 9.1, "female", 9, Arrays.asList("hiking", "travelling", "walking"))
                        , new Student("r009", "cooper", 8.7, "male", 10, Arrays.asList("travelling", "cycling", "hiking", "walking"))
                        ))),
                (new PerformanceContainer("Poor", Arrays.asList(new Student("r003", "tony", 3.6, "male", 8, Arrays.asList("travelling", "swimming", "walking")))))));
    }
}
