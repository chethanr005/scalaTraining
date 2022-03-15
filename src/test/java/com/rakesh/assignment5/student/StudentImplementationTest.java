package com.rakesh.assignment5.student;

import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import akka.http.scaladsl.model.ContentType;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Rakesh on Mar 14, 2022.
 */

public class StudentImplementationTest extends JUnitRouteTest {
    IStudentDatabase     iStudentDatabase = Mockito.mock(IStudentDatabase.class);
    MockStudentApiResult expected         = new MockStudentApiResult();
    TestRoute            appRoute         = testRoute(new StudentImplementation(iStudentDatabase).getRoutes());
    StudentMockData      mockData         = new StudentMockData();

    //get all students
    @Test
    public void getAllStudentApiTest() throws InterruptedException {

        CompletableFuture<List<Student>> students = mockData.getAllStudents();
        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);
        appRoute.run(HttpRequest.GET("/student/getAllStudents")).assertStatusCode(200).assertEntity(expected.getAllStudents());
    }

    //get students by ID
    @Test
    public void getStudentByIDTest() {
        //get Employee by ID
        Student std = new Student(1001, "Ajay", 1, 6.7, "male", Arrays.asList("basketball", "cricket"));
        Mockito.when(iStudentDatabase.getStudentByID(Mockito.anyInt(), Mockito.anyObject())).thenReturn(CompletableFuture.completedFuture(std));
        appRoute.run(HttpRequest.GET("/student/getStudentByID/1001")).assertStatusCode(200).assertEntity(expected.getStudentByID());
    }

    //male and female container
    @Test
    public void maleAndFemale() {
        CompletableFuture<List<Student>> students = mockData.getAllStudents();
        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);
        appRoute.run(HttpRequest.GET("/student/maleFemaleCount")).assertStatusCode(200).assertEntity(expected.maleAndFemaleCount());
    }

    //Add prefix name
    @Test
    public void prefixName() {
        CompletableFuture<List<Student>> students = mockData.getAllStudents();
        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);
        appRoute.run(HttpRequest.GET("/student/prefixName")).assertStatusCode(200).assertEntity(expected.prefixNames());
    }

    //Count of students by gradeLevel
    @Test
    public void countInAllGrade() {
        CompletableFuture<List<Student>> students = mockData.getAllStudents();
        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);
        appRoute.run(HttpRequest.GET("/student/countInAllGrade")).assertStatusCode(200).assertEntity(expected.countInAllGrade());

        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);
        appRoute.run(HttpRequest.GET("/student/countByGrade/1")).assertStatusCode(200).assertEntity(expected.countByGrade());

    }

    //student count in activities
    @Test
    public void countInAllActivities() {
        CompletableFuture<List<Student>> students = mockData.getAllStudents();
        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);
        appRoute.run(HttpRequest.GET("/student/countInAllActivities")).assertStatusCode(200).assertEntity(expected.countInAllActivities());

        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);
        appRoute.run(HttpRequest.GET("/student/countByActivity/chess")).assertStatusCode(200).assertEntity(expected.countByActivity());
    }

    // students grouped by performance poor,average,excellent
    @Test
    public void performance() {
        CompletableFuture<List<Student>> students = mockData.getAllStudents();
        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);
        appRoute.run(HttpRequest.GET("/student/performance")).assertStatusCode(200).assertEntity(expected.performance());

        Mockito.when(iStudentDatabase.getAllStudents(Mockito.anyObject())).thenReturn(students);
        appRoute.run(HttpRequest.GET("/student/performanceByLevel/poor")).assertStatusCode(200).assertEntity(expected.performanceByLevel());
    }

    //Updating  gradeLevel to 1 for student with regNo 1001.
    @Test
    public void updateData() {
        Mockito.when(iStudentDatabase.updateStudent(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
        appRoute.run(HttpRequest.PUT("/student/update?columnName=GradeLevel&newValue=1&regNo=1001")).assertStatusCode(200).assertEntity("OK");
    }

    //Deleting student by ID
    @Test
    public void deleteData()
    {
        //for valid regNo
        Mockito.when(iStudentDatabase.deleteStudentByID(Mockito.anyInt())).thenReturn(true);
        appRoute.run(HttpRequest.DELETE("/student/deleteByID/1001")).assertEntity("OK").assertStatusCode(200);

        //for Invalid regNo
        Mockito.when(iStudentDatabase.deleteStudentByID(Mockito.anyInt())).thenReturn(false);
        appRoute.run(HttpRequest.DELETE("/student/deleteByID/1045")).assertStatusCode(400);
    }

    //add new Student
    @Test
    public void addNewStudent()
    {
     //for valid student detail
        String mockStd="{\"activities\":[\"painting\",\"chess\",\"soccer\"],\"gender\":\"male\",\"gpa\":8.9,\"gradeLevel\":10,\"name\":\"Edwin\",\"regNo\":1012}";
        Mockito.when(iStudentDatabase.addNewStudent(Mockito.anyObject())).thenReturn(true);
        appRoute.run(HttpRequest.POST("/student/addStudent").withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON,mockStd))).assertStatusCode(200).assertEntity("OK");
    }
}