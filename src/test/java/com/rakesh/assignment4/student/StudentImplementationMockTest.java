package com.rakesh.assignment4.student;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Rakesh on Mar 04, 2022.
 */

public class StudentImplementationMockTest {

    static IStudentDatabase      iStudentDatabase      = Mockito.mock(IStudentDatabase.class);
    static StudentImplementation studentImplementation = new StudentImplementation(iStudentDatabase);
    StudentMockData mockData = new StudentMockData();
    static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    //1. get no of male and female students , Return result in class MaleAndFemaleContainer(int males, int females)
    @Test
    public void checkMaleAndFemaleCount() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Student>> students = mockData.getAllStudents();
        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);

        MaleAndFemaleContainer expectedCount = new MaleAndFemaleContainer(7, 3);
        Assert.assertEquals(expectedCount, studentImplementation.getMaleAndFemaleCount().get());
    }

    //2. Add Prefix to each student's name ,  Mr. or Ms. and return
    @Test
    public void checkNamePrefix() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Student>> students = mockData.getAllStudents();
        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);

        PrefixContainer expectedResult = mockData.prefixContainerMockData();
        Assert.assertEquals(expectedResult, studentImplementation.addPrefixToName().get());
    }

    //3. get no of students according to Grade level , Return result in class GradeLevelContainer(int gradeLevel, int students)
    @Test
    public void checkGradeLevel() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Student>> students = mockData.getAllStudents();
        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);

        GradeLevelContainer expectedResult1 = new GradeLevelContainer(9, 1);
        //when GradeLevel is passed.
        Assert.assertEquals(expectedResult1, studentImplementation.getCountBasedOnGradeLevel(9).get());

        //When GradeLevel is not passed.
        List<GradeLevelContainer> expectedList = mockData.gradeLevelContainersMockData();
        Assert.assertEquals(expectedList, studentImplementation.getCountOfAllGradeLevel().get());
    }

    //4. get no of students participating in each type of activity , Return result in class ActivityContainer(String activity, int students)
    @Test
    public void checkGetCountByActivities() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Student>> students = mockData.getAllStudents();
        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);

        ActivityContainer expectedResult = new ActivityContainer("cricket", 3);
        Assert.assertEquals(expectedResult, studentImplementation.getCountBasedOnActivity("cricket").get());

        //When activity is not passed
        List<ActivityContainer> expectedList = mockData.activityContainersMockData();
        Assert.assertEquals(expectedList, studentImplementation.getActivityCountList().get());
    }

    //5. group students based on GPA with below criteria, Return result in class PerformanceContainer(String level, List<Student> students)
    @Test
    public void checkStudentByGPA() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Student>> students = mockData.getAllStudents();
        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);

        PerformanceContainer expectedResult = mockData.performanceContainerByPassingValueMockData("Average");
        Assert.assertEquals(expectedResult, studentImplementation.getStudentsByLevel("Average").get());

        List<PerformanceContainer> expectedList = mockData.performanceContainersMockData();
        Assert.assertEquals(expectedList, studentImplementation.getAllStudentsByLevel().get());
    }

}
