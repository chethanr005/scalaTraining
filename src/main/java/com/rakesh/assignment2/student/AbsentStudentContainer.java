package com.rakesh.assignment2.student;

/**
 * Created by Rakesh on Feb 03, 2022.
 */

public class AbsentStudentContainer {
    String registrationCode;
    String name;
    int    subjectID;

    public AbsentStudentContainer(int subjectID, String name, String registrationCode) {
        this.registrationCode = registrationCode;
        this.name = name;
        this.subjectID = subjectID;
    }

    @Override
    public String toString() {
        return "{ subID:" + subjectID +
                " regNo:" + registrationCode +
                " name:" + name +
                '}';
    }
}
