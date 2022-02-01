package com.chethan.assignment1.employee;

//Increase salary for given department
public class IncreaseSalaryContainer {

    private String name;
    private double salary;

    IncreaseSalaryContainer(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    //Getters
    public String getName() {
        return this.name;
    }

    public double getSalary() {
        return this.salary;
    }


}
