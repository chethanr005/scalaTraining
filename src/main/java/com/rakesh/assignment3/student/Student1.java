package com.rakesh.assignment3.student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakesh on Feb 14, 2022.
 */

public class Student1 {

    private int    regNo;
    private String name;
    private int    gradeLevel;
    private double gpa;
    private String gender;
    List<String> activities = new ArrayList<>();

    public Student1(int regNo, String name, int gradeLevel, double gpa, String gender, List<String> activities) {
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
}


