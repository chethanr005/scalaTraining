package com.chethan.assignment2.student;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Chethan on Feb 02, 2022.
 */

public class StudentImplementationTest {


    /**
     1. Get total and average marks of all students
     **/
    @Test
    public void  totalAndAverageMarks()throws Exception{
        Map<String,TotalAverageContainer> totalAverageMarks =new TreeMap<String, TotalAverageContainer>();
        totalAverageMarks.put("andrew" ,new TotalAverageContainer(180,60.0));
        totalAverageMarks.put("anthony",new TotalAverageContainer(150,50.0));
        totalAverageMarks.put("cooper",new TotalAverageContainer(255,85.0));
        totalAverageMarks.put("hailey",new TotalAverageContainer(203,67.0));
        totalAverageMarks.put("julie",new TotalAverageContainer(129,43.0));
        totalAverageMarks.put("kail",new TotalAverageContainer(204,68.0));
        totalAverageMarks.put("kate",new TotalAverageContainer(188,62.0));
        totalAverageMarks.put("rose",new TotalAverageContainer(135,45.0));
        totalAverageMarks.put("swift",new TotalAverageContainer(202,67.0));
        totalAverageMarks.put("tony",new TotalAverageContainer(278,92.0));
        Assert.assertEquals(totalAverageMarks.toString(),StudentImplementation.getTotalAndAverage(StudentImplementation.getStudentList()).toString());
    }

    /**
     * 2. Total marks scored by each standard
     **/
    @Test
    public void totalMarksByStandard() throws Exception{
        List<TotalMarksByStandard> actualTotalMarksByStandard=Arrays.asList(new TotalMarksByStandard(10,932),
                new TotalMarksByStandard(9,534),new TotalMarksByStandard(8,458));
        Assert.assertEquals(actualTotalMarksByStandard.toString(), StudentImplementation.getTotalMarksByStandard(StudentImplementation.getStudentList()).toString());
    }

    /**
     * 3. Get the students who scored highest in given subject
     */
    @Test
    public void highestScoredStudentsByStandard() throws Exception{
        List<HighestScoreInSubjectContainer> actualHighestScorers=Arrays.asList(new HighestScoreInSubjectContainer(10,"cooper"),
                new HighestScoreInSubjectContainer(9,"swift"),new HighestScoreInSubjectContainer(8,"tony"));
        Assert.assertEquals(actualHighestScorers.toString(), StudentImplementation.getHighestScoreInSubject(StudentImplementation.getStudentList(),"java").toString());

    }

    /**
     * 4. Get the students details who did not appear in exam
     */
    @Test
    public void studentNotAppearedForExam()throws Exception{
        List<StudentsNotAppearedContainer> actualStudents=Arrays.asList(new StudentsNotAppearedContainer("rose", "R0001"),
                new StudentsNotAppearedContainer("anthony", "R0006"));
        Assert.assertEquals(actualStudents.toString(), StudentImplementation.getStudentsNotAppeared(StudentImplementation.getStudentList()).toString());
    }

    /**
     * 5. Highest marks in each subject with name of students
     */
    @Test
    public void highestMarksInEachSubject()throws Exception{
        List<HighestMarksInEachSubject> actualHighestMarks=Arrays.asList(new HighestMarksInEachSubject(101,92,"tony"),
                new HighestMarksInEachSubject(102,95,"tony"),
                new HighestMarksInEachSubject(103,95,"kail"));
        Assert.assertEquals(actualHighestMarks.toString(),StudentImplementation.getHighestMarksInEachSubject(StudentImplementation.getStudentList()).toString());
    }

    /**
     * 6. List of "award winning" students
     */
    @Test
    public void awardWinningStudents()throws Exception{
        List<AwardWinningStudentsContainer> actualAwardContainer=Arrays.asList(new AwardWinningStudentsContainer("tony",101,92),
                new AwardWinningStudentsContainer("tony",102 ,95 ),
                new AwardWinningStudentsContainer("tony",103 ,91 ),
        new AwardWinningStudentsContainer("kail",103 ,95 ),
        new AwardWinningStudentsContainer("kate",103 ,94 ),
                new AwardWinningStudentsContainer("anthony", 102, 90));
        Assert.assertEquals(actualAwardContainer.toString(),StudentImplementation.getAwardWinningStudents(StudentImplementation.getStudentList()).toString());
    }
}