package Seminar;

import java.util.Optional;

public class optional {
    public static Optional<Student> StudentData=Optional.empty();
    public static Optional<String> getname()
    {

        if(StudentData.isPresent())
         return StudentData.map(Student::getName);
        else
         return Optional.empty();
    }


    public static Optional<String> OFNullable()
    {
        Optional<String> test=Optional.ofNullable(null);
        return test;
    }


    public static Optional<String> OF()
    {
        Optional<String> test1=Optional.of("of()");
        return test1;
    }

    public static String ORElse()
    {
        return StudentData.map(Student::getName).orElse("Default name");
    }

    public static String ORElseGet()
    {
        return StudentData.map(Student::getName).orElseGet(()->"Accepts a Supplier");
    }

    public static String OrElseThrow()
    {
        return StudentData.map(Student::getName).orElseThrow(()->new RuntimeException("Name not found"));
    }

    public static void main(String[] args) {

        //Optional
        Optional<String> name=getname();
        if(name.isPresent())
            System.out.println(name.get());
        else
            System.out.println("NA");

        //ofnullable()
        System.out.println(OFNullable());

        //of()
        System.out.println(OF());

        //orElse()
        System.out.println("orElse() : "+ORElse());

        //orElseGet()
        System.out.println("orElseGet() : "+ORElseGet());

        //orElseThrow()
        System.out.println("orElseThrow() : "+OrElseThrow());

        //ifpresent()
        Optional<Integer> temp=Optional.ofNullable(null);
        System.out.println("ifPresent() : ");
        temp.ifPresent(s-> System.out.println(s+20));

        //isPresent()
        System.out.println("isPresent() : "+temp.isPresent());

        //Filter()
        Optional<String>studname=StudentData.filter(data->data.getGpa()>=3.5)
                .map(Student::getName);
        System.out.println("filter() : "+studname);


    }
}
