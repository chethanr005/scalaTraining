package com.rakesh.assignment4.employee;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

/**
 * Created by Rakesh on Feb 28, 2022.
 */

public class EmployeeImplementationTest {
    IEmployeeDataBase      iEmployeeDataBase      = new EmployeeDataBase();
    EmployeeImplementation employeeImplementation = new EmployeeImplementation(iEmployeeDataBase);
    EmployeeMockData       mockData               = new EmployeeMockData();

    /**
     * 1. Don't allow child labours while taking employee // 21 years
     */
    @Test(expected = Exception.class)
    public void checkAddEmployee() {
        employeeImplementation.addEmployee(new Employee(1005, "Rahul", "IT", 20000, "male", LocalDate.of(2021, 9, 23), LocalDate.of(2009, 4, 23), "Junior"));
    }

    /**
     * 2. Get no of employees by given department
     */
    @Test
    public void checkEmployeesCountByDepartment() throws ExecutionException, InterruptedException {
        DepartmentCountContainer expectedResult = new DepartmentCountContainer("Administration", 1);
        Assert.assertEquals(expectedResult, employeeImplementation.employeeCountByDepartment("Administration").get());
    }

    /**
     * 3. group employees by department
     */
    @Test
    public void checkEmployeeGroup() throws ExecutionException, InterruptedException {
        //if department is passed
        Assert.assertEquals(mockData.employeeGroupByDept("Administration"),employeeImplementation.getEmployeeGroupByDept("Administration").get());

        //if no department is passed
        Assert.assertEquals(mockData.employeeGroupMockData(), employeeImplementation.employeeGroupByDept().get());
    }

    /**
     * 4. Increase salary of employees for given Department , not necessary that there will be hike !
     */
    @Test
    public void checkHikedEmployees() throws ExecutionException, InterruptedException {
        Assert.assertEquals(mockData.hikeEmployeeMockData(), employeeImplementation.hikeEmployees("Administration", 0).get());
    }

    /**
     * 5. promote employees having 8 years experience to Senior position
     */
    @Test
    public void checkPromotedEmployees() throws ExecutionException, InterruptedException {
        Assert.assertEquals(mockData.promotedEmployeeMockData(), employeeImplementation.promoteEmployees().get());
    }
}