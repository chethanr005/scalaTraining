package com.kishor.assignment1.Employee;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class employeeDatabase {
    public static List<Employee> getEmployee() {
        Employee employee1=new Employee("Alex","R&D",50000.0,"male", LocalDate.of(15,5,2011),LocalDate.of(11,8,1996),"Senior");
        Employee employee2=new Employee("Mary","Backend",25000.0,"male", LocalDate.of(23,9,2014),LocalDate.of(21,5,1998),"Mid-term");
        Employee employee3=new Employee("Saroja","Administration",35000.0,"female", LocalDate.of(2019,8,11),LocalDate.of(1996,3,26),"Junior");
        Employee employee4=new Employee("Shanthi","IT Development",45000.0,"female", LocalDate.of(2018,8,11),LocalDate.of(1997,6,15),"Senior");
        Employee employee5=new Employee("Wilson","IT Development",35000.0,"female", LocalDate.of(2018,8,11),LocalDate.of(2004,8,29),"Junior");
        Employee employee6=new Employee("Rohit","Business Analysis",45000.0,"male", LocalDate.of(2020,8,11),LocalDate.of(1995,8,17),"Mid-Term");
        Employee employee7=new Employee("Edwin","Administration",55000.0,"male", LocalDate.of(2016,8,11),LocalDate.of(1994,2,21),"Junior");
        Employee employee8=new Employee("Jessi","Business Analysis",35000.0,"female", LocalDate.of(2018,8,11),LocalDate.of(1998,11,19),"Senior");
        Employee employee9=new Employee("Ajith","IT Development",55000.0,"male",LocalDate.of(2017,8,11),LocalDate.of(1992,9,2),"Junior");

                List<Employee> employees = Arrays.asList(employee1,employee2,employee3,employee4,employee5,employee6,employee7,employee8,employee9);
        return employees;
    }
}
