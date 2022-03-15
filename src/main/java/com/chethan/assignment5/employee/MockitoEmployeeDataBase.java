package com.chethan.assignment5.employee;




import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * Created by Chethan on Mar 04, 2022.
 */

public class MockitoEmployeeDataBase {
    IEmployeeDatabase i = new EmployeeDatabase();

    public static CompletableFuture<List<Employee>> getEmployeesList() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList(
                new Employee(1, "marry", "cs", 260000, "female", "2020-10-15", "2000-04-15", "junior"),
                new Employee(2, "rose", "ee", 250000, "female", "2015-12-25", "1993-06-21", "mid"),
                new Employee(3, "julie", "ec", 350000, "female", "2014-07-24", "1993-03-10", "mid"),
                new Employee(4, "tony", "cs", 250000, "male", "2010-04-04", "1989-08-23", "mid"),
                new Employee(5, "kail", "cs", 450000, "female", "2012-12-28", "1990-09-10", "mid"),
                new Employee(6, "kate", "ee", 450000, "female", "2015-10-01", "1994-01-26", "junior"),
                new Employee(7, "joe", "ee", 250000, "male", "2021-12-01", "2000-10-25", "junior"),
                new Employee(8, "anthony", "ec", 380000, "male", "2019-05-12", "1995-03-21", "junior"),
                new Employee(9, "andrew", "ec", 300000, "male", "2021-06-26", "1997-07-19", "junior"),
                new Employee(10, "swift", "cs", 500000, "female", "2020-07-12", "1996-05-02", "junior"),
                new Employee(11, "cooper", "ee", 470000, "male", "2020-10-18", "1996-05-05", "junior"),
                new Employee(12, "mary", "cs", 260000, "female", "2021-10-15", "2000-04-15", "junior"),
                new Employee(13, "hailey", "cs", 420000, "female", "2016-11-09", "1994-02-21", "mid")
        ));
    }

    public static CompletableFuture<Employee> getEmployee(int eid){
        return getEmployeesList().thenApply(studentsList->{
            return studentsList.stream().filter(student->student.getId()==eid).findAny().get();});
    }

    public static CompletableFuture<Employee> updateEmployeeByIdInMockito(int eid, String columnName, String value) {
        return getEmployeesList().thenApply(employeesList -> employeesList.stream().filter(employee -> employee.getId() == eid).map(IdEmployee -> {
            DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (columnName.equals("name"))
                IdEmployee.setName(value);
            else if (columnName.equals("department"))
                IdEmployee.setDepartment(value);
            else if (columnName.equals("salary"))
                IdEmployee.setSalary(Double.parseDouble(value));
            else if (columnName.equals("gender"))
                IdEmployee.setGender(value);
            else if (columnName.equals("joiningDate"))
                IdEmployee.setJoiningDate(LocalDate.parse(value, dateformat));
            else if (columnName.equals("dob"))
                IdEmployee.setDob(LocalDate.parse(value, dateformat));
            else if (columnName.equals("jobLevel"))
                IdEmployee.setJobLevel(value);

            return IdEmployee;
        }).findAny().get());
    }

    public static CompletableFuture<Employee> addMockitoEmployeee(Employee employee) throws IllegalAccessException, ExecutionException, InterruptedException {
        List<Employee> employeesList = new ArrayList<>();
        employeesList.addAll(getEmployeesList().get());


        DateTimeFormatter d1   = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate         adob = LocalDate.parse(employee.getDob().toString(), d1);

        if (Period.between(adob, LocalDate.now()).getYears() >= 21 && !Period.between(employee.getJoiningDate(), LocalDate.now()).isNegative()) {
            employeesList.add(employee);
        } else throw new IllegalAccessException("error adding an employee: check if the details of the employee is valid");
        System.out.println(employeesList);

        return CompletableFuture.supplyAsync(() -> employee);
    }
}
