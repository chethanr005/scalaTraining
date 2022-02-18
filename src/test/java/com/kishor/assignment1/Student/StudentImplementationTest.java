package com.kishor.assignment1.student;

import com.kishor.assignment3.student.StudentDatabase;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kishor on Jan 31, 2022.
 */

public class StudentImplementationTest {
    List<Student> list = StudentDatabase.getAllStudents();

    public StudentImplementationTest() throws SQLException {
    }

    /**
     * 1. get no of male and female students , Return result in class MaleAndFemalContainer(int males, int females)
     */
    @Test
    public void getMalesAndFemales() {
        MaleAndFemaleContainer maleAndFemaleContainer = StudentImplementation.maleAndFemaleCount(list);
        Assert.assertEquals(4, maleAndFemaleContainer.getmales());
        Assert.assertEquals(2, maleAndFemaleContainer.getFemales());
    }

    /**
     * 2. Add Prefix to each student's name ,  Mr. or Ms. and return
     */
    @Test
    public void getStudentNames() {
        List<String> studentNames          = Arrays.asList("Mr. Alex", "Mr. Mahesh", "Mr. James", "Mr. Kenny", "Ms. Lisba", "Ms. Emma");
        List<String> maleAndFemalContainer = StudentImplementation.addPrefixToStudents(list);
        Assert.assertEquals(studentNames, maleAndFemalContainer);
    }

    /**
     * 3 get total no of students according to Grade level , Return result in class GradeLevelContainer(int gradeLevel, int students)
     */
    @Test
    public void getAllGradeLevel() {
        List<GradeLevelContainer> expectedValue = Arrays.asList(new GradeLevelContainer(2, 1l),
                new GradeLevelContainer(3, 2l),
                new GradeLevelContainer(4, 2l),
                new GradeLevelContainer(5, 1l));
        List<GradeLevelContainer> totalNoOfStudents = StudentImplementation.getAllGradeLevel(list);
        Assert.assertEquals(expectedValue.toString(), totalNoOfStudents.toString());
    }

    /**
     * 4. get no of students participating in each type of activity , Return result in class ActivityContainer(String activity, int students)
     */
    @Test
    public void getAllPerformance() {
        List<ActivityContainer> expectedValue = Arrays.asList(new ActivityContainer("Painting", 2l),
                new ActivityContainer("Swimming", 4l),
                new ActivityContainer("Gymnastics", 2l),
                new ActivityContainer("Soccer", 2l),
                new ActivityContainer("Basketball", 1l),
                new ActivityContainer("Baseball", 1l),
                new ActivityContainer("Football", 2l),
                new ActivityContainer("Running", 1l),
                new ActivityContainer("Dancing", 1l),
                new ActivityContainer("Aerobics", 1l));
        List<ActivityContainer> activityContainers = StudentImplementation.activityContainers(list);
        Assert.assertEquals(expectedValue.toString(), activityContainers.toString());
    }

    /**
     * 5. group students based on GPA with below criteria, Return result in class PerformanceContainer(String level, int students)
     * 0 - 4.0 -> Poor
     * 4.1 - 7.0 -> Average
     * > 7.1 -> Excellent
     */
    @Test
    public void getParticularPerformance() {
        List<PerformanceContainer> expectedValue = Arrays.asList(new PerformanceContainer("poor", 1l),
                new PerformanceContainer("average", 4l),
                new PerformanceContainer("excellent", 1l));
        List<PerformanceContainer> performanceContainer = StudentImplementation.getPerformanceOfStudents(list);
        Assert.assertEquals(expectedValue.toString(), performanceContainer.toString());
    }


}