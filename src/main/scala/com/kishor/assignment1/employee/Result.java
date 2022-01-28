package com.kishor.assignment1.employee;

import java.time.LocalDate;
import java.util.List;

public class Result {
    public static void main(String[] args) {
        EmployeeDatabase.addEmployee("John", "IT Development", 35000.0, "male", LocalDate.of(2000, 8, 11), LocalDate.of(1998, 5, 12), "Junior");
        EmployeeDatabase.addEmployee("Sunil", "IT Development", 35000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(2010, 4, 20), "Junior");
        EmployeeDatabase.addEmployee("Rohit", "Business Analysis", 45000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(1995, 8, 17), "Mid-Term");
        EmployeeDatabase.addEmployee("Edwin", "Administration", 55000.0, "male", LocalDate.of(1990, 8, 11), LocalDate.of(1980, 2, 21), "Junior");
        EmployeeDatabase.addEmployee("Jessi", "Business Analysis", 35000.0, "female", LocalDate.of(2018, 8, 11), LocalDate.of(1998, 11, 19), "Senior");


        System.out.println(EmployeeDatabase.employees);

        List<Employee>            list = new EmployeeDatabase().getEmployee();
        EmployeeCountByDepartment ecbd = new EmployeeCountByDepartment();
        System.out.println(ecbd.noOfEmpByCount(list));
        GroupByDepartment gpd = new GroupByDepartment();
        gpd.depart(list);
        PromoteEmployee pe = new PromoteEmployee();
        pe.promote(list);

    }
}
