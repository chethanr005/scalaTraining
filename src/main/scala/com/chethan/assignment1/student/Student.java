package com.chethan.assignment1.student;

import java.util.*;

public class Student {
    private String name;
    private double gpa;
    private String gender;
    private int gradeLevel;
    private List<String> activities=new ArrayList<String>();


    //Setters
    private void setName(String name){
        this.name=name;
    }
    private void setGpa(double gpa){this.gpa=gpa;}
    private void setGender(String gender){
        this.gender=gender;
    }
    private void setGradeLevel(int gradeLevel){
        this.gradeLevel=gradeLevel;
    }
    private void setActivities(List<String> activities){
        this.activities=activities;
    }

    //Getters
    public String getName(){
        return name;
    }
    public double getGpa(){
        return gpa;
    }
    public String getGender(){
        return gender;
    }
    public int getGradeLevel(){
        return gradeLevel;
    }
    public List<String> getActivities(){
        return activities;
    }

    Student(String name, double gpa, String gender, int gradeLevel, List<String> activities){
        this.name=name;
        this.gpa=gpa;
        this.gender=gender;
        this.gradeLevel=gradeLevel;
        this.activities=activities;
    }

    Student(){}
   private static List<Student> students = Arrays.asList(new Student("rose", 7.5, "female", 10, Arrays.asList("swimming", "cycling")),
                new Student("julie", 7.0, "female", 9, Arrays.asList("hiking", "running")),
                new Student("tony", 3.6, "male", 8, Arrays.asList("travelling", "swimming", "walking")),
                new Student("kail", 6.1, "female", 10, Arrays.asList("hiking", "walking")),
                new Student("kate", 6.9, "female", 10, Arrays.asList("hiking", "cycling", "walking")),
                new Student("anthony", 8.1, "male", 10, Arrays.asList("swimming")),
                new Student("andrew", 5.4, "male", 8, Arrays.asList("cycling", "running")),
                new Student("swift", 9.1, "female", 9, Arrays.asList("hiking", "travelling", "walking")),
                new Student("cooper", 8.7, "male", 10, Arrays.asList("travelling", "cycling", "hiking", "walking")),
                new Student("hailey", 5.0, "female", 9, Arrays.asList("walking", "cycling")));


    List<Student> studentData(){
        return students;
    }

}
