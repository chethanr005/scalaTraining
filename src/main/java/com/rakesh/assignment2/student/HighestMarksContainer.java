package com.rakesh.assignment2.student;

/**
 * Created by Rakesh on Feb 03, 2022.
 */

public class HighestMarksContainer {
    String subject;
    String name;
    int    marks;

    public HighestMarksContainer(String subject, String name, int marks) {
        this.subject = subject;
        this.marks = marks;
        this.name = name;
    }

    @Override
    public String toString() {
        return "{ subject:'" + subject + '\'' +
                ", name:'" + name + '\'' +
                ", marks:" + marks + '}';
    }
}
