package com.chethan.assignment1.student;

import java.util.*;
import java.util.stream.Collectors;

//No of students participating in activity
public class ActivityContainer {

   Map<String, Long> activityList(List<Student> l){

       Map<String, Long> m1= new TreeMap<String,Long>();
        List<String> l3=l.stream().map(i -> i.getActivities()).flatMap(k->k.stream()).distinct().collect(Collectors.toList());
        l3.stream().forEach(i ->{  m1.put(i, l.stream().filter(k -> k.getActivities().contains(i)).count());});
        return m1;

    }
    public static void main(String[] args) {
        Student s= new Student ();
       ActivityContainer a=new ActivityContainer();
        System.out.println( a.activityList(s.studentData()));

    }
}
