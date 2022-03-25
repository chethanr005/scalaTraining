package com.kishor.assignment5.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public class Student {
    private int    regNo;
    private String name;
    private int    gradeLevel;
    private double gpa;
    private String gender;
    List<String> activities = new ArrayList<>();

    @JsonCreator
    public Student(@JsonProperty("regNo") int regNo, @JsonProperty("name") String name, @JsonProperty("gradeLevel") int gradeLevel, @JsonProperty("gpa") double gpa, @JsonProperty("gender") String gender, @JsonProperty("activities") List<String> activities) {
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

    public int getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "Student { " +
                "Regno = " + regNo + ",\t" +
                "Name = " + name + ",\t" +
                "GradeLevel = " + gradeLevel + ",\t" +
                "Gpa = " + gpa + ",\t" +
                "Gender = " + gender + ",\t" +
                "Activities = " + activities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return regNo == student.regNo && gradeLevel == student.gradeLevel && Double.compare(student.gpa, gpa) == 0 && Objects.equals(name, student.name) && Objects.equals(gender, student.gender) && Objects.equals(activities, student.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regNo, name, gradeLevel, gpa, gender, activities);
    }
}
