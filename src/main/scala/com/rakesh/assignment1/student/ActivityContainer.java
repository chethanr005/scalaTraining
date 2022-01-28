package com.rakesh.assignment1.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//4. get no of students participating in each type of activity , Return result in class ActivityContainer(String activity, int students)

public class ActivityContainer {
    public Map<String, Long> activityCount(List<Student> studentList)
    {
        Map<String,Long>gc= new HashMap<>();
        List<String> al= studentList.stream()
                .map(s->s.getActivities())
                .flatMap(s->s.stream())
                .distinct().collect(Collectors.toList());

        for(int i=0;i<al.size();i++)
        {
            int k=i;
            long c=0;
            c=studentList.stream()
                    .filter(s->s.getActivities().contains(al.get(k))).count();
            gc.put(al.get(k),c);
        }
        return gc;
    }
}
