package com.chethan.assignment4.employee;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.Optional;

/**
 * Created by Chethan on Mar 04, 2022.
 */

public class MockitoEmployeeTestCase {
    IEmployeeDatabase      employeeDatabase       =Mockito.mock(EmployeeDatabase.class);
    EmployeeImplementation employeeImplementation = new EmployeeImplementation(employeeDatabase);
    MockEmployeeDataBase    mockDatabase           = new MockEmployeeDataBase();

    // 1. Candidate under the age 21 are not allowed
    @Test(expected = Exception.class)
    public void under21() throws Exception {
        Mockito.when(employeeDatabase.addEmployee(Mockito.any(Employee.class))).thenReturn(MockitoEmployeeDataBase.addMockitoEmployeee(Mockito.any(Employee.class)));
        employeeDatabase.addEmployee(new Employee(15, "james", "cs", 300000, "male", "2021-12-18", "2003-12-12", "junior"));
    }

    // 2. No of employees by given department
    @Test
    public void noOfEmployeesByDept() throws Exception {
        Mockito.when(employeeDatabase.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        NoOfEmpByDeptContainer actualResult = employeeImplementation.getNoOfEmployeesByDept("ee").get();
        NoOfEmpByDeptContainer expectedResult=mockDatabase.getActualNoOfEmpByDeptContainer().get();
        Assert.assertEquals(expectedResult,actualResult);
    }

    // 3. Group employees by department
    @Test
    public void employeeGroup() throws Exception {
        Mockito.when(employeeDatabase.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        List<GroupEmployeesByDepartment>    expectedResult = mockDatabase.getExpectedMockitoGroupEmpByDeptList().get();
        List<GroupEmployeesByDepartment> actualResult   = employeeImplementation.getGroupEmployeesByDept().get();

        Assert.assertEquals(expectedResult,actualResult);
    }

    // 4. Increase salary of employees
    @Test
    public void salIncrease() throws Exception {
        Mockito.when(employeeDatabase.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());


        Mockito.when(employeeDatabase.updateEmployeeDetailsById(Mockito.anyInt(),Mockito.anyString(),Mockito.anyString()))
               .thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(3,"salary","402500")
                       ,MockitoEmployeeDataBase.updateEmployeeByIdInMockito(8,"salary","437000")
                       ,MockitoEmployeeDataBase.updateEmployeeByIdInMockito(9,"salary", "345000"));



        List<Employee> expectedResult=mockDatabase.getExpectedIncreasedSalaryEmployeesList().get();
        List<Employee> actualResult=employeeImplementation.getIncreasedSalaryEmployees(Optional.ofNullable("ec"),Optional.ofNullable(15.0)).get();
        Assert.assertEquals(expectedResult, actualResult);
    }

    // 5. Promote Employees based on experience
    @Test
    public void employeesPromotion() throws Exception {
        Mockito.when(employeeDatabase.getEmployeeData()).thenReturn(MockitoEmployeeDataBase.getEmployeesList());
        Mockito.when(employeeDatabase.updateEmployeeDetailsById(Mockito.anyInt(),Mockito.anyString(),Mockito.anyString()))
               .thenReturn(MockitoEmployeeDataBase.updateEmployeeByIdInMockito(4,"jobLevel","senior")
               ,MockitoEmployeeDataBase.updateEmployeeByIdInMockito(5,"jobLevel","senior"));
        List<Employee> expectedResult=mockDatabase.getActualPromotedEmployee().get();
        List<Employee> actualResult=employeeImplementation.getPromotedEmployees().get();
        Assert.assertEquals(expectedResult,actualResult);
    }
}
