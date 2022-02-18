package com.chethan.assignment1.employee;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Feb 01, 2022.
 */

public class EmployeeImplementation {

    private static List<Employee> employeesList = new ArrayList<Employee>();

    // Candidate under the age 21 are not allowed
    public static boolean addData(String name, String department, double salary, String gender, String joiningDate, String dob, String jobLevel) throws Exception {

        DateTimeFormatter d1   = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate         adob = LocalDate.parse(dob, d1);

        if (Period.between(adob, LocalDate.now()).getYears() >= 21) {
            LocalDate joinDate = LocalDate.parse(joiningDate, d1);

            Employee e = new Employee(name, department, salary, gender, joinDate, adob, jobLevel);
            employeesList.add(e);
        } else throw new IllegalAccessException("    Candidate below 21 years cannot be taken as Employee");
        return true;
    }


    public static List<Employee> getEmployeeData() throws Exception {
        EmployeeImplementation.addData("marry", "cs", 260000, "female", "15/10/2020", "15/04/2000", "junior");
        addData("rose", "ee", 250000, "female", "25/12/2015", "21/06/1993", "mid");
        addData("julie", "ec", 350000, "female", "12/07/2014", "10/03/1993", "mid");
        addData("tony", "cs", 250000, "male", "04/04/2010", "23/08/1989", "mid");
        addData("kail", "cs", 450000, "female", "28/12/2012", "10/09/1990", "mid");
        addData("kate", "ee", 450000, "female", "01/10/2015", "26/01/1994", "junior");
        addData("joe", "ee", 250000, "male", "01/12/2021", "25/10/2000", "junior");
        addData("anthony", "ec", 380000, "male", "12/05/2019", "21/03/1995", "junior");
        addData("andrew", "ec", 300000, "male", "26/06/2021", "19/07/1997", "junior");
        addData("swift", "cs", 500000, "female", "12/07/2020", "02/05/1996", "junior");
        addData("cooper", "ee", 470000, "male", "18/10/2020", "05/05/1996", "junior");
        addData("hailey", "cs", 420000, "female", "09/11/2016", "21/02/1994", "mid");
        addData("mary", "cs", 260000, "female", "15/10/2021", "15/04/2000", "junior");

        return employeesList;
    }


    //No of employees by given department
    public static NoOfEmpByDeptContainer getNoOfEmployeesByDept(List<Employee> employeeList, String dept) {

        return new NoOfEmpByDeptContainer(dept, employeeList.stream().filter(i -> i.getDepartment() == dept).count());
    }

    //Group employees by department
    public static Map<String, Long> getGroupByDept(List<Employee> employeeList) {

        List<String>      distinctDept = employeeList.stream().map(i -> i.getDepartment()).distinct().collect(Collectors.toList());
        Map<String, Long> m1           = new TreeMap<String, Long>();
        distinctDept.stream().forEach(i -> {
            m1.put(i, employeeList.stream().filter(k -> i == k.getDepartment()).count());
        });

        return m1;
    }

    //Increase salary for given department
    public static Map<String, Double> getIncreasedSalary(List<Employee> employeeList, String dept) {
        Optional<String>    deptIfPresent     = Optional.ofNullable(dept);
        Map<String, Double> promotedEmployees = new TreeMap<String, Double>();
        if (deptIfPresent.isPresent()) {
            employeeList.stream().filter(f -> f.getDepartment() == (deptIfPresent.get())).forEach(m -> {
                m.setSalary(m.getSalary() + (m.getSalary() * 0.15));
                promotedEmployees.put(m.getName(), m.getSalary());
            });
        }
        return promotedEmployees;
    }

    //Promote employees based on experience
    public static Map<String, String> getPromotedEmployees(List<Employee> employeeList) {
        Map<String, String> promotedEmployees = new TreeMap<String, String>();
        List<Employee>      promoteList       = employeeList.stream().filter(f -> Period.between(f.getJoiningDate(), LocalDate.now()).getYears() >= 8).collect(Collectors.toList());
        promoteList.stream().forEach(i -> {
            i.setJobLevel("senior");
            promotedEmployees.put(i.getName(), i.getJobLevel());
        });
        return promotedEmployees;
    }
}