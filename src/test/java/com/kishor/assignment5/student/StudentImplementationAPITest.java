package com.kishor.assignment5.student;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

/**
 * Created by Kishor on Mar 14, 2022.
 */

public class StudentImplementationAPITest extends JUnitRouteTest {

    IStudent         database         = Mockito.mock(StudentDatabase.class);
    StudentMockitoDB studentMockitoDB = new StudentMockitoDB();
    TestRoute        appRoute         = testRoute(new StudentImplementation(database).createRoute());

    @Test
    public void testgetStudentById() throws SQLException {
        Mockito.when(database.getStudentById(Mockito.anyInt())).thenReturn(studentMockitoDB.getMockStudentById(1001));
        appRoute.run(HttpRequest.GET("/student/getStudentByRegNo/1001")).assertEntity("{\"activities\":[\"Painting\"],\"gender\":\"male\",\"gpa\":4.6,\"gradeLevel\":2,\"name\":\"Alex\",\"regNo\":1001}");
    }

    @Test
    public void testgetAllStudents() throws SQLException {
        Mockito.when(database.getAllStudents()).thenReturn(studentMockitoDB.getMockAllStudents());
        appRoute.run(HttpRequest.GET("/student/getAllStudents")).assertEntity("[{\"activities\":[\"Painting\"],\"gender\":\"male\",\"gpa\":4.6,\"gradeLevel\":2,\"name\":\"Alex\",\"regNo\":1001},{\"activities\":[\"Swimming\"," +
                "\"Basketball\",\"Volleyball\"],\"gender\":\"male\",\"gpa\":5.9,\"gradeLevel\":3,\"name\":\"Mahesh\",\"regNo\":1003},{\"activities\":[\"Swimming\",\"Basketball\",\"Baseball\",\"Football\"],\"gender\":\"male\",\"gpa\":3.9," +
                "\"gradeLevel\":4,\"name\":\"James\",\"regNo\":1004},{\"activities\":[\"Painting\",\"Running\",\"Soccer\"],\"gender\":\"male\",\"gpa\":5.6,\"gradeLevel\":5,\"name\":\"Kenny\",\"regNo\":1011},{\"activities\":[\"Swimming\",\"Dancing\",\"Football\"],\"gender\":\"female\",\"gpa\":7.5,\"gradeLevel\":4,\"name\":\"Lisba\",\"regNo\":1012},{\"activities\":[\"Swimming\",\"Gymnastics\",\"Aerobics\"],\"gender\":\"female\",\"gpa\":6.0,\"gradeLevel\":3,\"name\":\"Emma\",\"regNo\":1048}]");
    }

    @Test
    public void testmaleAndFemaleCount() throws SQLException {
        Mockito.when(database.getAllStudents()).thenReturn(studentMockitoDB.getMockAllStudents());
        appRoute.run(HttpRequest.GET("/student/maleAndFemaleCount")).assertEntity("{\"females\":2,\"males\":4}");
    }

    @Test
    public void testaddPrefixToStudents() throws SQLException {
        Mockito.when(database.getAllStudents()).thenReturn(studentMockitoDB.getMockAllStudents());
        appRoute.run(HttpRequest.GET("/student/addPrefixToStudents")).assertEntity("[\"Mr Alex\",\"Mr Mahesh\",\"Mr James\",\"Mr Kenny\",\"Ms Lisba\",\"Ms Emma\"]");
    }

    @Test
    public void testgetAllGradeLevel() throws SQLException {
        Mockito.when(database.getAllStudents()).thenReturn(studentMockitoDB.getMockAllStudents());
        appRoute.run(HttpRequest.GET("/student/getAllGradeLevel")).assertEntity("[{\"gradeLevel\":2,\"students\":1},{\"gradeLevel\":3,\"students\":2},{\"gradeLevel\":4,\"students\":2},{\"gradeLevel\":5,\"students\":1}]");
    }

    @Test
    public void testgetNoOfStudentsBelongsToEachActivities() throws SQLException {
        Mockito.when(database.getAllStudents()).thenReturn(studentMockitoDB.getMockAllStudents());
        appRoute.run(HttpRequest.GET("/student/getNoOfStudentsBelongsToEachActivities")).assertEntity("[{\"activity\":\"Painting\",\"students\":2},{\"activity\":\"Swimming\",\"students\":4},{\"activity\":\"Basketball\",\"students\":2},{\"activity\":\"Volleyball\",\"students\":1},{\"activity\":\"Baseball\",\"students\":1},{\"activity\":\"Football\",\"students\":2},{\"activity\":\"Running\",\"students\":1},{\"activity\":\"Soccer\",\"students\":1},{\"activity\":\"Dancing\",\"students\":1},{\"activity\":\"Gymnastics\",\"students\":1},{\"activity\":\"Aerobics\",\"students\":1}]");
    }

    @Test
    public void testgetPerformanceOfStudents() throws SQLException {
        Mockito.when(database.getAllStudents()).thenReturn(studentMockitoDB.getMockAllStudents());
        appRoute.run(HttpRequest.GET("/student/getPerformanceOfStudents")).assertEntity("[{\"level\":\"poor\",\"students\":[{\"activities\":[\"Swimming\",\"Basketball\",\"Baseball\",\"Football\"],\"gender\":\"male\",\"gpa\":3.9,\"gradeLevel\":4,\"name\":\"James\",\"regNo\":1004}]},{\"level\":\"average\",\"students\":[{\"activities\":[\"Painting\"],\"gender\":\"male\",\"gpa\":4.6,\"gradeLevel\":2,\"name\":\"Alex\",\"regNo\":1001},{\"activities\":[\"Swimming\",\"Basketball\",\"Volleyball\"],\"gender\":\"male\",\"gpa\":5.9,\"gradeLevel\":3,\"name\":\"Mahesh\",\"regNo\":1003},{\"activities\":[\"Painting\",\"Running\",\"Soccer\"],\"gender\":\"male\",\"gpa\":5.6,\"gradeLevel\":5,\"name\":\"Kenny\",\"regNo\":1011},{\"activities\":[\"Swimming\",\"Gymnastics\",\"Aerobics\"],\"gender\":\"female\",\"gpa\":6.0,\"gradeLevel\":3,\"name\":\"Emma\",\"regNo\":1048}]},{\"level\":\"excellent\",\"students\":[{\"activities\":[\"Swimming\",\"Dancing\",\"Football\"],\"gender\":\"female\",\"gpa\":7.5,\"gradeLevel\":4,\"name\":\"Lisba\",\"regNo\":1012}]}]");
    }

}