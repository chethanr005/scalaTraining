package com.chethan.assignment5.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Chethan on Feb 24, 2022.
 */

public class Student {
    private final String       id;
    private       String       name;
    private       double       gpa;
    private       String       gender;
    private       int          gradeLevel;
    private       List<String> activities = new ArrayList<String>();

    @JsonCreator
    Student(@JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("gpa") double gpa,
            @JsonProperty("gender") String gender,
            @JsonProperty("gradeLevel") int gradeLevel,
            @JsonProperty("activities") List<String> activities) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.gender = gender;
        this.gradeLevel = gradeLevel;
        this.activities = activities;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    //Getters

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + gpa + " " + gender + " " + gradeLevel + " " + activities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Double.compare(student.gpa, gpa) == 0 && gradeLevel == student.gradeLevel && Objects.equals(id, student.id) && Objects.equals(name, student.name) && Objects.equals(gender, student.gender) && Objects.equals(activities, student.activities);
    }

}


