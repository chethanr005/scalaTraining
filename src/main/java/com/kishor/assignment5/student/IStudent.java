package com.kishor.assignment5.student;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public interface IStudent {
    public CompletableFuture<List<Student>> getAllStudents() throws SQLException;

    public CompletableFuture<Student> getStudentById(int regNo) throws SQLException;

    public CompletableFuture<Student> updateValueThroughRegNo(String columnName, String value, int regNo) throws SQLException;

    public CompletableFuture<Student> insertNewRecord(Student student) throws SQLException;

    public CompletableFuture<List<Student>> deleteStudentById(int regNo) throws SQLException;

    public boolean check(int regNo) throws SQLException;
}
