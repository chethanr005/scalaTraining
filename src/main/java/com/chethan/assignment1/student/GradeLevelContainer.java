package com.chethan.assignment1.student;

//  No of students according to grade level
public class GradeLevelContainer {

    private int  gradeLevel;
    private long students;

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
}




