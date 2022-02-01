package com.rakesh.assignment1.student;

import java.util.Map;


//3. get no of students according to Grade level , Return result in class GradeLevelContainer(int gradeLevel, int students)

public class GradeLevelContainer {

    public long studentsCount;
    public Map<Integer, Long> gradeCountMap;

    public GradeLevelContainer(Map<Integer, Long> gradeCountMap) {
        this.gradeCountMap = gradeCountMap;
    }

    public GradeLevelContainer(long studentsCount) {
        this.studentsCount = studentsCount;
    }





}
