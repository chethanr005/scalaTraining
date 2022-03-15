package com.kishor.assignment3.student;

/**
 * Created by Kishor on Feb 15, 2022.
 */

import com.kishor.assignment1.student.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDatabase {
    static Connection        con   = null;
    static PreparedStatement pstmt = null;
    static ResultSet         rs    = null;

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

    public static List<Student> getAllStudents() throws SQLException {
        List<Student> listOfStudent = new ArrayList<>();
        getConnection();
        String    qry  = "select * from public.\"StudentDataBase\";";
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

        closeConnection();
        return listOfStudent;
    }

    public static Student getStudentById(int regNo) throws SQLException {
        getConnection();
        String qry = "select * from public.\"StudentDataBase\" where \"RegNo\" =" + regNo + ";";
        pstmt = con.prepareStatement(qry);
        rs = pstmt.executeQuery();
        Student student = null;
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
        return student;

    }

    public static Student updateValueThroughRegNo(String columnName, String value, int regNo) throws SQLException {
        getConnection();
        Statement stmt = con.createStatement();
        String    qry  = "update public.\"StudentDataBase\" set \"" + columnName + "\"=" + value + " where \"RegNo\"=" + regNo + ";";
        stmt.executeUpdate(qry);
        closeConnection();
        return getStudentById(regNo);
    }

    public static Student insertNewRecord(Student student) throws SQLException {
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

    public static List<Student> deleteStudentById(int regNo) throws SQLException {
        getConnection();
        Statement stmt = con.createStatement();
        String    qry  = "delete from public.\"StudentDataBase\" where \"RegNo\"=" + regNo + ";";
        stmt.executeUpdate(qry);
        closeConnection();
        return getAllStudents();
    }

    public static void main(String[] args) throws SQLException {
        //getAllStudents().stream().forEach(System.out::println);
        // System.out.println(getStudentById(1001));
//        System.out.println(getActivities(1001));
        //System.out.println(updateValueThroughRegNo("Name","'Alex'", 1001));
        //System.out.println(insertNewRecord(new Student(1007,"Ashok1", 3, 6.9, "male", Arrays.asList("swimming", "basketball", "volleyball"))));
        // deleteStudentById(1006).stream().forEach(System.out::println);
    }
}