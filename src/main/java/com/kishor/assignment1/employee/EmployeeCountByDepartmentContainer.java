package com.kishor.assignment1.employee;

import java.util.Objects;

/**
 * 2. Get no of employees by given department
 */
public class EmployeeCountByDepartmentContainer {
    public Long   employeeCount;
    public String department;

    public EmployeeCountByDepartmentContainer(String department, Long employeeCount) {
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
