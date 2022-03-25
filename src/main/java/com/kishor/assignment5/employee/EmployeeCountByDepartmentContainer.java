package com.kishor.assignment5.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public class EmployeeCountByDepartmentContainer {
    public Long   employeeCount;
    public String department;

    @JsonCreator
    public EmployeeCountByDepartmentContainer(@JsonProperty String department, @JsonProperty Long employeeCount) {
        this.department = department;
        this.employeeCount = employeeCount;
    }

    public Long getEmployee() {
        return employeeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeCountByDepartmentContainer that = (EmployeeCountByDepartmentContainer) o;
        return employeeCount.equals(that.employeeCount) && department.equals(that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeCount, department);
    }

    @Override
    public String toString() {
        return "{" + "Department = " + department +
                " , employeeCount = " + employeeCount +
                '}';
    }
}
