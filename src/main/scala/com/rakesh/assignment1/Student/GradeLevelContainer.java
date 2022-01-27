package com.rakesh.assignment1.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GradeLevelContainer {

    public Map<Integer, Long> GradeLevelCount(List<Student> studentList) {

        Map<Integer, Long> gc = new HashMap<>();

        List<Integer> gl = studentList.stream()
                .map(Student::getGradeLevel).distinct().collect(Collectors.toList());


        for (int i = 0; i < gl.size(); i++) {
            int k = i;
            long c = 0;
            c =studentList.stream()
                    .filter(s -> s.getGradeLevel() == gl.get(k)).count();
            gc.put(gl.get(k), c);
        }
        return gc;
    }
}
