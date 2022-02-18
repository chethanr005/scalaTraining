package com.chethan.assignment3.student;


//No of students participating in activity
public class ActivityContainer {
    private String activity;
    private long   students;

    ActivityContainer(String activity, long students) {
        this.activity = activity;
        this.students = students;
    }

    //Getters
    public String getActivity() {
        return activity;
    }

    public long getStudents() {
        return students;
    }


}
