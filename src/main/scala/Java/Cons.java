package Java;

public class Cons {

    Cons() {
        System.out.println("this is an example for constructor reference");
    }


    int cube(int a) {
        return a * a * a;
    }

    void m1() {
        System.out.println("runnable example");
    }

    public static void main(String[] args) {
        Runnable r5 = () -> {

        };
    }
}
