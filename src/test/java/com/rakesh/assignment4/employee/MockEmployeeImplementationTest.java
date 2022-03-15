package com.rakesh.assignment4.employee;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Rakesh on Mar 04, 2022.
 */

public class MockEmployeeImplementationTest {
    IEmployeeDataBase      iEmployeeDataBase      = Mockito.mock(EmployeeDataBase.class);
    EmployeeImplementation employeeImplementation = new EmployeeImplementation(iEmployeeDataBase);
    EmployeeMockData       mockData               = new EmployeeMockData();

    /**
     * 1. Don't allow child labours while taking employee // 21 years
     */
    @Test(expected = Exception.class)
    public void checkAddEmployee() {
        Mockito.when(iEmployeeDataBase.addNewEmployee(Mockito.anyObject())).thenThrow(new RuntimeException("Under age"));
        employeeImplementation.addEmployee(new Employee(1005, "Rahul", "IT", 20000, "male", LocalDate.of(2021, 9, 23), LocalDate.of(2009, 4, 23), "Junior"));
    }

    /**
     * 2. Get no of employees by given department
     */
    @Test
    public void checkEmployeesCountByDepartment() throws ExecutionException, InterruptedException {
        Mockito.when(iEmployeeDataBase.getAllEmployee(Mockito.anyObject())).thenReturn(mockData.getAllEmployees());
        DepartmentCountContainer expectedResult = new DepartmentCountContainer("Administration", 1);
        Assert.assertEquals(expectedResult, employeeImplementation.employeeCountByDepartment("Administration").get());
    }

    /**
     * 3. group employees by department
     */
    @Test
    public void checkEmployeeGroup() throws ExecutionException, InterruptedException {
        //if department is passed
        Mockito.when(iEmployeeDataBase.getAllEmployee(Mockito.anyObject())).thenReturn(mockData.getAllEmployees());
        Assert.assertEquals(mockData.employeeGroupByDept("Administration"), employeeImplementation.getEmployeeGroupByDept("Administration").get());

        //if no department is passed
        Assert.assertEquals(mockData.employeeGroupMockData(), employeeImplementation.employeeGroupByDept().get());
    }

    /**
     * 4. Increase salary of employees for given Department , not necessary that there will be hike !
     */
    @Test
    public void checkHikedEmployees() throws ExecutionException, InterruptedException {
        Mockito.when(iEmployeeDataBase.getAllEmployee(Mockito.anyObject())).thenReturn(mockData.getAllEmployees());
        Mockito.when(iEmployeeDataBase.updateEmployeeData(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(mockData.updateData("JobLevel", "Senior", 1004), mockData.updateData("JobLevel", "Senior", 1003),
                mockData.updateData("JobLevel", "Senior", 1002), mockData.updateData("JobLevel", "Senior", 1001));

        Assert.assertEquals(mockData.hikeEmployeeMockData(), employeeImplementation.hikeEmployees("Administration", 0).get());
    }

    /**
     * 5. promote employees having 8 years experience to Senior position
     */
    @Test
    public void checkPromotedEmployees() throws ExecutionException, InterruptedException {
        Mockito.when(iEmployeeDataBase.getAllEmployee(Mockito.anyObject())).thenReturn(mockData.getAllEmployees());
        Mockito.when(iEmployeeDataBase.updateEmployeeData(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(mockData.updateData("JobLevel", "Senior", 1004), mockData.updateData("JobLevel", "Senior", 1003),
                mockData.updateData("JobLevel", "Senior", 1002), mockData.updateData("JobLevel", "Senior", 1001));
        Mockito.when(iEmployeeDataBase.getEmployeeByID(Mockito.anyInt(), Mockito.anyObject())).thenReturn(mockData.getEmployeeByID(1004), mockData.getEmployeeByID(1003), mockData.getEmployeeByID(1002), mockData.getEmployeeByID(1001));
        //System.out.println("       "+employeeImplementation.promoteEmployees().get());

        Assert.assertEquals(mockData.promotedEmployeeMockData(), employeeImplementation.promoteEmployees().get());
    }
}
