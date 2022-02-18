package com.chethan.assignment3.student;

//Students grouped based on GPA
public class PerformanceContainer {
    private String level;
    private long   students;

    PerformanceContainer(String level, long students) {
        this.level = level;
        this.students = students;
    }

    //Getters
    public String getLevel() {
        return level;
    }

    public long getStudents() {
        return students;
    }

}
