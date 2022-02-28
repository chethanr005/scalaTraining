package com.kishor.assignment1.student;

import java.util.Objects;

/**
 * 5. group students based on GPA with below criteria, Return result in class PerformanceContainer(String level, int students)
 */
public class PerformanceContainer {
    public String level;
    public Long   students;

    PerformanceContainer(String level, Long students) {
        this.level = level;
        this.students = students;
    }

    public String getlevel() {
        return level;
    }

    public Long getstudents() {
        return students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerformanceContainer that = (PerformanceContainer) o;
        return level.equals(that.level) && students.equals(that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, students);
    }

    @Override
    public String toString() {
        return "{" +
                "Level=" + level +
                ", students=" + students +
                '}';
    }
}