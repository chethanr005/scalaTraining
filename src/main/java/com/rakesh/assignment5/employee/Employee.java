package com.rakesh.assignment5.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

/**
 * Created by Rakesh on Mar 14, 2022.
 */

public class Employee {
    private int       empID;
    private String    name;
    private String    department;
    private double    salary;
    private String    gender;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate joiningDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dob;
    private String    jobLevel;

    @JsonCreator
    public Employee(@JsonProperty("empID") int empID, @JsonProperty("name") String name, @JsonProperty("department") String department, @JsonProperty("salary") double salary
            , @JsonProperty("gender") String gender, @JsonProperty("joiningDate") LocalDate joiningDate, @JsonProperty("dob") LocalDate dob, @JsonProperty("jobLevel") String jobLevel) {
        this.empID = empID;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.gender = gender;
        this.joiningDate=joiningDate;
        this.dob =dob;
        this.jobLevel = jobLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
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
                "}";
    }

    public boolean equals(Object obj) {
        Employee check = (Employee) obj;
        return this.empID == check.getEmpID() && this.name.equals(check.getName())
                && this.department.equals(check.getDepartment()) && this.salary == check.getSalary()
                && this.gender.equals(check.getGender()) && this.joiningDate.equals(check.getJoiningDate())
                && this.dob.equals(check.getDob()) && this.jobLevel.equals(check.getJobLevel());
    }
}
