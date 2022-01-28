package com.chethan.assignment1.student;

import java.util.*;
import java.util.stream.Collectors;

//  No of students according to grade level
public class GradeLevelContainer {

        Map<String, Long> gradeLevelStudents(List<Student> l) {
            List<Integer> l1 = l.stream().map(i -> i.getGradeLevel()).distinct().collect(Collectors.toList());
            Map<String, Long> m1 = new TreeMap<String, Long>();
            l1.stream().forEach(i ->{m1.put("No of students in class "+i+" is ",l.stream().filter(k-> i==k.getGradeLevel()).count());});
            return m1;
        }

    public static void main(String[] args) {
            Student s= new Student();
        GradeLevelContainer g= new GradeLevelContainer();
        System.out.println( g.gradeLevelStudents(s.studentData())  );

    }

}
