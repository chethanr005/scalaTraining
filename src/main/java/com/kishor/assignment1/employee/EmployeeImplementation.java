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
        List<String> distDpartment = employees.stream().map(m -> m.getDepartment()).distinct().collect(toList());
        return distDpartment.stream()
                            .map(m -> {
                                return new GroupByDepartmentContainer(employees.stream()
                                                                               .filter(f1 -> f1.getDepartment().equals(m))
                                                                               .map(m1 -> m1.getName()).collect(toList()), m);
                            })
                            .collect(toList());
    }

    public static List<IncreaseSalaryContainer> increaseSalaryForDept(List<Employee> employees, String s, double hike) throws SQLException {
        List<Integer> empIdOfDept = employees.stream()
                                             .filter(f -> f.getDepartment().equals(s))
                                             .map(m -> m.getEmpId())
                                             .collect(Collectors.toList());
        List<String> empNames = empIdOfDept.stream()
                                           .map(m -> employees.stream()
                                                              .filter(f -> f.getEmpId() == m)
                                                              .map(m1 -> m1.getName()).collect(toList()))
                                           .flatMap(f -> f.stream()).collect(toList());

        return empNames.stream().map(m1 -> employees.stream().filter(f -> f.getName() == m1)
                                                    .map(m2 -> {
                                                        double v = m2.getSalary() + hike;
                                                        empIdOfDept.stream().forEach(f -> {
                                                            try {
                                                                if (f == m2.getEmpId()) {
                                                                    EmployeeDatabase.updateValueThroughEmpId("Salary", String.valueOf(v), f);
                                                                }
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        });
                                                        return new IncreaseSalaryContainer(m1, v);
                                                    }).collect(toList()))
                       .flatMap(f -> f.stream()).collect(toList());
    }

    public static List<PromoteEmployeeContainer> promoteEmployee(List<Employee> employees) throws SQLException {
        return employees.stream().filter(f -> (Period.between(f.getJoiningDate(), LocalDate.now()).getYears()) > 8)
                        .map(m1 -> {
                            try {
                                EmployeeDatabase.updateValueThroughEmpId("JobLevel", "Senior", m1.getEmpId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return new PromoteEmployeeContainer(m1.getName(), m1.getJobLevel());
                        })
                        .collect(Collectors.toList());
    }

}
