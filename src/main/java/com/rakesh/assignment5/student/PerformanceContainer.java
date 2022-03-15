package com.rakesh.assignment5.student;

import java.util.List;

/**
 * Created by Rakesh on Mar 10, 2022.
 */

public class PerformanceContainer {
    public String        level;
    public List<Student> studentList;

    public PerformanceContainer(String level, List<Student> studentList) {
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
