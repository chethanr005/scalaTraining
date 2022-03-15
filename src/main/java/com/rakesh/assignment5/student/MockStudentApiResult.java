package com.rakesh.assignment5.student;

/**
 * Created by Rakesh on Mar 14, 2022.
 */

public class MockStudentApiResult {
    public String getAllStudents()
    {
        return "[{\"activities\":[\"basketball\",\"cricket\"],\"gender\":\"male\",\"gpa\":6.7,\"gradeLevel\":1,\"name\":\"Ajay\",\"regNo\":1001},{\"activities\":[\"painting\",\"soccer\"],\"gender\":\"female\",\"gpa\":7.8,\"gradeLevel\":4,\"name\":\"Jessi\",\"regNo\":1002},{\"activities\":[\"painting\",\"chess\",\"dancing\"],\"gender\":\"female\",\"gpa\":8.0,\"gradeLevel\":4,\"name\":\"Emily\",\"regNo\":1003},{\"activities\":[\"swimming\",\"soccer\"],\"gender\":\"male\",\"gpa\":3.9,\"gradeLevel\":6,\"name\":\"Dave\",\"regNo\":1004},{\"activities\":[\"swimming\",\"dancing\",\"painting\"],\"gender\":\"female\",\"gpa\":4.5,\"gradeLevel\":7,\"name\":\"Sophia\",\"regNo\":1005},{\"activities\":[\"basketball\",\"cricket\"],\"gender\":\"male\",\"gpa\":3.9,\"gradeLevel\":7,\"name\":\"James\",\"regNo\":1006},{\"activities\":[\"swimming\",\"dancing\",\"soccer\"],\"gender\":\"male\",\"gpa\":8.5,\"gradeLevel\":8,\"name\":\"Sunil\",\"regNo\":1007},{\"activities\":[\"painting\",\"basketball\",\"soccer\"],\"gender\":\"male\",\"gpa\":6.9,\"gradeLevel\":8,\"name\":\"John\",\"regNo\":1008},{\"activities\":[\"cricket\",\"soccer\"],\"gender\":\"male\",\"gpa\":5.5,\"gradeLevel\":9,\"name\":\"Rohit\",\"regNo\":1009}," +
                "{\"activities\":[\"painting\",\"chess\",\"soccer\"],\"gender\":\"male\",\"gpa\":8.9,\"gradeLevel\":10,\"name\":\"Edwin\",\"regNo\":1010}]";
    }

    public String getStudentByID()
    {
        return "{\"activities\":[\"basketball\",\"cricket\"],\"gender\":\"male\",\"gpa\":6.7,\"gradeLevel\":1,\"name\":\"Ajay\",\"regNo\":1001}";
    }

    public String maleAndFemaleCount()
    {
        return "{\"femaleCount\":3,\"maleCount\":7}";
    }

    public String prefixNames()
    {
        return "[\"Mr.Ajay\",\"Mr.Dave\",\"Mr.Edwin\",\"Mr.James\",\"Mr.John\",\"Mr.Rohit\",\"Mr.Sunil\",\"Ms.Emily\",\"Ms.Jessi\",\"Ms.Sophia\"]";
    }
    public String countInAllGrade()
    {
        return "[{\"gradeLevel\":1,\"studentsCount\":1},{\"gradeLevel\":4,\"studentsCount\":2},{\"gradeLevel\":6,\"studentsCount\":1},{\"gradeLevel\":7,\"studentsCount\":2},{\"gradeLevel\":8,\"studentsCount\":2},{\"gradeLevel\":9,\"studentsCount\":1},{\"gradeLevel\":10,\"studentsCount\":1}]";
    }
    public String countByGrade()
    {
        return "{\"gradeLevel\":1,\"studentsCount\":1}";
    }
    public String countInAllActivities()
    {
        return "[{\"activity\":\"basketball\",\"studentCount\":3},{\"activity\":\"cricket\",\"studentCount\":3},{\"activity\":\"painting\",\"studentCount\":5},{\"activity\":\"soccer\",\"studentCount\":6}," +
                "{\"activity\":\"chess\",\"studentCount\":2},{\"activity\":\"dancing\",\"studentCount\":3},{\"activity\":\"swimming\",\"studentCount\":3}]";
    }
    public String countByActivity()
    {
        return "{\"activity\":\"chess\",\"studentCount\":2}";
    }
    public String performance()
    {
        return "[{\"level\":\"Average\",\"studentList\":[{\"activities\":[\"basketball\",\"cricket\"],\"gender\":\"male\",\"gpa\":6.7,\"gradeLevel\":1,\"name\":\"Ajay\",\"regNo\":1001},{\"activities\":[\"swimming\",\"dancing\",\"painting\"],\"gender\":\"female\",\"gpa\":4.5,\"gradeLevel\":7,\"name\":\"Sophia\",\"regNo\":1005},{\"activities\":[\"painting\",\"basketball\",\"soccer\"],\"gender\":\"male\",\"gpa\":6.9,\"gradeLevel\":8,\"name\":\"John\",\"regNo\":1008},{\"activities\":[\"cricket\",\"soccer\"],\"gender\":\"male\",\"gpa\":5.5,\"gradeLevel\":9,\"name\":\"Rohit\",\"regNo\":1009}]},{\"level\":\"Excellent\",\"studentList\":[{\"activities\":[\"painting\",\"soccer\"],\"gender\":\"female\",\"gpa\":7.8,\"gradeLevel\":4,\"name\":\"Jessi\",\"regNo\":1002},{\"activities\":[\"painting\",\"chess\",\"dancing\"],\"gender\":\"female\",\"gpa\":8.0,\"gradeLevel\":4,\"name\":\"Emily\",\"regNo\":1003},{\"activities\":[\"swimming\",\"dancing\",\"soccer\"],\"gender\":\"male\",\"gpa\":8.5,\"gradeLevel\":8,\"name\":\"Sunil\",\"regNo\":1007},{\"activities\":[\"painting\",\"chess\",\"soccer\"],\"gender\":\"male\",\"gpa\":8.9,\"gradeLevel\":10,\"name\":\"Edwin\",\"regNo\":1010}]},{\"level\":\"Poor\",\"studentList\":[{\"activities\":[\"swimming\",\"soccer\"],\"gender\":\"male\",\"gpa\":3.9,\"gradeLevel\":6,\"name\":\"Dave\",\"regNo\":1004}," +
                "{\"activities\":[\"basketball\",\"cricket\"],\"gender\":\"male\",\"gpa\":3.9,\"gradeLevel\":7,\"name\":\"James\",\"regNo\":1006}]}]";
    }
    public String performanceByLevel()
    {
        return "{\"level\":\"poor\",\"studentList\":[{\"activities\":[\"swimming\",\"soccer\"],\"gender\":\"male\",\"gpa\":3.9,\"gradeLevel\":6,\"name\":\"Dave\",\"regNo\":1004},{\"activities\":[\"basketball\",\"cricket\"]," +
                "\"gender\":\"male\",\"gpa\":3.9,\"gradeLevel\":7,\"name\":\"James\",\"regNo\":1006}]}";
    }


}
