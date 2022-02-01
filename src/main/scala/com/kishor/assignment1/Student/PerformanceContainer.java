package com.kishor.assignment1.Student;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 5. group students based on GPA with below criteria, Return result in class PerformanceContainer(String level, int students)
 */
class PerformanceContainer {
    public Map<String,Long> performerGroup (List<Student> student) {
        Long a = student.stream().filter(f -> f.getGpa()>=0.0 && f.getGpa()<=4.0).count();
        Long b = student.stream().filter(f -> f.getGpa()>=4.1 && f.getGpa()<=7.0).count();
        Long c = student.stream().filter(f -> f.getGpa()>=7.1).count();
        Map<String,Long> perform = new HashMap<String,Long>();
        perform.put("poor",a);
        perform.put("Average",b);
        perform.put("Excellent",c);
        return perform;
    }
}