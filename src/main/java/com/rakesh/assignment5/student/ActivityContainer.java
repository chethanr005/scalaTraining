package com.rakesh.assignment5.student;

/**
 * Created by Rakesh on Mar 10, 2022.
 */

public class ActivityContainer {
    public String activity;
    public int    studentCount;

    public ActivityContainer(String activity, long studentCount) {
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
