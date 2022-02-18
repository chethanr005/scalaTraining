package com.chethan.assignment2.student;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Feb 02, 2022.
 */

public class StudentImplementation {

    public static List<Subject> getSubject() {
        return Arrays.asList(new Subject(101, "java"),
                new Subject(102, "scala"),
                new Subject(103, "angular"));
    }

    public static List<Student> getStudentList() throws Exception {
        return Arrays.asList(new Student("rose", "R0001", 10, Arrays.asList(new MarkDetails(101, Optional.ofNullable(65)), new MarkDetails(102, Optional.ofNullable(null)), new MarkDetails(103, Optional.ofNullable(70)))),
                new Student("julie", "R0002", 9, Arrays.asList(new MarkDetails(101, Optional.ofNullable(34)), new MarkDetails(102, Optional.ofNullable(45)), new MarkDetails(103, Optional.ofNullable(50)))),
                new Student("tony", "R0003", 8, Arrays.asList(new MarkDetails(101, Optional.ofNullable(92)), new MarkDetails(102, Optional.ofNullable(95)), new MarkDetails(103, Optional.ofNullable(91)))),
                new Student("kail", "R0004", 10, Arrays.asList(new MarkDetails(101, Optional.ofNullable(45)), new MarkDetails(102, Optional.ofNullable(64)), new MarkDetails(103, Optional.ofNullable(95)))),
                new Student("kate", "R0005", 10, Arrays.asList(new MarkDetails(101, Optional.ofNullable(30)), new MarkDetails(102, Optional.ofNullable(64)), new MarkDetails(103, Optional.ofNullable(94)))),
                new Student("anthony", "R0006", 10, Arrays.asList(new MarkDetails(101, Optional.ofNullable(60)), new MarkDetails(102, Optional.ofNullable(90)), new MarkDetails(103, Optional.ofNullable(null)))),
                new Student("andrew", "R0007", 8, Arrays.asList(new MarkDetails(101, Optional.ofNullable(51)), new MarkDetails(102, Optional.ofNullable(59)), new MarkDetails(103, Optional.ofNullable(70)))),
                new Student("swift", "R0008", 9, Arrays.asList(new MarkDetails(101, Optional.ofNullable(64)), new MarkDetails(102, Optional.ofNullable(59)), new MarkDetails(103, Optional.ofNullable(79)))),
                new Student("cooper", "R0009", 10, Arrays.asList(new MarkDetails(101, Optional.ofNullable(81)), new MarkDetails(102, Optional.ofNullable(89)), new MarkDetails(103, Optional.ofNullable(85)))),
                new Student("hailey", "R0010", 9, Arrays.asList(new MarkDetails(101, Optional.ofNullable(57)), new MarkDetails(102, Optional.ofNullable(69)), new MarkDetails(103, Optional.ofNullable(77)))));

    }

    /**
     * 1. Get total and average marks of all students
     **/
    public static Map<String, TotalAverageContainer> getTotalAndAverage(List<Student> studentList) {
        Map<String, TotalAverageContainer> totalAverageMarks = new TreeMap<String, TotalAverageContainer>();
        studentList.stream().forEach(m -> {
            totalAverageMarks.put(m.getName(), new TotalAverageContainer(m.getStudentTotal(m), m.getStudentAverage(m)));
        });
        return totalAverageMarks;
    }

    /**
     * 2. Total marks scored by each standard
     **/
    public static List<TotalMarksByStandard> getTotalMarksByStandard(List<Student> studentList) throws Exception {

        return Student.getDistinctStandard(getStudentList()).stream().map(m -> {
            return new TotalMarksByStandard(m, Student.getTotalByStandard(studentList, m));
        }).collect(Collectors.toList());
    }

    /**
     * 3. Get the students who scored highest in given subject
     */
    public static List<HighestScoreInSubjectContainer> getHighestScoreInSubject(List<Student> studentList, String subjectName) throws Exception {

        List<HighestScoreInSubjectContainer> highestScoringStudentsList = new ArrayList<HighestScoreInSubjectContainer>();

        if (Subject.getSubjectList(getSubject()).contains(subjectName)) {
            int subjectID = getSubject().stream().filter(f -> f.getName() == subjectName).map(m -> m.getId()).findAny().get();

            List<HighestScoreInSubjectContainer> highestScoringByStandard = Student.getDistinctStandard(getStudentList()).stream()
                                                                                   .map(m1 -> new HighestScoreInSubjectContainer(m1, studentList.stream()
                                                                                                                                                .filter(f1 -> f1.getStandard() == m1)
                                                                                                                                                .collect(Collectors.toList()).stream()
                                                                                                                                                                                                                    .max(Comparator.comparing(s -> s.getSpecificSubjectMarks(s, subjectID)))
                                                                                                                                                .map(m2 -> m2.getName()).get())).collect(Collectors.toList());
            highestScoringStudentsList.addAll(highestScoringByStandard);

        } else throw new NoSuchElementException("Subject Not Found!!!!!!!!!!");
        return highestScoringStudentsList;
    }

    /**
     * 4. Get the students details who did not appear in exam
     */
    public static List<StudentsNotAppearedContainer> getStudentsNotAppeared(List<Student> studentList) {
        return studentList.stream().filter(m -> m.ifAbsent(m).contains(true)).map(f -> new StudentsNotAppearedContainer(f.getName(), f.getRegistrationCode()))
                          .collect(Collectors.toList());
    }

    /**
     * 5. Highest marks in each subject with name of students
     */
    public static List<HighestMarksInEachSubject> getHighestMarksInEachSubject(List<Student> studentList) throws Exception {
        return  Subject.getIdList(getSubject()).stream().map(m1 -> studentList.stream().max(Comparator.comparing(s -> s.getSpecificSubjectMarks(s, m1)))
                                                                             .map(m2 -> new HighestMarksInEachSubject(m1, m2.getMarkDetails().stream().filter(f1 -> f1.getSubjectId() == m1)
                                                                                                                            .map(m3 -> m3.getMarks()).findAny().get(), m2.getName())).get()).collect(Collectors.toList());
    }

    /**
     * 6. List of "award winning" students
     */

    public static List<AwardWinningStudentsContainer> getAwardWinningStudents(List<Student> studentList) {
        return studentList.stream().map(m1 -> m1.getMarkDetails().stream().filter(f1 -> f1.getMarks() >= 90)
                            .map(m2 -> new AwardWinningStudentsContainer(m1.getName(), m2.getSubjectId(), m2.getMarks())).collect(Collectors.toList()))
                          .flatMap(f3 -> f3.stream())
                          .collect(Collectors.toList());
    }


}