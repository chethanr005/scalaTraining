package com.kishor.assignment1.employee;

import com.kishor.assignment3.employee.EmployeeDatabase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by Kishor on Feb 01, 2022.
 */

public class EmployeeImplementation {
    public static Employee addEmployee(int empId, String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel) {
        int p = Period.between(dob, LocalDate.now()).getYears();
        if (p > 21) {
            return new Employee(empId, name, department, salary, gender, joiningDate, dob, jobLevel);
        } else {
            return null;
        }
    }

    public static EmployeeCountByDepartmentContainer noOfEmpByCount(List<Employee> employees, String dept) {
        Long countOFDept = employees.stream().filter(f -> f.getDepartment().equals(dept)).count();
        return new EmployeeCountByDepartmentContainer(dept, countOFDept);
    }

    public static List<GroupByDepartmentContainer> grpByDept(List<Employee> employees) {
        return employees.stream().map(employee -> employee.getDepartment()).distinct().collect(toList()).stream()
                        .map(department -> {
                            return new GroupByDepartmentContainer(employees.stream()
                                                                           .filter(employee1 -> employee1.getDepartment().equals(department))
                                                                           .map(employee2 -> employee2.getName()).collect(toList()), department);
                        })
                        .collect(toList());
    }

    public static List<IncreaseSalaryContainer> increaseSalaryForDept(List<Employee> employees, String department, double hike) throws SQLException {
        return employees.stream().filter(employee -> employee.getDepartment().equals(department)).collect(toList())
                        .stream().map(employee -> {
                    double increasedSalary = employee.getSalary() + hike;
                    try {
                        EmployeeDatabase.updateValueThroughEmpId("Salary", String.valueOf(increasedSalary), employee.getEmpId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return new IncreaseSalaryContainer(employee.getName(), increasedSalary);
                }).collect(toList());
    }

    public static List<PromoteEmployeeContainer> promoteEmployee(List<Employee> employees) throws SQLException {
        return employees.stream().filter(employee -> (Period.between(employee.getJoiningDate(), LocalDate.now()).getYears()) > 8)
                        .map(employee -> {
                            try {
                                EmployeeDatabase.updateValueThroughEmpId("JobLevel", "Senior", employee.getEmpId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return new PromoteEmployeeContainer(employee.getName(), employee.getJobLevel());
                        })
                        .collect(Collectors.toList());
    }

    public static void main(String[] args) throws SQLException {
        List<Employee> e = EmployeeDatabase.getAllEmployees();
        //System.out.println(increaseSalaryForDept(e,"IT Development",5000l));
        System.out.println(grpByDept(e));
    }

}
