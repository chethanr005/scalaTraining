package com.chethan.assignment1.employee;

import java.time.*;
import java.time.format.*;
import java.util.*;

//Employee database and No child labour
public class EmployeeData extends Employee {

    private List<Employee> l1=new ArrayList<Employee>();

    private void addData(String name, String department, double salary, String gender, String joiningDate, String dob, String jobLevel) throws Exception {

        DateTimeFormatter d1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate adob = LocalDate.parse(dob, d1);

        if (Period.between(adob, LocalDate.now()).getYears() >= 21) {
            LocalDate joinDate = LocalDate.parse(joiningDate, d1);

            Employee e = new Employee(name, department, salary, gender, joinDate, adob, jobLevel);
            l1.add(e);
        }

        else throw new IllegalAccessException("    Candidate below 21 years cannot be taken as Employee");
    }

    private List<Employee> getEmployeeData(){
        return l1;
    }


    public static void main(String[] args) throws Exception {
        EmployeeData e1= new EmployeeData();
        e1.addData("marry", "cs", 260000, "female", "15/10/2020", "15/04/2000" ,"junior");
        e1.addData("rose", "ee", 250000, "female","25/12/2015", "21/06/1993", "mid");
        e1.addData("julie", "ec", 350000, "female","12/07/2014", "10/03/1993", "mid");
        e1.addData("tony", "cs", 250000, "male", "04/04/2010","23/08/1989", "mid");
        e1.addData("kail", "cs", 450000, "female", "28/12/2012", "10/09/1990", "mid");
        e1.addData("kate", "ee", 450000, "female","01/10/2015", "26/01/1994", "junior");
        e1.addData("joe", "ee", 250000, "male","01/12/2021", "25/10/2000", "junior");
        e1.addData("anthony", "ec", 380000, "male", "12/05/2019", "21/03/1995", "junior");
        e1.addData("andrew", "ec", 300000, "male", "26/06/2021", "19/07/1997", "junior");
        e1.addData("swift", "cs", 500000, "female", "12/07/2020", "02/05/1996", "junior");
        e1.addData("cooper", "ee", 470000, "male", "18/10/2020", "05/05/1996", "junior");
        e1.addData("hailey", "cs", 420000, "female", "09/11/2016", "21/02/1994", "mid");
        e1.addData("marry", "cs", 260000, "female", "15/10/2021", "15/04/2000", "junior");


        NoOfDepartmentEmployees d1=new NoOfDepartmentEmployees();
        System.out.println( d1.employeesInDept(e1.getEmployeeData()));

        DepartmentGroup dg1= new DepartmentGroup();
        System.out.println(dg1.employeesInDept(e1.getEmployeeData()));

        PromoteEmployees p1= new PromoteEmployees();
        p1.seniorEmp(e1.getEmployeeData());

        IncreaseSalary i1= new IncreaseSalary();
        i1.salInc(e1.getEmployeeData(), "ee", 4, 2000);







    }
}
