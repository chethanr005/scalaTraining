package com.rakesh.assignment3.employee;

public class PromoteEmployeeContainer {

    public int    empID;
    public String empName;
    public String position;

    public PromoteEmployeeContainer(int empID, String empName, String position) {
        this.empID = empID;
        this.empName = empName;
        this.position = position;
    }

    @Override
    public String toString() {
        return "{" +
                "empID:" + empID +
                ", empName:'" + empName + '\'' +
                ", position:'" + position + '\'' +
                '}';
    }
}
