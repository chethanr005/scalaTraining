//package com.chethan.assignment1.employee;
//
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.Map;
//import java.util.TreeMap;
//
///**
// * Created by Chethan on Feb 01, 2022.
// */
//
//public class EmployeeTestCase {
//
//    // Candidate under the age 21 are not allowed
//    @Test(expected = Exception.class)
//    public void under21() throws Exception {
//        EmployeeImplementation.addData("james", "cs", 300000, "male", "18/12/2021", "12/12/2005", "junior");
//    }
//
//    // Adding new employee (21 or above)
//    @Test
//    public void addEmployee() throws Exception {
//
//        Assert.assertEquals(true, EmployeeImplementation.addData("james", "cs", 300000, "male", "18/12/2021", "12/12/2000", "junior"));
//
//
//    }
//
//    //No of employees by given department
//    @Test
//    public void noOfEmployees() throws Exception {
//        NoOfEmpByDeptContainer deptEmployees = EmployeeImplementation.getNoOfEmployeesByDept(EmployeeImplementation.getEmployeeData(), "ee");
//        Assert.assertEquals(4, deptEmployees.getEmployees());
//    }
//
//    //Group employees by department
//    @Test
//    public void employeeGroup() throws Exception {
//        Map<String, Long> empGroup = new TreeMap<String, Long>();
//        empGroup.put("cs", 6l);
//        empGroup.put("ec", 3l);
//        empGroup.put("ee", 4l);
//        Assert.assertEquals(empGroup, EmployeeImplementation.getGroupByDept(EmployeeImplementation.getEmployeeData()));
//    }
//
//    //Increase salary of employees
//    @Test
//    public void salIncrease() throws Exception {
//        Map<String, Double> promotedEmployees = new TreeMap<String, Double>();
//        promotedEmployees.put("andrew", 345000.0);
//        promotedEmployees.put("anthony", 437000.0);
//        promotedEmployees.put("julie", 402500.0);
//        Assert.assertEquals(promotedEmployees, EmployeeImplementation.getIncreasedSalary(EmployeeImplementation.getEmployeeData(), "ec"));
//
//    }
//
//    //Promote Employees based on experience
//    @Test
//    public void employeesPromotion() throws Exception {
//        Map<String, String> promotedEmployees = new TreeMap<String, String>();
//        promotedEmployees.put("kail", "senior");
//        promotedEmployees.put("tony", "senior");
//        Assert.assertEquals(promotedEmployees, EmployeeImplementation.getPromotedEmployees(EmployeeImplementation.getEmployeeData()));
//    }
//
//}
