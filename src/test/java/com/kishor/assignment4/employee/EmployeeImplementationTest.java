package com.kishor.assignment4.employee;

import junit.framework.TestCase;
import org.junit.Assert;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kishor on Feb 28, 2022.
 */

public class EmployeeImplementationTest extends TestCase {
    EmployeeMockDatabase   mockDatabase           = new EmployeeMockDatabase();
    IEmployee              database               = new EmployeeDatabase();
    EmployeeImplementation employeeImplementation = new EmployeeImplementation(database);

    /**
     * 1. Get no of employees by given department
     */
    public void testGetNoOfEmployeeByCount() throws SQLException, ExecutionException, InterruptedException {
        Assert.assertEquals(mockDatabase.getMockNoOfEmployeeByCount().get(), employeeImplementation.getNoOfEmployeeByCount("IT Development").get());
    }

    /**
     * 2. group employees by department
     */
    public void testGroupByDepartment() throws SQLException, ExecutionException, InterruptedException {
        Assert.assertEquals(mockDatabase.getMockGroupByDepartment().get(), employeeImplementation.groupByDepartment().get());
    }

    /**
     * 3. Increase salary of employees for given Department , not necessary that there will be hiked !
     */
    public void testIncreaseSalaryForDept() throws SQLException, ExecutionException, InterruptedException {
        Assert.assertEquals(mockDatabase.getMockIncreaseSalaryForDepartment().get(), employeeImplementation.increaseSalaryForDept("IT Development", 5000).get());
    }

    /**
     * 4. promote employees having 8 years experience to Senior position
     */
    public void testPromoteEmployee() throws SQLException, ExecutionException, InterruptedException {
        Assert.assertEquals(mockDatabase.getMockPromoteEmployee().get(), employeeImplementation.getPromoteEmployee().get());
    }
}
