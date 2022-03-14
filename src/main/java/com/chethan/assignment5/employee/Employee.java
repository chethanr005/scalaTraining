package com.chethan.assignment5.employee;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


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

    @JsonCreator
    protected Employee(@JsonProperty("eid") int id,
                       @JsonProperty("name")String name,
                       @JsonProperty("department")String department,
                       @JsonProperty("salary") double salary,
                       @JsonProperty("gender")String gender,
                       @JsonProperty("joiningDate")String joiningDate,
                       @JsonProperty("dob") String dob,
                       @JsonProperty("jobLevel")String jobLevel) {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Double.compare(employee.salary, salary) == 0 && Objects.equals(name, employee.name) && Objects.equals(department, employee.department) && Objects.equals(gender, employee.gender) && Objects.equals(joiningDate, employee.joiningDate) && Objects.equals(dob, employee.dob) && Objects.equals(jobLevel, employee.jobLevel);
    }
}
