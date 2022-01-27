package com.chethan.assignment1.student;

import java.util.List;
import java.util.stream.Collectors;

public class StudentName {

    List<String> getStudents(){
        return Student.students().stream().map(i -> { if(i.getGender()=="male") return "Mr."+i.getName();
                                                        else return "Ms."+i.getName();
                                                    }).collect(Collectors.toList());
    }

    public static void main(String[] args) {

      StudentName s1=new StudentName();
      s1.getStudents().forEach( System.out::println );

    }

}
