package com.chethan.assignment5.student;


import java.util.List;
import java.util.Objects;

//Students grouped based on GPA
public class PerformanceContainer {
    private final String           level;
    private final List<Student> students;

    PerformanceContainer(String level, List<Student> students) {
        this.level = level;
        this.students = students;
    }

    //Getters
    public String getLevel() {
        return level;
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerformanceContainer that = (PerformanceContainer) o;
        return Objects.equals(level, that.level) && Objects.equals(students, that.students);
    }

    @Override
    public String toString() {
        return "level= " + level + " students= " + students;
    }
}
