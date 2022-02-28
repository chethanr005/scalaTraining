package com.kishor.assignment4.employee;

import java.util.Objects;

/**
 * Created by Kishor on Feb 28, 2022.
 */

public class GetPromoteEmployeeContainer {
    String empName;
    String jobLevel;

    public GetPromoteEmployeeContainer(String empName, String jobLevel) {
        this.empName = empName;
        this.jobLevel = jobLevel;
    }

    public void setEmpName(Integer empId) {
        this.empName = empName;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getEmpName() {
        return empName;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetPromoteEmployeeContainer that = (GetPromoteEmployeeContainer) o;
        return empName.equals(that.empName) && jobLevel.equals(that.jobLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empName, jobLevel);
    }

    @Override
    public String toString() {
        return "{" +
                "empName=" + empName +
                ", jobLevel='" + jobLevel + '\'' +
                '}';
    }
}
