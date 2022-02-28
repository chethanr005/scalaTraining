package com.chethan.assignment3.employee;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Chethan on Feb 16, 2022.
 */

public class EmployeeDatabase {

    private static Connection con = null;

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


    // get all employees details from database
    static List<Employee> getEmployeeData() {

        getDatabaseConnection();

        List<Employee> employeesList = new ArrayList<Employee>();

        PreparedStatement pstmt = null;
        ResultSet         rs    = null;
        String            qry   = "select * from public.\"Employee\"";

        try {
            pstmt = con.prepareStatement(qry);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int    id          = rs.getInt("id");
                String name        = rs.getString(2);
                String department  = rs.getString(3);
                double salary      = rs.getDouble(4);
                String gender      = rs.getString(5);
                String joiningDate = rs.getString(6);
                String dob         = rs.getString(7);
                String jobLevel    = rs.getString(8);

                employeesList.add(new Employee(id, name, department, salary, gender, joiningDate, dob, jobLevel));
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
        return employeesList;
    }


    //   get a student detail from database
    static Employee getEmployee(int sid) {

        getDatabaseConnection();

        Employee          employee = null;
        PreparedStatement pstmt    = null;
        ResultSet         rs       = null;
        String            qry      = "select * from public.\"Employee\" where \"id\"=?";

        try {
            pstmt = con.prepareStatement(qry);
            pstmt.setInt(1, sid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int    id          = rs.getInt("id");
                String name        = rs.getString(2);
                String department  = rs.getString(3);
                double salary      = rs.getDouble(4);
                String gender      = rs.getString(5);
                String joiningDate = rs.getString(6);
                String dob         = rs.getString(7);
                String jobLevel    = rs.getString(8);
                employee = new Employee(id, name, department, salary, gender, joiningDate, dob, jobLevel);
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
        return employee;
    }

    // add a new Employee to database
    static boolean addEmployee(Employee employee) throws Exception {
        getDatabaseConnection();
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            DateTimeFormatter d1   = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate                  adob = LocalDate.parse(employee.getDob().toString(), d1);

            if (Period.between(adob, LocalDate.now()).getYears() >= 21 && !Period.between(employee.getJoiningDate(),LocalDate.now()).isNegative()) {
                stmt.executeUpdate("insert into public.\"Employee\" values ('" + employee.getId() + "', '" + employee.getName() + "', '" + employee.getDepartment()
                        + "', " + employee.getSalary() + ", '" + employee.getGender() + "', '" + employee.getJoiningDate().toString() + "', '" + employee.getDob().toString() + "', '" + employee.getJobLevel() + "')");
            } else throw new IllegalAccessException();

        } catch (SQLException | IllegalAccessException e) {
            throw  new IllegalAccessException("error adding an employee: check if the details of the employee is valid");
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
        return true;
    }

    // update employee details
    static void updateStudentDetails(int sid, String columnName, String value){
        getDatabaseConnection();
        Statement stmt = null;
        DateTimeFormatter d1   = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            if (columnName.equals("dob")){
                LocalDate                  adob = LocalDate.parse(value, d1);
                if (Period.between(adob, LocalDate.now()).getYears() >= 21) {
                    stmt = con.createStatement();
                    stmt.executeUpdate("update public.\"Employee\" set \"" + columnName + "\"='" + value + "' where \"id\"=" + sid);
                }else System.err.println("Invalid dob : Student details not Updated");

            }

            else if(columnName.equals("joiningDate")){
                LocalDate                  joinDate = LocalDate.parse(value, d1);
                if (!Period.between(joinDate,LocalDate.now()).isNegative()) {
                    stmt = con.createStatement();
                    stmt.executeUpdate("update public.\"Employee\" set \"" + columnName + "\"='" + value + "' where \"id\"=" + sid);
                }else System.err.println("Invalid joining Date : Student details not updated");
            }

            else if(columnName.equals("id")||columnName.equals("name")||columnName.equals("department")||columnName.equals("salary")||columnName.equals("gender")||columnName.equals("jobLevel")){
                stmt = con.createStatement();
                stmt.executeUpdate("update public.\"Employee\" set \"" + columnName + "\"='" + value + "' where \"id\"=" + sid);
            }
            else {
                System.err.println("Student details not Updated");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } finally {
            if(stmt!=null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeDatabaseConnection();
    }

    // delete student by ID
    static void deleteEmployeeById(int sid){
        getDatabaseConnection();
        Statement stmt=null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("delete from public.\"Employee\" where \"id\"="+sid);
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(stmt!=null){
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



