package com.rakesh.assignment1.student;

import java.util.List;

public class Output {
    public static List<Student> listOfStudents= StudentDataBase.getAllStudents();
    public static void main(String[] args) {

        MaleAndFemaleContainer result=new MaleAndFemaleContainer();
        GradeLevelContainer glc=new GradeLevelContainer();
        ActivityContainer ac=new ActivityContainer();
        PerformanceContainer pl=new PerformanceContainer();


        System.out.println("Male count : "+result.maleCount(listOfStudents));
        System.out.println();
        System.out.println("Female count : "+result.femaleCount(listOfStudents));
        System.out.println();
        System.out.println("List of Students name : "+result.studentNames(listOfStudents));
        System.out.println();
        System.out.println("Students count based on Grade-level : "+glc.gradeLevelCount(listOfStudents));
        System.out.println();
        System.out.println("Activity Count List : "+ac.activityCount(listOfStudents));
        System.out.println();
        System.out.println("Performance List Count : "+pl.performanceList(listOfStudents));


    }
}
