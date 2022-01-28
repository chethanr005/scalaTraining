package Java;

public class RunnableExample implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<=10;i++)
            System.out.println(System.nanoTime());

    }


    public static void main(String[] args) {

        RunnableExample r=new RunnableExample();
       new Thread(r). start();

    }


}
