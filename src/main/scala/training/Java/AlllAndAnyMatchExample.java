package training.Java;
public class AlllAndAnyMatchExample {
    public static boolean allMatch(){
        return StudentDataBase.getAllStudents().stream()
                .allMatch(student -> student.getGpa()>=3.9);
    }
    public static boolean anyMatch() {
        return StudentDataBase.getAllStudents().stream()
                .anyMatch(student -> student.getGpa()>=6.3);
    }
    public static boolean noneMatch() {
        return StudentDataBase.getAllStudents().stream()
                .noneMatch(student -> student.getGpa()==5.0);
    }
    public static void main(String[] args) {
        System.out.println("All Match: "+allMatch());
        System.out.println("Any Match: "+anyMatch());
        System.out.println("None Match: "+noneMatch());
    }
}
//all fun takes predicate as I/p nd returns boolean.

