package com.kishor.assignment1.student;

import java.util.Map;

/**
 * 5. group students based on GPA with below criteria, Return result in class PerformanceContainer(String level, int students)
 */
public class PerformanceContainer {
    public Long              gradeLevel;
    public Map<String, Long> students;

    PerformanceContainer(Map<String, Long> students) {
        this.students = students;
    }

    PerformanceContainer(Long gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Long getGradeLevel() {
        return gradeLevel;
    }

    public Map<String, Long> getStudents() {
        return students;
    }
}