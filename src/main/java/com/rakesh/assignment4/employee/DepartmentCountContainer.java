package com.rakesh.assignment4.employee;

/**
 * Created by Rakesh on Feb 25, 2022.
 */

public class DepartmentCountContainer {
    String department;
    int    empCount;

    DepartmentCountContainer(String department, long empCount) {
        this.department = department;
        this.empCount = (int) empCount;
    }

    @Override
    public String toString() {
        return "{" +
                "\'" + department + '\'' +
                " : " + empCount +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        DepartmentCountContainer check = (DepartmentCountContainer) obj;
        return this.empCount == check.empCount && this.department.equals(check.department);
    }
}
