package com.kishor.assignment3.employee;

import com.kishor.assignment1.employee.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kishor on Feb 16, 2022.
 */

public class EmployeeDatabase {
    static Connection        con  = null;
    static PreparedStatement stmt = null;
    static ResultSet         rs   = null;
    static DateTimeFormatter d1   = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static void getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
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
        if (stmt != null) {
            try {
                stmt.close();
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

    public static List<Employee> getAllEmployees() throws SQLException {
        List<Employee> listOfEmployee = new ArrayList<>();
        getConnection();
        String    qry  = "select * from public.\"EmployeeDataBase\";";
        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(qry);
        while (rs.next()) {
            LocalDate joiningDate  = LocalDate.parse(rs.getString("JoiningDate"), d1);
            LocalDate birthdayDate = LocalDate.parse(rs.getString("DOB"), d1);
            listOfEmployee.add(new Employee(
                    rs.getInt("EmpId"),
                    rs.getString("Name"),
                    rs.getString("Department"),
                    rs.getDouble("Salary"),
                    rs.getString("Gender"),
                    joiningDate,
                    birthdayDate,
                    rs.getString("JobLevel")));
        }

        closeConnection();
        return listOfEmployee;
    }

    public static Employee getEmployeeById(int empId) throws SQLException {
        getConnection();
        String qry = "select * from public.\"EmployeeDataBase\" where \"EmpId\" =" + empId + ";";
        stmt = con.prepareStatement(qry);
        rs = stmt.executeQuery();
        Employee employee = null;
        if (rs.next()) {
            LocalDate joiningDate  = LocalDate.parse(rs.getString("JoiningDate"), d1);
            LocalDate birthdayDate = LocalDate.parse(rs.getString("DOB"), d1);
            employee = new Employee(
                    rs.getInt("EmpId"),
                    rs.getString("Name"),
                    rs.getString("Department"),
                    rs.getDouble("Salary"),
                    rs.getString("Gender"),
                    joiningDate,
                    birthdayDate,
                    rs.getString("JobLevel"));
        }
        closeConnection();
        return employee;
    }

    public static Employee updateValueThroughEmpId(String columnName, String value, Integer empId) throws Exception {
        getConnection();
        DateTimeFormatter d1   = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Statement         stmt = con.createStatement();
        String            qry  = "update public.\"EmployeeDataBase\" set \"" + columnName + "\"='" + value + "' where \"EmpId\"=" + empId + ";";
        try {
            if (columnName == "EmpId" || columnName == "Name" || columnName == "Department" || columnName == "Gender" || columnName == "JobLevel" || columnName == "Salary")
                stmt.executeUpdate(qry);
            else if (columnName == "JoiningDate" && !Period.between(LocalDate.parse(value, d1), LocalDate.now()).isNegative())
                stmt.executeUpdate(qry);
            else if (columnName == "DOB" && Period.between(LocalDate.parse(value, d1), LocalDate.now()).getYears() > 21)
                stmt.executeUpdate(qry);
            else
                throw new Exception();
        } catch (Exception e) {
            System.out.println("Age or Joining date is not proper!");
        }
        closeConnection();
        return getEmployeeById(empId);
    }

    public static Employee insertNewRecord(Employee employee) throws SQLException {
        getConnection();
        Statement stmt = con.createStatement();
        String qry = "insert into public.\"EmployeeDataBase\" values (" + employee.getEmpId() + ",'" + employee.getName() + "','" + employee.getDepartment() + "'," + employee.getSalary() + ",'" + employee.getGender() +
                "','" + employee.getJoiningDate().toString() + "','" + employee.getDob().toString() + "','" + employee.getJobLevel() + "')";
        int age = Period.between(employee.getDob(), LocalDate.now()).getYears();
        try {
            if (age > 21 && !Period.between(employee.getJoiningDate(), LocalDate.now()).isNegative())
                stmt.executeUpdate(qry);
            else
                throw new Exception();
        } catch (Exception e) {
            System.out.println("Age or Joining date is not proper!");
        }
        closeConnection();
        return getEmployeeById(employee.getEmpId());
    }

    public static List<Employee> deleteEmployeeById(int empId) throws SQLException {
        getConnection();
        Statement stmt = con.createStatement();
        String    qry  = "delete from public.\"EmployeeDataBase\" where \"EmpId\"=" + empId + ";";
        stmt.executeUpdate(qry);
        closeConnection();
        return getAllEmployees();
    }
}
