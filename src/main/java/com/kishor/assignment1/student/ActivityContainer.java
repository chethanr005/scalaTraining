package com.kishor.assignment1.student;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityContainer that = (ActivityContainer) o;
        return Objects.equals(activity, that.activity) && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activity, students);
    }

    @Override
    public String toString() {
        return "{" +
                "activity='" + activity + '\'' +
                ", students=" + students +
                '}';
    }
}
