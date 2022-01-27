package com.rakesh.assignment1.Student;

import java.util.List;

public class Output {
    public static List<Student> ListOfStudents= StudentDataBase.getAllStudents();
    public static void main(String[] args) {

        MaleAndFemaleContainer result=new MaleAndFemaleContainer();
        GradeLevelContainer glc=new GradeLevelContainer();
        ActivityContainer ac=new ActivityContainer();
        PerformanceContainer pl=new PerformanceContainer();


        System.out.println("Male count : "+result.MaleCount(ListOfStudents));
        System.out.println();
        System.out.println("Female count : "+result.FemaleCount(ListOfStudents));
        System.out.println();
        System.out.println("List of Students name : "+result.StudentNames(ListOfStudents));
        System.out.println();
        System.out.println("Students count based on Grade-level : "+glc.GradeLevelCount(ListOfStudents));
        System.out.println();
        System.out.println("Activity Count List : "+ac.ActivityCount(ListOfStudents));
        System.out.println();
        System.out.println("Performance List Count : "+pl.PerformanceList(ListOfStudents));


    }
}
