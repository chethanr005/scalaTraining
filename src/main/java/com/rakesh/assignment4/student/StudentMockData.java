package com.rakesh.assignment4.student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rakesh on Feb 25, 2022.
 */

public class StudentMockData {
    public List<GradeLevelContainer> gradeLevelContainersMockData() {
        return Arrays.asList(new GradeLevelContainer(1, 1), new GradeLevelContainer(4, 2),
                new GradeLevelContainer(6, 1), new GradeLevelContainer(7, 2),
                new GradeLevelContainer(8, 2), new GradeLevelContainer(9, 1),
                new GradeLevelContainer(10, 1));
    }

    public PrefixContainer prefixContainerMockData() {
        return new PrefixContainer(Arrays.asList("Mr.Ajay", "Mr.Dave", "Mr.Edwin", "Mr.James", "Mr.John", "Mr.Rohit", "Mr.Sunil", "Ms.Emily", "Ms.Jessi", "Ms.Sophia"));
    }

    public List<ActivityContainer> activityContainersMockData() {
        return Arrays.asList(new ActivityContainer("basketball", 3L),
                new ActivityContainer("cricket", 3L),
                new ActivityContainer("painting", 5L),
                new ActivityContainer("soccer", 6L),
                new ActivityContainer("swimming", 3L),
                new ActivityContainer("dancing", 3L),
                new ActivityContainer("chess", 2L)
        );
    }

    public List<PerformanceContainer> performanceContainersMockData() {
        List<PerformanceContainer> result = new ArrayList<>();
        result.add(new PerformanceContainer("Average", Arrays.asList(
                new Student(1001, "Ajay", 1, 6.7, "male", Arrays.asList("basketball", "cricket")),
                new Student(1005, "Sophia", 7, 4.5, "female", Arrays.asList("swimming", "dancing", "painting")),
                new Student(1008, "John", 8, 6.9, "male", Arrays.asList("painting", "basketball", "soccer")),
                new Student(1009, "Rohit", 9, 5.5, "male", Arrays.asList("cricket", "soccer")))));

        result.add(new PerformanceContainer("Excellent", Arrays.asList(
                new Student(1002, "Jessi", 4, 7.8, "female", Arrays.asList("painting", "soccer")),
                new Student(1007, "Sunil", 8, 8.5, "male", Arrays.asList("swimming", "dancing", "soccer")),
                new Student(1010, "Edwin", 10, 8.9, "male", Arrays.asList("painting", "chess", "soccer")),
                new Student(1003, "Emily", 4, 8.0, "female", Arrays.asList("painting", "chess", "dancing")))));

        result.add(new PerformanceContainer("Poor", Arrays.asList(
                new Student(1004, "Dave", 6, 3.9, "male", Arrays.asList("swimming", "soccer")),
                new Student(1006, "James", 7, 3.9, "male", Arrays.asList("basketball", "cricket")))));

        return result;
    }

    public PerformanceContainer performanceContainerByPassingValueMockData(String level) {
        PerformanceContainer result = null;
        if (level.equals("Average")) {
            result = new PerformanceContainer("Average", Arrays.asList(
                    new Student(1001, "Ajay", 1, 6.7, "male", Arrays.asList("basketball", "cricket")),
                    new Student(1005, "Sophia", 7, 4.5, "female", Arrays.asList("swimming", "dancing", "painting")),
                    new Student(1008, "John", 8, 6.9, "male", Arrays.asList("painting", "basketball", "soccer")),
                    new Student(1009, "Rohit", 9, 5.5, "male", Arrays.asList("cricket", "soccer"))));
        }
        if (level.equals("Excellent")) {
            result = new PerformanceContainer("Excellent", Arrays.asList(
                    new Student(1002, "Jessi", 4, 7.8, "female", Arrays.asList("painting", "soccer")),
                    new Student(1007, "Sunil", 8, 8.5, "male", Arrays.asList("swimming", "dancing", "soccer")),
                    new Student(1010, "Edwin", 10, 8.9, "male", Arrays.asList("painting", "chess", "soccer")),
                    new Student(1003, "Emily", 4, 8.0, "female", Arrays.asList("painting", "chess", "dancing"))));
        }
        if (level.equals("Poor")) {
            result = new PerformanceContainer("Poor", Arrays.asList(
                    new Student(1004, "Dave", 6, 3.9, "male", Arrays.asList("swimming", "soccer")),
                    new Student(1006, "James", 7, 3.9, "male", Arrays.asList("basketball", "cricket"))));
        }
        return result;
    }
}
