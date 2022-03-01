package com.rakesh.assignment3.student;

import java.util.Map;

//4. get no of students participating in each type of activity , Return result in class ActivityContainer(String activity, int students)

public class ActivityContainer {

    public long              getStudentCount;
    public Map<String, Long> getActivityMap;

    public ActivityContainer(long getStudentCount) {
        this.getStudentCount = getStudentCount;
    }

    public ActivityContainer(Map<String, Long> getActivityMap) {
        this.getActivityMap = getActivityMap;
    }
}
