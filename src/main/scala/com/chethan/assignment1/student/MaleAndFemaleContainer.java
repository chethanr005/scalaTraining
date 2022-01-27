package com.chethan.assignment1.student;

import java.util.List;


public class MaleAndFemaleContainer {

     long male(List<Student> l){
       return l.stream().filter(i -> i.getGender()=="male").count();
     }

     long female(List<Student> l) {
         return l.stream().filter(i -> i.getGender() == "female").count();
     }

    public static void main(String[] args) {

        Student s= new Student();
        MaleAndFemaleContainer m= new MaleAndFemaleContainer();
        System.out.println(  "No of Female Students = "+  m.female(s.studentData()));
        System.out.println(  "No of Male Students = "  +  m.male(s.studentData()));

    }

}
