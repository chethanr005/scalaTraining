//package com.kishor.assignment1.Student;
//
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Created by Kartik on Jan 28, 2022.
// */
//
//public class StudentImplTest {
//
//    List<Student> studentsDatabase = Arrays.asList();
//
//
//    @Test
//    public void getMalesFemalesWhenEmptyListIsPassed() {
//        MaleAndFemaleContainer maleAndFemaleStudents = StudentImpl.getMaleAndFemaleStudents(studentsDatabase);
//
//        Assert.assertEquals(0, maleAndFemaleStudents.males);
//        Assert.assertEquals(0, maleAndFemaleStudents.females);
//
//    }
//
//    @Test
//    public void getMalesFemales() {
//        MaleAndFemaleContainer maleAndFemaleStudents = StudentImpl.getMaleAndFemaleStudents(studentsDatabase);
//
//        Assert.assertEquals(0, maleAndFemaleStudents.males);
//        Assert.assertEquals(0, maleAndFemaleStudents.females);
//
//    }
//
//    @Test
//    public void getMalesFemales2() {
//        MaleAndFemaleContainer maleAndFemaleStudentsWithEmptyList = StudentImpl.getMaleAndFemaleStudents(studentsDatabase);
//
//        Assert.assertEquals(0, maleAndFemaleStudentsWithEmptyList.males);
//        Assert.assertEquals(0, maleAndFemaleStudentsWithEmptyList.females);
//
//        /////////////////////////////////////////////////
//        MaleAndFemaleContainer maleAndFemaleStudentsWithOutEmptyList = StudentImpl.getMaleAndFemaleStudents(studentsDatabase); // pass correct value
//
//        Assert.assertEquals(1, maleAndFemaleStudentsWithOutEmptyList.males);
//        Assert.assertEquals(1, maleAndFemaleStudentsWithOutEmptyList.females);
//
//
//    }
//
//
//}