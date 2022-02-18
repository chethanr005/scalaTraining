package com.kishor.assignment1.student;

/**
 * 3. get no of students according to Grade level , Return result in class GradeLevelContainer(int gradeLevel, int students)
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
    public String toString() {
        return "{" +
                "GradeLevel = " + gradeLevel +
                ", Students = " + students +
                '}';
    }
}
