package com.chethan.assignment4.student;



import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Chethan on Mar 04, 2022.
 */

public class MockitoStudentDataBase {

    public static CompletableFuture<List<Student>> studentsList =
            CompletableFuture.supplyAsync(()->Arrays.asList(
            new Student("r001","rose", 7.5, "female", 10, Arrays.asList("swimming", "cycling")),
            new Student("r002","julie", 7.0, "female", 9, Arrays.asList("hiking", "running")),
            new Student("r003","tony", 3.6, "male", 8, Arrays.asList("travelling", "swimming", "walking")),
            new Student("r004","kail", 6.1, "female", 10, Arrays.asList("hiking", "walking")),
            new Student("r005","kate", 6.9, "female", 10, Arrays.asList("hiking", "cycling", "walking")),
            new Student("r006","anthony", 8.1, "male", 10, Arrays.asList("swimming")),
            new Student("r007","andrew", 5.4, "male", 8, Arrays.asList("cycling", "running")),
            new Student("r008","swift", 9.1, "female", 9, Arrays.asList("hiking", "travelling", "walking")),
            new Student("r009","cooper", 8.7, "male", 10, Arrays.asList("travelling", "cycling", "hiking", "walking")),
            new Student("r010","hailey", 5.0, "female", 9, Arrays.asList("walking", "cycling"))));
}
