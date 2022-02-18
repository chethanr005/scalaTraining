package com.chethan.assignment2.student;

/**
 * Created by Chethan on Feb 04, 2022.
 */

/**
 * 6. List of "award winning" students
 */
public class AwardWinningStudentsContainer {

    private final String studentName;
    private final int    subjectId;
    private final int    marks;

    AwardWinningStudentsContainer(String studentName, int subjectId, int marks) {
        this.studentName = studentName;
        this.subjectId = subjectId;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "studentName=" + studentName + ", " + "subjectId=" + subjectId + ", " + "marks=" + marks;
    }
}
