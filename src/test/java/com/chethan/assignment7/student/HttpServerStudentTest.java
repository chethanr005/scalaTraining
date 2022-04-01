package com.chethan.assignment7.student;

import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.model.headers.BasicHttpCredentials;
import akka.http.javadsl.model.headers.RawHeader;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Mar 31, 2022.
 */

public class HttpServerStudentTest extends JUnitRouteTest {
    IStudentDatabase       database = Mockito.mock(StudentDatabase.class);
    HttpServer             server   = new HttpServer(database);
    TestRoute              appRoute = testRoute(server.createRoute());
    MockHttpServerDatabase mockData = new MockHttpServerDatabase();


    @Test
    public void updateDatabaseTest() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        Mockito.when(database.updateStudentDetails(Mockito.any(Student.class))).thenReturn(MockitoStudentDataBase.updateStudent(new Student("r005", "me", 6.9, "female", 10, Arrays.asList("hiking", "cycling", "walking"))));
        String student = "{\"activities\":[\"hiking\",\"cycling\",\"walking\"],\"gender\":\"female\",\"gpa\":6.9,\"gradeLevel\":10,\"id\":\"r005\",\"name\":\"kate\"}";
        appRoute.run(HttpRequest.PUT("/student/updateStudent").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("rose", "r001"))
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

        appRoute.run(HttpRequest.POST("/student/addNewStudent").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("rose", "r001"))
                                .addHeader(RawHeader.create("apiKey", "student")).addHeader(RawHeader.create("secretKey", "e283dbb0-4ffb-4535-be21-e70b7056f444")).withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, student))).assertStatusCode(200).assertEntity(mockData.addExpectedStudent());

    }

    @Test
    public void invalidAuthenticationTest() {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());
        appRoute.run(HttpRequest.GET("/student").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("swift", "1000")))
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

        appRoute.run(HttpRequest.POST("/student/addNewStudent").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("rose", "r001"))
                                .addHeader(RawHeader.create("apiKey", "student")).addHeader(RawHeader.create("secretKey", "null")).withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, student))).assertStatusCode(StatusCodes.UNAUTHORIZED).assertEntity("Authentication is possible but has failed or not yet been provided.");


    }

    @Test
    public void addStudentFromCSVTest() throws ExecutionException, InterruptedException, IOException {
        Mockito.when(database.getStudentsData()).thenReturn(MockitoStudentDataBase.getStudentsList());

        Mockito.when(database.getStudent(Mockito.anyString())).thenReturn(MockitoStudentDataBase.getStudent("r010"), MockitoStudentDataBase.getStudent("009"), MockitoStudentDataBase.getStudent("r008"));

        Mockito.when(database.addStudent(Mockito.any(Student.class))).thenReturn(MockitoStudentDataBase
                        .addStudent(new Student("r009", "nine", 5.0, "female", 9, Arrays.asList("walking", "cycling"))), MockitoStudentDataBase.addStudent(new Student("r010", "ten", 5.0, "female", 9, Arrays.asList("walking", "cycling"))));



        Mockito.when(database.updateStudentDetails(Mockito.any(Student.class))).thenReturn(MockitoStudentDataBase.updateStudent(new Student("r005", "five", 5.0, "female", 9, Arrays.asList("walking", "cycling"))));


        String path = "\"G:\\\\Sources\\\\scala-training\\\\src\\\\main\\\\java\\\\com\\\\chethan\\\\assignment7\\\\student\\\\UpdateStudent.csv\"";
        appRoute.run(HttpRequest.POST("/student/updateDataFromCSV").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("rose", "r001"))
                                .addHeader(RawHeader.create("apiKey", "student")).addHeader(RawHeader.create("fileName", "UpdateDatabase.csv")).withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, path))).assertStatusCode(200);
                //.assertEntity("");


        String path2= "\"G:\\\\Sources\\\\scala-training\\\\src\\\\main\\\\java\\\\com\\\\chethan\\\\assignment7\\\\student\\\\StudentDatabase.csv\"";

        appRoute.run(HttpRequest.POST("/student/exportCSVFile").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("rose", "r001"))
                                .addHeader(RawHeader.create("apiKey", "student")).addHeader(RawHeader.create("fileName", "UpdateDatabase.csv")).withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, path2))).assertStatusCode(200);


        String expectedData="r001,rose,7.5,female,10,swimming;cycling\n" +
                "r002,kate,6.9,female,10,hiking;cycling;walking\n" +
                "r003,hailey,5.0,female,9,walking;cycling\n" +
                "r004,cooper,8.7,male,10,travelling;cycling;hiking;walking\n" +
                "r005,five,5.0,female,9,walking;cycling\n" +
                "r006,anthony,8.1,male,10,swimming\n" +
                "r007,kail,6.1,female,10,hiking;walking\n" +
                "r008,tony,3.6,male,8,travelling;swimming;walking\n" +
                "r009,nine,5.0,female,9,walking;cycling\n" +
                "r010,ten,5.0,female,9,walking;cycling";



        String path3="G:\\Sources\\scala-training\\src\\main\\java\\com\\chethan\\assignment7\\student\\StudentDatabase.csv";
            BufferedReader reader = new BufferedReader(new FileReader(path3));
            String line= reader.readLine();
            List<String> data= new ArrayList<String>();

            while(line!=null){
                data.add(line);
                line= reader.readLine();
            }
            String actualData=data.stream().collect(Collectors.joining("\n"));

        Assert.assertEquals(true, new File(path3).exists());
        Assert.assertEquals(expectedData,actualData);

    }
}