package com.kishor.assignment1.student;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Kishor on Jan 31, 2022.
 */

public class StudentImplementationTest {
    List<Student>      list          = StudentDataBase.getAllStudents();
    List<String>       studentNames  = Arrays.asList("Mr. Alex", "Mr. Mahesh", "Mr. James", "Mr. Kenny", "Ms. Jane", "Ms. Emma", "Ms. Lisba");
    Map<Integer, Long> allGrades     = StudentDataBase.getAllStudents().stream()
                                                      .map(m -> m.getGradeLevel()).collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    Map<String, Long>  allPeformance = new HashMap<String, Long>() {{
        put("Excellent", 2L);
        put("poor", 2L);
        put("Average", 3L);

    }};

    /**
     * 1. get no of male and female students , Return result in class MaleAndFemalContainer(int males, int females)
     */
    @Test
    public void getMalesAndFemales() {
        MaleAndFemalContainer maleAndFemalContainer = StudentImplementation.maleAndFemaleCount(list);
        Assert.assertEquals(4, maleAndFemalContainer.getmales());
        Assert.assertEquals(3, maleAndFemalContainer.getFemales());
    }

    /**
     * 2. Add Prefix to each student's name ,  Mr. or Ms. and return
     */
    @Test
    public void getStudentNames() {
        List<String> maleAndFemalContainer = StudentImplementation.addPrefixToStudents(list);
        Assert.assertEquals(studentNames, maleAndFemalContainer);
    }

    /**
     * 3. get no of students according to Grade level , Return result in class GradeLevelContainer(int gradeLevel, int students)
     */
    @Test
    public void getGradeLevelForAValue() {
        GradeLevelContainer noOfStudents = StudentImplementation.gradeLevelContainer(3, list);
        Assert.assertEquals(2, noOfStudents.students);
    }

    /**
     * 3.1 get total no of students according to Grade level , Return result in class GradeLevelContainer(int students)
     */
    @Test
    public void getAllGradeLevel() {
        GradeLevelContainer totalNoOfStudents = StudentImplementation.gradeLevelContainers(list);
        Assert.assertEquals(allGrades, totalNoOfStudents.gradeLevel);
    }

    /**
     * 4. get no of students participating in each type of activity , Return result in class ActivityContainer(String activity, int students)
     */
    @Test
    public void getAllPerformance() {
        PerformanceContainer performanceContainer = StudentImplementation.performanceContainer(list);
        Assert.assertEquals(allPeformance, performanceContainer.students);
    }

    /**
     * 5. group students based on GPA with below criteria, Return result in class PerformanceContainer(String level, int students)
     * 0 - 4.0 -> Poor
     * 4.1 - 7.0 -> Average
     * > 7.1 -> Excellent
     */
    @Test
    public void getParticularPerformance() {
        PerformanceContainer performanceContainer = StudentImplementation.performanceContainers(list, "poor");
        Assert.assertEquals(Optional.of(2L).get(), performanceContainer.gradeLevel);
    }


}