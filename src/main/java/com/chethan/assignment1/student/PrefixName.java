//package com.chethan.assignment1.student;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
////Student name with Prefixes
//public class PrefixName {
//
//    List<String> getPrefixNames(List<Student> l){
//        return l.stream().map(i -> { if(i.getGender()=="male") return "Mr."+i.getName();
//                                                        else return "Ms."+i.getName();
//                                                    }).collect(Collectors.toList());
//    }
//
//    public static void main(String[] args) {
//
//        Student s= new Student();
//        PrefixName p=new PrefixName();
//        p.getPrefixNames(s.studentData()).forEach(System.out::println);
//
//    }
//
//}
