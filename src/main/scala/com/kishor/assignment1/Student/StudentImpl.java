package com.kishor.assignment1.Student;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kartik on Jan 28, 2022.
 */

public class StudentImpl {

    // 1.
    public static MaleAndFemaleContainer getMaleAndFemaleStudents(List<Student> students) {
        return new MaleAndFemaleContainer(0, 0);
    }

    // 2.
    public static List<Student> addPrefixAndReturn(List<Student> students) {
        return students;
    }

    // 3.
    public GradeLevelContainer2 getByGradeLevel(List<Student> students, int gradeLevel) {
        return new GradeLevelContainer2(gradeLevel, 0);
    }

    // 3.1.
    public List<GradeLevelContainer2> getByGradeLevels(List<Student> students) {
        return Arrays.asList(new GradeLevelContainer2(0, 0));
    }

}

class MaleAndFemaleContainer {
    int males;
    int females;

    MaleAndFemaleContainer(int males, int females) {
        this.males = males;
        this.females = females;
    }
}

class GradeLevelContainer2 {
    int gradeLevel;
    int students;

    GradeLevelContainer2(int gradeLevel, int students) {
        this.gradeLevel = gradeLevel;
        this.students = students;
    }
}