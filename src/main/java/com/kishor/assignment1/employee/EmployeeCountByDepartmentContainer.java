package com.kishor.assignment1.employee;

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
    public String toString() {
        return "{" + "Department = " + department +
                " , employeeCount = " + employeeCount +
                '}';
    }
}
