package com.kishor.assignment1.Employee;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeCountByDepartment {
    public Map<String, Long> noOfEmpByCount(List<Employee> employees) {
        Map<String, Long> grpByDept = employees.stream().collect(Collectors.groupingBy(c-> c.getDepartment(),Collectors.counting()));
        return grpByDept;
    }
}
