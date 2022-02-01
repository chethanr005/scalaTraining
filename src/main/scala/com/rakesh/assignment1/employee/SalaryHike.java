package com.rakesh.assignment1.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//4. Increase salary of employees for given Department , not necessary that there will be hike !

public class SalaryHike {
    public Map<String,Double> hike(List<Employee> empList, String dept) {
        Optional<String> department = Optional.ofNullable(dept);
        Map<String,Double> res=new HashMap<>();


        if (department.isPresent()) {
            List<Employee>filteredList=empList.stream()
                    .filter(s -> s.getDepartment().equals(department.get())).collect(Collectors.toList());

            for (Employee employee : filteredList) {
                updateDatabase(employee);
                res.put(employee.getName(), employee.getSalary());
            }

            return res;
        }
        else {
            return null;
        }
    }
    private void updateDatabase(Employee emp)
    {
        emp.setSalary(emp.getSalary()+5000.0);
    }

}
