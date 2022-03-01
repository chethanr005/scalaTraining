package com.kishor.assignment2.student;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Kishor on Feb 02, 2022.
 */

public class StudentImplementationTest {
    List<Student> studentList = StudentImplementation.getStudents();

    /**
     * 1. Get total and average marks for all students
     */
    @Test
    public void getTotalAndAverageMarks() {
        List<TotalAndAverageContainer> expectedMap = Arrays.asList(new TotalAndAverageContainer("Kishor", 230, 75l),
                new TotalAndAverageContainer("Devi", 259, 86l),
                new TotalAndAverageContainer("Jhon", 272, 89l),
                new TotalAndAverageContainer("Rita", 132, 44l),
                new TotalAndAverageContainer("Krishna", 130, 43l),
                new TotalAndAverageContainer("Suresh", 200, 65l),
                new TotalAndAverageContainer("Ramesh", 207, 68l),
                new TotalAndAverageContainer("Mahesh", 184, 60l),
                new TotalAndAverageContainer("Dinesh", 163, 53l),
                new TotalAndAverageContainer("Ravi", 231, 76l));

        List<TotalAndAverageContainer> totalAndAverageMarks = StudentImplementation.getTotalAndAverage(studentList);
        Assert.assertEquals(expectedMap.toString(), totalAndAverageMarks.toString());
    }

    /**
     * 2. Total marks scored by each standard
     */
    @Test
    public void totalMarksFromEachStandard() {
        List<TotalMarksOfEachGradeContainer> expectedValue = Arrays.asList(
                new TotalMarksOfEachGradeContainer(2, 489),
                new TotalMarksOfEachGradeContainer(3, 404),
                new TotalMarksOfEachGradeContainer(4, 330),
                new TotalMarksOfEachGradeContainer(5, 391),
                new TotalMarksOfEachGradeContainer(6, 394));
        List<TotalMarksOfEachGradeContainer> actualValue = StudentImplementation.totalMarksOfEachStandard(studentList);
        Assert.assertEquals(expectedValue.toString(), actualValue.toString());
    }

    /**
     * 3. Get Students who scored Highest in given subject from each standard
     */
    @Test
    public void highestScoredStudentForGivenSubject() {
        List<HighestMarksScoredContainer> expectedValue = Arrays.asList(new HighestMarksScoredContainer("Kishor", 2),
                new HighestMarksScoredContainer("Jhon", 3),
                new HighestMarksScoredContainer("Suresh", 4),
                new HighestMarksScoredContainer("Mahesh", 5),
                new HighestMarksScoredContainer("Ravi", 6)
        );
        List<HighestMarksScoredContainer> actualValue = StudentImplementation.highestMarksInEachStandardForGivenSubject(studentList, "Maths");
        Assert.assertEquals(expectedValue.toString(), actualValue.toString());
    }

    /**
     * 4. Get Student details who did not appear for exam
     */
    @Test
    public void getAbsenteesForExam() {
        Map<String, String> expectedMap = new TreeMap<>();
        expectedMap.put("Krishna", "R0004");
        expectedMap.put("Rita", "R0003");
        Map<String, String> mockListOfAbsentees = StudentImplementation.studentWhoAreAbsentForExam(studentList);
        Assert.assertEquals(expectedMap, mockListOfAbsentees);
    }

    /**
     * 5. Highest Marks in each subject with Name of Student
     */
    @Test
    public void highestMarksInEachSubjects() {
        List<HighestMarksInEachSubjectContainer> expectedValue = Arrays.asList(
                new HighestMarksInEachSubjectContainer(101, 98, "Jhon"),
                new HighestMarksInEachSubjectContainer(102, 96, "Devi"),
                new HighestMarksInEachSubjectContainer(103, 99, "Ravi"));
        List<HighestMarksInEachSubjectContainer> actualValue = StudentImplementation.highestMarksInEachSubject(studentList);
        Assert.assertEquals(expectedValue.toString(), actualValue.toString());
    }

    /**
     * 6. Get list of Award Winning Students, criteria if Student scores >= 90 in any subject , he/she will awarded for that subject.
     * One student can get multiple awards.
     */
    @Test
    public void awardWinningStudents() {
        List<AwardWinningStudentCOntainer> expectedValue = Arrays.asList(new AwardWinningStudentCOntainer("Devi", 102, 96),
                new AwardWinningStudentCOntainer("Jhon", 101, 98),
                new AwardWinningStudentCOntainer("Ravi", 103, 99));
        List<AwardWinningStudentCOntainer> actualValue = StudentImplementation.awardWinningStudents(studentList);
        Assert.assertEquals(expectedValue.toString(), actualValue.toString());
    }

}