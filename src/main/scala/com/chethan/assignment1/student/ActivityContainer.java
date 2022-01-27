package com.chethan.assignment1.student;

import java.util.*;
import java.util.stream.Collectors;


public class ActivityContainer {

   Map<String, Long> activityList(){

       Map<String, Long> m1= new TreeMap<String,Long>();
        List<String> l3=Student.students().stream().map(i -> i.getActivities()).flatMap(k->k.stream()).distinct().collect(Collectors.toList());
        l3.stream().forEach(i ->{  m1.put(i, Student.students().stream().filter(k -> k.getActivities().contains(i)).count());});
        return m1;

    }
    public static void main(String[] args) {

       ActivityContainer a=new ActivityContainer();
        System.out.println( a.activityList()  );



    }
}
