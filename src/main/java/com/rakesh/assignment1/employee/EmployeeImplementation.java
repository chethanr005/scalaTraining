package com.rakesh.assignment1.employee;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeImplementation {
    private static List<Employee> employeeData = new ArrayList<>();

    //DataBase Related
    public void addEmployee(String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel) {
        employeeData.add(new Employee(name, department, salary, gender, joiningDate, dob, jobLevel));
    }

    public static void addEmployee() {
        Employee emp1 = new Employee("John", "IT Development", 35000.0, "male", LocalDate.of(2021, 8, 11), LocalDate.of(1998, 5, 12), "Junior");
        Employee emp2 = new Employee("Sunil", "IT Development", 35000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(1997, 4, 20), "Junior");
        Employee emp3 = new Employee("Rohit", "Business Analysis", 45000.0, "male", LocalDate.of(2020, 8, 11), LocalDate.of(1995, 8, 17), "Mid-Term");
        Employee emp4 = new Employee("Edwin", "Administration", 55000.0, "male", LocalDate.of(2010, 8, 11), LocalDate.of(1994, 2, 21), "Junior");


        employeeData.add(emp1);
        employeeData.add(emp2);
        employeeData.add(emp3);
        employeeData.add(emp4);
    }

    public static List<Employee> getDatabase() {
        addEmployee();
        return employeeData;
    }
    //---------------------------------------------------------------------------

    //1. Don't allow child labours while taking employee
    public static AddEmployeeContainer addEmployee(String name, String department, double salary, String gender, String joiningDate, String dob, String jobLevel) {
        DateTimeFormatter      dtf   = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate              ldDob = LocalDate.parse(dob, dtf);
        LocalDate              ldJd  = LocalDate.parse(joiningDate, dtf);
        int                    age   = Period.between(ldDob, LocalDate.now()).getYears();
        EmployeeImplementation ei    = new EmployeeImplementation();
        if (age >= 21) {
            ei.addEmployee(name, department, salary, gender, ldJd, ldDob, jobLevel);
            return new AddEmployeeContainer(true);
        } else {
            return new AddEmployeeContainer(false);
        }
    }


    //2. Get no of employees by given department
    public static EmployeeDepartmentContainer getCountOfDepartment(List<Employee> employeeList, String department) {
        long count = employeeList.stream().filter(s -> s.getDepartment().equals(department)).count();
        return new EmployeeDepartmentContainer(count);
    }

    public static EmployeeDepartmentContainer getAllDeptCount(List<Employee> employeeList) {
        Map<String, Long> result = employeeList.stream().collect(Collectors.groupingBy(s -> s.getDepartment(), Collectors.counting()));
        return new EmployeeDepartmentContainer(result);
    }


    //3. group employees by department
    public static GroupEmployeeContainer getGroupByDepartment(List<Employee> employeeList) {
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
    public static HikeSalaryContainer getHikedEmployees(List<Employee> employeeList, String department) {
        Map<String, Double> res  = new HashMap<>();
        Optional<String>    dept = Optional.ofNullable(department);
        if (dept.isPresent()) {
            List<Employee> filteredList = employeeList.stream().filter(s -> s.getDepartment().equals(dept.get())).collect(Collectors.toList());

            for (Employee employee : filteredList) {
                updateDatabase(employee);
                res.put(employee.getName(), employee.getSalary());
            }
            return new HikeSalaryContainer(res);
        } else {
            return new HikeSalaryContainer(null);
        }
    }

    private static void updateDatabase(Employee emp) {
        emp.setSalary(emp.getSalary() + 5000.0);
    }


    //5. promote employees having 8 years experience to Senior position
    public static PromoteEmployeeContainer promoteEmployees(List<Employee> employeeList) {
        Map<String, String> result = new HashMap<>();
        List<Employee> filteredEmp = employeeList.stream()
                                                 .filter(s -> Period.between(s.getJoiningDate(), LocalDate.now()).getYears() > 8).collect(Collectors.toList());
        for (Employee employee : filteredEmp) {
            updatePosition(employee);
            result.put(employee.getName(), employee.getJobLevel());
        }
        return new PromoteEmployeeContainer(result);
    }

    private static void updatePosition(Employee emp) {
        emp.setJobLevel("Senior");
    }

}
