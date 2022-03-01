package com.rakesh.assignment4.student;

/**
 * Created by Rakesh on Feb 25, 2022.
 */

public class ActivityContainer {
    String activity;
    int    studentCount;

    ActivityContainer(String activity, long studentCount) {
        this.activity = activity;
        this.studentCount = (int) studentCount;
    }

    @Override
    public boolean equals(Object o) {
        ActivityContainer obj = (ActivityContainer) o;
        return studentCount == obj.studentCount && this.studentCount == obj.studentCount;
    }

    @Override
    public String toString() {
        return "{" + activity + " : " + studentCount + '}';
    }
}
