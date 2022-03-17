package com.kishor.assignment5.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public class Employee {
    public int       empId;
    public String    name;
    public String    department;
    public double    salary;
    public String    gender;
    public LocalDate joiningDate;
    public LocalDate dob; // date of birth
    public String    jobLevel;


    @JsonCreator
    public Employee(@JsonProperty("EmpId") int empId, @JsonProperty("Name") String name, @JsonProperty("Department") String department, @JsonProperty("Salary") double salary, @JsonProperty("Gender") String gender,
                    @JsonProperty("JoiningDate") String joiningDate, @JsonProperty("Dob") String dob, @JsonProperty("JobLevel") String jobLevel) {
        this.empId = empId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.gender = gender;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.joiningDate = LocalDate.parse(joiningDate, formatter);
        this.dob = LocalDate.parse(dob, formatter);
        this.jobLevel = jobLevel;
    }

    public int getEmpId() {
        return empId;
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
                "EmpId = " + empId +
                ", Name= '" + name + '\'' +
                ", Department= '" + department + '\'' +
                ", Salary= " + salary +
                ", Gender='" + gender + '\'' +
                ", JoiningDate= '" + joiningDate + '\'' +
                ", Dob= '" + dob + '\'' +
                ", JobLevel= '" + jobLevel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return empId == employee.empId && Double.compare(employee.salary, salary) == 0 && Objects.equals(name, employee.name) && Objects.equals(department, employee.department) && Objects.equals(gender, employee.gender) && Objects.equals(joiningDate, employee.joiningDate) && Objects.equals(dob, employee.dob) && Objects.equals(jobLevel, employee.jobLevel);
    }

}
