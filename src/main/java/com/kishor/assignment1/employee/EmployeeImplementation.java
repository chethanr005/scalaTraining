package com.kishor.assignment1.employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by Kishor on Feb 01, 2022.
 */

public class EmployeeImplementation {

    public static EmployeeContainer addEmployee(String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel) {
        int p = Period.between(dob, LocalDate.now()).getYears();
        if (p > 21) {
            return new EmployeeContainer(name, department, salary, gender, joiningDate, dob, jobLevel);
        } else {
            return null;
        }
    }

    public static EmployeeCountByDepartmentContainer noOfEmpByCount(List<EmployeeContainer> employeeContainers, String dept) {
        Long countOFDept = employeeContainers.stream().filter(f -> f.getDepartment() == dept).count();
        return new EmployeeCountByDepartmentContainer(countOFDept);
    }

    public static GroupByDepartmentContainer grpByDept(List<EmployeeContainer> employeeContainers) {
        Map<String, List<EmployeeContainer>> depts = employeeContainers.stream()
                                                                       .collect(Collectors.groupingBy(EmployeeContainer::getDepartment));
        Map<String, List<String>> grpByDepts = new HashMap<>();
        depts.forEach((k, v) -> grpByDepts.put(k, v.stream().map(m -> m.getName()).collect(toList())));
        return new GroupByDepartmentContainer(grpByDepts);
    }

    public static IncreaseSalaryContainer increaseSalaryForDept(List<EmployeeContainer> employeeContainers, String s) {
        //Optional<String>        dept             = Optional.ofNullable(s);
        Map<String, Double>     salIncresedNames = new HashMap<>();
        List<EmployeeContainer> empNamesOfDept   = employeeContainers.stream().filter(m -> m.getDepartment() == s).collect(Collectors.toList());
        for (EmployeeContainer i : empNamesOfDept) {
            increment(i);
            salIncresedNames.put(i.getName(), i.getSalary());
        }
        return new IncreaseSalaryContainer(salIncresedNames);
    }

    public static void increment(EmployeeContainer employeeContainer) {
        Double sal = employeeContainer.getSalary();
        employeeContainer.setSalary(sal + 5000L);
    }

    public static PromoteEmployeeContainer promoteEmployee(List<EmployeeContainer> employeeContainers) {
        List<EmployeeContainer> emp            = employeeContainers.stream().filter(f -> (Period.between(f.getJoiningDate(), LocalDate.now()).getYears()) > 8).collect(Collectors.toList());
        Map<String, String>     promoteEmpName = new HashMap<>();
        for (EmployeeContainer i : emp) {
            promote(i);
            promoteEmpName.put(i.getName(), i.getJobLevel());
        }
        return new PromoteEmployeeContainer(promoteEmpName);
    }

    public static void promote(EmployeeContainer employeeContainer) {
        employeeContainer.setJobLevel("Senior");
    }

}
