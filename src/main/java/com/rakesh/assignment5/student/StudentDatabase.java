package com.rakesh.assignment5.student;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Rakesh on Mar 10, 2022.
 */

public class StudentDatabase implements IStudentDatabase {
    static Connection con  = null;
    static Statement  stmt = null;
    static ResultSet  rst  = null;


    private static void getConnection() {
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
            if (!rst.isClosed()) {
                rst.close();
            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * to get all Student Data
     *
     * @return
     */
    @Override
    public CompletableFuture<List<Student>> getAllStudents(ExecutorService service) {

        System.out.println("DataBase ..... ");
        Supplier<List<Student>> getStudentsDatabase = () -> {
            // System.out.println("Data Base Thread" + Thread.currentThread().getName());
            List<Student> studentsData = new ArrayList<>();
            getConnection();
            try {
                //Thread.sleep(2000);
                rst = stmt.executeQuery("Select * from public.\"Student\"");
                while (rst.next()) {
                    List<String> activitiesList = getActivities(rst);
                    studentsData.add(new Student(rst.getInt("RegNo"), rst.getString("Name"),
                            rst.getInt("GradeLevel"), rst.getDouble("GPA"), rst.getString("Gender"),
                            activitiesList));
                }
            } catch (Exception e) {
                System.err.println("DataBase : " + e.getMessage());
            }
            getConnection();
            return studentsData;
        };

        return CompletableFuture.supplyAsync(getStudentsDatabase, service);
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

    /**
     * to get Student Data  by RegNo
     *
     * @return
     */
    @Override
    public CompletableFuture<Student> getStudentByID(int regNo, ExecutorService service) {
        Supplier<Student> student = () -> {
            getConnection();
            Student std = null;
            try {
                rst = stmt.executeQuery("Select * from \"Student\" where \"RegNo\"=" + regNo + "");
                if (rst.next()) {
                    std = new Student(rst.getInt("RegNo"), rst.getString("Name"), rst.getInt("GradeLevel")
                            , rst.getDouble("GPA"), rst.getString("Gender"), getActivities(rst));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return std;
        };


        return CompletableFuture.supplyAsync(student, service);
    }

    /**
     * Update Student data
     */
    @Override
    public Boolean updateStudent(String columnName, String newValue, int regNo) {
        getConnection();
        boolean isUpdated = false;

        if (columnName.equalsIgnoreCase("Name")) {
            try {
                stmt.executeUpdate("update public.\"Student\" set \"Name\" =\'" + newValue + "\' where \"RegNo\"=" + regNo);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("GradeLevel")) {
            try {
                int gradeLevel = Integer.parseInt(newValue);
                stmt.executeUpdate("update public.\"Student\" set \"GradeLevel\" =\'" + gradeLevel + "\' where \"RegNo\"=" + regNo);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("GPA")) {
            try {
                double gpa = Double.parseDouble(newValue);
                stmt.executeUpdate("update public.\"Student\" set \"GPA\" =\'" + gpa + "\' where \"RegNo\"=" + regNo);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("Gender")) {
            try {
                stmt.executeUpdate("update public.\"Student\" set \"Gender\" =\'" + newValue + "\' where \"RegNo\"=" + regNo);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("Activities")) {
            try {
                newValue = "Array[" + newValue + "]";
                stmt.executeUpdate("update public.\"Student\" set \"Activities\" =" + newValue + " where \"RegNo\"=" + regNo);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return true;// have to change logic for activity
        } else {
            isUpdated = false;
            return false;
        }

        try {
            rst = stmt.executeQuery("Select * from public.\"Student\" where \"RegNo\"=" + regNo);
            if (rst.next()) {
                isUpdated = rst.getString(columnName).equals(newValue) ? true : false;
            }
        } catch (Exception e) {
        }
        closeConnection();
        return isUpdated;
    }

    /**
     * to delete all student data
     */
    @Override
    public Boolean deleteAllStudent() {
        getConnection();

        try {
            stmt.execute("Delete from public.\"Student\"");

            rst = stmt.executeQuery("Select * from public.\"Student\"");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        closeConnection();
        return true;
    }

    /**
     * to delete student by RegNo
     */
    @Override
    public Boolean deleteStudentByID(int regNo) {
        getConnection();
        boolean isDeleted = false;
        try {
            rst = stmt.executeQuery("Select * from public.\"Student\" where \"RegNo\"=" + regNo);
            if (rst.next()) {
                try {
                    stmt.execute("Delete from public.\"Student\" where \"RegNo\"=" + regNo);
                } catch (Exception e) {
                    System.err.println("Delete Block");
                    ;
                    System.err.println(e.getMessage());
                }
                try {
                    rst = stmt.executeQuery("Select * from public.\"Student\" where \"RegNo\"=" + regNo);
                    isDeleted = !rst.next();
                } catch (Exception e) {
                    System.err.println("IsDeleted Check Block");
                    e.printStackTrace();
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        closeConnection();
        return isDeleted;
    }

    /**
     * to add new student
     *
     * @return
     */

    @Override
    public Boolean addNewStudent(Student student) {
        boolean isAdded = false;
        getConnection();
        int          regNo      = student.getRegNo();
        String       name       = student.getName();
        int          gradeLevel = student.getGradeLevel();
        double       gpa        = student.getGpa();
        String       gender     = student.getGender();
        List<String> activities = student.getActivities();

        try {
            rst = stmt.executeQuery("Select * from public.\"Student\" where \"RegNo\"=" + regNo);
            if (!rst.next()) {
                try {
                    stmt.executeUpdate("insert into public.\"Student\"(\"RegNo\",\"Name\",\"GradeLevel\",\"GPA\",\"Gender\",\"Activities\") " +
                            "values(" + regNo + ",\'" + name + "\'," + gradeLevel + "," + gpa + ",\'" + gender + "\',Array[" + activities.stream().map(m -> {
                        return "\'" + m + "\'";
                    }).collect(Collectors.joining(",")) + "])");
                    //System.out.println("Data Added");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                try {
                    rst = stmt.executeQuery("Select * from public.\"Student\" where \"RegNo\"=" + regNo);
                    isAdded = rst.next();
                } catch (Exception e) {
                }
                closeConnection();
            } else {
                isAdded = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }

//    public static void main(String[] args) {
//        StudentDatabase bd = new StudentDatabase();
//
//        System.out.println(bd.updateStudent("Activities", "'chess','Hello'", 1001));
//    }
}
