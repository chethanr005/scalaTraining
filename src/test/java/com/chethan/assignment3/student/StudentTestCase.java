package com.chethan.assignment3.student;

/**
 * Created by Chethan on Feb 16, 2022.
 */

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class StudentTestCase {

    // No of male and female counts test case
    @Test
    public void maleAndFemaleCountTest() {
        MaleAndFemaleContainer m1 = StudentImplementation.getMaleAndFemale(StudentDatabase.getStudentData());
        Assert.assertEquals(4, m1.getMales());
        Assert.assertEquals(6, m1.getFemales());
    }

    //Prefix Students name with  Mr. and Ms.
    @Test
    public void prefixNameTest() {
        List<String> studentNames = Arrays.asList("Ms.julie", "Mr.tony", "Ms.kail", "Mr.anthony", "Mr.andrew", "Ms.swift", "Mr.cooper", "Ms.hailey", "Ms.rose", "Ms.kate");
        List<String> prefixNames  = StudentImplementation.getPrefixStudentsName(StudentDatabase.getStudentData());
        Assert.assertEquals(studentNames, prefixNames);
    }

    //No of Students according to Grade Level-1
    @Test
    public void gradeLevelTest1() {
        GradeLevelContainer studentCount = StudentImplementation.getGradeLevel(StudentDatabase.getStudentData(), 10);
        Assert.assertEquals(5, studentCount.getStudents());
    }


    //No of Students according to Grade Level-2
    @Test
    public void gradeLevelTest2() {
        Map<Integer, Long> gradeGroup = new TreeMap<Integer, Long>();
        gradeGroup.put(8, 2l);
        gradeGroup.put(9, 3l);
        gradeGroup.put(10, 5l);

        Assert.assertEquals(gradeGroup, StudentImplementation.getGradeLevel2(StudentDatabase.getStudentData()));
    }

    //No of Students participating in each type of activity
    @Test
    public void activityContainerTest() {
        ActivityContainer activityCount = StudentImplementation.getActivityContainer(StudentDatabase.getStudentData(), "cycling");
        Assert.assertEquals(5, activityCount.getStudents());
    }

    //No of Students participating in each type of activity-2
    @Test
    public void activityContainerTest2() {
        Map<String, Long> activityCount = new TreeMap<String, Long>();
        activityCount.put("cycling", 5l);
        activityCount.put("hiking", 5l);
        activityCount.put("running", 2l);
        activityCount.put("swimming", 3l);
        activityCount.put("travelling", 3l);
        activityCount.put("walking", 6l);
        Assert.assertEquals(activityCount, com.chethan.assignment1.student.StudentImplementation.getActivityContainer2(com.chethan.assignment1.student.StudentImplementation.studentsList));
    }


    //Group students based on Criteria-1
    @Test
    public void performanceContainerTest() {
        PerformanceContainer studentCount = StudentImplementation.getPerformanceContainer(StudentDatabase.getStudentData(), "excellent");
        Assert.assertEquals(4, studentCount.getStudents());
    }

    //Group students based on Criteria-2
    @Test
    public void performanceContainerTest2() {
        Map<String, Long> performanceGroup = new TreeMap<String, Long>();
        performanceGroup.put("Average", 5l);
        performanceGroup.put("Excellent", 4l);
        performanceGroup.put("Poor", 1l);
        Assert.assertEquals(performanceGroup, StudentImplementation.getPerformanceContainer2(StudentDatabase.getStudentData()));
    }

}