package com.kishor.assignment1.Student;

import java.util.List;

public class Result {
    public static void main(String[] args) {
        List<Student> list = StudentDataBase.getAllStudents();
        ActivityContainer act = new ActivityContainer();
        System.out.println(act.activityCount(list));
        GradeLevelContainer glc = new GradeLevelContainer();
        System.out.println(glc.gradeCount(list));
        MaleAndFemalContainer mfc = new MaleAndFemalContainer();
        System.out.println(mfc.maleCount(list));
        System.out.println(mfc.femaleCount(list));
        System.out.println(mfc.studentNames(list));
       PerformanceContainer pfc = new PerformanceContainer();
        System.out.println(pfc.performerGroup(list));
    }
}
