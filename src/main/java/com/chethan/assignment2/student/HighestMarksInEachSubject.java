package com.chethan.assignment2.student;

/**
 * Created by Chethan on Feb 03, 2022.
 */

/**
 * 5. Highest marks in each subject with name of students
 */
public class HighestMarksInEachSubject {
    private final int subjectId;
    private final int marks;
    private final String studentName;

    HighestMarksInEachSubject(int subjectId, int marks, String studentName) {
        this.subjectId = subjectId;
        this.marks = marks;
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "subjectId=" + subjectId + ", " + "marks=" + marks + ", " + "studentName=" + studentName;
    }
}
