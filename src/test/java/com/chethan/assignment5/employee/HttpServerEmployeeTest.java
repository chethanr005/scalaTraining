package com.chethan.assignment5.employee;

import akka.http.javadsl.model.ContentType;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.ExecutionException;


/**
 * Created by Chethan on Mar 14, 2022.
 */

public class HttpServerEmployeeTest extends JUnitRouteTest {

    IEmployeeDatabase                  database =Mockito.mock(EmployeeDatabase.class);
    HttpServer server   = new HttpServer(database);
    TestRoute                                  appRoute = testRoute(server.createRoute());
    MockHttpServerDatabase                     mockData =new MockHttpServerDatabase();


    @Test
    public void welcome()  {

        appRoute.run(HttpRequest.GET("/employee")).assertStatusCode(200).assertEntity("  Welcome to Employee Database  ");
    }

    @Test
    public void allEmployeesTest()  {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        appRoute.run(HttpRequest.GET("/employee/getAllEmployees")).assertStatusCode(200).assertEntity(mockData.getExpectedAllEmployees());
    }

    @Test
    public void employeeTest()  {
        Mockito.when(database.getEmployeeById(Mockito.anyInt())).thenReturn(MockitoEmployeeDataBase.getEmployee(10));
        appRoute.run(HttpRequest.GET("/employee/10")).assertStatusCode(200).assertEntity(mockData.getExpectedStudentr10());
    }

    @Test
    public void noOfEmpByDeptContainerTest() {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        appRoute.run(HttpRequest.GET("/employee/noOfEmpByDeptContainer/cs")).assertStatusCode(200).assertEntity(mockData.getExpectedNoOfEmpByDeptContainer());
    }

    @Test
    public void groupEmployeesByDepartmentTest() {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        appRoute.run(HttpRequest.GET("/employee/groupEmployeesByDepartment")).assertStatusCode(200).assertEntity(mockData.getExpectedGroupEmployeesByDepartment());
    }

    @Test
    public void increaseEmployeesSalaryTest() {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        Mockito.when(database.updateEmployeeDetailsById(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(3, "salary", "402500")).thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(8, "salary", "437000")).thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(9, "salary", "345000"));
        appRoute.run(HttpRequest.PUT("/employee/increaseEmployeesSalary?department=ec&hike=15.0")).assertStatusCode(200).assertEntity(mockData.getExpectedIncreasedSalaryEmployees());
    }

    @Test
    public void promoteEmployeesTest() {

        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        Mockito.when(database.updateEmployeeDetailsById(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(4, "jobLevel", "senior")).thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(5,"jobLevel", "senior"));
        appRoute.run(HttpRequest.PUT("/employee/promoteEmployees")).assertStatusCode(200).assertEntity(mockData.getExpectedPromotedEmployees());
    }


    @Test
    public void updateEmployeeTest() {
        Mockito.when(database.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        Mockito.when(database.updateEmployeeDetailsById(Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(7, "name","me"));
        appRoute.run(HttpRequest.PUT("/employee/updateEmployee?eid=7&column=name&value=me")).assertStatusCode(200).assertEntity(mockData.updateEmployee());
    }

    @Test
    public void addEmployeeTest() throws IllegalAccessException, ExecutionException, InterruptedException {
        Mockito.when(database.addEmployee(Mockito.any(Employee.class))).thenReturn(MockitoEmployeeDataBase.addMockitoEmployeee(new Employee(15,"me","ee",250000,"male","2021-12-01","2000-10-25","junior")));
        String inputEmployee="{\n" +
                "    \"department\": \"ee\"," +
                "    \"dob\": \"2000-10-25\"," +
                "    \"gender\": \"male\"," +
                "    \"jobLevel\": \"junior\"," +
                "    \"joiningDate\": \"2021-12-01\"," +
                "    \"name\": \"me\"," +
                "    \"salary\": 250000.0," +
                "    \"id\": 15" +
                "}";


        appRoute.run(HttpRequest.POST("/employee/addNewEmployee").withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON,inputEmployee))).assertStatusCode(200).assertEntity(mockData.addEmployee());
    }


}