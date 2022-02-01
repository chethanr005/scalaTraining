package com.rakesh.assignment1.student;

import java.util.Arrays;
import java.util.List;

public class StudentDataBase {


    public static List<Student> getAllStudents(){

        Student student1 = new Student("Ajay",1,6.7, "male",Arrays.asList("basketball","cricket"));
        Student student2 = new Student("Jessi",4,7.8,"female", Arrays.asList("painting","soccer"));

        Student student3 = new Student("Emily",4,8.0,"female", Arrays.asList("painting", "chess","dancing"));
        Student student4 = new Student("Dave",6,3.9,"male", Arrays.asList("swimming","soccer"));

        Student student5 = new Student("Sophia",7,4.5,"female", Arrays.asList("swimming", "dancing","painting"));
        Student student6 = new Student("James",7,3.9,"male", Arrays.asList("basketball","cricket"));

        Student student7 = new Student("Sunil",8,8.5,"male", Arrays.asList("swimming", "dancing","soccer"));
        Student student8 = new Student("John",8,6.9,"male", Arrays.asList("painting", "basketball","soccer"));

        Student student9 = new Student("Rohit",9,5.5,"male", Arrays.asList("cricket","soccer"));
        Student student10 = new Student("Edwin",10,8.9,"male", Arrays.asList("painting", "chess","soccer"));

        List<Student> students = Arrays.asList(student1,student2,student3,student4,student5,student6,student7,student8,student9,student10);
        return students;
    }

}
