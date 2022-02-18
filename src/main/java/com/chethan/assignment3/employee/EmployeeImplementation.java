package com.chethan.assignment3.employee;

import java.time.*;
import java.util.*;
import java.util.stream.*;

/**
 * Created by Chethan on Feb 16, 2022.
 */

public class EmployeeImplementation {


    //No of employees by given department
    public static NoOfEmpByDeptContainer getNoOfEmployeesByDept(List<Employee> employeeList, String dept) {

        return new NoOfEmpByDeptContainer(dept, employeeList.stream().filter(i -> i.getDepartment().equals(dept)).count());
    }


    //Group employees by department
    public static List<GroupEmployeesByDepartment> getGroupByDept(List<Employee> employeeList) {

        List<String>      distinctDept = employeeList.stream().map(i -> i.getDepartment()).distinct().collect(Collectors.toList());
        Map<String, Long> m1           = new TreeMap<String, Long>();
        return distinctDept.stream().map(i -> new GroupEmployeesByDepartment(i,
                employeeList.stream().filter(k -> i.equals(k.getDepartment())).count())).collect(Collectors.toList());
    }

    //Increase salary for given department
    public static Map<String, Double> getIncreasedSalary(List<Employee> employeeList, Optional<String> dept, Optional<Double> hike) {
        Map<String, Double> promotedEmployees = new TreeMap<String, Double>();
        if (dept.isPresent()) {
            employeeList.stream().filter(f -> f.getDepartment().equals (dept.get())).forEach(m -> { EmployeeDatabase.updateStudentDetails(m.getId(), "salary", ""+(m.getSalary()+m.getSalary()*(hike.get()/100)));
                promotedEmployees.put(m.getName(), m.getSalary()+m.getSalary()*(hike.get()/100));
            });
        }
        return promotedEmployees;
    }

    //Promote employees based on experience
    public static Map<String, String> getPromotedEmployees(List<Employee> employeeList) {
        Map<String, String>                             promotedEmployees = new TreeMap<String, String>();
        List<Employee> promoteList       = employeeList.stream().filter(f -> Period.between(f.getJoiningDate(), LocalDate.now()).getYears() >= 8).collect(Collectors.toList());
        promoteList.stream().forEach(i -> {EmployeeDatabase.updateStudentDetails(i.getId(),"jobLevel","senior");
            promotedEmployees.put(i.getName(), i.getJobLevel());
        });
        return promotedEmployees;
    }
}
