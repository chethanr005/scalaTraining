package com.kishor.assignment1.employee;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * 1. Don't allow child labours while taking employee // 21 years
 */
public class EmployeeDatabase {
    public static List<EmployeeContainer> getEmployee() {
        EmployeeContainer emp1 = new EmployeeContainer("John", "IT Development", 35000.0, "male", LocalDate.of(2000, 8, 11), LocalDate.of(1998, 5, 12), "Junior");
        EmployeeContainer emp2 = new EmployeeContainer("Sunil", "IT Development", 10000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(2010, 4, 20), "Junior");
        EmployeeContainer emp3 = new EmployeeContainer("Rohit", "Business Analysis", 45000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(1995, 8, 17), "Mid-Term");
        EmployeeContainer emp4 = new EmployeeContainer("Edwin", "Administration", 55000.0, "male", LocalDate.of(1990, 8, 11), LocalDate.of(1980, 2, 21), "Junior");
        EmployeeContainer emp5 = new EmployeeContainer("Jessi", "Business Analysis", 35000.0, "female", LocalDate.of(2018, 8, 11), LocalDate.of(1998, 11, 19), "Senior");

        List<EmployeeContainer> list = Arrays.asList(emp1, emp2, emp3, emp4, emp5);
        return list;
    }


}
