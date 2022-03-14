package com.chethan.assignment5.student;


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
 * Created by Chethan on Feb 24, 2022.
 */

public class StudentDatabase implements IStudentDatabase {
    private static Connection con = null;
    ExecutorService threadPool = Executors.newCachedThreadPool();

    // connecting to database
    private static void getDatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/training?user=postgres&password=admin");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // closing all connection with database
    private static void closeDatabaseConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // get all students details from database
    @Override
    public CompletableFuture<List<Student>> getStudentsData() {
        Supplier<List<Student>> studentList = () -> {
            getDatabaseConnection();

            List<Student> studentsList = new ArrayList<Student>();

            PreparedStatement pstmt = null;
            ResultSet         rs    = null;
            String            qry   = "select * from public.\"Student\"";

            try {
                pstmt = con.prepareStatement(qry);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    String       id         = rs.getString("id");
                    String       name       = rs.getString(2);
                    double       gpa        = rs.getDouble(3);
                    String       gender     = rs.getString(4);
                    int          gradeLevel = rs.getInt(5);
                    List<String> activities = Arrays.asList((String[]) rs.getArray(6).getArray());

                    studentsList.add(new Student(id, name, gpa, gender, gradeLevel, activities));

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                closeDatabaseConnection();
            }
            return studentsList;
        };
        return CompletableFuture.supplyAsync(studentList, threadPool);
    }

    //   get a student detail from database
    @Override
    public CompletableFuture<Student> getStudent(String sid) {

        Supplier<Student> student = () -> {

            getDatabaseConnection();

            Student studentObject = null;

            PreparedStatement pstmt = null;
            ResultSet         rs    = null;
            String            qry   = "select * from public.\"Student\" where \"id\"=?";

            try {
                pstmt = con.prepareStatement(qry);
                pstmt.setString(1, sid);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    String       id         = rs.getString("id");
                    String       name       = rs.getString(2);
                    double       gpa        = rs.getDouble(3);
                    String       gender     = rs.getString(4);
                    int          gradeLevel = rs.getInt(5);
                    List<String> activities = Arrays.asList((String[]) rs.getArray(6).getArray());

                    studentObject = new Student(id, name, gpa, gender, gradeLevel, activities);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                closeDatabaseConnection();
            }
            return studentObject;
        };
        return CompletableFuture.supplyAsync(student, threadPool);
    }


    // add a new student to database
    @Override
    public CompletableFuture<Student> addStudent(Student student) {

        getDatabaseConnection();

        Statement stmt = null;

        String activities = student.getActivities().stream().map(m -> "'" + m + "'").collect(Collectors.joining(",", "Array[", "]"));

        try {
            stmt = con.createStatement();
            stmt.executeUpdate("insert into public.\"Student\" values ('" + student.getId() + "', '" + student.getName() + "', " + student.getGpa()
                    + ", '" + student.getGender() + "', " + student.getGradeLevel() + ", " + activities + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeDatabaseConnection();
        return getStudent(student.getId());
    }

    // update student details
    @Override
    public CompletableFuture<Student> updateStudentDetails(String sid, String columnName, String value) {

        getDatabaseConnection();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("update public.\"Student\" set \"" + columnName + "\"=" + value + " where \"id\"='" + sid + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeDatabaseConnection();
        return getStudent(sid);
    }

    // delete student by ID
    @Override
    public void deleteStudentsById(String sid) {
        getDatabaseConnection();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("delete from public.\"Student\" where \"id\"='" + sid + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        closeDatabaseConnection();
    }
}
