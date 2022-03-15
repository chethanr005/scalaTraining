package com.rakesh.assignment2.student;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Rakesh on Feb 02, 2022.
 */

public class StudentImplementation {

    public static List<Subject> subjectGroup() {
        Subject s1 = new Subject(101, "Java");
        Subject s2 = new Subject(102, "Scala");
        Subject s3 = new Subject(103, "C++");
        return Arrays.asList(s1, s2, s3);
    }

    public static List<Student> getStudentsList() {
        Student s1  = new Student("Edwin", "DA001", 7, Arrays.asList((new MarkDetailsContainer(101, Optional.of(60))), (new MarkDetailsContainer(102, Optional.of(72))), (new MarkDetailsContainer(103, Optional.empty()))));
        Student s2  = new Student("Antony", "DA002", 7, Arrays.asList((new MarkDetailsContainer(101, Optional.of(80))), (new MarkDetailsContainer(102, Optional.of(90))), (new MarkDetailsContainer(103, Optional.of(65)))));
        Student s3  = new Student("Priyanka", "DA003", 8, Arrays.asList((new MarkDetailsContainer(101, Optional.of(78))), (new MarkDetailsContainer(102, Optional.of(80))), (new MarkDetailsContainer(103, Optional.of(70)))));
        Student s4  = new Student("Raj", "DA004", 8, Arrays.asList((new MarkDetailsContainer(101, Optional.empty())), (new MarkDetailsContainer(102, Optional.of(91))), (new MarkDetailsContainer(103, Optional.of(83)))));
        Student s5  = new Student("Wilson", "DA005", 9, Arrays.asList((new MarkDetailsContainer(101, Optional.of(92))), (new MarkDetailsContainer(102, Optional.of(96))), (new MarkDetailsContainer(103, Optional.of(65)))));
        Student s6  = new Student("Sunil", "DA006", 9, Arrays.asList((new MarkDetailsContainer(101, Optional.of(96))), (new MarkDetailsContainer(102, Optional.of(87))), (new MarkDetailsContainer(103, Optional.of(67)))));
        Student s7  = new Student("John", "DA007", 9, Arrays.asList((new MarkDetailsContainer(101, Optional.of(89))), (new MarkDetailsContainer(102, Optional.empty())), (new MarkDetailsContainer(103, Optional.of(77)))));
        Student s8  = new Student("Jessi", "DA008", 10, Arrays.asList((new MarkDetailsContainer(101, Optional.of(68))), (new MarkDetailsContainer(102, Optional.of(55))), (new MarkDetailsContainer(103, Optional.of(84)))));
        Student s9  = new Student("Ali", "DA009", 10, Arrays.asList((new MarkDetailsContainer(101, Optional.of(73))), (new MarkDetailsContainer(102, Optional.of(86))), (new MarkDetailsContainer(103, Optional.of(85)))));
        Student s10 = new Student("Sreeja", "DA010", 10, Arrays.asList((new MarkDetailsContainer(101, Optional.of(63))), (new MarkDetailsContainer(102, Optional.of(78))), (new MarkDetailsContainer(103, Optional.of(90)))));

        return Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10);
    }


    //1. Get total and average marks for all students
    public static Map<String, TotalAndAverageContainer> getTotalAndAverage(List<Student> studentList) {
        Map<String, TotalAndAverageContainer> result = new TreeMap<>();
        for (Student std : studentList) {
            long                     total = Student.getTotal(std);
            double                   avg   = total / 3;
            TotalAndAverageContainer res   = new TotalAndAverageContainer(total, avg);
            result.put(std.registrationCode, res);
        }
        return result;
    }


    //2. Total marks scored by each standard
    public static List<TotalMarksByStandardContainer> getTotalByStandard(List<Student> studentList) {
        List<TotalMarksByStandardContainer> result    = new ArrayList<>();
        List<Integer>                       standards = studentList.stream().map(Student::getStandard).distinct().collect(Collectors.toList());

        for (int i = 0; i < standards.size(); i++) {
            int               k     = i;
            Optional<Integer> total = studentList.stream().filter(stud -> stud.getStandard() == standards.get(k)).map(Student::getMarkDetails).flatMap(m -> m.stream()).map(MarkDetailsContainer::getMarks).reduce(Integer::sum);
            result.add(new TotalMarksByStandardContainer(standards.get(i), total.get()));
        }
        return result;
    }

    //3. Get Students who scored Highest in given subject in each standard
    public static List<HighestScoredStudentsContainer> getHighestScoredStudents(List<Student> studentList, String subject) {
        List<HighestScoredStudentsContainer> result    = new ArrayList<>();
        List<Integer>                        standards = studentList.stream().map(Student::getStandard).distinct().collect(Collectors.toList());
        for (int i = 0; i < standards.size(); i++) {
            int           k                   = i;
            List<Student> filteredStudentList = studentList.stream().filter(s -> s.getStandard() == standards.get(k)).collect(Collectors.toList());
            String        name                = getStudentName(filteredStudentList, subject).map(Student::getName).get();
            result.add(new HighestScoredStudentsContainer(standards.get(k), name));
        }
        return result;
    }


    //This method returns the name of the Highest Scored Student.
    private static Optional<Student> getStudentName(List<Student> filteredList, String sub) {
        return filteredList.stream().max(Comparator.comparing(s -> getStudentMark(s, getSubjectId(sub))));
    }

    //This method returns marks of Subject ID passed.
    private static int getStudentMark(Student student, int subjectID) {
        int mark = student.getMarkDetails().stream().filter(sub -> sub.subjectId == subjectID).map(marks -> marks.getMarks()).findAny().get();
        return mark;
    }

    //Checks if the subjectName is present in data or not.
    private static int getSubjectId(String subject) {
        List<Integer> id = subjectGroup().stream().filter(s -> s.name.equals(subject)).map(i -> i.id).collect(Collectors.toList());
        if (id.isEmpty()) {
            throw new RuntimeException("Subject not found");
        } else {
            return id.get(0).intValue();
        }
    }


    //4. Get Student details who did not appear for exam
    public static List<AbsentStudentContainer> getAbsentStudents(List<Student> studentList) {
        List<AbsentStudentContainer> result = new ArrayList<>();
        for (Student std : studentList) {
            List<MarkDetailsContainer> marksList = std.getMarkDetails().stream().collect(Collectors.toList());
            for (MarkDetailsContainer sub : marksList) {
                if (sub.marks.isPresent()) {
                } else {
                    result.add(new AbsentStudentContainer(sub.subjectId, std.getName(), std.getRegistrationCode()));
                }
            }
        }
        return result;
    }

    //5. Highest Marks in each subject with Name of Student
    public static List<HighestMarksContainer> highestScoredBySubject(List<Student> studentList) {
        List<HighestMarksContainer> result  = new ArrayList<>();
        List<Subject>               subList = subjectGroup();
        for (int i = 0; i < subList.size(); i++) {
            int               k    = i;
            String            name = getStudentName(studentList, subList.get(k).name).map(Student::getName).get();
            Optional<Integer> mark = getStudentName(studentList, subList.get(k).name).map(Student::getMarkDetails).map(m -> fetchMark(m, subList.get(k).id)).map(res -> res.marks).get();

            result.add(new HighestMarksContainer(subList.get(k).name, name, mark.get()));
        }
        return result;
    }

    private static MarkDetailsContainer fetchMark(List<MarkDetailsContainer> markList, int subID) {
        MarkDetailsContainer result = markList.stream().filter(m -> m.subjectId == subID).findFirst().get();
        return result;
    }

    //6. Get list of Award Winning Students, criteria if Student scores >= 90 in any subject , he/she will awarded for that subject.
    public static List<AwardWinnerContainer> awardedStudents(List<Student> studentList) {
        List<Subject> subList = subjectGroup();
        return (subList.stream().map(subject -> (studentList.stream().filter(std -> getStudentMark(std, subject.id) >= 90).collect(Collectors.toList()).stream()
                                                            .map(f -> new AwardWinnerContainer(subject.name, f.getName(), f.getRegistrationCode(), getStudentMark(f, subject.id)))
                                                            .collect(Collectors.toList()))).flatMap(s -> s.stream()).collect(Collectors.toList()));

    }

}


