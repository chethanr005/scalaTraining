package com.chethan.assignment7.student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Mar 29, 2022.
 */

public class StudentDatabase implements IStudentDatabase {
    private static Connection      con        = null;
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    private static void getDatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/training?user=postgres&password=admin");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeDatabaseConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public CompletableFuture<List<Student>> getStudentsData() {

        Supplier<List<Student>> studentListSupplier = () -> {

            getDatabaseConnection();
            PreparedStatement preparedStatement = null;
            ResultSet         resultSet         = null;

            List<Student> studentsList = new ArrayList<Student>();

            try {
                preparedStatement = con.prepareStatement("select * from public.\"Student\"");
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String       id         = resultSet.getString(1);
                    String       name       = resultSet.getString(2);
                    Double       gpa        = resultSet.getDouble(3);
                    String       gender     = resultSet.getString(4);
                    int          gradeLevel = resultSet.getInt(5);
                    List<String> activities = Arrays.asList((String[]) resultSet.getArray(6).getArray());

                    studentsList.add(new Student(id, name, gpa, gender, gradeLevel, activities));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                closeDatabaseConnection();
            }
            return studentsList;
        };
        return CompletableFuture.supplyAsync(studentListSupplier, threadPool);
    }

    @Override
    public CompletableFuture<Student> getStudent(String sid) {

        Supplier<Student> studentSupplier = () -> {
            getDatabaseConnection();
            Statement statement = null;
            ResultSet resultSet = null;
            Student   student   = null;

            try {
                statement = con.createStatement();
                resultSet = statement.executeQuery("select * from public.\"Student\" where \"id\"='" + sid + "'");

                if (resultSet.next()) {
                    String       id         = resultSet.getString(1);
                    String       name       = resultSet.getString(2);
                    Double       gpa        = resultSet.getDouble(3);
                    String       gender     = resultSet.getString(4);
                    int          gradeLevel = resultSet.getInt(5);
                    List<String> activities = Arrays.asList((String[]) resultSet.getArray(6).getArray());

                    student = new Student(id, name, gpa, gender, gradeLevel, activities);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                closeDatabaseConnection();
            }
            return student;
        };
        return CompletableFuture.supplyAsync(studentSupplier, threadPool);
    }

    @Override
    public CompletableFuture<Student> addStudent(Student student) {

        getDatabaseConnection();
        Statement                  statement     = null;
        CompletableFuture<Student> returnStudent = null;
        String                     activities    = student.getActivities().stream().map(activity -> "'" + activity + "'").collect(Collectors.joining(",", "Array[", "]"));

        try {
            statement = con.createStatement();
            statement.executeUpdate("insert into public.\"Student\" values('" +
                    student.getId() + "','" +
                    student.getName() + "', " +
                    student.getGpa() + ", '" +
                    student.getGender() + "', " +
                    student.getGradeLevel() + ", " +
                    activities + ")");

            returnStudent = getStudent(student.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeDatabaseConnection();
        }
        return returnStudent;
    }

    @Override
    public CompletableFuture<Student> updateStudentDetails(Student student) {

        getDatabaseConnection();
        Statement statement  = null;
        String    activities = student.getActivities().stream().map(activity -> "'" + activity + "'").collect(Collectors.joining(",", "Array[", "]"));

        CompletableFuture<Student> updatedStudent = null;
        try {
            statement = con.createStatement();
            statement.executeUpdate("update public.\"Student\" set \"name\" ='" + student.getName()
                    + "', \"gpa\" =" + student.getGpa()
                    + ", \"gender\" ='" + student.getGender()
                    + "', \"gradeLevel\" =" + student.getGradeLevel()
                    + ", \"activities\" =" + activities
                    + " where \"id\" ='" + student.getId() + "'");
            updatedStudent=getStudent(student.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeDatabaseConnection();
        }
        return updatedStudent;
    }

    @Override
    public void deleteStudentsById(String sid) {

        getDatabaseConnection();
        Statement statement = null;
        try {
            statement = con.createStatement();
            statement.executeUpdate("delete from public.\"Student\" where \"id\"='" + sid + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeDatabaseConnection();
        }
    }
}
