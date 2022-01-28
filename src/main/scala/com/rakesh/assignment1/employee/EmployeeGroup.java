package com.rakesh.assignment1.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeGroup {

    public Map<String, List<String>> empGroupList(List<Employee> empList)
    {
        Map<String, List<String >> empgroup= new HashMap<>();

        List<String> dept=empList.stream().map(s->s.getDepartment()).collect(Collectors.toList());

        for(int i=0;i<dept.size();i++)
        {
            int k=i;
            List<String>name=null;
            name= empList.stream().filter(s->s.getDepartment().equals(dept.get(k))).map(s->s.getName()).collect(Collectors.toList());
            empgroup.put(dept.get(k),name);
        }
        return empgroup;
    }
}
