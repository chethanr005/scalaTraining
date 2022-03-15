package com.chethan.assignment5.student;

/**
 * Created by Chethan on Mar 14, 2022.
 */

public class MockHttpServerDatabase {

    public String getExpectedAllStudents(){
        return "[{\"activities\":[\"swimming\",\"cycling\"],\"gender\":\"female\",\"gpa\":7.5,\"gradeLevel\":10,\"id\":\"r001\",\"name\":\"rose\"},{\"activities\":[\"hiking\",\"running\"],\"gender\":\"female\",\"gpa\":7.0,\"gradeLevel\":9,\"id\":\"r002\",\"name\":\"julie\"},{\"activities\":[\"travelling\",\"swimming\",\"walking\"],\"gender\":\"male\",\"gpa\":3.6,\"gradeLevel\":8,\"id\":\"r003\",\"name\":\"tony\"},{\"activities\":[\"hiking\",\"walking\"],\"gender\":\"female\",\"gpa\":6.1,\"gradeLevel\":10,\"id\":\"r004\",\"name\":\"kail\"},{\"activities\":[\"hiking\",\"cycling\",\"walking\"],\"gender\":\"female\",\"gpa\":6.9,\"gradeLevel\":10,\"id\":\"r005\",\"name\":\"kate\"},{\"activities\":[\"swimming\"],\"gender\":\"male\",\"gpa\":8.1,\"gradeLevel\":10,\"id\":\"r006\",\"name\":\"anthony\"},{\"activities\":[\"cycling\",\"running\"],\"gender\":\"male\",\"gpa\":5.4,\"gradeLevel\":8,\"id\":\"r007\",\"name\":\"andrew\"},{\"activities\":[\"hiking\",\"travelling\",\"walking\"],\"gender\":\"female\",\"gpa\":9.1,\"gradeLevel\":9,\"id\":\"r008\",\"name\":\"swift\"},{\"activities\":[\"travelling\",\"cycling\",\"hiking\",\"walking\"],\"gender\":\"male\",\"gpa\":8.7,\"gradeLevel\":10,\"id\":\"r009\",\"name\":\"cooper\"},{\"activities\":[\"walking\",\"cycling\"],\"gender\":\"female\",\"gpa\":5.0,\"gradeLevel\":9,\"id\":\"r010\",\"name\":\"hailey\"}]";
    }

    public String getExpectedStudentr001(){
        return "{\"activities\":[\"walking\",\"cycling\"],\"gender\":\"female\",\"gpa\":5.0,\"gradeLevel\":9,\"id\":\"r010\",\"name\":\"hailey\"}";
    }

    public String getExpectedMaleAndFemaleContainer(){
        return "{\"females\":6,\"males\":4}";
    }

    public String getExpectedPrefixNames(){
        return "[\"Ms.rose\",\"Ms.julie\",\"Mr.tony\",\"Ms.kail\",\"Ms.kate\",\"Mr.anthony\",\"Mr.andrew\",\"Ms.swift\",\"Mr.cooper\",\"Ms.hailey\"]";
    }

    public String getExpectedGradeLevelContainer(){
    return "{\"gradeLevel\":10,\"students\":5}";
    }

    public String getExpectedGradeLevelContainer2(){
    return"[{\"gradeLevel\":10,\"students\":5},{\"gradeLevel\":9,\"students\":3},{\"gradeLevel\":8,\"students\":2}]"  ;
    }

    public String getExpectedActivityContainer(){
        return "{\"activity\":\"running\",\"students\":2}";
    }

    public String getExpectedActivityContainer2(){
    return "[{\"activity\":\"swimming\",\"students\":3},{\"activity\":\"cycling\",\"students\":5},{\"activity\":\"hiking\",\"students\":5},{\"activity\":\"running\",\"students\":2},{\"activity\":\"travelling\",\"students\":3},{\"activity\":\"walking\",\"students\":6}]";    }

    public String getExpectedPerformanceContainer(){
        return "{\"level\":\"poor\",\"students\":[{\"activities\":[\"travelling\",\"swimming\",\"walking\"],\"gender\":\"male\",\"gpa\":3.6,\"gradeLevel\":8,\"id\":\"r003\",\"name\":\"tony\"}]}";
    }

    public String getExpectedPerformanceContainer2(){
    return "[{\"level\":\"Average\",\"students\":[{\"activities\":[\"hiking\",\"running\"],\"gender\":\"female\",\"gpa\":7.0,\"gradeLevel\":9,\"id\":\"r002\",\"name\":\"julie\"},{\"activities\":[\"hiking\",\"walking\"],\"gender\":\"female\",\"gpa\":6.1,\"gradeLevel\":10,\"id\":\"r004\",\"name\":\"kail\"},{\"activities\":[\"hiking\",\"cycling\",\"walking\"],\"gender\":\"female\",\"gpa\":6.9,\"gradeLevel\":10,\"id\":\"r005\",\"name\":\"kate\"},{\"activities\":[\"cycling\",\"running\"],\"gender\":\"male\",\"gpa\":5.4,\"gradeLevel\":8,\"id\":\"r007\",\"name\":\"andrew\"},{\"activities\":[\"walking\",\"cycling\"],\"gender\":\"female\",\"gpa\":5.0,\"gradeLevel\":9,\"id\":\"r010\",\"name\":\"hailey\"}]},{\"level\":\"Excellent\",\"students\":[{\"activities\":[\"swimming\",\"cycling\"],\"gender\":\"female\",\"gpa\":7.5,\"gradeLevel\":10,\"id\":\"r001\",\"name\":\"rose\"},{\"activities\":[\"swimming\"],\"gender\":\"male\",\"gpa\":8.1,\"gradeLevel\":10,\"id\":\"r006\",\"name\":\"anthony\"},{\"activities\":[\"hiking\",\"travelling\",\"walking\"],\"gender\":\"female\",\"gpa\":9.1,\"gradeLevel\":9,\"id\":\"r008\",\"name\":\"swift\"},{\"activities\":[\"travelling\",\"cycling\",\"hiking\",\"walking\"],\"gender\":\"male\",\"gpa\":8.7,\"gradeLevel\":10,\"id\":\"r009\",\"name\":\"cooper\"}]},{\"level\":\"Poor\",\"students\":[{\"activities\":[\"travelling\",\"swimming\",\"walking\"],\"gender\":\"male\",\"gpa\":3.6,\"gradeLevel\":8,\"id\":\"r003\",\"name\":\"tony\"}]}]";
    }

    public String getExpectedUpdatedStudent(){
    return "{\"activities\":[\"hiking\",\"cycling\",\"walking\"],\"gender\":\"female\",\"gpa\":6.9,\"gradeLevel\":10,\"id\":\"r005\",\"name\":\"me\"}";
    }

    public String addExpectedEmployee(){
        return "{\"activities\":[\"walking\",\"cycling\"],\"gender\":\"female\",\"gpa\":5.0,\"gradeLevel\":9,\"id\":\"r011\",\"name\":\"me\"}";
    }


    }
