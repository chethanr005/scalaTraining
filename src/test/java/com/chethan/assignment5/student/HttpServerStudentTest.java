package com.chethan.assignment5.student;


import akka.http.javadsl.model.ContentType;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * Created by Chethan on Mar 14, 2022.
 */

public class HttpServerStudentTest extends JUnitRouteTest {
    IStudentDatabase database= Mockito.mock(StudentDatabase.class);
    HttpServer server= new HttpServer(database);
    TestRoute appRoute = testRoute(server.createRoute());
    MockHttpServerDatabase mockData=new MockHttpServerDatabase();


    @Test
    public void welcome()  {
        appRoute.run(HttpRequest.GET("/student")).assertStatusCode(200).assertEntity("  Welcome to Student Database  ");

    }

    @Test
    public void getStudentTest()  {
        Mockito.when(database.getStudent(Mockito.any(String.class))).thenReturn(MockitoStudentDataBase.getStudent("r010"));
        appRoute.run(HttpRequest.GET("/student/r010")).assertStatusCode(200).assertEntity(mockData.getExpectedStudentr001());
    }


    @Test
    public void getAllStudentsTest()  {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/getAllStudents")).assertStatusCode(200).assertEntity(mockData.getExpectedAllStudents());
    }

    @Test
    public void maleAndFemaleContainerTest()  {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/maleAndFemaleContainer")).assertStatusCode(200).assertEntity(mockData.getExpectedMaleAndFemaleContainer());
    }

    @Test
    public void prefixNamesTest()  {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/prefixNames")).assertStatusCode(200).assertEntity(mockData.getExpectedPrefixNames());
    }


    @Test
    public void gradeLevelContainerTest() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/gradeLevelContainer/10")).assertStatusCode(200).assertEntity(mockData.getExpectedGradeLevelContainer());
    }


    @Test
    public void gradeLevelContainerTest2() {

        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/gradeLevelContainer2")).assertStatusCode(200).assertEntity(mockData.getExpectedGradeLevelContainer2());

    }

    @Test
    public void activityContainerTest() {

        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/activityContainer/running")).assertStatusCode(200).assertEntity(mockData.getExpectedActivityContainer());
    }


    @Test
    public void activityContainerTest2() {

        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/activityContainer2")).assertStatusCode(200).assertEntity(mockData.getExpectedActivityContainer2());
    }

    @Test
    public void performanceContainerTest() {

        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/performanceContainer/poor")).assertStatusCode(200).assertEntity(mockData.getExpectedPerformanceContainer());
    }

    @Test
    public void performanceContainerTest2() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/performanceContainer2")).assertStatusCode(200).assertEntity(mockData.getExpectedPerformanceContainer2());
    }


    @Test
    public void updateDatabaseTest() {
     Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
     Mockito.when(database.updateStudentDetails(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(MockitoStudentDataBase.updateStudent("r005","name","me"));
     appRoute.run(HttpRequest.PUT("/student/updateStudent?sid=r010&column=name&value='me'")).assertStatusCode(200).assertEntity(mockData.getExpectedUpdatedStudent());
    }

    @Test
    public void addEmployeeTest() throws ExecutionException, InterruptedException {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        Mockito.when(database.addStudent(Mockito.any(Student.class))).thenReturn(MockitoStudentDataBase.addStudent(new Student("r011","me",5.0,"female",9, Arrays.asList("walking","cycling"))));

        String student="{" +
                "    \"activities\": [" +
                "        \"walking\"," +
                "        \"cycling\"" +
                "    ]," +
                "    \"gender\": \"female\"," +
                "    \"gpa\": 5.0," +
                "    \"gradeLevel\": 9," +
                "    \"id\": \"r011\"," +
                "    \"name\": \"me\"" +
                "}";

        appRoute.run(HttpRequest.POST("/student/addNewStudent").withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON,student))).assertStatusCode(200).assertEntity(mockData.addExpectedEmployee());


    }

}