package com.chethan.assignment7.student;

import akka.Done;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Mar 30, 2022.
 */

public class StudentImplementation {

    ExecutorService threadPool = Executors.newCachedThreadPool();
    private IStudentDatabase studentDatabase;
    private BufferedReader   bufferedReader;
    private FileWriter       fileWriter;

    StudentImplementation(IStudentDatabase studentDatabase) {
        this.studentDatabase = studentDatabase;
    }

    private List<String> csvFileReader(String path) {
        List<String> studentsList = new ArrayList<String>();
        try {
            bufferedReader = new BufferedReader(new FileReader(path));

            String stringStudent = bufferedReader.readLine();
            while (stringStudent != null) {
                studentsList.add(stringStudent);
                stringStudent = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return studentsList;
    }

     CompletableFuture<Done> csvFileWriter(String path) {
        try {
            List<Student> actualStudentsList = studentDatabase.getStudentsData().get();
            String studentsData = actualStudentsList.stream().map(student -> {
                String actualActivities = student.getActivities().stream().collect(Collectors.joining(";"));
                return student.getId() + "," + student.getName() + "," + student.getGpa() + "," + student.getGender() + "," + student.getGradeLevel() + "," + actualActivities;
            }).collect(Collectors.joining("\n"));

            FileWriter fr = new FileWriter(path);
            fr.write(studentsData);
            fr.flush();
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(new File(path).exists())
        return CompletableFuture.completedFuture(Done.getInstance());
        else    return null;
    }

    public CompletableFuture<Map<String, List<String>>> updateFromCSVToDatabase(String path) {


        Map<String, List<String>> outputData = new TreeMap<String, List<String>>();
        List<String>              added    = new ArrayList<String>();
        List<String>              updated    = new ArrayList<String>();

        List<String> studentsList = csvFileReader(path);

        studentsList.stream().forEach(studentString -> {
            String[]     student    = studentString.split(",");
            List<String> activities = Arrays.asList(student[student.length - 1].split(";"));
            try {
                if (studentDatabase.getStudent(student[0]).get()== null) {
                    CompletableFuture<Student> addedStudent = studentDatabase.addStudent(new Student(student[0], student[1], Double.parseDouble(student[2]), student[3], Integer.parseInt(student[4]), activities));
                    added.add(addedStudent.get().getId());
                } else {
                    CompletableFuture<Student> updatedStudent = studentDatabase.updateStudentDetails(new Student(student[0], student[1], Double.parseDouble(student[2]), student[3], Integer.parseInt(student[4]), activities));
                    updated.add(updatedStudent.get().getId());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        outputData.put("added", added);
        outputData.put("updated", updated);
        return CompletableFuture.completedFuture(outputData);
    }

    public CompletableFuture<List<String>> deleteStudentFromCSV(String path) {

        List<String> studentsList = csvFileReader(path);
        List<String> success      = new ArrayList<String>();
        studentsList.stream().forEach(studentString -> {
            String[] studentArray = studentString.split(",");
            studentDatabase.deleteStudentsById(studentArray[0]);
            success.add(studentArray[0]);
        });
        return CompletableFuture.completedFuture(success);
    }
}
