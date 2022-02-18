package com.rakesh.assignment3.employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakesh on Feb 16, 2022.
 */

public class EmployeeDataBase {
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
            e.printStackTrace();
        }
    }

    public static List<Employee1> getAllEmployees() {
        List<Employee1> result = new ArrayList<>();
        getConnection();
        try {
            rst = stmt.executeQuery("Select * from public.\"Employee\"");
            while (rst.next()) {
                LocalDate joiningDate = LocalDate.parse(rst.getString("JoingDate"));
                LocalDate dob         = LocalDate.parse(rst.getString("DOB"));
                result.add(new Employee1(rst.getInt("EmpID"), rst.getString("Name"), rst.getString("Department")
                        , rst.getDouble("Salary"), rst.getString("Gender"), joiningDate, dob, rst.getString("JobLevel")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        closeConnection();
        return result;
    }

    public static Employee1 getEmployeeByID(int empID) {
        Employee1 result = null;
        getConnection();
        try {
            rst = stmt.executeQuery("Select * from public.\"Employee\" where \"EmpID\"=" + empID);
            if (rst.next()) {
                LocalDate joiningDate = LocalDate.parse(rst.getString("JoingDate"));
                LocalDate dob         = LocalDate.parse(rst.getString("DOB"));
                result = new Employee1(rst.getInt("EmpID"), rst.getString("Name"), rst.getString("Department")
                        , rst.getDouble("Salary"), rst.getString("Gender"), joiningDate, dob, rst.getString("JobLevel"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        closeConnection();
        return result;
    }

    public static void addEmployee(Employee1 employee) {
        getConnection();
        int age = Period.between(employee.getDob(), LocalDate.now()).getYears();
        if (age >= 21) {
            DateTimeFormatter dtf       = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String            joingDate = dtf.format(employee.getJoiningDate());
            String            dob       = dtf.format(employee.getDob());
            try {
                stmt.executeUpdate("insert into public.\"Employee\"(\"EmpID\",\"Name\",\"Department\",\"Salary\",\"Gender\",\"JoingDate\",\"DOB\",\"JobLevel\")" +
                        " values(" + employee.getEmpID() + ",\'" + employee.getName() + "\',\'" + employee.getDepartment() + "\'," + employee.getSalary() + "," +
                        "\'" + employee.getGender() + "\',\'" + joingDate + "\',\'" + dob + "\',\'" + employee.getJobLevel() + "\')");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        } else {
            throw new RuntimeException("Employee age is under 21 years");
        }
    }

    public static void updateData(String columnName, String newValue, int empID) {
        getConnection();
        if (columnName.equalsIgnoreCase("Name")) {
            try {
                stmt.executeUpdate("update public.\"Employee\" set \"Name\" =\'" + newValue + "\' where \"EmpID\"=" + empID);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("Department")) {
            try {
                stmt.executeUpdate("update public.\"Employee\" set \"Department\" =\'" + newValue + "\' where \"EmpID\"=" + empID);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("salary")) {
            try {
                double salary = Double.parseDouble(newValue);
                stmt.executeUpdate("update public.\"Employee\" set \"Salary\" =\'" + salary + "\' where \"EmpID\"=" + empID);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("Gender")) {
            try {
                stmt.executeUpdate("update public.\"Employee\" set \"Gender\" =\'" + newValue + "\' where \"EmpID\"=" + empID);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("joiningDate")) {
            try {
                DateTimeFormatter dtf       = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate         joingdate = LocalDate.parse(newValue, dtf);
                int               days      = Period.between(joingdate, LocalDate.now()).getDays();
                if (days > -1) {
                    stmt.executeUpdate("update public.\"Employee\" set \"JoingDate\" =\'" + newValue + "\' where \"EmpID\"=" + empID);
                } else {
                    throw new RuntimeException("Invalid Joining date");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("DOB")) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate         dob = LocalDate.parse(newValue, dtf);
                int               age = Period.between(dob, LocalDate.now()).getYears();
                if (age >= 21) {
                    stmt.executeUpdate("update public.\"Employee\" set \"DOB\" =\'" + newValue + "\' where \"EmpID\"=" + empID);
                } else {
                    throw new RuntimeException("Employee age cannot be under 21");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (columnName.equalsIgnoreCase("jobLevel")) {
            try {
                stmt.executeUpdate("update public.\"Employee\" set \"JobLevel\" =\'" + newValue + "\' where \"EmpID\"=" + empID);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            throw new RuntimeException("No Column Name found in the DataBase");
        }
        closeConnection();
    }

    public static void deleteEmployeeByID(int empID) {
        getConnection();
        try {
            stmt.execute("Delete from public.\"Employee\" where \"EmpID\"=" + empID);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        closeConnection();
    }

    public static void deleteAllEmployee() {
        getConnection();
        try {
            stmt.executeUpdate("Delete from public.\"Employee\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
    }

//    public static void main(String[] args) {
//       // deleteEmployeeByID(1005);
//       // deleteAllEmployee();
//        updateData("Joblevel", "Junior", 1004);
//    }
}
