package com.chethan.assignment2.student;

/**
 * Created by Chethan on Feb 03, 2022.
 */

/**
 * 3. Get the students who scored highest in given subject
 */

public class HighestScoreInSubjectContainer {
    private final int    standard;
    private final String studentName;

    HighestScoreInSubjectContainer(int standard, String studentName) {
        this.standard = standard;
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "standard=" + standard + ", " + "studentName=" + studentName;
    }
}
