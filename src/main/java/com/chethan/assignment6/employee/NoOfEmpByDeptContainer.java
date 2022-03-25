package com.chethan.assignment6.employee;

import java.util.Objects;

// No of Employees in Every Department
public class NoOfEmpByDeptContainer {

    private final String dept;
    private final long   employees;

    NoOfEmpByDeptContainer(String dept, long employees) {
        this.employees = employees;
        this.dept = dept;
    }

    //Getters
    public String getDept() {
        return this.dept;
    }

    public long getEmployees() {
        return this.employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoOfEmpByDeptContainer that = (NoOfEmpByDeptContainer) o;
        return employees == that.employees && Objects.equals(dept, that.dept);
    }

    @Override
    public String toString() {
        return "dept= " + dept + " employees= " + employees;
    }
}
