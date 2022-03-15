package com.kishor.assignment4.employee;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * Created by Kishor on Feb 28, 2022.
 */

public class EmployeeDatabase implements IEmployee {
    static Connection        con  = null;
    static PreparedStatement stmt = null;
    static ResultSet         rs   = null;
    static DateTimeFormatter d1   = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    ExecutorService service = Executors.newCachedThreadPool();

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

    public CompletableFuture<List<Employee>> getAllEmployees() throws SQLException {
        Supplier<List<Employee>> getEmp = () -> {
            List<Employee> listOfEmployee = new ArrayList<>();
            getConnection();
            String qry = "select * from public.\"EmployeeDataBase\";";
            try {
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
            } catch (SQLException e) {
            }


            closeConnection();
            return listOfEmployee;
        };
        return CompletableFuture.supplyAsync(getEmp, service);
    }

    public CompletableFuture<Employee> getEmployeeById(int empId) throws SQLException {
        Supplier<Employee> getEmployeesById = () -> {
            Employee employee = null;
            getConnection();
            String qry = "select * from public.\"EmployeeDataBase\" where \"EmpId\" =" + empId + ";";
            try {
                stmt = con.prepareStatement(qry);
                rs = stmt.executeQuery();
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

            } catch (SQLException e) {
            }
            return employee;
        };
        return CompletableFuture.supplyAsync(getEmployeesById, service);
    }

    public CompletableFuture<Employee> updateValueThroughEmpId(String columnName, String value, Integer empId) throws Exception {
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
                throw new RuntimeException();
        } catch (Exception e) {
            System.out.println("Age or Joining date is not proper!");
        }
        closeConnection();
        return getEmployeeById(empId);
    }

    public CompletableFuture<Employee> insertNewRecord(Employee employee) throws SQLException {
        getConnection();
        Statement stmt = con.createStatement();
        String qry = "insert into public.\"EmployeeDataBase\" values (" + employee.getEmpId() + ",'" + employee.getName() + "','" + employee.getDepartment() + "'," + employee.getSalary() + ",'" + employee.getGender() +
                "','" + employee.getJoiningDate().toString() + "','" + employee.getDob().toString() + "','" + employee.getJobLevel() + "')";
        int age = Period.between(employee.getDob(), LocalDate.now()).getYears();
        if (age > 21 && !Period.between(employee.getJoiningDate(), LocalDate.now()).isNegative()) {
            try {
                stmt.executeUpdate(qry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            throw new RuntimeException("Employee under age");
        closeConnection();
        return getEmployeeById(employee.getEmpId());
    }

    public CompletableFuture<List<Employee>> deleteEmployeeById(int empId) throws SQLException {
        getConnection();
        Statement stmt = con.createStatement();
        String    qry  = "delete from public.\"EmployeeDataBase\" where \"EmpId\"=" + empId + ";";
        stmt.executeUpdate(qry);
        closeConnection();
        return getAllEmployees();
    }

    public static void main(String[] args) throws Exception {
        EmployeeDatabase e = new EmployeeDatabase();
        //e.getAllEmployees().get().stream().forEach(System.out::println);
        System.out.println(e.updateValueThroughEmpId("JobLevel", "Junior", 2).get());
        System.out.println(e.getEmployeeById(1).get());

    }

}
