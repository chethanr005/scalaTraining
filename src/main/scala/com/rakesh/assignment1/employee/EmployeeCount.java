package com.rakesh.assignment1.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeCount {
    public Map<String, Long> DepartmentCount(List<Employee> employeeList)
    {

        Map<String, Long> deptcount = new HashMap<>();

        List<String> dept = employeeList.stream()
                .map(Employee::getDepartment).distinct().collect(Collectors.toList());


        for (int i = 0; i < dept.size(); i++) {
            int k = i;
            long c = 0;
            c =employeeList.stream()
                    .filter(s -> s.getDepartment().equals(dept.get(k))).count();
            deptcount.put(dept.get(k), c);
        }
        return deptcount;
    }
}
