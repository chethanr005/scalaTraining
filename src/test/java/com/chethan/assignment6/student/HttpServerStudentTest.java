package com.chethan.assignment6.student;


import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.model.headers.BasicHttpCredentials;
import akka.http.javadsl.model.headers.RawHeader;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
/**
 * Created by Chethan on Mar 22, 2022.
 */

public class HttpServerStudentTest extends JUnitRouteTest {
    IStudentDatabase       database = Mockito.mock(StudentDatabase.class);
    HttpServer             server   = new HttpServer(database);
    TestRoute              appRoute = testRoute(server.createRoute());
    MockHttpServerDatabase mockData = new MockHttpServerDatabase();



    @Test
    public void getStudentTest() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        Mockito.when(database.getStudent(Mockito.any(String.class))).thenReturn(MockitoStudentDataBase.getStudent("r010"));

        appRoute.run(HttpRequest.GET("/student/r010").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008")))
                .assertStatusCode(200).assertEntity(mockData.getExpectedStudentr001());
    }


    @Test
    public void getAllStudentsTest() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008")))
                .assertStatusCode(200).assertEntity(mockData.getExpectedAllStudents());
    }

    @Test
    public void maleAndFemaleContainerTest() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/maleAndFemaleContainer").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008")))
                .assertStatusCode(200).assertEntity(mockData.getExpectedMaleAndFemaleContainer());
    }

    @Test
    public void prefixNamesTest() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/prefixNames").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008")))
                .assertStatusCode(200).assertEntity(mockData.getExpectedPrefixNames());
    }


    @Test
    public void gradeLevelContainerTest() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/gradeLevelContainer/10").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008")))
                .assertStatusCode(200).assertEntity(mockData.getExpectedGradeLevelContainer());
    }


    @Test
    public void gradeLevelContainerTest2() {

        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/gradeLevelContainer2").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008"))).assertStatusCode(200).assertEntity(mockData.getExpectedGradeLevelContainer2());

    }

    @Test
    public void activityContainerTest() {

        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/activityContainer/running").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008"))).assertStatusCode(200).assertEntity(mockData.getExpectedActivityContainer());
    }


    @Test
    public void activityContainerTest2() {

        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/activityContainer2").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008"))).assertStatusCode(200).assertEntity(mockData.getExpectedActivityContainer2());
    }

    @Test
    public void performanceContainerTest() {

        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/performanceContainer/poor").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008"))).assertStatusCode(200).assertEntity(mockData.getExpectedPerformanceContainer());
    }

    @Test
    public void performanceContainerTest2() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student/performanceContainer2").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008"))).assertStatusCode(200).assertEntity(mockData.getExpectedPerformanceContainer2());
    }


    @Test
    public void updateDatabaseTest() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        Mockito.when(database.updateStudentDetails(Mockito.any(Student.class))).thenReturn(MockitoStudentDataBase.updateStudent(new Student("r005", "me", 6.9, "female", 10, Arrays.asList("hiking", "cycling", "walking"))));
        String student = "{\"activities\":[\"hiking\",\"cycling\",\"walking\"],\"gender\":\"female\",\"gpa\":6.9,\"gradeLevel\":10,\"id\":\"r005\",\"name\":\"kate\"}";
        appRoute.run(HttpRequest.PUT("/student/updateStudent").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008"))
                                .addHeader(RawHeader.create("apiKey", "student")).addHeader(RawHeader.create("secretKey", "e283dbb0-4ffb-4535-be21-e70b7056f444")).withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, student)))
                .assertStatusCode(200).assertEntity(mockData.getExpectedUpdatedStudent());
    }

    @Test
    public void addStudentTest() throws ExecutionException, InterruptedException {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        Mockito.when(database.addStudent(Mockito.any(Student.class))).thenReturn(MockitoStudentDataBase.addStudent(new Student("r011", "me", 5.0, "female", 9, Arrays.asList("walking", "cycling"))));

        String student = "{" +
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

        appRoute.run(HttpRequest.POST("/student/addNewStudent").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008"))
                                .addHeader(RawHeader.create("apiKey", "student")).addHeader(RawHeader.create("secretKey", "e283dbb0-4ffb-4535-be21-e70b7056f444")).withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, student))).assertStatusCode(200).assertEntity(mockData.addExpectedStudent());


    }

    @Test
    public void filterTest() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        String filterObject = "{\"performance\": \"average\"," +
                "    \"gender\": \"female\"," +
                "    \"activities\": \"running\"," +
                "    \"gradeLevel\": 9}";
        appRoute.run(HttpRequest.POST("/student/filterStudents").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","r008"))
                                .withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, filterObject))).assertStatusCode(200).assertEntity(mockData.getFilteredStudent());
    }

    @Test
    public void invalidAuthenticationTest() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift","1000")))
                .assertStatusCode(StatusCodes.UNAUTHORIZED).assertEntity("The supplied authentication is invalid");
    }


    @Test
    public void invalidHeaderTest() throws ExecutionException, InterruptedException {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        Mockito.when(database.addStudent(Mockito.any(Student.class))).thenReturn(MockitoStudentDataBase.addStudent(new Student("r011", "me", 5.0, "female", 9, Arrays.asList("walking", "cycling"))));

        String student = "{" +
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

        appRoute.run(HttpRequest.POST("/student/addNewStudent").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift", "r008"))
                                .addHeader(RawHeader.create("apiKey", "student")).addHeader(RawHeader.create("secretKey", "null")).withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, student))).assertStatusCode(StatusCodes.UNAUTHORIZED).assertEntity("Authentication is possible but has failed or not yet been provided.");


    }
}
