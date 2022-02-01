package com.kishor.assignment1.student;

import java.util.Collection;
import java.util.Set;

/**
 * 4. get no of students participating in each type of activity , Return result in class ActivityContainer(String activity, int students)
 */
public class ActivityContainer {
    Set<String>      activity;
    Collection<Long> students;

    ActivityContainer(Set<String> activity, Collection<Long> students) {
        this.activity = activity;
        this.students = students;
    }

    public Set<String> getActivity() {
        return activity;
    }

    public Collection<Long> getStudents() {
        return students;
    }
}
