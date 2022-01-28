package com.chethan.assignment1.student;

import java.util.*;

//Students grouped based on GPA
public class PerformanceContainer {

    Map<String, Long> gradeLevelStudents(List<Student> l) {

        Map<String, Long> m1 = new TreeMap<String, Long>();
        m1.put("Poor", l.stream().filter(i -> 0<=i.getGpa() && i.getGpa()<= 4.0).count());
        m1.put("Average", l.stream().filter(i -> 4.1<=i.getGpa() && i.getGpa()<= 7.0).count());
        m1.put("Excellent", l.stream().filter(i -> 7.1<=i.getGpa()).count());
        return m1;

    }

    public static void main(String[] args) {
        Student s= new Student ();
        PerformanceContainer p=new PerformanceContainer();
        System.out.println( p.gradeLevelStudents(s.studentData()) );

    }
}
