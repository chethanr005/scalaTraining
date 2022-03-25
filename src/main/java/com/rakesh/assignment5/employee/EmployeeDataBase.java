package com.rakesh.assignment5.employee;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

/**
 * Created by Rakesh on Mar 14, 2022.
 */

public class EmployeeDataBase implements IEmployeeDataBase {
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

    @Override
    public CompletableFuture<List<Employee>> getAllEmployee(ExecutorService service) {
        Supplier<List<Employee>> getALlEmpData = () -> {
            List<Employee> result = new ArrayList<>();
            getConnection();
            try {
                rst = stmt.executeQuery("Select * from public.\"Employee\"");
                while (rst.next()) {
                    LocalDate joiningDate = LocalDate.parse(rst.getString("JoingDate"));
                    LocalDate dob         = LocalDate.parse(rst.getString("DOB"));
                    result.add(new Employee(rst.getInt("EmpID"), rst.getString("Name"), rst.getString("Department"), rst.getDouble("Salary"), rst.getString("Gender"), joiningDate, dob, rst.getString("JobLevel")));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
            return result;
        };
        return CompletableFuture.supplyAsync(getALlEmpData, service);
    }

    @Override
    public CompletableFuture<Employee> getEmployeeByID(int empID, ExecutorService service) {
        Supplier<Employee> getEmployee = () -> {
            Employee result = null;
            getConnection();
            try {
                rst = stmt.executeQuery("Select * from public.\"Employee\" where \"EmpID\"=" + empID);
                if (rst.next()) {
                    LocalDate joiningDate = LocalDate.parse(rst.getString("JoingDate"));
                    LocalDate dob         = LocalDate.parse(rst.getString("DOB"));
                    result = new Employee(rst.getInt("EmpID"), rst.getString("Name"), rst.getString("Department"), rst.getDouble("Salary"), rst.getString("Gender"), joiningDate, dob, rst.getString("JobLevel"));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return result;
        };
        return CompletableFuture.supplyAsync(getEmployee, service);
    }

    @Override
    public boolean addNewEmployee(Employee employee) {
        boolean isAdded = false;
        getConnection();
        int age   = Period.between(employee.getDob(), LocalDate.now()).getYears();
        int empID = employee.getEmpID();
        try {
            rst = stmt.executeQuery("Select * from public.\"Employee\" where \"EmpID\"=" + empID);
            if (!rst.next()) {
                if (age >= 21) {
                    DateTimeFormatter dtf       = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String            joingDate = dtf.format(employee.getJoiningDate());
                    String            dob       = dtf.format(employee.getDob());
                    try {
                        stmt.executeUpdate("insert into public.\"Employee\"(\"EmpID\",\"Name\",\"Department\",\"Salary\",\"Gender\",\"JoingDate\",\"DOB\",\"JobLevel\")" + " values(" + employee.getEmpID() + ",\'" + employee.getName() + "\',\'" + employee.getDepartment() + "\'," + employee.getSalary() + "," + "\'" + employee.getGender() + "\',\'" + joingDate + "\',\'" + dob + "\',\'" + employee.getJobLevel() + "\')");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    return false;
                }
                try {
                    rst = stmt.executeQuery("Select * from public.\"Employee\" where \"EmpID\"=" + empID);
                    isAdded = rst.next();
                } catch (Exception e) {
                }

                closeConnection();
            }
        } catch (Exception e) {
        }
        return isAdded;
    }

    @Override
    public boolean updateEmployeeData(String columnName, String newValue, int empID) {
        boolean isUpdated = false;
        //System.out.println("I am called");
        getConnection();
        try {
            rst = stmt.executeQuery("Select * from public.\"Employee\" where \"EmpID\"=" + empID);
            if (rst.next()) {
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
                    return false;
                }

                // to check if value is updated or not
                try {
                    rst = stmt.executeQuery("Select * from public.\"Employee\" where \"EmpID\"=" + empID);
                    if (rst.next()) {
                        isUpdated = rst.getString(columnName).equals(newValue);
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
        closeConnection();

        return isUpdated;
    }

    @Override
    public boolean deleteEmployeeByID(int empID) {
        boolean isDeleted = false;
        getConnection();
        try {
            rst = stmt.executeQuery("Select * from public.\"Employee\" where \"EmpID\"=" + empID);
            if (rst.next()) {
                try {
                    stmt.execute("Delete from public.\"Employee\" where \"EmpID\"=" + empID);
                } catch (Exception e) {
                    System.err.println("Delete Block");
                    ;
                    System.err.println(e.getMessage());
                }
                try {
                    rst = stmt.executeQuery("Select * from public.\"Employee\" where \"EmpID\"=" + empID);
                    isDeleted = !rst.next();
                } catch (Exception e) {
                    System.err.println("IsDeleted  Block");
                    e.printStackTrace();
                }
            } else {
                throw new RuntimeException("No Employee data found with the EmpID :" + empID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        closeConnection();
        return isDeleted;
    }

    @Override
    public boolean deleteAllEmployee() {
        boolean isDeleted = false;
        getConnection();
        try {
            stmt.executeUpdate("Delete from public.\"Employee\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rst = stmt.executeQuery("Select * from public.\"EMployee\"");
            isDeleted = !rst.next();
        } catch (Exception e) {
        }
        closeConnection();
        return isDeleted;
    }

    public static boolean checkDepartment(String department) {
        boolean isPresent = false;
        System.out.println();
        getConnection();
        try {
            rst = stmt.executeQuery("Select * from public.\"Employee\" where \"Department\"=\'" + department + "\'");
            isPresent = rst.next();
        } catch (Exception e) {
            System.err.println(" Check Department Block : " + e.getMessage());
        }
        return isPresent;
    }

//    public static void main(String[] args) {
//        EmployeeDataBase db=new EmployeeDataBase();
//        System.out.println(db.addNewEmployee( new Employee(1006,"xyz","IT",35000,"male","2021-02-11","2011-01-03","junior")));
//    }
}

