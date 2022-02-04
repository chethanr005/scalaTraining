package com.kishor.assignment2.student;

/**
 * Created by Kishor on Feb 04, 2022.
 */

public class HighestMarksInEachSubjectContainer {
    int marks, subjectId;
    String studentName;

    public HighestMarksInEachSubjectContainer(int subjectId, int marks, String studentName) {
        this.marks = marks;
        this.studentName = studentName;
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return
                "studentName='" + studentName + '\'' +
                        ", marks=" + marks +
                        ", subjectId='" + subjectId + '\'';
    }
}
