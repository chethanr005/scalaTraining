package com.kishor.assignment4.student;

import junit.framework.TestCase;
import org.junit.Assert;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kishor on Feb 25, 2022.
 */

public class StudentImplementationTest extends TestCase {
    StudentMockDatabase   mockData              = new StudentMockDatabase();
    IStudent              database              = Mockito.mock(StudentDatabase.class);
    StudentImplementation studentImplementation = new StudentImplementation(database);
    StudentMockitoDB      studentMockitoDB      = new StudentMockitoDB();

    /**
     * 1. get no of male and female students , Return result in class MaleAndFemalContainer(int males, int females)
     */
    public void testMaleAndFemaleCount() throws ExecutionException, InterruptedException, SQLException {
        Mockito.when(database.getAllStudents()).thenReturn(studentMockitoDB.getMockAllStudents());
        Assert.assertEquals(mockData.getMockMaleAndFemale().get(), studentImplementation.maleAndFemaleCount().get());
    }

    /**
     * 2. Add Prefix to each student's name ,  Mr. or Ms. and return
     */
    public void testAddPrefixToStudents() throws ExecutionException, InterruptedException, SQLException {
        Mockito.when(database.getAllStudents()).thenReturn(studentMockitoDB.getMockAllStudents());
        Assert.assertEquals(mockData.getMockStudentNames().get(), studentImplementation.addPrefixToStudents().get());
    }

    /**
     * 3 get total no of students according to Grade level , Return result in class GradeLevelContainer(int gradeLevel, int students)
     */
    public void testGetAllGradeLevel() throws ExecutionException, InterruptedException, SQLException {
        Mockito.when(database.getAllStudents()).thenReturn(studentMockitoDB.getMockAllStudents());
        Assert.assertEquals(mockData.getMockAllGradeLevel().get(), studentImplementation.getAllGradeLevel().get());
    }

    /**
     * 4. get no of students participating in each type of activity , Return result in class ActivityContainer(String activity, int students)
     */
    public void testGetNoOfStudentsBelongsToEachActivities() throws SQLException, ExecutionException, InterruptedException {
        Mockito.when(database.getAllStudents()).thenReturn(studentMockitoDB.getMockAllStudents());
        Assert.assertEquals(mockData.getMockAllActivitiesCount().get(), studentImplementation.getNoOfStudentsBelongsToEachActivities().get());
    }

    /**
     * 5. group students based on GPA with below criteria, Return result in class PerformanceContainer(String level, int students)
     * 0 - 4.0 -> Poor
     * 4.1 - 7.0 -> Average
     * > 7.1 -> Excellent
     */
    public void testGetPerformanceOfStudents() throws SQLException, ExecutionException, InterruptedException {
        Mockito.when(database.getAllStudents()).thenReturn(studentMockitoDB.getMockAllStudents());
        Assert.assertEquals(mockData.getMockAllPerformance().get(), studentImplementation.getPerformanceOfStudents().get());
    }
}