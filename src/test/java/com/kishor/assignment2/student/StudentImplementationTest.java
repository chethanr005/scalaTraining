package com.kishor.assignment2.student;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Kishor on Feb 02, 2022.
 */

public class StudentImplementationTest {
    List<Student> studentList = StudentImplementation.getStudents();

    @Test
    public void getTotalAndAverageMarks() {
        Map<String, TotalAndAverageContainer> expectedMap = new TreeMap<>();
        expectedMap.put("Devi", new TotalAndAverageContainer(259, 86.0f));
        expectedMap.put("Dinesh", new TotalAndAverageContainer(163, 53.0f));
        expectedMap.put("Jhon", new TotalAndAverageContainer(272, 89.0f));
        expectedMap.put("Kishor", new TotalAndAverageContainer(230, 75.0f));
        expectedMap.put("Krishna", new TotalAndAverageContainer(130, 43.0f));
        expectedMap.put("Mahesh", new TotalAndAverageContainer(184, 60.0f));
        expectedMap.put("Ramesh", new TotalAndAverageContainer(207, 68.0f));
        expectedMap.put("Ravi", new TotalAndAverageContainer(231, 76.0f));
        expectedMap.put("Rita", new TotalAndAverageContainer(132, 44.0f));
        expectedMap.put("Suresh", new TotalAndAverageContainer(200, 65.0f));

        Map<String, TotalAndAverageContainer> totalAndAverageMarks = StudentImplementation.getTotalAndAverage(studentList);
        Assert.assertEquals(expectedMap.toString(), totalAndAverageMarks.toString());
    }
    @Test
    public void totalMarksFromEachStandard() {
        int expectedValue = 489;
        int actualValue = StudentImplementation.totalMarksOfEachStandard(studentList, 2);
        Assert.assertEquals(expectedValue, actualValue);
    }
    @Test
    public void highestScoredStudentForGivenSubject(){
    List<HighestMarksScoredContainer> expectedValue = Arrays.asList(new HighestMarksScoredContainer("Kishor",2),
            new HighestMarksScoredContainer("Jhon", 3),
            new HighestMarksScoredContainer("Suresh",4),
            new HighestMarksScoredContainer("Mahesh", 5),
            new HighestMarksScoredContainer("Ravi", 6)
    );
    List<HighestMarksScoredContainer> actualValue = StudentImplementation.highestMarksInEachStandardForGivenSubject(studentList, "Maths");
    Assert.assertEquals(expectedValue.toString(), actualValue.toString());
    }
    @Test
    public void getAbsenteesForExam() {
        Map<String, String> expectedMap = new TreeMap<>();
        expectedMap.put("Krishna", "R0004");
        expectedMap.put("Rita", "R0003");
        Map<String, String> mockListOfAbsentees = StudentImplementation.studentWhoAreAbsentForExam(studentList);
        Assert.assertEquals(expectedMap, mockListOfAbsentees);
    }

    @Test
    public void totalMarksScoredByGivenStandard() {
        int     expectedResult                   = 489;
        Integer totalActualMarksForGivenStandard = StudentImplementation.totalMarksOfEachStandard(studentList, 2);
        Assert.assertEquals(java.util.Optional.of(expectedResult).get(), totalActualMarksForGivenStandard);
    }
    @Test
    public void awardWinningStudents() {
        List<AwardWinningStudentCOntainer> expectedValue = Arrays.asList(new AwardWinningStudentCOntainer("Devi",102,96),
                new AwardWinningStudentCOntainer("Jhon",101,98),
                new AwardWinningStudentCOntainer("Ravi",103, 99));
        List<AwardWinningStudentCOntainer> actualValue = StudentImplementation.awardWinningStudents(studentList);
        Assert.assertEquals(expectedValue.toString(), actualValue.toString());
    }

}