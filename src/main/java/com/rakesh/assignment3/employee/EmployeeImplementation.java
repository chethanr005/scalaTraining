package com.rakesh.assignment3.employee;


import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeImplementation {
    //1. Add new Employee
    public static void addNewEmployee(Employee1 employee) {
        EmployeeDataBase.addEmployee(employee);
    }

    //2. Get no of employees by given department
    public static EmployeeDepartmentContainer getCountOfDepartment(List<Employee1> employeeList, String department) {
        long count = employeeList.stream().filter(s -> s.getDepartment().equals(department)).count();
        return new EmployeeDepartmentContainer(count);
    }

    public static EmployeeDepartmentContainer getAllDeptCount(List<Employee1> employeeList) {
        Map<String, Long> result = employeeList.stream().collect(Collectors.groupingBy(s -> s.getDepartment(), Collectors.counting()));
        return new EmployeeDepartmentContainer(result);
    }


    //3. group employees by department
    public static GroupEmployeeContainer getGroupByDepartment(List<Employee1> employeeList) {
        Map<String, List<String>> result = new HashMap<>();

        List<String> dept = employeeList.stream().map(s -> s.getDepartment()).collect(Collectors.toList());

        for (int i = 0; i < dept.size(); i++) {
            int          k    = i;
            List<String> name = null;
            name = employeeList.stream().filter(s -> s.getDepartment().equals(dept.get(k))).map(s -> s.getName()).sorted().collect(Collectors.toList());
            result.put(dept.get(k), name);
        }

        return new GroupEmployeeContainer(result);
    }

    //4. Increase salary of employees for given Department , not necessary that there will be hike !
    public static HikeSalaryContainer getHikedEmployees(List<Employee1> employeeList, String department, double hike) {
        Map<String, Double> res  = new HashMap<>();
        Optional<String>    dept = Optional.ofNullable(department);
        if (dept.isPresent()) {
            List<Employee1> filteredList = employeeList.stream().filter(s -> s.getDepartment().equals(dept.get())).collect(Collectors.toList());

            for (Employee1 employee : filteredList) {
                updateSalary(employee, hike);
                res.put(employee.getName(), employee.getSalary());
            }
            return new HikeSalaryContainer(res);
        } else {
            return new HikeSalaryContainer(null);
        }
    }

    private static void updateSalary(Employee1 emp, double hike) {
        String newValue = String.valueOf(emp.getSalary() + hike);
        emp.setSalary(emp.getSalary() + hike);
        EmployeeDataBase.updateData("Salary", newValue, emp.getEmpID());
    }


    //5. promote employees having 8 years experience to Senior position
    public static List<PromoteEmployeeContainer> promoteEmployees(List<Employee1> employeeList) {
       List<PromoteEmployeeContainer> result=new ArrayList<>();
        List<Employee1> filteredEmp = employeeList.stream()
                                                  .filter(s -> Period.between(s.getJoiningDate(), LocalDate.now()).getYears() > 8).collect(Collectors.toList());
        for (Employee1 employee : filteredEmp) {
            updatePosition(employee);
            result.add(new PromoteEmployeeContainer(employee.getEmpID(), employee.getName(),employee.getJobLevel()));
        }
        return result;
    }

    private static void updatePosition(Employee1 emp) {
        emp.setJobLevel("Senior");
        EmployeeDataBase.updateData("JobLevel", "Senior", emp.getEmpID());
    }
}
