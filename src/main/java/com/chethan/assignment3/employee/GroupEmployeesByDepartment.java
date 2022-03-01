package com.chethan.assignment3.employee;

import java.util.Objects;

//Group employees by department
public class GroupEmployeesByDepartment {

    private String dept;
    private long   employees;

    GroupEmployeesByDepartment(String dept, long employees) {
        this.employees = employees;
        this.dept = dept;
    }

    //Getters
    public String getDept() {
        return dept;
    }

    public long getEmployees() {
        return employees;
    }

    public boolean equals(GroupEmployeesByDepartment g) {
        System.out.println();
        return dept.equals(g.dept)&& employees==g.employees;
    }

    @Override
    public String toString() {
        return  dept +" "+ employees ;
    }
}

