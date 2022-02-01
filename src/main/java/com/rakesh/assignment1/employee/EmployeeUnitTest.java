package com.rakesh.assignment1.employee;

import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class EmployeeUnitTest {

    public static List<Employee> employeeList=EmployeeImplementation.getDatabase();

    //1. Don't allow child labours while taking employee // 21 years
    @Test
    public void addNewEmployeeCheck()
    {
        AddEmployeeContainer res=EmployeeImplementation
                .addEmployee("Dark","IT Development",25000.0,"male","2021-11-02","2000-01-17","Junior");
        Assert.assertEquals(true,res.isAdded);
    }



    //2. Get no of employees by given department
    @Test
    public void EmployeeCountByDepartmentCheck()
    {
        EmployeeDepartmentContainer res=EmployeeImplementation.getCountOfDepartment(employeeList,"Business Analysis");
        Assert.assertEquals(1,res.employeeCount);

        Map<String,Long> expectedMap=new HashMap<>();
        expectedMap.put("Administration",1L);
        expectedMap.put("Business Analysis",1L);
        expectedMap.put("IT Development",2L);
        EmployeeDepartmentContainer actualMap=EmployeeImplementation.getAllDeptCount(employeeList);

        Assert.assertEquals(expectedMap,actualMap.getAllDepartmentCount);
    }



    //3. group employees by department
    @Test
    public void getGroupOfEmployeeByDept()
    {
        GroupEmployeeContainer actualMap=EmployeeImplementation.getGroupByDepartment(employeeList);
        Map<String,List<String>> expectedMap=new HashMap<>();
        expectedMap.put("Administration", Arrays.asList("Edwin"));
        expectedMap.put("IT Development", Arrays.asList("John","Sunil"));
        expectedMap.put("Business Analysis", Arrays.asList("Rohit"));

        Assert.assertEquals(expectedMap,actualMap.groupedEmployee);
    }



    //4. Increase salary of employees for given Department , not necessary that there will be hike !
    @Test
    public void getHikedEmployeeCheck()
    {
        //If Optional department is present.
        HikeSalaryContainer actualMap=EmployeeImplementation.getHikedEmployees(employeeList, "IT Development");

        Map<String,Double> expectedMap=new HashMap<>();
        expectedMap.put("John",40000.0);
        expectedMap.put("Sunil",40000.0);
        Assert.assertEquals(expectedMap,actualMap.hikedEmployees);

        //if Optional department is null;
        HikeSalaryContainer res=EmployeeImplementation.getHikedEmployees(employeeList,null);
        Assert.assertNull(res.hikedEmployees);
    }



    //5. promote employees having 8 years experience to Senior position
    @Test
    public void promotedEmployeesCheck()
    {
        PromoteEmployeeContainer actualMap=EmployeeImplementation.promoteEmployees(employeeList);

        Map<String,String> expectedMap=new HashMap<>();
        expectedMap.put("Edwin","Senior");

        Assert.assertEquals(expectedMap,actualMap.promotedEmployees);

    }
}
