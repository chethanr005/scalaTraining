package com.kishor.assignment5.student;

import java.util.Objects;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public class GradeLevelContainer {
    public Integer gradeLevel;
    public Long    students;

    GradeLevelContainer(Integer gradelevel, Long students) {
        this.gradeLevel = gradelevel;
        this.students = students;
    }

    public Integer getGradeLevel() {
        return gradeLevel;
    }

    public Long getStudents() {
        return students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeLevelContainer that = (GradeLevelContainer) o;
        return Objects.equals(gradeLevel, that.gradeLevel) && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gradeLevel, students);
    }

    @Override
    public String toString() {
        return "{" +
                "GradeLevel = " + gradeLevel +
                ", Students = " + students +
                '}';
    }
}
