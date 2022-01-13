package Java;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class child extends parent {
    @Override
    public void display() {
        System.out.println("Child");
        super.display();
    }
    public static void main(String[] args) {
        child c = new child();
        c.display();
        parent p=new parent();
        p.display();
        student s1=new student("xyz",12,5);
        student s2=new student("yzx",11,4);
        student s3=new student("zxy",16,10);
        List<student> students= Arrays.asList(s1,s2,s3);
        System.out.println(students);
        System.out.println(students.get(1));
    }
}
