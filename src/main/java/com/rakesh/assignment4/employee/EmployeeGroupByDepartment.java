package com.rakesh.assignment4.employee;

import java.util.List;

/**
 * Created by Rakesh on Feb 25, 2022.
 */

public class EmployeeGroupByDepartment {
    String         department;
    List<Employee> employeeList;

    EmployeeGroupByDepartment(String department, List<Employee> employeeList) {
        this.department = department;
        this.employeeList = employeeList;
    }

    @Override
    public String toString() {
        return "{" +
                "'" + department + '\'' +
                " : " + employeeList +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        EmployeeGroupByDepartment check = (EmployeeGroupByDepartment) object;
        return this.department.equals(check.department) && this.employeeList.equals(check.employeeList);
    }

}
