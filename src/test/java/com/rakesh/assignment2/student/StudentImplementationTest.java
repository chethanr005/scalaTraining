package com.rakesh.assignment2.student;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Rakesh on Feb 02, 2022.
 */

public class StudentImplementationTest {
    static List<Student> studentList = StudentImplementation.getStudentsList();

    //1. Get total and average marks for all students
    @Test
    public void checkGetTotalAndAverage() {
        Map<String, TotalAndAverageContainer> actualMap = StudentImplementation.getTotalAndAverage(studentList);

        Map<String, TotalAndAverageContainer> expectedMap = new TreeMap<>();
        expectedMap.put("DA001", new TotalAndAverageContainer(132, 44.0));
        expectedMap.put("DA002", new TotalAndAverageContainer(235, 78.0));
        expectedMap.put("DA003", new TotalAndAverageContainer(228, 76.0));
        expectedMap.put("DA004", new TotalAndAverageContainer(174, 58.0));
        expectedMap.put("DA005", new TotalAndAverageContainer(253, 84.0));
        expectedMap.put("DA006", new TotalAndAverageContainer(250, 83.0));
        expectedMap.put("DA007", new TotalAndAverageContainer(166, 55.0));
        expectedMap.put("DA008", new TotalAndAverageContainer(207, 69.0));
        expectedMap.put("DA009", new TotalAndAverageContainer(244, 81.0));
        expectedMap.put("DA010", new TotalAndAverageContainer(231, 77.0));

        Assert.assertEquals(expectedMap.toString(), actualMap.toString());
    }

    //2. Total marks scored by each standard
    @Test
    public void checkGetTotalByStandard() {
        List<TotalMarksByStandardContainer> expectedResult = new ArrayList<>();
        expectedResult.add(new TotalMarksByStandardContainer(7, 367));
        expectedResult.add(new TotalMarksByStandardContainer(8, 402));
        expectedResult.add(new TotalMarksByStandardContainer(9, 669));
        expectedResult.add(new TotalMarksByStandardContainer(10, 682));

        List<TotalMarksByStandardContainer> actualResult = StudentImplementation.getTotalByStandard(studentList);
        Assert.assertEquals(expectedResult.toString(), actualResult.toString());
    }

    //3. Get Students who scored Highest in given subject in each standard
    @Test
    public void checkGetHighestScoredStudents() {
        List<HighestScoredStudentsContainer> expectedResult = new ArrayList<>();
        expectedResult.add(new HighestScoredStudentsContainer(7, "Antony"));
        expectedResult.add(new HighestScoredStudentsContainer(8, "Raj"));
        expectedResult.add(new HighestScoredStudentsContainer(9, "Wilson"));
        expectedResult.add(new HighestScoredStudentsContainer(10, "Ali"));

        List<HighestScoredStudentsContainer> actualResult = StudentImplementation.getHighestScoredStudents(studentList, "Scala");
        Assert.assertEquals(expectedResult.toString(), actualResult.toString());
    }

    //4. Get Student details who did not appear for exam
    @Test
    public void checkGetAbsentStudents() {
        List<AbsentStudentContainer> expectedResult = new ArrayList<>();
        expectedResult.add(new AbsentStudentContainer(103, "Edwin", "DA001"));
        expectedResult.add(new AbsentStudentContainer(101, "Raj", "DA004"));
        expectedResult.add(new AbsentStudentContainer(102, "John", "DA007"));

        List<AbsentStudentContainer> actualResult = StudentImplementation.getAbsentStudents(studentList);
        Assert.assertEquals(expectedResult.toString(), actualResult.toString());
    }

    //5. Highest Marks in each subject with Name of Student
    @Test
    public void checkHighestScoredBySubject() {
        List<HighestMarksContainer> expectedResult = new ArrayList<>();
        expectedResult.add(new HighestMarksContainer("Java", "Sunil", 96));
        expectedResult.add(new HighestMarksContainer("Scala", "Wilson", 96));
        expectedResult.add(new HighestMarksContainer("C++", "Sreeja", 90));
        List<HighestMarksContainer> actualResult = StudentImplementation.highestScoredBySubject(studentList);
        Assert.assertEquals(expectedResult.toString(), actualResult.toString());

    }

    @Test
    public void checkAwardedStudents() {
        List<AwardWinnerContainer> expectedResult = new ArrayList<>();
        expectedResult.add(new AwardWinnerContainer("Java", "Wilson", "DA005", 92));
        expectedResult.add(new AwardWinnerContainer("Java", "Sunil", "DA006", 96));
        expectedResult.add(new AwardWinnerContainer("Scala", "Antony", "DA002", 90));
        expectedResult.add(new AwardWinnerContainer("Scala", "Raj", "DA004", 91));
        expectedResult.add(new AwardWinnerContainer("Scala", "Wilson", "DA005", 96));
        expectedResult.add(new AwardWinnerContainer("C++", "Sreeja", "DA010", 90));

        List<AwardWinnerContainer> actualResult = StudentImplementation.awardedStudents(studentList);
        Assert.assertEquals(expectedResult.toString(), actualResult.toString());

    }

}
