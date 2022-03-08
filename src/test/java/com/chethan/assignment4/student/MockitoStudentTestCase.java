package com.chethan.assignment4.student;

/**
 * Created by Chethan on Mar 04, 2022.
 */
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MockitoStudentTestCase {

    //private IStudentDatabase      studentDatabase       = new StudentDatabase();
    private MockStudentDatabase   mockData              = new MockStudentDatabase();
    private IStudentDatabase studentDatabase=Mockito.mock(IStudentDatabase.class);
    private StudentImplementation studentImplementation = new StudentImplementation(studentDatabase);

    // 1. No of male and female counts test case
    @Test
    public void maleAndFemaleContainerTest() throws ExecutionException, InterruptedException {

        Mockito.when(studentDatabase.getStudentsData()).thenReturn(MockitoStudentDataBase.studentsList);

        MaleAndFemaleContainer actualResult   = studentImplementation.getMaleAndFemaleContainer().get();
        MaleAndFemaleContainer expectedResult = mockData.getExpectedMaleAndFemaleContainer().get();
        Assert.assertEquals(actualResult, expectedResult);

    }

    // 2. Prefix Students name with  Mr. and Ms.
    @Test
    public void prefixNameTest() throws ExecutionException, InterruptedException {

        Mockito.when(studentDatabase.getStudentsData()).thenReturn(MockitoStudentDataBase.studentsList);
        List<String> expectedPrefixNames = mockData.getExpectedMockitoPrefixNames().get();
        List<String> actualPrefixNames   = studentImplementation.getPrefixStudentsName().get();
        Assert.assertEquals(expectedPrefixNames, actualPrefixNames);
    }

    // 3. No of Students according to Grade Level-1
    @Test
    public void gradeLevelTest1() throws ExecutionException, InterruptedException {
        Mockito.when(studentDatabase.getStudentsData()).thenReturn(MockitoStudentDataBase.studentsList);
        GradeLevelContainer expectedResult = mockData.getExpectedGradeLevelContainer().get();
        GradeLevelContainer actualResult   = studentImplementation.getGradeLevelContainer(10).get();
        Assert.assertEquals(expectedResult, actualResult);
    }

    // 3. No of Students according to Grade Level-2
    @Test
    public void gradeLevelTest2() throws ExecutionException, InterruptedException {
        Mockito.when(studentDatabase.getStudentsData()).thenReturn(MockitoStudentDataBase.studentsList);
        List<GradeLevelContainer> expectedResult = mockData.getExpectedMockitoGradeLevelContainerList().get();
        List<GradeLevelContainer> actualResult   = studentImplementation.getGradeLevelContainer2().get();
        Assert.assertEquals(expectedResult, actualResult);
    }

    // 4. No of Students participating in each type of activity - 1
    @Test
    public void activityContainerTest1() throws ExecutionException, InterruptedException {
        Mockito.when(studentDatabase.getStudentsData()).thenReturn(MockitoStudentDataBase.studentsList);
        ActivityContainer expectedResult = mockData.getExpectedActivityContainer().get();
        ActivityContainer activityCount  = studentImplementation.getActivityContainer("cycling").get();
        Assert.assertEquals(expectedResult, activityCount);
    }

    // 4. No of Students participating in each type of activity - 2
    @Test
    public void activityContainerTest2() throws ExecutionException, InterruptedException {
        Mockito.when(studentDatabase.getStudentsData()).thenReturn(MockitoStudentDataBase.studentsList);
        List<ActivityContainer> expectedResult = mockData.getExpectedMockitoActivityContainer2().get();
        List<ActivityContainer> activityCount  = studentImplementation.getActivityContainer2().get();
        Assert.assertEquals(expectedResult, activityCount);
    }

    // 5. Group students based on performance Criteria-1
    @Test
    public void performanceContainerTest() throws ExecutionException, InterruptedException {
        Mockito.when(studentDatabase.getStudentsData()).thenReturn(MockitoStudentDataBase.studentsList);
        PerformanceContainer  actualResult= studentImplementation.getPerformanceContainer("excellent").get();
        PerformanceContainer expectedResult   = mockData.getExpectedMockitoPerformanceContainer().get();
        Assert.assertEquals(expectedResult, actualResult);
    }

    // 5. Group students based on performance Criteria-2
    @Test
    public void performanceContainerTest2() throws ExecutionException, InterruptedException {
        Mockito.when(studentDatabase.getStudentsData()).thenReturn(MockitoStudentDataBase.studentsList);
        List<PerformanceContainer>  actualResult= studentImplementation.getPerformanceContainer2().get();
        List<PerformanceContainer> expectedResult   = mockData.getExpectedMockitoPerformanceContainer2().get();
        Assert.assertEquals(expectedResult, actualResult);
    }
}
