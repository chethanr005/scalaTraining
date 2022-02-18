package com.kishor.assignment1.student;

/**
 * 4. get no of students participating in each type of activity , Return result in class ActivityContainer(String activity, int students)
 */
public class ActivityContainer {
    String activity;
    Long   students;

    ActivityContainer(String activity, Long students) {
        this.activity = activity;
        this.students = students;
    }

    public String getActivity() {
        return activity;
    }

    public Long getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "{" +
                "activity='" + activity + '\'' +
                ", students=" + students +
                '}';
    }
}
