package com.kishor.assignment4.employee;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by Kishor on Feb 28, 2022.
 */

public class Employee {
    private int       empId;
    private String    name;
    private String    department;
    private double    salary;
    private String    gender;
    private LocalDate joiningDate;
    private LocalDate dob; // date of birth
    private String    jobLevel;

    public Employee(int empId, String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel) {
        this.empId = empId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.gender = gender;
        this.joiningDate = joiningDate;
        this.dob = dob;
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
                ", Department= " + department +
                ", Salary= " + salary +
                ", Gender='" + gender + '\'' +
                ", JoiningDate= " + joiningDate +
                ", Dob= " + dob +
                ", JobLevel= " + jobLevel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return empId == employee.empId && Double.compare(employee.salary, salary) == 0 && Objects.equals(name, employee.name) && Objects.equals(department, employee.department) && Objects.equals(gender, employee.gender) && Objects.equals(joiningDate, employee.joiningDate) && Objects.equals(dob, employee.dob) && Objects.equals(jobLevel, employee.jobLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, name, department, salary, gender, joiningDate, dob, jobLevel);
    }
}
