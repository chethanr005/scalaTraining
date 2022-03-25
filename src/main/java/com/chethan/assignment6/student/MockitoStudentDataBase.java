package com.chethan.assignment6.student;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by Chethan on Mar 04, 2022.
 */

public class MockitoStudentDataBase {

    public static CompletableFuture<List<Student>> getStudentsList() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList(
                new Student("r001", "rose", 7.5, "female", 10, Arrays.asList("swimming", "cycling")),
                new Student("r002", "julie", 7.0, "female", 9, Arrays.asList("hiking", "running")),
                new Student("r003", "tony", 3.6, "male", 8, Arrays.asList("travelling", "swimming", "walking")),
                new Student("r004", "kail", 6.1, "female", 10, Arrays.asList("hiking", "walking")),
                new Student("r005", "kate", 6.9, "female", 10, Arrays.asList("hiking", "cycling", "walking")),
                new Student("r006", "anthony", 8.1, "male", 10, Arrays.asList("swimming")),
                new Student("r007", "andrew", 5.4, "male", 8, Arrays.asList("cycling", "running")),
                new Student("r008", "swift", 9.1, "female", 9, Arrays.asList("hiking", "travelling", "walking")),
                new Student("r009", "cooper", 8.7, "male", 10, Arrays.asList("travelling", "cycling", "hiking", "walking")),
                new Student("r010", "hailey", 5.0, "female", 9, Arrays.asList("walking", "cycling"))));
    }


    public static CompletableFuture<Student> getStudent(String sid) {
        return getStudentsList().thenApplyAsync(studentsList -> {
            return studentsList.stream().filter(student -> student.getId().equals(sid)).findAny().get();
        });
    }

    public static CompletableFuture<Student> updateStudent(Student student) {
        return getStudent(student.getId()).thenApply(actualStudents -> {
            actualStudents.setName(student.getName());
            actualStudents.setGpa(student.getGpa());
            actualStudents.setGender(student.getGender());
            actualStudents.setGradeLevel(student.getGradeLevel());
            actualStudents.setActivities(student.getActivities());
            return actualStudents;
        });
    }

    public static CompletableFuture<Student> addStudent(Student student) throws ExecutionException, InterruptedException {

        List<Student> studentsList = new ArrayList<Student>();
        studentsList.addAll(getStudentsList().get());
        studentsList.add(student);
        return CompletableFuture.completedFuture(student);
    }
}
