package com.rakesh.assignment5.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Rakesh on Mar 14, 2022.
 */

public class DepartmentCountContainer {
    public String department;
    public int    empCount;

    @JsonCreator
    public DepartmentCountContainer(@JsonProperty String department, @JsonProperty long empCount) {
        this.department = department;
        this.empCount = (int) empCount;
    }

    @Override
    public String toString() {
        return "{" +
                "" + department +
                " : " + empCount +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        DepartmentCountContainer check = (DepartmentCountContainer) obj;
        return this.empCount == check.empCount && this.department.equals(check.department);
    }
}
