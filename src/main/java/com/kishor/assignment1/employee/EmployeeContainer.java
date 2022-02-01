package com.kishor.assignment1.employee;

import java.time.LocalDate;

public class EmployeeContainer {
    private String    name;
    private String    department;
    private double    salary;
    private String    gender;
    private LocalDate joiningDate;
    private LocalDate dob; // date of birth
    private String    jobLevel; // date of birth

    public EmployeeContainer(String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.gender = gender;
        this.joiningDate = joiningDate;
        this.dob = dob;
        this.jobLevel = jobLevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name= '" + name + '\'' +
                ", department= " + department +
                ", salary= " + salary +
                ", gender='" + gender + '\'' +
                ", joiningDate= " + joiningDate +
                ",Dob= " + dob +
                ",jobLevel= " + jobLevel +
                '}';
    }
}
