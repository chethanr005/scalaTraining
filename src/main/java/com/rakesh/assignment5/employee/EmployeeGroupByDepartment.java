package com.rakesh.assignment5.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Rakesh on Mar 14, 2022.
 */

public class EmployeeGroupByDepartment {
    public String         department;
    public List<Employee> employeeList;

    @JsonCreator
    public EmployeeGroupByDepartment(@JsonProperty String department, @JsonProperty List<Employee> employeeList) {
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
