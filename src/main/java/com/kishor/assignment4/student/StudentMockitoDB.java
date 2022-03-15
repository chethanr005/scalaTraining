package com.kishor.assignment4.student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Kishor on Mar 07, 2022.
 */

public class StudentMockitoDB {
    public static ArrayList<Student> Students = new ArrayList<Student>();

    public CompletableFuture<List<Student>> getMockAllStudents() {
        Students.add(new Student(1001, "Alex", 2, 4.6, "male", Arrays.asList(("Painting"))));
        Students.add(new Student(1003, "Mahesh", 3, 5.9, "male", Arrays.asList("Swimming", "Basketball", "Volleyball")));
        Students.add(new Student(1004, "James", 4, 3.9, "male", Arrays.asList("Swimming", "Basketball", "Baseball", "Football")));
        Students.add(new Student(1011, "Kenny", 5, 5.6, "male", Arrays.asList("Painting", "Running", "Soccer")));
        Students.add(new Student(1012, "Lisba", 4, 7.5, "female", Arrays.asList("Swimming", "Dancing", "Football")));
        Students.add(new Student(1048, "Emma", 3, 6.0, "female", Arrays.asList("Swimming", "Gymnastics", "Aerobics")));
        return CompletableFuture.supplyAsync(() -> Students);
    }
}
