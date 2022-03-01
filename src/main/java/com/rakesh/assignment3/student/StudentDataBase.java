package com.rakesh.assignment3.student;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rakesh on Feb 14, 2022.
 */

public class StudentDataBase {
    static Connection con  = null;
    static Statement  stmt = null;
    static ResultSet  rst  = null;

    public static void getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/training?user=postgres&password=admin";
            con = DriverManager.getConnection(url);
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeConnection() {
        try {
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
    }

    private static List<String> getActivities(ResultSet activities) {
        List<String> activitiesList = null;
        try {
            String[] tempActivity = (String[]) activities.getArray("Activities").getArray();
            activitiesList = Arrays.asList(tempActivity);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return activitiesList;
    }

    //Get All Students from database.
    public static List<Student1> getAllStudents() {
        getConnection();//Establishing Connection with database.
        List<Student1> getAllStudent1s = new ArrayList<>();
        try {
            rst = stmt.executeQuery("Select * from public.\"Student\"");
            while (rst.next()) {
                List<String> activitiesList = getActivities(rst);
                getAllStudent1s.add(new Student1(rst.getInt("RegNo"), rst.getString("Name"),
                        rst.getInt("GradeLevel"), rst.getDouble("GPA"), rst.getString("Gender"),
                        activitiesList));
            }
        } catch (Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        try {
            rst.close();
        } catch (Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        closeConnection();//Closing database connection connection.
        return getAllStudent1s;
    }

    //Get Student by regNo from database.
    public static Student1 getStudentByID(int regNo) {
        getConnection();

        Student1 std = null;
        try {
            rst = stmt.executeQuery("Select * from \"Student\" where \"RegNo\"=" + regNo + "");
            if (rst.next()) {
                std = new Student1(rst.getInt("RegNo"), rst.getString("Name"), rst.getInt("GradeLevel")
                        , rst.getDouble("GPA"), rst.getString("Gender"), getActivities(rst));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            rst.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        closeConnection();
        return std;
    }

    //Inserting student in database.
    public static void addStudent(Student1 student1) {
        getConnection();
        int          regNo      = student1.getRegNo();
        String       name       = student1.getName();
        int          gradeLevel = student1.getGradeLevel();
        double       gpa        = student1.getGpa();
        String       gender     = student1.getGender();
        List<String> activities = student1.getActivities();
        try {
            stmt.executeUpdate("insert into public.\"Student\"(\"RegNo\",\"Name\",\"GradeLevel\",\"GPA\",\"Gender\",\"Activities\") " +
                    "values(" + regNo + ",\'" + name + "\'," + gradeLevel + "," + gpa + ",\'" + gender + "\',Array[" + activities.stream().map(m -> {
                return "\'" + m + "\'";
            }).collect(Collectors.joining(",")) + "])");
            //System.out.println("Data Added");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        closeConnection();
    }

    //Updating value in DataBase
    public static void updateData(String columnName, String newValue, int regNO) {
        getConnection();
        if (columnName.equalsIgnoreCase("Name")) {
            try {
                stmt.executeUpdate("update public.\"Student\" set \"Name\" =\'" + newValue + "\' where \"RegNo\"=" + regNO);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("GradeLevel")) {
            try {
                int gradeLevel = Integer.parseInt(newValue);
                stmt.executeUpdate("update public.\"Student\" set \"GradeLevel\" =\'" + gradeLevel + "\' where \"RegNo\"=" + regNO);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("GPA")) {
            try {
                double gpa = Double.parseDouble(newValue);
                stmt.executeUpdate("update public.\"Student\" set \"GPA\" =\'" + gpa + "\' where \"RegNo\"=" + regNO);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("Gender")) {
            try {
                stmt.executeUpdate("update public.\"Student\" set \"Gender\" =\'" + newValue + "\' where \"RegNo\"=" + regNO);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("Activities")) {
            try {
                newValue = "Array[" + newValue + "]";
                System.out.println("update public.\"Student\" set \"Activities\" =\'" + newValue + "\' where \"RegNo\"=" + regNO);
                stmt.executeUpdate("update public.\"Student\" set \"Activities\" =" + newValue + " where \"RegNo\"=" + regNO);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            throw new RuntimeException("No Column Name found in the DataBase");
        }
        closeConnection();
    }

    //To Delete Student by ID
    public static void deleteDataByID(int regNo) {
        getConnection();
        try {
            stmt.execute("Delete from public.\"Student\" where \"RegNo\"=" + regNo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        closeConnection();
    }
}

