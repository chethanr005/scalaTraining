//package com.chethan.assignment1.student;
//
//
//
//
//import java.util.*;
//
///**
// * Created by Chethan on Jan 31, 2022.
// */
//
//public class StudentTestCase {
//
//    // No of male and female counts test case
//    @Test
//    public void maleAndFemaleCountTest() {
//        MaleAndFemaleContainer m1 = StudentImplementation.getMaleAndFemale(StudentImplementation.studentsList);
//        Assert.assertEquals(4, m1.getMales());
//        Assert.assertEquals(6, m1.getFemales());
//    }
//
//    //Prefix Students name with  Mr. and Ms.
//    @Test
//    public void prefixNameTest() {
//        List<String> studentNames = Arrays.asList("Ms.rose", "Ms.julie", "Mr.tony", "Ms.kail", "Ms.kate", "Mr.anthony", "Mr.andrew", "Ms.swift", "Mr.cooper", "Ms.hailey");
//        List<String> prefixNames  = StudentImplementation.getPrefixStudentsName(StudentImplementation.studentsList);
//        Assert.assertEquals(studentNames, prefixNames);
//    }
//
//    //No of Students according to Grade Level-1
//    @Test
//    public void gradeLevelTest1() {
//        GradeLevelContainer studentCount = StudentImplementation.getGradeLevel(StudentImplementation.studentsList, 10);
//        Assert.assertEquals(5, studentCount.getStudents());
//    }
//
////    //No of Students according to Grade Level-2
////    @Test
////    public void  gradeLevelTest2(){
////        List<GradeLevelContainer> gradeLevelList= Arrays.asList(new GradeLevelContainer(10,5),
////                new GradeLevelContainer(9,3),new GradeLevelContainer(8,2));
////        Assert.assertEquals(gradeLevelList, StudentImplementation.getGradeLevel2(StudentImplementation.studentsList));
////
////    }
//
//    //No of Students according to Grade Level-3
//    @Test
//    public void gradeLevelTest3() {
//        Map<Integer, Long> gradeGroup = new TreeMap<Integer, Long>();
//        gradeGroup.put(8, 2l);
//        gradeGroup.put(9, 3l);
//        gradeGroup.put(10, 5l);
//
//        Assert.assertEquals(gradeGroup, StudentImplementation.getGradeLevel3(StudentImplementation.studentsList));
//    }
//
//    //No of Students participating in each type of activity
//    @Test
//    public void activityContainerTest() {
//        ActivityContainer activityCount = StudentImplementation.getActivityContainer(StudentImplementation.studentsList, "cycling");
//        Assert.assertEquals(5, activityCount.getStudents());
//    }
//
//    //No of Students participating in each type of activity-2
//    @Test
//    public void activityContainerTest2() {
//        Map<String, Long> activityCount = new TreeMap<String, Long>();
//        activityCount.put("cycling", 5l);
//        activityCount.put("hiking", 5l);
//        activityCount.put("running", 2l);
//        activityCount.put("swimming", 3l);
//        activityCount.put("travelling", 3l);
//        activityCount.put("walking", 6l);
//        Assert.assertEquals(activityCount, StudentImplementation.getActivityContainer2(StudentImplementation.studentsList));
//    }
//
//
//    //Group students based on Criteria-1
//    @Test
//    public void performanceContainerTest() {
//        PerformanceContainer studentCount = StudentImplementation.getPerformanceContainer(StudentImplementation.studentsList, "excellent");
//        Assert.assertEquals(4, studentCount.getStudents());
//    }
//
//    //Group students based on Criteria-2
//    @Test
//    public void performanceContainerTest2() {
//        Map<String, Long> performanceGroup = new TreeMap<String, Long>();
//        performanceGroup.put("Average", 5l);
//        performanceGroup.put("Excellent", 4l);
//        performanceGroup.put("Poor", 1l);
//        Assert.assertEquals(performanceGroup, StudentImplementation.getPerformanceContainer2(StudentImplementation.studentsList));
//    }
//}
