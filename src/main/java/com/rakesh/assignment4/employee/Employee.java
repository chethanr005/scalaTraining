package com.rakesh.assignment4.employee;

import java.time.LocalDate;

/**
 * Created by Rakesh on Feb 25, 2022.
 */

public class Employee {
    private int       empID;
    private String    name;
    private String    department;
    private double    salary;
    private String    gender;
    private LocalDate joiningDate;
    private LocalDate dob;
    private String    jobLevel;

    public Employee(int empID, String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel) {
        this.empID = empID;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.gender = gender;
        this.joiningDate = joiningDate;
        this.dob = dob;
        this.jobLevel = jobLevel;
    }


    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public int getEmpID() {
        return empID;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    @Override
    public String toString() {
        return "{" +
                "empID:" + empID +
                ", name:'" + name + '\'' +
                ", department:'" + department + '\'' +
                ", salary:" + salary +
                ", gender:'" + gender + '\'' +
                ", joiningDate:" + joiningDate +
                ", dob:" + dob +
                ", jobLevel:'" + jobLevel + '\'' +
                '}';
    }

    public boolean equals(Object obj) {
        Employee check = (Employee) obj;
        return this.empID == check.getEmpID() && this.name.equals(check.name)
                && this.department.equals(check.department) && this.salary == check.salary
                && this.gender.equals(check.gender) && this.joiningDate.equals(check.joiningDate)
                && this.dob.equals(check.dob) && this.jobLevel.equals(check.jobLevel);
    }
}
