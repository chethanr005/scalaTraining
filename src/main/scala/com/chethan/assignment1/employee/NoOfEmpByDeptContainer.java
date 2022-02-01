package com.chethan.assignment1.employee;

// No of Employees in Every Department
public class NoOfEmpByDeptContainer {

    private String dept;
    private long   employees;

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

}
