package com.chethan.assignment1.student;

import java.util.Map;
import java.util.TreeMap;

public class PerformanceContainer {

    Map<String, Long> gradeLevelStudents() {

        Map<String, Long> m1 = new TreeMap<String, Long>();
        m1.put("Poor", Student.students().stream().filter(i -> 0<=i.getGpa() && i.getGpa()<= 4.0).count());
        m1.put("Average", Student.students().stream().filter(i -> 4.1<=i.getGpa() && i.getGpa()<= 7.0).count());
        m1.put("Excellent", Student.students().stream().filter(i -> 7.1<=i.getGpa()).count());
        return m1;

    }

    public static void main(String[] args) {
        PerformanceContainer p=new PerformanceContainer();
        System.out.println( p.gradeLevelStudents() );

    }
}
