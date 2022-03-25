package com.rakesh.assignment5.employee;

import akka.http.javadsl.model.ContentType;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * Created by Rakesh on Mar 14, 2022.
 */

public class EmployeeImplementationTest extends JUnitRouteTest {

    IEmployeeDataBase iEmployeeDataBase = Mockito.mock(EmployeeDataBase.class);
    EmployeeMockData  mockData          = new EmployeeMockData();
    MockApiResult     expected          = new MockApiResult();
    TestRoute         appRoute          = testRoute(new EmployeeImplementation(iEmployeeDataBase).getRoutes());

    //get all employee
    @Test
    public void getAllEmployees() throws InterruptedException {
        Mockito.when(iEmployeeDataBase.getAllEmployee(Mockito.anyObject())).thenReturn(mockData.getAllEmployees());
        appRoute.run(HttpRequest.GET("/employee/getAllEmployee")).assertStatusCode(200).assertEntity(expected.getAllEmployee());
    }

    //get Employee by ID
    @Test
    public void getEmployeeByID() {
        Employee emp = new Employee(1001, "John", "IT Development", 35000.0, "male", LocalDate.of(2021,8,11),LocalDate.of(1998,5,12), "Junior");
        Mockito.when(iEmployeeDataBase.getEmployeeByID(Mockito.anyInt(), Mockito.anyObject())).thenReturn(CompletableFuture.completedFuture(emp));
        appRoute.run(HttpRequest.GET("/employee/getEmployeeByID/1001")).assertStatusCode(200).assertEntity(expected.getByID());
    }
//    Thread.sleep(100);


    //get employee count for given department
    @Test
    public void employeesCountByDept() {
        Mockito.when(iEmployeeDataBase.getAllEmployee(Mockito.anyObject())).thenReturn(mockData.getAllEmployees());
        appRoute.run(HttpRequest.GET("/employee/employeesCountByDept/Administration")).assertStatusCode(200).assertEntity("{\"department\":\"Administration\",\"empCount\":1}");

        //for invalid department
        appRoute.run(HttpRequest.GET("/employee/employeesCountByDept/Hello")).assertStatusCode(404).assertEntity("Not Found");
    }

    //get group of employees based on department
    @Test
    public void groupEmployee() {
        Mockito.when(iEmployeeDataBase.getAllEmployee(Mockito.anyObject())).thenReturn(mockData.getAllEmployees());
        appRoute.run(HttpRequest.GET("/employee/employeeGroupByDept")).assertStatusCode(200).assertEntity(expected.employeeGroup());
    }

    //get promoted employee
    @Test
    public void promotedEmployee() throws ExecutionException, InterruptedException {
        //to get all employee
        Mockito.when(iEmployeeDataBase.getAllEmployee(Mockito.anyObject())).thenReturn(mockData.getAllEmployees());
        //to update employee jobLevel
        Mockito.when(iEmployeeDataBase.updateEmployeeData(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(mockData.updateData("JobLevel", "Senior", 1004), mockData.updateData("JobLevel", "Senior", 1003),
                mockData.updateData("JobLevel", "Senior", 1002), mockData.updateData("JobLevel", "Senior", 1001));
        //to get Employee by ID
        Mockito.when(iEmployeeDataBase.getEmployeeByID(Mockito.anyInt(), Mockito.anyObject())).thenReturn(mockData.getEmployeeByID(1004), mockData.getEmployeeByID(1003), mockData.getEmployeeByID(1002), mockData.getEmployeeByID(1001));

        appRoute.run(HttpRequest.GET("/employee/promotedEmployees")).assertStatusCode(200).assertEntity(expected.promotedEmployee());
    }

    //to get Hiked employee
    @Test
    public void hikedEmployee() {
        Mockito.when(iEmployeeDataBase.getAllEmployee(Mockito.any(ExecutorService.class))).thenReturn(mockData.getAllEmployees());
        appRoute.run(HttpRequest.PUT("/employee/hikeEmployeeByDept?dept=Administration&hike=0.0")).assertStatusCode(200).assertEntity(expected.hikedEmployee());
    }

    //to Update an employee data
    @Test
    public void updateEmployee() throws ExecutionException, InterruptedException {
        Mockito.when(iEmployeeDataBase.updateEmployeeData(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
        Mockito.when(iEmployeeDataBase.updateEmployeeData(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(mockData.updateData("Name", "ABC", 1004), mockData.updateData("Name", "ABC", 1003),
                mockData.updateData("Name", "ABC", 1002), mockData.updateData("Name", "ABC", 1001));
        appRoute.run(HttpRequest.PUT("/employee/update?columnName=Name&newValue=ABC&empID=1001")).assertStatusCode(200).assertEntity("OK");

        //for invalid empID
        Mockito.when(iEmployeeDataBase.updateEmployeeData(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(false);
        appRoute.run(HttpRequest.PUT("/employee/update?columnName=Name&newValue=ABC&empID=1023")).assertStatusCode(400).assertEntity("The request contains bad syntax or cannot be fulfilled.");
    }
    //Deleting employee by ID
    @Test
    public void deleteData()
    {
        //for valid regNo
        Mockito.when(iEmployeeDataBase.deleteEmployeeByID(Mockito.anyInt())).thenReturn(true);
        appRoute.run(HttpRequest.DELETE("/employee/deleteByID/1001")).assertEntity("OK").assertStatusCode(200);

        //for Invalid regNo
        Mockito.when(iEmployeeDataBase.deleteEmployeeByID(Mockito.anyInt())).thenReturn(false);
        appRoute.run(HttpRequest.DELETE("/employee/deleteByID/1045")).assertStatusCode(400);
    }
    //adding new employee
    @Test
    public void addData()
    {
        String data="{\n" +
                "    \"department\": \"IT Development\"," +
                "    \"dob\": \"2000-01-01\"," +
                "    \"empID\": 1011," +
                "    \"gender\": \"male\"," +
                "    \"jobLevel\": \"Junior\"," +
                "    \"joiningDate\":\"2020-11-11\"," +
                "    \"name\": \"John\"," +
                "    \"salary\": 45000.0" +
                "}";
        //for valid employee
        Mockito.when(iEmployeeDataBase.addNewEmployee(Mockito.anyObject())).thenReturn(true);
        appRoute.run(HttpRequest.POST("/employee/addEmployee").withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON,data))).assertEntity("OK").assertStatusCode(200);

        String data1="{\n" +
                "    \"department\": \"IT Development\"," +
                "    \"dob\": \"2011-01-01\"," +
                "    \"empID\": 1011," +
                "    \"gender\": \"male\"," +
                "    \"jobLevel\": \"Junior\"," +
                "    \"joiningDate\":\"2020-11-11\"," +
                "    \"name\": \"John\"," +
                "    \"salary\": 45000.0" +
                "}";
        Mockito.when(iEmployeeDataBase.addNewEmployee(Mockito.any(Employee.class))).thenReturn(false);
        appRoute.run(HttpRequest.POST("/employee/addEmployee").withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON,data1))).assertEntity("The request contains bad syntax or cannot be fulfilled.").assertStatusCode(400);
    }
}