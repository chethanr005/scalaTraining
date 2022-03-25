package com.kishor.assignment5.student;

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
 * Created by Kishor on Mar 11, 2022.
 */

public class StudentDatabase implements IStudent {
    static Connection        con   = null;
    static PreparedStatement pstmt = null;
    static ResultSet         rs    = null;
    ExecutorService service = Executors.newCachedThreadPool();

    private static void getConnection() {
        try {
            Class.forName("org.postgresql.Driver");            //we load and register the driver
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/training?user=postgres&password=admin");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void closeConnection() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("resultSet interface not closed");
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.err.println("statement interface not closed");
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("connection interface not closed");
            }
        }
    }

    private static List<String> getActivities(int regNo) throws SQLException {
        getConnection();
        List<String> activities = null;
        String       qry        = "select \"Activities\" from public.\"StudentDataBase\" where \"RegNo\" =" + regNo + ";";
        pstmt = con.prepareStatement(qry);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            activities = Arrays.asList((String[]) (rs.getArray("Activities")).getArray());
        }
        closeConnection();
        return activities;
    }

    @Override
    public CompletableFuture<List<Student>> getAllStudents() throws SQLException {
        Supplier<List<Student>> getStudent = () -> {
            List<Student> listOfStudent = new ArrayList<>();
            getConnection();
            String qry = "select * from public.\"StudentDataBase\";";
            try {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(qry);
                while (rs.next()) {
                    listOfStudent.add(new Student(rs.getInt("RegNo"),
                            rs.getString("Name"),
                            rs.getInt("GradeLevel"),
                            rs.getDouble("GPA"),
                            rs.getString("Gender"),
                            Arrays.asList((String[]) (rs.getArray("Activities")).getArray())));
                }
            } catch (SQLException e) {
            }

            closeConnection();
            return listOfStudent;
        };
        return CompletableFuture.supplyAsync(getStudent, service);
    }

    public CompletableFuture<Student> getStudentById(int regNo) throws SQLException {
        Supplier<Student> getStudentsById = () -> {
            Student student = null;
            getConnection();
            String qry = "select * from public.\"StudentDataBase\" where \"RegNo\" =" + regNo + ";";
            try {
                pstmt = con.prepareStatement(qry);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    student = new Student(
                            rs.getInt("RegNo"),
                            rs.getString("Name"),
                            rs.getInt("GradeLevel"),
                            rs.getDouble("GPA"),
                            rs.getString("Gender"),
                            getActivities(regNo));
                }
                closeConnection();
            } catch (SQLException e) {
            }
            return student;
        };
        return CompletableFuture.supplyAsync(getStudentsById, service);

    }

    public CompletableFuture<Student> updateValueThroughRegNo(String columnName, String value, int regNo) throws SQLException {
        getConnection();
        Statement stmt = con.createStatement();
        String    qry  = "update public.\"StudentDataBase\" set \"" + columnName + "\"='" + value + "' where \"RegNo\"=" + regNo + ";";
        stmt.executeUpdate(qry);
        closeConnection();
        return getStudentById(regNo);
    }

    public CompletableFuture<Student> insertNewRecord(Student student) throws SQLException {
        getConnection();
        Statement stmt = con.createStatement();
        String qry = "insert into public.\"StudentDataBase\"(\"RegNo\",\"Name\",\"GradeLevel\",\"GPA\",\"Gender\",\"Activities\") " +
                "values(" + student.getRegNo() + ",\'" + student.getName() + "\'," + student.getGradeLevel() + "," + student.getGpa() + ",\'" + student.getGender() + "\',Array[" + student.getActivities().stream().map(m -> {
            return "\'" + m + "\'";
        }).collect(Collectors.joining(",")) + "])";
        stmt.executeUpdate(qry);
        closeConnection();
        return getStudentById(student.getRegNo());
    }

    public CompletableFuture<List<Student>> deleteStudentById(int regNo) throws SQLException, IllegalArgumentException {
        getConnection();
        Statement stmt = con.createStatement();
        String    qry  = "delete from public.\"StudentDataBase\" where \"RegNo\"=" + regNo + ";";
        stmt.executeUpdate(qry);
        closeConnection();
        return getAllStudents();
    }

    public boolean check(int regNo) throws SQLException {
        getConnection();
        String qry = "select * from public.\"StudentDataBase\" where \"RegNo\"=" + regNo + ";";
        pstmt = con.prepareStatement(qry);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            closeConnection();
            return true;
        } else {
            closeConnection();
            return false;
        }
    }

    public static void main(String[] args) throws SQLException, ExecutionException, InterruptedException {
        StudentDatabase i = new StudentDatabase();
        //i.getAllStudents().get().stream().forEach(System.out::println);
        //System.out.println(i.check(1111));
    }
}
