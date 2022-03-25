package com.chethan.assignment6.student;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Chethan on Mar 11, 2022.
 */

public interface IStudentDatabase {

    CompletableFuture<List<Student>> getStudentsData();

    CompletableFuture<Student> getStudent(String sid);

    CompletableFuture<Student> addStudent(Student student);

    CompletableFuture<Student> updateStudentDetails(Student student);

    void deleteStudentsById(String sid);
}
