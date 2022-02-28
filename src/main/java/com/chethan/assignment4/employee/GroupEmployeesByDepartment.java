package com.chethan.assignment4.employee;

import java.util.List;
import java.util.Objects;

//Group employees by department
public class GroupEmployeesByDepartment {

    private String         dept;
    private List<Employee> employees;

    GroupEmployeesByDepartment(String dept, List<Employee> employees) {
        this.employees = employees;
        this.dept = dept;
    }

    //Getters
    public String getDept() {
        return dept;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public boolean equals(GroupEmployeesByDepartment g) {
        System.out.println();
        return dept.equals(g.dept) && employees == g.employees;
    }

    @Override
    public String toString() {
        return dept + " " + employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupEmployeesByDepartment that = (GroupEmployeesByDepartment) o;
        return Objects.equals(dept, that.dept) && Objects.equals(employees, that.employees);
    }
}

