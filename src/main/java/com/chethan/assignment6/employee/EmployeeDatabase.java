package com.chethan.assignment6.employee;

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
 * Created by Chethan on Mar 11, 2022.
 */

public class EmployeeDatabase implements IEmployeeDatabase {
    private static Connection      con        = null;
    private final  ExecutorService threadPool = Executors.newCachedThreadPool();

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


    //get all employees details from database
    @Override
    public CompletableFuture<List<Employee>> getEmployeeData() {

        Supplier<List<Employee>> employeeList = () -> {
            getDatabaseConnection();

            List<Employee> employeesList = new ArrayList<Employee>();

            PreparedStatement pstmt = null;
            ResultSet         rs    = null;
            String            qry   = "select * from public.\"Employee\"";


            try {
                pstmt = con.prepareStatement(qry);
                rs = pstmt.executeQuery();
                DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE;

                while (rs.next()) {
                    int       id          = rs.getInt("id");
                    String    name        = rs.getString(2);
                    String    department  = rs.getString(3);
                    double    salary      = rs.getDouble(4);
                    String    gender      = rs.getString(5);
                    LocalDate joiningDate = LocalDate.parse(rs.getString(6), format);
                    LocalDate dob         = LocalDate.parse(rs.getString(7), format);
                    String    jobLevel    = rs.getString(8);

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
        };
        return CompletableFuture.supplyAsync(employeeList, threadPool);
    }


    //   get a student detail from database
    @Override
    public CompletableFuture<Employee> getEmployeeById(int sid) {

        Supplier<Employee> employeeSupplier = () -> {
            getDatabaseConnection();
            Employee          employee = null;
            PreparedStatement pstmt    = null;
            ResultSet         rs       = null;
            String            qry      = "select * from public.\"Employee\" where \"id\"=?";

            try {
                DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE;
                pstmt = con.prepareStatement(qry);
                pstmt.setInt(1, sid);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int       id          = rs.getInt("id");
                    String    name        = rs.getString(2);
                    String    department  = rs.getString(3);
                    double    salary      = rs.getDouble(4);
                    String    gender      = rs.getString(5);
                    LocalDate joiningDate = LocalDate.parse(rs.getString(6), format);
                    LocalDate dob         = LocalDate.parse(rs.getString(7), format);
                    String    jobLevel    = rs.getString(8);
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
        };
        return CompletableFuture.supplyAsync(employeeSupplier, threadPool);
    }

    // add a new Employee to database
    @Override
    public CompletableFuture<Employee> addEmployee(Employee employee) throws IllegalAccessException {
        getDatabaseConnection();
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            LocalDate adob = employee.getDob();

            if (Period.between(adob, LocalDate.now()).getYears() >= 21 && !Period.between(employee.getJoiningDate(), LocalDate.now()).isNegative() && Period.between(employee.getJoiningDate(), employee.getDob()).isNegative()) {
                stmt.executeUpdate("insert into public.\"Employee\" values ('" + employee.getId() + "', '" + employee.getName() + "', '" + employee.getDepartment()
                        + "', " + employee.getSalary() + ", '" + employee.getGender() + "', '" + employee.getJoiningDate() + "', '" + employee.getDob() + "', '" + employee.getJobLevel() + "')");
            } else throw new IllegalAccessException();

        } catch (SQLException | IllegalAccessException e) {
            throw new IllegalAccessException("error adding an employee: check if the details of the employee is valid");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeDatabaseConnection();
        }

        return getEmployeeById(employee.getId());
    }


    // update employee details
    @Override
    public CompletableFuture<Employee> updateEmployeeDetailsById(Employee employee) {
        getDatabaseConnection();
        Statement         stmt = null;
        DateTimeFormatter d1   = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            if (Period.between(employee.getDob(), LocalDate.now()).getYears() >= 21 && !Period.between(employee.getJoiningDate(), LocalDate.now()).isNegative() && Period.between(employee.getJoiningDate(), employee.getDob()).isNegative()) {
                stmt = con.createStatement();
                stmt.executeUpdate("update public.\"Employee\" set \"" + "name" + "\"='" + employee.getName() +
                        "', \"department" + "\"='" + employee.getDepartment() +
                        "', \"salary" + "\"=" + employee.getSalary() +
                        ", \"gender" + "\"='" + employee.getGender() +
                        "', \"joiningDate" + "\"='" + employee.getJoiningDate() +
                        "', \"dob" + "\"='" + employee.getDob() +
                        "', \"jobLevel" + "\"='" + employee.getJobLevel() +
                        "' where \"id\"=" + employee.getId());
            } else throw new IllegalAccessException("Invalid data : Student details not Updated");
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeDatabaseConnection();
        }
        return getEmployeeById(employee.getId());
    }


    // delete student by ID
    @Override
    public void deleteEmployeeById(int sid) {
        getDatabaseConnection();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("delete from public.\"Employee\" where \"id\"=" + sid);
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
            closeDatabaseConnection();
        }
    }
}
