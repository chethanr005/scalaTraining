package com.kishor.assignment4.student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Kishor on Feb 24, 2022.
 */

public class StudentImplementation {
    private IStudent iStudent;

    StudentImplementation(IStudent database) {
        this.iStudent = database;
    }

    ExecutorService service = Executors.newCachedThreadPool();

    public CompletableFuture<MaleAndFemaleContainer> maleAndFemaleCount() throws SQLException, ExecutionException, InterruptedException {
        List<Student> students = iStudent.getAllStudents().get();
        Supplier<MaleAndFemaleContainer> maleAndFemale = () -> {
            long maleCount   = students.stream().filter(student -> student.getGender().equals("male")).count();
            long femaleCount = students.stream().filter(student -> student.getGender().equals("female")).count();
            return new MaleAndFemaleContainer(maleCount, femaleCount);
        };
        return CompletableFuture.supplyAsync(maleAndFemale, service);
    }

    public CompletableFuture<List<String>> addPrefixToStudents() throws SQLException, ExecutionException, InterruptedException {
        List<Student> students = iStudent.getAllStudents().get();
        Supplier<List<String>> prefix = () -> {
            return students.stream().map(student -> {
                if (student.getGender().equals("male"))
                    return "Mr " + student.getName();
                else
                    return "Ms " + student.getName();
            }).collect(Collectors.toList());
        };
        return CompletableFuture.supplyAsync(prefix, service);
    }

    public CompletableFuture<List<GradeLevelContainer>> getAllGradeLevel() throws SQLException, ExecutionException, InterruptedException {
        List<Student> students = iStudent.getAllStudents().get();
        Supplier<List<GradeLevelContainer>> getGradeLeve = () -> {
            return students.stream().map(student -> student.getGradeLevel()).distinct().collect(Collectors.toList()).stream()
                           .map(distinctGrade -> {
                               Long count = students.stream().filter(student -> student.getGradeLevel() == distinctGrade).count();
                               return new GradeLevelContainer(distinctGrade, count);
                           }).collect(Collectors.toList());
        };
        return CompletableFuture.supplyAsync(getGradeLeve, service);
    }


    public CompletableFuture<List<ActivityContainer>> getNoOfStudentsBelongsToEachActivities() throws SQLException, ExecutionException, InterruptedException {
        List<Student> students = iStudent.getAllStudents().get();
        Supplier<List<ActivityContainer>> allActivities = () -> {
            return students.stream().flatMap(student -> student.getActivities().stream()).distinct().collect(Collectors.toList()).stream()
                           .map(activity -> {
                               Long count = students.stream().map(student -> student.getActivities()).flatMap(activities -> activities.stream()).filter(activities -> activities.equals(activity)).count();
                               return new ActivityContainer(activity, count);
                           }).collect(Collectors.toList());
        };
        return CompletableFuture.supplyAsync(allActivities, service);
    }

    public CompletableFuture<List<PerformanceContainer>> getPerformanceOfStudents() throws SQLException, ExecutionException, InterruptedException {
        List<Student> students = iStudent.getAllStudents().get();
        Supplier<List<PerformanceContainer>> getPerformance = () -> {
            ArrayList<PerformanceContainer> result    = new ArrayList<>();
            List<Student>                   poor      = students.stream().filter(student -> student.getGpa() >= 0.0 && student.getGpa() <= 4.0).collect(Collectors.toList());
            List<Student>                   average   = students.stream().filter(student -> student.getGpa() >= 4.1 && student.getGpa() <= 7.0).collect(Collectors.toList());
            List<Student>                   excellent = students.stream().filter(student -> student.getGpa() >= 7.1).collect(Collectors.toList());
            result.add(new PerformanceContainer("poor", poor));
            result.add(new PerformanceContainer("average", average));
            result.add(new PerformanceContainer("excellent", excellent));
            return result;
        };
        return CompletableFuture.supplyAsync(getPerformance, service);
    }

    public static void main(String[] args) throws SQLException, ExecutionException, InterruptedException {
        StudentDatabase       d    = new StudentDatabase();
        StudentImplementation s    = new StudentImplementation(d);
        StudentMockDatabase   mock = new StudentMockDatabase();
        System.out.println("1.1 Males Count     : " + s.maleAndFemaleCount().get().getmales());
        System.out.println("1.2 Females Count     : " + s.maleAndFemaleCount().get().getmales() + "\n\n");
        System.out.println("2. Student Names with Prefix     : " + s.addPrefixToStudents().get() + "\n\n");
        System.out.println("3. All Grade Level's     :");
        s.getAllGradeLevel().get().stream().forEach(System.out::println);
        System.out.println("\n\n4. No of Student's belonging to particular Activity     :");
        s.getNoOfStudentsBelongsToEachActivities().get().stream().forEach(System.out::println);
        System.out.println("\n\n5. All Student's Performance     :");
        s.getPerformanceOfStudents().get().stream().forEach(System.out::println);
    }


}
