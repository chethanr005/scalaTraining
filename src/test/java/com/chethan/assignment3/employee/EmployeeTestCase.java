package com.chethan.assignment3.employee;

/**
 * Created by Chethan on Feb 16, 2022.
 */


import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class EmployeeTestCase {

    // Candidate under the age 21 are not allowed
    @Test(expected = Exception.class)
    public void under21() throws Exception {
        EmployeeDatabase.addEmployee(new Employee(15,"james", "cs", 300000, "male", "2021-12-18", "2002-12-12", "junior"));
    }

    // Adding new employee (21 or above)
    @Test
    public void addEmployee() throws Exception {

        Assert.assertEquals(true, EmployeeDatabase.addEmployee(new Employee(16,"james", "cs", 300000, "male", "2021-12-18", "2000-12-12", "junior")));


    }

    //No of employees by given department
    @Test
    public void noOfEmployees() throws Exception {
        NoOfEmpByDeptContainer deptEmployees =EmployeeImplementation.getNoOfEmployeesByDept(EmployeeDatabase.getEmployeeData(), "ee");
        Assert.assertEquals(4, deptEmployees.getEmployees());
    }



    //Group employees by department
    @Test
    public void employeeGroup() throws Exception {
     List<GroupEmployeesByDepartment> expectedGroup=new ArrayList<GroupEmployeesByDepartment>();
     expectedGroup.add(new GroupEmployeesByDepartment("cs",6));
     expectedGroup.add(new GroupEmployeesByDepartment("ee",4));
     expectedGroup.add(new GroupEmployeesByDepartment("ec",3));
        Assert.assertEquals(expectedGroup.toString(),EmployeeImplementation.getGroupByDept(EmployeeDatabase.getEmployeeData()).toString());
    }

    //Increase salary of employees
    @Test
    public void salIncrease() throws Exception {
        Map<String, Double> promotedEmployees = new TreeMap<String, Double>();
        promotedEmployees.put("andrew", 345000.0);
        promotedEmployees.put("anthony", 437000.0);
        promotedEmployees.put("julie", 402500.0);
        Assert.assertEquals(promotedEmployees, EmployeeImplementation.getIncreasedSalary(EmployeeDatabase.getEmployeeData(), Optional.ofNullable("ec"),Optional.ofNullable(15.0)));

    }

    //Promote Employees based on experience
    @Test
    public void employeesPromotion() throws Exception {
        Map<String, String> promotedEmployees = new TreeMap<String, String>();
        promotedEmployees.put("kail", "senior");
        promotedEmployees.put("tony", "senior");
        Assert.assertEquals(promotedEmployees, EmployeeImplementation.getPromotedEmployees(EmployeeDatabase.getEmployeeData()));
    }

}
