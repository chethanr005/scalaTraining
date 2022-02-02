package com.chethan.assignment1.employee;

//Group employees by department
public class DepartmentGroup {

    private String dept;
    private int    employees;

    DepartmentGroup(String dept, int employees) {
        this.employees = employees;
        this.dept = dept;
    }

    //Getters
    public String getDept() {
        return dept;
    }

    public int getEmployees() {
        return employees;
    }

}
