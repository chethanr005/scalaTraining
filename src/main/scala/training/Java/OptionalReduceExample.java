package training.Java;

import java.util.Optional;

public class OptionalReduceExample {
    public static Optional<Student> getmaxGpa () {
        return StudentDataBase.getAllStudents().stream()
                .reduce((s1,s2) -> {
                    if(s1.getGpa()>s2.getGpa()){
                        return s1;
                    }
                    else
                        return s2;
                        });
    }

    public static void main(String[] args) {
        Optional<Student> res = getmaxGpa();
        if(res.isPresent())
        {
            System.out.println(res.get());
        }
    }
}
