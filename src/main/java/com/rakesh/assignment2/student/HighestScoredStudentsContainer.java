package com.rakesh.assignment2.student;

/**
 * Created by Rakesh on Feb 03, 2022.
 */

public class HighestScoredStudentsContainer {
    int    standard;
    String studentName;

    public HighestScoredStudentsContainer(int standard, String studentName) {
        this.standard = standard;
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "{ standard=" + standard +
                " studentName='" + studentName + '\'' + "}";
    }
}
