package com.chethan.assignment4.student;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Chethan on Feb 24, 2022.
 */

public class StudentTestCase {
    private IStudentDatabase      studentDatabase       = new StudentDatabase();
    private StudentImplementation studentImplementation = new StudentImplementation(studentDatabase);
    private MockStudentDatabase   mockData              = new MockStudentDatabase();

    // 1. No of male and female counts test case
    @Test
    public void abc() throws ExecutionException, InterruptedException {

        MaleAndFemaleContainer actualResult   = studentImplementation.getMaleAndFemaleContainer().get();
        MaleAndFemaleContainer expectedResult = mockData.getExpectedMaleAndFemaleContainer().get();

        Assert.assertEquals(actualResult, expectedResult);

    }

    // 2. Prefix Students name with  Mr. and Ms.
    @Test
    public void prefixNameTest() throws ExecutionException, InterruptedException {
        List<String> expectedPrefixNames = mockData.getExpectedPrefixNames().get();
        List<String> actualPrefixNames   = studentImplementation.getPrefixStudentsName().get();
        Assert.assertEquals(expectedPrefixNames, actualPrefixNames);
    }

    // 3. No of Students according to Grade Level-1
    @Test
    public void gradeLevelTest1() throws ExecutionException, InterruptedException {
        GradeLevelContainer expectedResult = mockData.getExpectedGradeLevelContainer().get();
        GradeLevelContainer actualResult   = studentImplementation.getGradeLevelContainer(10).get();
        Assert.assertEquals(expectedResult, actualResult);
    }

    // 3. No of Students according to Grade Level-2
    @Test
    public void gradeLevelTest2() throws ExecutionException, InterruptedException {
        List<GradeLevelContainer> expectedResult = mockData.getExpectedGradeLevelContainerList().get();
        List<GradeLevelContainer> actualResult   = studentImplementation.getGradeLevelContainer2().get();
        Assert.assertEquals(expectedResult, actualResult);
    }

    // 4. No of Students participating in each type of activity - 1
    @Test
    public void activityContainerTest1() throws ExecutionException, InterruptedException {
        ActivityContainer expectedResult = mockData.getExpectedActivityContainer().get();
        ActivityContainer activityCount  = studentImplementation.getActivityContainer("cycling").get();
        Assert.assertEquals(expectedResult, activityCount);
    }

    // 4. No of Students participating in each type of activity - 2
    @Test
    public void activityContainerTest2() throws ExecutionException, InterruptedException {
        List<ActivityContainer> expectedResult = mockData.getExpectedActivityContainer2().get();
        List<ActivityContainer> activityCount  = studentImplementation.getActivityContainer2().get();
        Assert.assertEquals(expectedResult, activityCount);
    }

    // 5. Group students based on performance Criteria-1
    @Test
    public void performanceContainerTest() throws ExecutionException, InterruptedException {
        PerformanceContainer expectedResult = studentImplementation.getPerformanceContainer("excellent").get();
        PerformanceContainer actualResult   = mockData.getExpectedPerformanceContainer().get();
        Assert.assertEquals(expectedResult, actualResult);
    }

    // 5. Group students based on performance Criteria-2
    @Test
    public void performanceContainerTest2() throws ExecutionException, InterruptedException {
        List<PerformanceContainer> expectedResult = studentImplementation.getPerformanceContainer2().get();
        List<PerformanceContainer> actualResult   = mockData.getExpectedPerformanceContainer2().get();
        Assert.assertEquals(expectedResult, actualResult);
    }

}
