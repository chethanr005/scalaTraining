package com.rakesh.assignment1.student;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Rakesh on Feb 01, 2022.
 */

public class StudentImplementationTest {
    List<Student> list = StudentDataBase.getAllStudents();

    // 1. get no of male and female students , Return result in class MaleAndFemalContainer(int males, int females)
    @Test
    public void MaleAndFemaleCountCheck() {
        MaleAndFemaleContainer res = StudentImplementation.maleAndFemaleCounts(list);

        Assert.assertEquals(7, res.maleCount);
        Assert.assertEquals(3, res.femaleCount);
    }

    //2. Add Prefix to each student's name ,  Mr. or Ms. and return
    @Test
    public void nameCheck() {
        //For valid list
        List<String> expectedList = Arrays.asList("Mr.Ajay", "Mr.Dave", "Mr.Edwin", "Mr.James", "Mr.John", "Mr.Rohit", "Mr.Sunil", "Ms.Emily", "Ms.Jessi", "Ms.Sophia");
        Assert.assertEquals(expectedList, StudentImplementation.getNames(list));

        //For empty list
        List<String>  expectedList1 = new ArrayList<>();
        List<Student> emptyStudent  = new ArrayList<>();
        Assert.assertEquals(expectedList1, StudentImplementation.getNames(emptyStudent));
    }

    //3. get no of students according to Grade level , Return result in class GradeLevelContainer(int gradeLevel, int students)
    @Test
    public void gradeLevelCheck() {
        //When GardeLevel is passed in parameter , returns count of that gradeLevel.
        GradeLevelContainer res = StudentImplementation.countBasedOnGradeLevel(list, 4);
        Assert.assertEquals(2, res.studentsCount);

        //When List is passed return Map of GardeLevel-Count
        Map<Integer, Long> expectedMap = new HashMap<>();
        expectedMap.put(1, 1L);
        expectedMap.put(4, 2L);
        expectedMap.put(6, 1L);
        expectedMap.put(7, 2L);
        expectedMap.put(8, 2L);
        expectedMap.put(9, 1L);
        expectedMap.put(10, 1L);
        GradeLevelContainer actualMap = StudentImplementation.getAllGradeCount(list);
        Assert.assertEquals(expectedMap, actualMap.gradeCountMap);
    }

    //4. get no of students participating in each type of activity , Return result in class ActivityContainer(String activity, int students)
    @Test
    public void getCountByActivities() {
        //When activity is passed in parameter , returns count of that activity.
        ActivityContainer res = StudentImplementation.countBasedOnActivity(list, "soccer");
        Assert.assertEquals(6, res.getStudentCount);

        //When only list is passed returns the Map of Employee count
        ActivityContainer actualMap   = StudentImplementation.ActivityGroupMap(list);
        Map<String, Long> expectedMap = new HashMap<>();
        expectedMap.put("soccer", 6L);
        expectedMap.put("dancing", 3L);
        expectedMap.put("swimming", 3L);
        expectedMap.put("basketball", 3L);
        expectedMap.put("painting", 5L);
        expectedMap.put("chess", 2L);
        expectedMap.put("cricket", 3L);
        Assert.assertEquals(expectedMap, actualMap.getActivityMap);
    }

    @Test
    public void getCountBYGPA() {
        PerformanceContainer res = StudentImplementation.countByLevel(list, "Average");
        Assert.assertEquals(4, res.studentCount);

        PerformanceContainer res1        = StudentImplementation.getGpaLevelMap(list);
        Map<String, Long>    expectedMap = new HashMap<>();
        expectedMap.put("Average", 4L);
        expectedMap.put("Excellent", 4L);
        expectedMap.put("Poor", 2L);

        Assert.assertEquals(expectedMap, res1.gpaGroupMap);
    }
}