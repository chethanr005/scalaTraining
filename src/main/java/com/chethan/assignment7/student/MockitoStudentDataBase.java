package com.chethan.assignment7.student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by Chethan on Mar 31, 2022.
 */

public class MockitoStudentDataBase {
    private static List<Student> studentsList = new ArrayList<Student>();

    static {
        studentsList.add(new Student("r001", "rose", 7.5, "female", 10, Arrays.asList("swimming", "cycling")));
        studentsList.add(new Student("r002", "kate", 6.9, "female", 10, Arrays.asList("hiking", "cycling", "walking")));
        studentsList.add(new Student("r003", "hailey", 5.0, "female", 9, Arrays.asList("walking", "cycling")));
        studentsList.add(new Student("r004", "cooper", 8.7, "male", 10, Arrays.asList("travelling", "cycling", "hiking", "walking")));
        studentsList.add(new Student("r005", "andrew", 5.4, "male", 8, Arrays.asList("cycling", "running")));
        studentsList.add(new Student("r006", "anthony", 8.1, "male", 10, Arrays.asList("swimming")));
        studentsList.add(new Student("r007", "kail", 6.1, "female", 10, Arrays.asList("hiking", "walking")));
        studentsList.add(new Student("r008", "tony", 3.6, "male", 8, Arrays.asList("travelling", "swimming", "walking")));
    }
    static CompletableFuture<List<Student>> getStudentsList(){
        return CompletableFuture.completedFuture(studentsList);
    }


    public static CompletableFuture<Student> getStudent(String sid) {
        return getStudentsList().thenApplyAsync(studentsList -> {
            Optional<Student> optionalStudent = studentsList.stream().filter(student -> student.getId().equals(sid)).findAny();
            if (optionalStudent.isPresent())
                return optionalStudent.get();
            else return null;
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
        studentsList.add(student);
        return CompletableFuture.completedFuture(student);
    }
}
