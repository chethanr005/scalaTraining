package com.rakesh.assignment5.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakesh on Mar 08, 2022.
 */

public class Student {
    private int    regNo;
    private String name;
    private int    gradeLevel;
    private double gpa;
    private String gender;
    List<String> activities = new ArrayList<>();

    @JsonCreator
    public Student(@JsonProperty("regNO") int regNo, @JsonProperty("name") String name, @JsonProperty("gradeLevel") int gradeLevel, @JsonProperty("gpa") double gpa, @JsonProperty("gender") String gender,
                   @JsonProperty("activities") List<String> activities) {
        this.regNo = regNo;
        this.name = name;
        this.gradeLevel = gradeLevel;
        this.gpa = gpa;
        this.gender = gender;
        this.activities = activities;
    }

    public int getRegNo() {
        return regNo;
    }

    public String getName() {
        return name;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public double getGpa() {
        return gpa;
    }

    public String getGender() {
        return gender;
    }

    public List<String> getActivities() {
        return activities;
    }

    @Override
    public String toString() {
        return "(" +
                "regNo:" + regNo +
                ", name:'" + name + '\'' +
                ", gradeLevel:" + gradeLevel +
                ", gpa:" + gpa +
                ", gender:'" + gender + '\'' +
                ", activities:" + activities +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        Student student = (Student) o;
        return regNo == student.getRegNo() && gradeLevel == student.getGradeLevel()
                && name.equals(student.getName()) && gpa == student.getGpa() && gender.equals(student.getGender()) && this.activities.equals(student.getActivities());
    }

}
