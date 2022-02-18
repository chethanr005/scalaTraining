package com.chethan.assignment2.student;

/**
 * Created by Chethan on Feb 03, 2022.
 */

public class StudentsNotAppearedContainer {
    private final String studentName;
    private final String registrationCode;

    StudentsNotAppearedContainer(String studentName, String registrationCode) {
        this.studentName = studentName;
        this.registrationCode = registrationCode;
    }

    @Override
    public String toString() {
        return "studentName=" + studentName + ", " + "registrationCode=" + registrationCode;
    }
}
