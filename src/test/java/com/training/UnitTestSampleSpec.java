package com.training;

import com.kishor.assignment1.Employee.PromoteEmployee;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kartik on Jan 28, 2022.
 */

public class UnitTestSampleSpec {
    @Test
    public void test() {
        List<Student> students = Arrays.asList(
                new Student("rose", "A+"),
                new Student("rose2", "A-"),
                new Student("rose3", "B")
        );

        GradeLevelContainer container = StudentCheck.getGradeContainer(students, "A+");
        boolean             result    = container.getStudents() == 1;

        assertEquals(1, container.getStudents());
        assertTrue(result);
        PromoteEmployee promoteEmployee = new PromoteEmployee();
    }


}

class Student {
    private String name;
    private String grade;

    Student(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }

    String getGrade() {
        return this.grade;
    }

    public String getName() {
        return name;
    }
}

class GradeLevelContainer {

    private String gradeLevel;
    private long   students;

    GradeLevelContainer(String gradeLevel, long students) {
        this.gradeLevel = gradeLevel;
        this.students = students;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public long getStudents() {
        return students;
    }
}

class StudentCheck {
    public static GradeLevelContainer getGradeContainer(List<Student> students, String grade) {
        long filteredStudents = students.stream().filter((s) -> s.getGrade() == grade).count();
        return new GradeLevelContainer(grade, filteredStudents);
    }
}