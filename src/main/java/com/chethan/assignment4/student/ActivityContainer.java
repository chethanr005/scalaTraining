package com.chethan.assignment4.student;


import java.util.Objects;

//No of students participating in activity
public class ActivityContainer {
    private final String activity;
    private final long   students;

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

    @Override
    public String toString() {
        return "activity= " + activity + " students= " + students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityContainer that = (ActivityContainer) o;
        return students == that.students && Objects.equals(activity, that.activity);
    }
}
