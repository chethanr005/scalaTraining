package com.kishor.assignment2.student;

/**
 * Created by Kishor on Feb 04, 2022.
 */

public class AwardWinningStudentCOntainer {
    String studentName;
    int    marks, subjectId;

    AwardWinningStudentCOntainer(String studentName, int subjectId, int marks) {
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
