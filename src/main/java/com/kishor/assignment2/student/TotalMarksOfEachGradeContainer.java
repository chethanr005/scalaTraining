package com.kishor.assignment2.student;

/**
 * Created by Kishor on Feb 17, 2022.
 */

public class TotalMarksOfEachGradeContainer {
    int standard;
    int totalMarks;

    public TotalMarksOfEachGradeContainer(int standard, int totalMarks) {
        this.standard = standard;
        this.totalMarks = totalMarks;
    }

    @Override
    public String toString() {
        return "{" +
                "Standard = " + standard +
                ", TotalMarks = " + totalMarks +
                '}';
    }
}
