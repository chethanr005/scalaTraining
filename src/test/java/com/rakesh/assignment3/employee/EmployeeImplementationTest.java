package com.rakesh.assignment3.employee;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by Dark Alpha on Feb 17, 2022.
 */

public class EmployeeImplementationTest {
    public static List<Employee1> employeeList = EmployeeDataBase.getAllEmployees();

    //1. Don't allow child labours while taking employee // 21 years
    @Test(expected = Exception.class)
    public void addNewEmployee() {
        EmployeeImplementation.addNewEmployee(new Employee1(1005, "Xyz", "IT", 2000.0, "male", LocalDate.of(2021, 4, 12)
                , LocalDate.of(2020, 1, 21), "Junior"));
    }


    //2. Get no of employees by given department
    @Test
    public void EmployeeCountByDepartmentCheck() {
        EmployeeDepartmentContainer res = EmployeeImplementation.getCountOfDepartment(employeeList, "Business Analysis");
        Assert.assertEquals(1, res.employeeCount);

        Map<String, Long> expectedMap = new HashMap<>();
        expectedMap.put("Administration", 1L);
        expectedMap.put("Business Analysis", 1L);
        expectedMap.put("IT Development", 2L);
        EmployeeDepartmentContainer actualMap = EmployeeImplementation.getAllDeptCount(employeeList);

        Assert.assertEquals(expectedMap, actualMap.getAllDepartmentCount);
    }


    //3. group employees by department
    @Test
    public void getGroupOfEmployeeByDept() {
        GroupEmployeeContainer    actualMap   = EmployeeImplementation.getGroupByDepartment(employeeList);
        Map<String, List<String>> expectedMap = new HashMap<>();
        expectedMap.put("Administration", Arrays.asList("Edwin"));
        expectedMap.put("IT Development", Arrays.asList("John", "Sunil"));
        expectedMap.put("Business Analysis", Arrays.asList("Rohit"));

        Assert.assertEquals(expectedMap, actualMap.groupedEmployee);
    }


    //4. Increase salary of employees for given Department , not necessary that there will be hike !
    @Test
    public void getHikedEmployeeCheck() {
        //If Optional department is present.
        HikeSalaryContainer actualMap = EmployeeImplementation.getHikedEmployees(employeeList, "IT Development", 5000);

        Map<String, Double> expectedMap = new HashMap<>();
        expectedMap.put("John", 50000.0);
        expectedMap.put("Sunil", 50000.0);
        Assert.assertEquals(expectedMap, actualMap.hikedEmployees);

        //if Optional department is null;
        HikeSalaryContainer res = EmployeeImplementation.getHikedEmployees(employeeList, null, 0);
        Assert.assertNull(res.hikedEmployees);
    }


    //5. promote employees having 8 years experience to Senior position
    @Test
    public void promotedEmployeesCheck() {
        List<PromoteEmployeeContainer> actual = EmployeeImplementation.promoteEmployees(employeeList);

        List<PromoteEmployeeContainer> expected = new ArrayList<>();
        expected.add(new PromoteEmployeeContainer(1004, "Edwin", "Senior"));

        Assert.assertEquals(expected.toString(), actual.toString());

    }
}