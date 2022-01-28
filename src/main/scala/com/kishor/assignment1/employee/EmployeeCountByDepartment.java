package com.kishor.assignment1.employee;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 2. Get no of employees by given department
 */
public class EmployeeCountByDepartment {
    public Map<String, Long> noOfEmpByCount(List<Employee> employees) {
        Map<String, Long> grpByDept = employees.stream().collect(Collectors.groupingBy(c -> c.getDepartment(), Collectors.counting()));
        return grpByDept;
    }
}
