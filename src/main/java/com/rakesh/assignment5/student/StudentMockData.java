package com.rakesh.assignment5.student;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Rakesh on Mar 14, 2022.
 */

public class StudentMockData {
    public  CompletableFuture<List<Student>> getAllStudents() {
       Student student1 = new Student(1001, "Ajay", 1, 6.7, "male", Arrays.asList("basketball", "cricket"));
       Student student2 = new Student(1002, "Jessi", 4, 7.8, "female", Arrays.asList("painting", "soccer"));

       Student student3 = new Student(1003, "Emily", 4, 8.0, "female", Arrays.asList("painting", "chess", "dancing"));
       Student student4 = new Student(1004, "Dave", 6, 3.9, "male", Arrays.asList("swimming", "soccer"));

       Student student5 = new Student(1005, "Sophia", 7, 4.5, "female", Arrays.asList("swimming", "dancing", "painting"));
       Student student6 = new Student(1006, "James", 7, 3.9, "male", Arrays.asList("basketball", "cricket"));

       Student student7 = new Student(1007, "Sunil", 8, 8.5, "male", Arrays.asList("swimming", "dancing", "soccer"));
       Student student8 = new Student(1008, "John", 8, 6.9, "male", Arrays.asList("painting", "basketball", "soccer"));

       Student student9  = new Student(1009, "Rohit", 9, 5.5, "male", Arrays.asList("cricket", "soccer"));
       Student student10 = new Student(1010, "Edwin", 10, 8.9, "male", Arrays.asList("painting", "chess", "soccer"));

        List<Student> students = Arrays.asList(student1, student2, student3, student4, student5, student6, student7, student8, student9, student10);
        return CompletableFuture.supplyAsync(() -> students);
    }
}
