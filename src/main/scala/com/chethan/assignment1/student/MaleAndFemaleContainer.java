package com.chethan.assignment1.student;

public class MaleAndFemaleContainer {

     long male=Student.students().stream().filter(i -> i.getGender()=="male").count();

     long female=Student.students().stream().filter(i -> i.getGender()=="female").count();


    public static void main(String[] args) {


        MaleAndFemaleContainer m= new MaleAndFemaleContainer();
        System.out.println(  "No of Female Students = "+  m.female );
        System.out.println(  "No of Male Students = "  +  m.male   );



    }

}
