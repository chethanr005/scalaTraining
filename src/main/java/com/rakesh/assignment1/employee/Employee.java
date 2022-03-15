package com.rakesh.assignment1.employee;

import java.time.LocalDate;

public class Employee {


    private String    name;
    private String    department;
    private double    salary;
    private String    gender;
    private LocalDate joiningDate;
    private LocalDate dob;
    private String    jobLevel;


    public Employee(String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.gender = gender;
        this.joiningDate = joiningDate;
        this.dob = dob;
        this.jobLevel = jobLevel;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    @Override
    public String toString() {
        return "( Name - '" + name + '\'' +
                " Department - '" + department + '\'' +
                " Salary - " + salary +
                " Gender - '" + gender + '\'' +
                " JoiningDate - " + joiningDate +
                " DOB - " + dob +
                " JobLevel - '" + jobLevel + '\'' +
                " )\n";
    }
}
