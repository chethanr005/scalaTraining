package com.chethan.assignment1.employee;

//Promote employees based on experience
public class PromoteEmployeesContainer {

    private String name;
    private String jobLevel;

    PromoteEmployeesContainer(String name, String jobLevel) {
        this.name = name;
        this.jobLevel = jobLevel;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getJobLevel() {
        return jobLevel;
    }

}
