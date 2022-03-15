package com.rakesh.assignment5.student;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by Rakesh on Mar 10, 2022.
 */

public interface IStudentDatabase {
    public CompletableFuture<List<Student>> getAllStudents(ExecutorService service);

    public CompletableFuture<Student> getStudentByID(int regNo, ExecutorService service);

    public Boolean updateStudent(String columnName, String newValue, int regNo);

    public Boolean deleteAllStudent();

    public Boolean deleteStudentByID(int regNo);

    public Boolean addNewStudent(Student student);
}
