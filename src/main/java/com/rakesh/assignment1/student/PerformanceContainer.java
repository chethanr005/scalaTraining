package com.rakesh.assignment1.student;

import java.util.Map;

//5. group students based on GPA with below criteria, Return result in class PerformanceContainer(String level, int students)

public class PerformanceContainer {
    public Map<String, Long> gpaGroupMap;
    public long              studentCount;

    public PerformanceContainer(Map<String, Long> gpaGroupMap) {
        this.gpaGroupMap = gpaGroupMap;
    }

    public PerformanceContainer(long studentCount) {
        this.studentCount = studentCount;
    }
}
