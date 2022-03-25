package com.chethan.assignment6.student;

//  No of students according to grade level
public class GradeLevelContainer {

    private final int  gradeLevel;
    private final long students;

    public GradeLevelContainer(int gradeLevel, long students) {
        this.gradeLevel = gradeLevel;
        this.students = students;
    }

    //Getters
    public int getGradeLevel() {
        return gradeLevel;
    }

    public long getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "gradeLevel= " + gradeLevel + " students= " + students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeLevelContainer that = (GradeLevelContainer) o;
        return gradeLevel == that.gradeLevel && students == that.students;
    }
}




