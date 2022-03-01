package com.rakesh.assignment4.student;

/**
 * Created by Rakesh on Feb 25, 2022.
 */

public class GradeLevelContainer {
    int gradeLevel;
    int studentsCount;

    GradeLevelContainer(int gradeLevel, long studentsCount) {
        this.gradeLevel = gradeLevel;
        this.studentsCount = (int) studentsCount;
    }

    @Override
    public boolean equals(Object o) {
        GradeLevelContainer check = (GradeLevelContainer) o;
        return gradeLevel == check.gradeLevel && studentsCount == check.studentsCount;
    }

    @Override
    public String toString() {
        return "{" +
                "gradeLevel : " + gradeLevel +
                ", studentsCount : " + studentsCount +
                '}';
    }
}
