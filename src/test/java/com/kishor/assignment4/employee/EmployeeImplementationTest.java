package com.kishor.assignment4.employee;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kishor on Feb 28, 2022.
 */

public class EmployeeImplementationTest extends TestCase {
    EmployeeMockDatabase   mockDatabase           = new EmployeeMockDatabase();
    //IEmployee              database               = new EmployeeDatabase();
    IEmployee              database               = Mockito.mock(EmployeeDatabase.class);
    EmployeeImplementation employeeImplementation = new EmployeeImplementation(database);
    EmployeeMockitoDB      employeeMockitoDB      = new EmployeeMockitoDB();

    /**
     * 1. Don't allow child labours while taking employee // 21 years
     */
    @Test(expected = Exception.class)
    public void testAddNewEmployee() throws Exception {
        Mockito.when(database.insertNewRecord(Mockito.anyObject())).thenReturn(employeeMockitoDB.insertMockEmployee(6, "Geetha", "Administration", 40000.0, "female",
                LocalDate.of(2020, 07, 03), LocalDate.of(1994, 12, 13), "junior"));

        employeeImplementation.addEmployee(6, "Geetha", "Administration", 40000.0, "female", LocalDate.of(2020, 07, 03), LocalDate.of(2004, 12, 13), "junior");
    }

    /**
     * 2. Get no of employees by given department
     */
    public void testGetNoOfEmployeeByCount() throws SQLException, ExecutionException, InterruptedException {
        Mockito.when(database.getAllEmployees()).thenReturn(employeeMockitoDB.getMockAllEmployees());
        Assert.assertEquals(mockDatabase.getMockNoOfEmployeeByCount().get(), employeeImplementation.getNoOfEmployeeByCount("IT Development").get());
    }

    /**
     * 3. group employees by department
     */
    public void testGroupByDepartment() throws SQLException, ExecutionException, InterruptedException {
        Mockito.when(database.getAllEmployees()).thenReturn(employeeMockitoDB.getMockAllEmployees());
        Assert.assertEquals(mockDatabase.getMockGroupByDepartment().get(), employeeImplementation.groupByDepartment().get());
    }

    /**
     * 4. Increase salary of employees for given Department , not necessary that there will be hiked !
     */
    public void testIncreaseSalaryForDept() throws SQLException, ExecutionException, InterruptedException {
        Mockito.when(database.getAllEmployees()).thenReturn(employeeMockitoDB.getMockAllEmployees());
        Assert.assertEquals(mockDatabase.getMockIncreaseSalaryForDepartment().get(), employeeImplementation.increaseSalaryForDept("IT Development", 5000).get());
    }

    /**
     * 5. promote employees having 8 years experience to Senior position
     */
    public void testPromoteEmployee() throws Exception {
        Mockito.when(database.getAllEmployees()).thenReturn(employeeMockitoDB.getMockAllEmployees());
        Mockito.when(database.updateValueThroughEmpId(Mockito.any(String.class), Mockito.any(String.class), Mockito.any(Integer.class)))
               .thenReturn(employeeMockitoDB.updateMockValueThroughEmpId("JobLevel", "Senior", 1),
                       employeeMockitoDB.updateMockValueThroughEmpId("JobLevel", "Senior", 4));
        Assert.assertEquals(mockDatabase.getMockPromoteEmployee().get(), employeeImplementation.getPromoteEmployee().get());


    }
}
