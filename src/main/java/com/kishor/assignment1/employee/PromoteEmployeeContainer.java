package com.kishor.assignment1.employee;


/**
 * 5. promote employees having 8 years experience to Senior position
 */
public class PromoteEmployeeContainer {
    String empName;
    String jobLevel;

    public PromoteEmployeeContainer(String empName, String jobLevel) {
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
    public String toString() {
        return "{" +
                "empName=" + empName +
                ", jobLevel='" + jobLevel + '\'' +
                '}';
    }
}
