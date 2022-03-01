package com.rakesh.assignment4.student;

import java.util.List;

/**
 * Created by Rakesh on Feb 25, 2022.
 */

public class PerformanceContainer {
    String        level;
    List<Student> studentList;

    PerformanceContainer(String level, List<Student> studentList) {
        this.level = level;
        this.studentList = studentList;
    }

    @Override
    public boolean equals(Object o) {
        PerformanceContainer check = (PerformanceContainer) o;
        return this.level.equals(check.level) && this.studentList.equals(check.studentList);
    }

    @Override
    public String toString() {
        return "{" + level +
                " = " + studentList +
                '}';
    }
}
