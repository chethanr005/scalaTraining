package com.rakesh.assignment4.student;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by Rakesh on Feb 24, 2022.
 */

public interface IStudentDatabase {

    public CompletableFuture<List<Student>> getAllStudents(ExecutorService service);

    public CompletableFuture<Student> getStudentByID(int regNo, ExecutorService service);

    public boolean updateStudent(String columnName, String newValue, int regNo);

    public boolean deleteAllStudent();

    public boolean deleteStudentByID(int regNo);

    public boolean addNewStudent(Student student);

}
