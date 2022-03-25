package com.chethan.assignment6.employee;

import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.model.headers.BasicHttpCredentials;
import akka.http.javadsl.model.headers.HttpCredentials;
import akka.http.javadsl.model.headers.RawHeader;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import com.chethan.assignment6.student.MockitoStudentDataBase;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;


/**
 * Created by Chethan on Mar 14, 2022.
 */

public class HttpServerEmployeeTest extends JUnitRouteTest {

    IEmployeeDatabase      database = Mockito.mock(EmployeeDatabase.class);
    HttpServer             server   = new HttpServer(database);
    TestRoute              appRoute = testRoute(server.createRoute());
    MockHttpServerDatabase mockData = new MockHttpServerDatabase();

    @Test
    public void allEmployeesTest() {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        appRoute.run(HttpRequest.GET("/employee").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("cooper",String.valueOf("11")))).assertStatusCode(200).assertEntity(mockData.getExpectedAllEmployees());
    }

    @Test
    public void employeeTest() {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        Mockito.when(database.getEmployeeById(Mockito.anyInt())).thenReturn(MockitoEmployeeDataBase.getEmployee(10));
        appRoute.run(HttpRequest.GET("/employee/10").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("cooper",String.valueOf("11")))).assertStatusCode(200).assertEntity(mockData.getExpectedEmployee10());
    }

    @Test
    public void noOfEmpByDeptContainerTest() {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        appRoute.run(HttpRequest.GET("/employee/noOfEmpByDeptContainer/cs").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("cooper",String.valueOf("11")))).assertStatusCode(200).assertEntity(mockData.getExpectedNoOfEmpByDeptContainer());
    }

    @Test
    public void groupEmployeesByDepartmentTest() {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        appRoute.run(HttpRequest.GET("/employee/groupEmployeesByDepartment").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("cooper",String.valueOf("11")))).assertStatusCode(200).assertEntity(mockData.getExpectedGroupEmployeesByDepartment());
    }

    @Test
    public void increaseEmployeesSalaryTest() {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        Mockito.when(database.updateEmployeeDetailsById(Mockito.any(Employee.class))).thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(new Employee(3, "julie", "ec", 350000, "female", LocalDate.of(2014, 7, 24), LocalDate.of(1993, 3, 10), "mid"))).thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(new Employee(9, "andrew", "ec", 300000, "male", LocalDate.of(2021, 6, 26), LocalDate.of(1997, 7, 19), "junior"))).thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(new Employee(8, "anthony", "ec", 380000, "male", LocalDate.of(2019, 5, 12), LocalDate.of(1995, 3, 21), "junior")));
        appRoute.run(HttpRequest.PUT("/employee/increaseEmployeesSalary?department=ec&hike=15.0").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("cooper",String.valueOf("11")))
                .addHeader(RawHeader.create("apiKey", "employee")).addHeader(RawHeader.create("secretKey", "e283dbb0-4ffb-4535-be21-e70b7056f444"))).assertStatusCode(200).assertEntity(mockData.getExpectedIncreasedSalaryEmployees());
    }

    @Test
    public void promoteEmployeesTest() {

        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        Mockito.when(database.updateEmployeeDetailsById(Mockito.any(Employee.class))).thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(new Employee(4, "tony", "cs", 250000, "male", LocalDate.of(2010, 4, 4),
                LocalDate.of(1989, 8, 23), "senior"))).thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(new Employee(5, "kail", "cs", 450000, "female", LocalDate.of(2012, 12, 28), LocalDate.of(1990, 9, 10),
                "senior")));
        appRoute.run(HttpRequest.PUT("/employee/promoteEmployees").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("cooper",String.valueOf("11")))
                                .addHeader(RawHeader.create("apiKey", "employee")).addHeader(RawHeader.create("secretKey", "e283dbb0-4ffb-4535-be21-e70b7056f444"))).assertStatusCode(200).assertEntity(mockData.getExpectedPromotedEmployees());
    }


    @Test
    public void updateEmployeeTest() {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        Mockito.when(database.updateEmployeeDetailsById(Mockito.any(Employee.class))).thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(new Employee(7, "me", "ee", 250000, "male", LocalDate.of(2021, 12, 1),
                LocalDate.of(2000, 10, 25), "junior")));
        String updatedEmployee = "{\"department\":\"ee\",\"dob\":\"2000-10-25\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2021-12-01\",\"name\":\"joe\",\"salary\":250000.0,\"id\":7}";
        appRoute.run(HttpRequest.PUT("/employee/updateEmployee").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("cooper",String.valueOf("11")))
                                .addHeader(RawHeader.create("apiKey", "employee")).addHeader(RawHeader.create("secretKey", "e283dbb0-4ffb-4535-be21-e70b7056f444")).withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, updatedEmployee))).assertStatusCode(200).assertEntity(mockData.updateEmployee());
    }

    @Test
    public void addEmployeeTest() throws IllegalAccessException, ExecutionException, InterruptedException {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        Mockito.when(database.addEmployee(Mockito.any(Employee.class))).thenReturn(MockitoEmployeeDataBase.addMockitoEmployee(new Employee(15, "me", "ee", 250000, "male", LocalDate.of(2021, 12, 01), LocalDate.of(2000, 10, 25), "junior")));
        String inputEmployee = "{\n" +
                "    \"department\": \"ee\"," +
                "    \"dob\": \"2000-10-25\"," +
                "    \"gender\": \"male\"," +
                "    \"jobLevel\": \"junior\"," +
                "    \"joiningDate\": \"2021-12-01\"," +
                "    \"name\": \"me\"," +
                "    \"salary\": 250000.0," +
                "    \"id\": 15" +
                "}";


        appRoute.run(HttpRequest.POST("/employee/addNewEmployee").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("cooper",String.valueOf("11")))
                                .addHeader(RawHeader.create("apiKey", "employee")).addHeader(RawHeader.create("secretKey", "e283dbb0-4ffb-4535-be21-e70b7056f444")).withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, inputEmployee))).assertStatusCode(200).assertEntity(mockData.addEmployee());
    }

    @Test
    public void filterEmployeeTest()  {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        String filterObject="{ \"department\": \"ee\"," +
                "    \"startDate\": \"1996-05-02\"," +
                "    \"gender\": \"female\"," +
                "    \"jobLevel\": \"junior\"," +
                "    \"endDate\": \"2020-07-12\"" +
                "}";

        appRoute.run(HttpRequest.POST("/employee/getFilteredEmployees").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("cooper",String.valueOf("11"))).withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON,
                filterObject))).assertStatusCode(200).assertEntity(mockData.getFilteredEmployee());

    }

    @Test
    public void invalidAuthenticationTest() {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        appRoute.run(HttpRequest.GET("/employee").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("cooper",String.valueOf("100")))).assertStatusCode(StatusCodes.UNAUTHORIZED).assertEntity("The supplied authentication is invalid");
    }


    @Test
    public void invalidHeaderTest() throws IllegalAccessException, ExecutionException, InterruptedException {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        Mockito.when(database.addEmployee(Mockito.any(Employee.class))).thenReturn(MockitoEmployeeDataBase.addMockitoEmployee(new Employee(15, "me", "ee", 250000, "male", LocalDate.of(2021, 12, 01), LocalDate.of(2000, 10, 25), "junior")));
        String inputEmployee = "{\n" +
                "    \"department\": \"ee\"," +
                "    \"dob\": \"2000-10-25\"," +
                "    \"gender\": \"male\"," +
                "    \"jobLevel\": \"junior\"," +
                "    \"joiningDate\": \"2021-12-01\"," +
                "    \"name\": \"me\"," +
                "    \"salary\": 250000.0," +
                "    \"id\": 15" +
                "}";


        appRoute.run(HttpRequest.POST("/employee/addNewEmployee").addCredentials(BasicHttpCredentials.createBasicHttpCredentials("cooper",String.valueOf("11")))
                                .addHeader(RawHeader.create("apiKey", "employee")).addHeader(RawHeader.create("secretKey", "null")).withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, inputEmployee))).assertStatusCode(StatusCodes.UNAUTHORIZED).assertEntity("Authentication is possible but has failed or not yet been provided.");


    }


}