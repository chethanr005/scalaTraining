package Java;

public class paren implements inter2,inter1 {
//    @Override
//    public void m1() {
//        //System.out.println("Class");
//        inter1.m1();
//    }

    public static void main(String[] args) {
        inter1 obj=new paren();
        obj.m1();
        inter2 i2=new paren();
        i2.m1();
    }

    @Override
    public void m1() {
        inter2.super.m1();
    }
}
