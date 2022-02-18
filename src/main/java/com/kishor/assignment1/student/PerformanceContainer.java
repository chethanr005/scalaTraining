package com.kishor.assignment1.student;

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
    public String toString() {
        return "{" +
                "Level=" + level +
                ", students=" + students +
                '}';
    }
}