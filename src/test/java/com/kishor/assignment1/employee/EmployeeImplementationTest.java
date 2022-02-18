package com.kishor.assignment1.employee;

import com.kishor.assignment3.employee.EmployeeDatabase;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kishor on Feb 01, 2022.
 */

public class EmployeeImplementationTest {

    public EmployeeImplementationTest() throws SQLException {
    }

    /**
     * 1. Don't allow child labours while taking employee // 21 years
     */
    @Test
    public void childLabours() {
        Employee employee  = EmployeeImplementation.addEmployee(1, "Kishor", "IT Development", 500000, "male", LocalDate.of(2000, 8, 11), LocalDate.of(2008, 5, 12), "Junior");
        Employee employee1 = EmployeeImplementation.addEmployee(2, "Kishor", "IT Development", 500000, "male", LocalDate.of(2000, 8, 11), LocalDate.of(1996, 8, 11), "Junior");
        Assert.assertNull(employee);
        Assert.assertNotNull(employee1);
    }

    /**
     * 2. Get no of employees by given department
     */
    @Test
    public void noOfEmployeesByDepartment() throws SQLException {
        List<Employee>                     list      = EmployeeDatabase.getAllEmployees();
        EmployeeCountByDepartmentContainer empByDept = EmployeeImplementation.noOfEmpByCount(list, "IT Development");
        Assert.assertEquals(java.util.Optional.of(2L).get(), empByDept.employeeCount);
    }

    /**
     * 3. group employees by department
     */
    @Test
    public void groupByDept() throws SQLException {
        List<Employee>                   list          = EmployeeDatabase.getAllEmployees();
        List<GroupByDepartmentContainer> expectedValue = new ArrayList<GroupByDepartmentContainer>();
        expectedValue.add(new GroupByDepartmentContainer(Arrays.asList("Rohit", "Jessi"), "Business Analysis"));
        expectedValue.add(new GroupByDepartmentContainer(Arrays.asList("Edwin"), "Administration"));
        expectedValue.add(new GroupByDepartmentContainer(Arrays.asList("Sunil", "Jyoti"), "IT Development"));
        List<GroupByDepartmentContainer> groupByDepartmentContainer = EmployeeImplementation.grpByDept(list);
        Assert.assertEquals(expectedValue.toString(), groupByDepartmentContainer.toString());
    }

    /**
     * 4. Increase salary of employees for given Department , not necessary that there will be hike !
     */
    @Test
    public void increseSal() throws SQLException {
        List<Employee>                list          = EmployeeDatabase.getAllEmployees();
        List<IncreaseSalaryContainer> expectedValue = new ArrayList<IncreaseSalaryContainer>();
        expectedValue.add(new IncreaseSalaryContainer("Sunil", 27000.0));
        expectedValue.add(new IncreaseSalaryContainer("Jyoti", 32000.0));
        List<IncreaseSalaryContainer> increaseSalaryContainer = EmployeeImplementation.increaseSalaryForDept(list, "IT Development", 1000);
        Assert.assertEquals(expectedValue.toString(), increaseSalaryContainer.toString());
    }

    /**
     * 5. promote employees having 8 years experience to Senior position
     */
    @Test
    public void promoteEmployee() throws SQLException {
        List<Employee>                 list          = EmployeeDatabase.getAllEmployees();
        List<PromoteEmployeeContainer> expectedValue = new ArrayList<PromoteEmployeeContainer>();
        expectedValue.add(new PromoteEmployeeContainer("Edwin", "Senior"));
        expectedValue.add(new PromoteEmployeeContainer("Jyoti", "Senior"));
        List<PromoteEmployeeContainer> promoteEmployeeContainer = EmployeeImplementation.promoteEmployee(list);
        Assert.assertEquals(expectedValue.toString(), promoteEmployeeContainer.toString());
    }
}