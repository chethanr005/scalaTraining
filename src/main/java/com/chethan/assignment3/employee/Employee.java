package com.chethan.assignment3.employee;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


//Employee
public class Employee {
    private int       id;
    private String    name;
    private String    department;
    private double    salary;
    private String    gender;
    private LocalDate joiningDate;
    private LocalDate dob; // date of birth
    private String    jobLevel; // date of birth


    protected Employee() {
    }

    protected Employee(int id, String name, String department, double salary, String gender, String joiningDate, String dob, String jobLevel) {

        DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.gender = gender;
        this.joiningDate = LocalDate.parse(joiningDate, d1);
        this.dob = LocalDate.parse(dob, d1);
        this.jobLevel = jobLevel;

    }

    //Setters
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

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }


    //Getters
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

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + department + " " + salary + " " + gender + " " + joiningDate + " " + dob + " " + jobLevel;
    }
}
