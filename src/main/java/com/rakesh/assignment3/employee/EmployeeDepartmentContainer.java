package com.rakesh.assignment3.employee;

import java.util.Map;

public class EmployeeDepartmentContainer {
    long              employeeCount;
    Map<String, Long> getAllDepartmentCount;

    public EmployeeDepartmentContainer(Map<String, Long> getAllDepartmentCount) {
        this.getAllDepartmentCount = getAllDepartmentCount;
    }

    public EmployeeDepartmentContainer(long employeeCount) {
        this.employeeCount = employeeCount;
    }
}
