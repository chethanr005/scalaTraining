package com.kishor.assignment1.Student;

import java.util.List;

public class Result {
    public static void main(String[] args) {
        List<Student> list = StudentDataBase.getAllStudents();
        activityContainer act = new activityContainer();
        act.activityCount(list);
        gradeLevelContainer glc = new gradeLevelContainer();
        glc.gradeCount(list);
        maleAndFemalContainer mfc = new maleAndFemalContainer();
        mfc.maleCount(list);
        mfc.femaleCount(list);
        mfc.studentNames(list);
    }
}
