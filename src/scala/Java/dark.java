package Java;

public interface dark {
public void display();
public default boolean IsPrime(int n)
{
    for(int i=2;i<n;i++) {
        if(n%i==0) return false;
    }
    return true;
}
public static boolean IsEven(int n)
{
    return n%2==0;
}

}
