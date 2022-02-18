package com.rakesh.assignment2.student;

/**
 * Created by Rakesh on Feb 03, 2022.
 */

public class AwardWinnerContainer {
    String subject;
    String name;
    String regNo;
    int    marks;

    public AwardWinnerContainer(String subject, String name, String regNo, int marks) {
        this.subject = subject;
        this.name = name;
        this.regNo = regNo;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "{ " +
                "subject:" + subject +
                ", name:" + name +
                ", regNo:" + regNo +
                ", marks:" + marks +
                " }";
    }
}
