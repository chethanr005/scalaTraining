package com.kishor.assignment2.student;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Kishor on Feb 02, 2022.
 */

public class StudentImplementation {

    static        Subject       s1       = new Subject(101, "Maths");
    static        Subject       s2       = new Subject(102, "Science");
    static        Subject       s3       = new Subject(103, "Social");
    public static List<Subject> subjects = Arrays.asList(s1, s2, s3);

    public static List<Subject> getSubject() {
        return subjects;
    }

    public static List<Student> getStudents() {
        Student student1  = new Student("Kishor", "R0001", 2, Arrays.asList(new MarkDetails(101, Optional.ofNullable(86)), new MarkDetails(102, Optional.ofNullable(56)), new MarkDetails(103, Optional.ofNullable(88))));
        Student student2  = new Student("Devi", "R0002", 2, Arrays.asList(new MarkDetails(101, Optional.ofNullable(78)), new MarkDetails(102, Optional.ofNullable(96)), new MarkDetails(103, Optional.ofNullable(85))));
        Student student3  = new Student("Jhon", "R0010", 3, Arrays.asList(new MarkDetails(101, Optional.ofNullable(98)), new MarkDetails(102, Optional.ofNullable(86)), new MarkDetails(103, Optional.ofNullable(88))));
        Student student4  = new Student("Rita", "R0003", 3, Arrays.asList(new MarkDetails(101, Optional.ofNullable(null)), new MarkDetails(102, Optional.ofNullable(45)), new MarkDetails(103, Optional.ofNullable(87))));
        Student student5  = new Student("Krishna", "R0004", 4, Arrays.asList(new MarkDetails(101, Optional.ofNullable(52)), new MarkDetails(102, Optional.ofNullable(78)), new MarkDetails(103, Optional.ofNullable(null))));
        Student student6  = new Student("Suresh", "R0005", 4, Arrays.asList(new MarkDetails(101, Optional.ofNullable(86)), new MarkDetails(102, Optional.ofNullable(77)), new MarkDetails(103, Optional.ofNullable(37))));
        Student student7  = new Student("Ramesh", "R0006", 5, Arrays.asList(new MarkDetails(101, Optional.ofNullable(45)), new MarkDetails(102, Optional.ofNullable(85)), new MarkDetails(103, Optional.ofNullable(77))));
        Student student8  = new Student("Mahesh", "R0007", 5, Arrays.asList(new MarkDetails(101, Optional.ofNullable(74)), new MarkDetails(102, Optional.ofNullable(55)), new MarkDetails(103, Optional.ofNullable(55))));
        Student student9  = new Student("Dinesh", "R0008", 6, Arrays.asList(new MarkDetails(101, Optional.ofNullable(38)), new MarkDetails(102, Optional.ofNullable(77)), new MarkDetails(103, Optional.ofNullable(48))));
        Student student10 = new Student("Ravi", "R0009", 6, Arrays.asList(new MarkDetails(101, Optional.ofNullable(44)), new MarkDetails(102, Optional.ofNullable(88)), new MarkDetails(103, Optional.ofNullable(99))));

        List<Student> students = Arrays.asList(student1, student2, student3, student4, student5, student6, student7, student8, student9, student10);
        return students;
    }


    /**
     * 1. Get total and average marks for all students
     */
    public static Map<String, TotalAndAverageContainer> getTotalAndAverage(List<Student> studentList) {
        List<Integer>                         total   = studentList.stream().map(m -> m.getMarksList().stream().reduce(0, (a, b) -> a + b)).collect(Collectors.toList());
        List<Integer>                         average = studentList.stream().map(m -> m.getMarksList().stream().reduce(0, (a, b) -> a + b / 3)).collect(Collectors.toList());
        List<String>                          names   = StudentImplementation.getStudents().stream().map(m -> m.getName()).collect(Collectors.toList());
        Map<String, TotalAndAverageContainer> result  = new TreeMap<>();
        for (int i = 0; i < total.size(); i++) {
            result.put(names.get(i), new TotalAndAverageContainer(total.get(i), average.get(i)));
        }
        return result;
    }

    /**
     * 2. Total marks scored by each standard
     */
    public static Integer totalMarksOfEachStandard(List<Student> studentList, Integer standard) {
        return studentList.stream().filter(f -> f.getStandard() == standard).map(m -> {
            return m.getMarksList().stream().reduce(0, (a, b) -> a + b);
        }).reduce(0, (a, b) -> a + b);
    }

    /**
     * 3. Get Students who scored Highest in given subject in each standard
     */
    public static List<HighestMarksScoredContainer> highestMarksInEachStandardForGivenSubject(List<Student> studentList, String subject) {
        int subjectId = Subject.giveId(subject);
        return Student.getDistinctStandard(studentList)
                      .stream()
                      .map(m1 -> {
                          return new HighestMarksScoredContainer(studentList.stream().filter(f1 -> f1.getStandard() == m1).collect(Collectors.toList())
                                                                            .stream().max(Comparator.comparing(c -> c.getMarksList(c, subjectId)))
                                                                            .map(Student::getName).get(), m1);
                      }).collect(Collectors.toList());


    }


    /**
     * 4 . Get Student details who did not appear for exam
     */
    public static Map<String, String> studentWhoAreAbsentForExam(List<Student> studentList) {
        Map<String, String> absenteesList = new TreeMap<String, String>();
        studentList.stream().filter(f -> f.ifAbsent().contains(true)).forEach(i -> {
            absenteesList.put(i.getName(), i.getRegistrationCode());
        });
        return absenteesList;
    }

    /**
     * 5. Highest Marks in each subject with Name of Student
     */
    public static List<HighestMarksInEachSubjectContainer> highestMarksInEachSubject(List<Student> studentList) {
        return Subject.getIdList(getSubject()).stream()
                      .map(m1 -> studentList.stream()
                                            .max(Comparator.comparing(s -> s.getSpecificSubjectMarks(s, m1)))
                                            .map(m2 -> new HighestMarksInEachSubjectContainer(m1,
                                                    m2.getMarkDetails().stream().filter(f1 -> f1.getId() == m1)
                                                      .map(m3 -> m3.getMarks()).findAny().get(),
                                                    m2.getName())).get()).collect(Collectors.toList());
    }

    /**
     * 6. Get list of Award Winning Students, criteria if Student scores >= 90 in any subject , he/she will awarded for that subject.
     * One student can get multiple awards.
     */
    public static List<AwardWinningStudentCOntainer> awardWinningStudents(List<Student> studentList) {
        return studentList.stream().map(m1 -> m1.getMarkDetails().stream().filter(f1 -> f1.getMarks() >= 90)
                                                .map(m2 -> new AwardWinningStudentCOntainer(m1.getName(), m2.getId(), m2.getMarks())).collect(Collectors.toList()))
                          .flatMap(f3 -> f3.stream())
                          .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(getTotalAndAverage(StudentImplementation.getStudents()) + "\n\n=================================");
        System.out.println(totalMarksOfEachStandard(StudentImplementation.getStudents(), 2) + "\n\n=================================");
        System.out.println(highestMarksInEachStandardForGivenSubject(StudentImplementation.getStudents(), "Maths") + "\n\n=================================");
        System.out.println(studentWhoAreAbsentForExam(StudentImplementation.getStudents()) + "\n\n=================================");
        System.out.println(highestMarksInEachSubject(StudentImplementation.getStudents()) + "\n\n=================================");
        System.out.println(awardWinningStudents(StudentImplementation.getStudents()) + "\n\n=================================");
        //System.out.println(totalMarksOfEachStandard(StudentImplementation.getStudents(),2));

    }
}
