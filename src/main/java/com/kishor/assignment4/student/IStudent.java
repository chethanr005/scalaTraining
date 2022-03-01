package com.kishor.assignment4.student;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Kishor on Feb 25, 2022.
 */

public interface IStudent {
    public CompletableFuture<List<Student>> getAllStudents() throws SQLException;

    public CompletableFuture<Student> getStudentById(int regNo) throws SQLException;

    public CompletableFuture<Student> updateValueThroughRegNo(String columnName, String value, int regNo) throws SQLException;

    public CompletableFuture<Student> insertNewRecord(Student student) throws SQLException;

    public CompletableFuture<List<Student>> deleteStudentById(int regNo) throws SQLException;
}
