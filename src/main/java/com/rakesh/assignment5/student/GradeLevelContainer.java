package com.rakesh.assignment5.student;

/**
 * Created by Rakesh on Mar 10, 2022.
 */

public class GradeLevelContainer {
    public int gradeLevel;
    public int studentsCount;


    public GradeLevelContainer(int gradeLevel, long count) {
        this.gradeLevel = gradeLevel;
        this.studentsCount = (int) count;
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
