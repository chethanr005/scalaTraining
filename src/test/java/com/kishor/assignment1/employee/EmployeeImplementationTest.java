package com.kishor.assignment1.employee;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kishor on Feb 01, 2022.
 */

public class EmployeeImplementationTest {
    List<EmployeeContainer>   list            = EmployeeDatabase.getEmployee();
    Map<String, List<String>> grpBydept       = new HashMap<>();
    Map<String, Double>       increseSalary   = new HashMap<>();
    Map<String, String>       promoteEmployee = new HashMap<>();

    /**
     * 1. Don't allow child labours while taking employee // 21 years
     */
    @Test
    public void childLabours() {
        EmployeeContainer employeeContainer  = EmployeeImplementation.addEmployee("Kishor", "IT Development", 500000, "male", LocalDate.of(2000, 8, 11), LocalDate.of(2008, 5, 12), "Junior");
        EmployeeContainer employeeContainer1 = EmployeeImplementation.addEmployee("Kishor", "IT Development", 500000, "male", LocalDate.of(2000, 8, 11), LocalDate.of(1996, 8, 11), "Junior");
        Assert.assertNull(employeeContainer);
        Assert.assertNotNull(employeeContainer1);
    }

    /**
     * 2. Get no of employees by given department
     */
    @Test
    public void noOfEmployeesByDepartment() {
        EmployeeCountByDepartmentContainer empByDept = EmployeeImplementation.noOfEmpByCount(list, "IT Development");
        Assert.assertEquals(java.util.Optional.of(2L).get(), empByDept.employeeCount);
    }

    /**
     * 3. group employees by department
     */
    @Test
    public void groupByDept() {
        grpBydept.put("Administration", Arrays.asList("Edwin"));
        grpBydept.put("IT Development", Arrays.asList("John", "Sunil"));
        grpBydept.put("Business Analysis", Arrays.asList("Rohit", "Jessi"));
        GroupByDepartmentContainer groupByDepartmentContainer = EmployeeImplementation.grpByDept(list);
        Assert.assertEquals(grpBydept, groupByDepartmentContainer.grpByDept);
    }

    /**
     * 4. Increase salary of employees for given Department , not necessary that there will be hike !
     */
    @Test
    public void increseSal() {
        increseSalary.put("Sunil", 15000.0);
        increseSalary.put("John", 40000.0);
        IncreaseSalaryContainer increaseSalaryContainer = EmployeeImplementation.increaseSalaryForDept(list, "IT Development");
        Assert.assertEquals(increseSalary, increaseSalaryContainer.increaseSal);
    }

    /**
     * 5. promote employees having 8 years experience to Senior position
     */
    @Test
    public void promoteEmployee() {
        promoteEmployee.put("Edwin", "Senior");
        promoteEmployee.put("John", "Senior");
        PromoteEmployeeContainer promoteEmployeeContainer = EmployeeImplementation.promoteEmployee(list);
        Assert.assertEquals(promoteEmployee, promoteEmployeeContainer.promoteEmp);
    }
}