package com.chethan.assignment1.student;

import java.util.*;
import java.util.stream.Collectors;

public class GradeLevelContainer {

        Map<String, Long> gradeLevelStudents() {
            List<Integer> l1 = Student.students().stream().map(i -> i.getGradeLevel()).distinct().collect(Collectors.toList());
            Map<String, Long> m1 = new TreeMap<String, Long>();
            l1.stream().forEach(i ->{m1.put("No of students in class "+i+" is ",Student.students().stream().filter(k-> i==k.getGradeLevel()).count());});
            return m1;
        }

    public static void main(String[] args) {
        GradeLevelContainer g= new GradeLevelContainer();
        System.out.println( g.gradeLevelStudents()  );





    }




}
