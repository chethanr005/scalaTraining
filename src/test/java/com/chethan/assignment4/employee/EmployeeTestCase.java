package com.chethan.assignment4.employee;


import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * Created by Chethan on Feb 25, 2022.
 */

public class EmployeeTestCase {
    IEmployeeDatabase      employeeDatabase       = new EmployeeDatabase();
    EmployeeImplementation employeeImplementation = new EmployeeImplementation(employeeDatabase);
    MockEmployeeDataBase   mockDatabase           = new MockEmployeeDataBase();

    // 1. Candidate under the age 21 are not allowed
    @Test(expected = Exception.class)
    public void under21() throws Exception {
        employeeDatabase.addEmployee(new Employee(15, "james", "cs", 300000, "male", "2021-12-18", "2003-12-12", "junior"));
    }

    // 2. No of employees by given department
    @Test
    public void noOfEmployeesByDept() throws Exception {
        NoOfEmpByDeptContainer actualResult   = employeeImplementation.getNoOfEmployeesByDept("ee").get();
        NoOfEmpByDeptContainer expectedResult = mockDatabase.getActualNoOfEmpByDeptContainer().get();
        Assert.assertEquals(expectedResult, actualResult);
    }

    // 3. Group employees by department
    @Test
    public void employeeGroup() throws Exception {
        List<GroupEmployeesByDepartment> expectedResult = mockDatabase.getExpectedGroupEmpByDeptList().get();
        List<GroupEmployeesByDepartment> actualResult   = employeeImplementation.getGroupEmployeesByDept().get();

        Assert.assertEquals(expectedResult, actualResult);
    }

    // 4. Increase salary of employees
    @Test
    public void salIncrease() throws Exception {
        List<Employee> expectedResult = mockDatabase.getExpectedIncreasedSalaryEmployessList().get();
        List<Employee> actualResult   = employeeImplementation.getIncreasedSalaryEmployees(Optional.ofNullable("ec"), Optional.ofNullable(15.0)).get();
        Assert.assertEquals(expectedResult, actualResult);
    }

    // 5. Promote Employees based on experience
    @Test
    public void employeesPromotion() throws Exception {
        List<Employee> expectedResult = mockDatabase.getActualPromotedEmployee().get();
        List<Employee> actualResult   = employeeImplementation.getPromotedEmployees().get();
        Assert.assertEquals(expectedResult, actualResult);
    }
}
