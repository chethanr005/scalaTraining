package com.chethan.assignment7.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * Created by Chethan on Mar 30, 2022.
 */

public class Student {

    private String       id;
    private String       name;
    private Double       gpa;
    private String       gender;
    private Integer      gradeLevel;
    private List<String> activities;

    @JsonCreator
    public Student(@JsonProperty("id") String id,
                   @JsonProperty("name") String name,
                   @JsonProperty("gpa") Double gpa,
                   @JsonProperty("gender") String gender,
                   @JsonProperty("gradeLevel") Integer gradeLevel,
                   @JsonProperty("activities") List<String> activities) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.gender = gender;
        this.gradeLevel = gradeLevel;
        this.activities = activities;
    }

    //Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    //Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getGpa() {
        return gpa;
    }

    public String getGender() {
        return gender;
    }

    public Integer getGradeLevel() {
        return gradeLevel;
    }

    public List<String> getActivities() {
        return activities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name) && Objects.equals(gpa, student.gpa) && Objects.equals(gender, student.gender) && Objects.equals(gradeLevel, student.gradeLevel) && Objects.equals(activities, student.activities);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gpa=" + gpa +
                ", gender='" + gender + '\'' +
                ", gradeLevel=" + gradeLevel +
                ", activities=" + activities +
                '}';
    }
}
