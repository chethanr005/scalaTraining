package com.chethan.assignment1.student;

import java.util.ArrayList;
import java.util.List;

//Student Database
public class Student {
    private String       name;
    private double       gpa;
    private String       gender;
    private int          gradeLevel;
    private List<String> activities = new ArrayList<String>();


    //Setters
    private void setName(String name) {
        this.name = name;
    }

    private void setGpa(double gpa) {
        this.gpa = gpa;
    }

    private void setGender(String gender) {
        this.gender = gender;
    }

    private void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    private void setActivities(List<String> activities) {
        this.activities = activities;
    }

    //Getters
    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public String getGender() {
        return gender;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public List<String> getActivities() {
        return activities;
    }

    Student(String name, double gpa, String gender, int gradeLevel, List<String> activities) {
        this.name = name;
        this.gpa = gpa;
        this.gender = gender;
        this.gradeLevel = gradeLevel;
        this.activities = activities;
    }

}





