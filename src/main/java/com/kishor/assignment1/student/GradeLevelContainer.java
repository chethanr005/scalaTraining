package com.kishor.assignment1.student;

import java.util.Map;

/**
 * 3. get no of students according to Grade level , Return result in class GradeLevelContainer(int gradeLevel, int students)
 */
public class GradeLevelContainer {
    public Map<Integer, Long> gradeLevel;
    public long               students;

    GradeLevelContainer(long students) {
        this.students = students;
    }

    GradeLevelContainer(Map<Integer, Long> gradelevel) {
        this.gradeLevel = gradelevel;
    }

    public Map<Integer, Long> getGradeLevel() {
        return gradeLevel;
    }

    public Long getStudents() {
        return students;
    }
}
