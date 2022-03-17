package com.kishor.assignment5.employee;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

/**
 * Created by Kishor on Mar 15, 2022.
 */

public class EmployeeImplementationAPITest extends JUnitRouteTest {
    EmployeeMockDatabase mockDatabase      = new EmployeeMockDatabase();
    IEmployee            database          = Mockito.mock(EmployeeDatabase.class);
    EmployeeMockitoDB    employeeMockitoDB = new EmployeeMockitoDB();
    TestRoute            appRoute          = testRoute(new EmployeeImplementation(database).createRoute());

    @Test
    public void testgetStudentById() throws SQLException {
        Mockito.when(database.getAllEmployees()).thenReturn(employeeMockitoDB.getMockAllEmployees());
        appRoute.run(HttpRequest.GET("/employee/getAllEmployee")).assertEntity("[{\"department\":\"IT Development\",\"dob\":{\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"dayOfMonth\":12,\"dayOfWeek\":\"TUESDAY\",\"dayOfYear\":132,\"era\":\"CE\",\"leapYear\":false,\"month\":\"MAY\",\"monthValue\":5,\"year\":1998},\"empId\":1,\"gender\":\"female\",\"jobLevel\":\"Junior\",\"joiningDate\":{\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"dayOfMonth\":11,\"dayOfWeek\":\"FRIDAY\",\"dayOfYear\":224,\"era\":\"CE\",\"leapYear\":true,\"month\":\"AUGUST\",\"monthValue\":8,\"year\":2000},\"name\":\"Jyoti\",\"salary\":45000.0},{\"department\":\"IT Development\",\"dob\":{\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"dayOfMonth\":12,\"dayOfWeek\":\"WEDNESDAY\",\"dayOfYear\":132,\"era\":\"CE\",\"leapYear\":false,\"month\":\"MAY\",\"monthValue\":5,\"year\":2010},\"empId\":2,\"gender\":\"male\",\"jobLevel\":\"Junior\",\"joiningDate\":{\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"dayOfMonth\":11,\"dayOfWeek\":\"FRIDAY\",\"dayOfYear\":224,\"era\":\"CE\",\"leapYear\":true,\"month\":\"AUGUST\",\"monthValue\":8,\"year\":2000},\"name\":\"Sunil\",\"salary\":40000.0},{\"department\":\"Business Analysis\",\"dob\":{\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"dayOfMonth\":12,\"dayOfWeek\":\"TUESDAY\",\"dayOfYear\":132,\"era\":\"CE\",\"leapYear\":false,\"month\":\"MAY\",\"monthValue\":5,\"year\":1998},\"empId\":3,\"gender\":\"male\",\"jobLevel\":\"Mid-Term\",\"joiningDate\":{\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"dayOfMonth\":11,\"dayOfWeek\":\"TUESDAY\",\"dayOfYear\":224,\"era\":\"CE\",\"leapYear\":true,\"month\":\"AUGUST\",\"monthValue\":8,\"year\":2020},\"name\":\"Rohit\",\"salary\":35000.0},{\"department\":\"Administration\",\"dob\":{\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"dayOfMonth\":12,\"dayOfWeek\":\"TUESDAY\",\"dayOfYear\":132,\"era\":\"CE\",\"leapYear\":false,\"month\":\"MAY\",\"monthValue\":5,\"year\":1998},\"empId\":4,\"gender\":\"male\",\"jobLevel\":\"Junior\",\"joiningDate\":{\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"dayOfMonth\":11,\"dayOfWeek\":\"SATURDAY\",\"dayOfYear\":223,\"era\":\"CE\",\"leapYear\":false,\"month\":\"AUGUST\",\"monthValue\":8,\"year\":1990},\"name\":\"Edwin\",\"salary\":35000.0},{\"department\":\"Business Analysis\",\"dob\":{\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"dayOfMonth\":12,\"dayOfWeek\":\"TUESDAY\",\"dayOfYear\":132,\"era\":\"CE\",\"leapYear\":false,\"month\":\"MAY\",\"monthValue\":5,\"year\":1998},\"empId\":5,\"gender\":\"female\",\"jobLevel\":\"Junior\",\"joiningDate\":{\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"dayOfMonth\":11,\"dayOfWeek\":\"SATURDAY\",\"dayOfYear\":223,\"era\":\"CE\",\"leapYear\":false,\"month\":\"AUGUST\",\"monthValue\":8,\"year\":2018},\"name\":\"Jessi\",\"salary\":35000.0}]");
    }

    @Test
    public void testgetAllEmployeeById() throws SQLException {
        Mockito.when(database.getEmployeeById(Mockito.anyInt())).thenReturn(employeeMockitoDB.getMockEmployeById(1));
        appRoute.run(HttpRequest.GET("/employee/getAllEmployeeById/1")).assertEntity("{\"department\":\"IT Development\",\"dob\":{\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"dayOfMonth\":12,\"dayOfWeek\":\"TUESDAY\"," +
                "\"dayOfYear\":132,\"era\":\"CE\",\"leapYear\":false,\"month\":\"MAY\",\"monthValue\":5,\"year\":1998},\"empId\":1,\"gender\":\"female\",\"jobLevel\":\"Senior\",\"joiningDate\":{\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"dayOfMonth\":11,\"dayOfWeek\":\"FRIDAY\",\"dayOfYear\":224,\"era\":\"CE\",\"leapYear\":true,\"month\":\"AUGUST\",\"monthValue\":8,\"year\":2000},\"name\":\"Jyoti\",\"salary\":70000.0}");
    }

    @Test
    public void testgetNoOfEmployeeByCount() throws SQLException {
        Mockito.when(database.getAllEmployees()).thenReturn(employeeMockitoDB.getMockAllEmployees());
        appRoute.run(HttpRequest.GET("/employee/getNoOfEmployeeByCount/Administration")).assertEntity("{\"department\":\"Administration\",\"employee\":2,\"employeeCount\":2}");
    }

    @Test
    public void testgetGroupByDepartment() throws SQLException {
        Mockito.when(database.getAllEmployees()).thenReturn(employeeMockitoDB.getMockAllEmployees());
        appRoute.run(HttpRequest.GET("/employee/getGroupByDepartment")).assertEntity("[{\"dept\":\"IT Development\",\"names\":[\"Jyoti\",\"Sunil\"]},{\"dept\":\"Business Analysis\",\"names\":[\"Rohit\",\"Jessi\"]},{\"dept\":\"Administration\",\"names\":[\"Edwin\",\"Geetha\"]}]");
    }

    @Test
    public void testincreaseSalaryForDept() throws Exception {
        Mockito.when(database.getAllEmployees()).thenReturn(employeeMockitoDB.getMockAllEmployees());
        Mockito.when(database.updateValueThroughEmpId(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(employeeMockitoDB.updateMockValueThroughEmpId("Salary", "55000.0", 4),
                employeeMockitoDB.updateMockValueThroughEmpId("Salary", "60000.0", 6));
        appRoute.run(HttpRequest.PUT("/employee/increaseSalaryForDept?department=Administration&hike=5000")).assertEntity("[{\"increaseSal\":40000.0,\"name\":\"Edwin\"},{\"increaseSal\":50000.0,\"name\":\"Geetha\"}]");
    }

    @Test
    public void testgetPromoteEmployee() throws Exception {
        Mockito.when(database.getAllEmployees()).thenReturn(employeeMockitoDB.getMockAllEmployees());
        Mockito.when(database.updateValueThroughEmpId(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(employeeMockitoDB.updateMockValueThroughEmpId("JobLevel", "Senior", 1)).thenReturn(employeeMockitoDB.updateMockValueThroughEmpId("JobLevel", "Senior", 4));
        appRoute.run(HttpRequest.GET("/employee/getPromoteEmployee")).assertEntity("[{\"empName\":\"Jyoti\",\"jobLevel\":\"Senior\"},{\"empName\":\"Edwin\",\"jobLevel\":\"Senior\"}]");

    }


}