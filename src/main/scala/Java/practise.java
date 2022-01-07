package Java;

public class practise {
    public boolean prime(int n)
    {
        int c=0;
        int m=n/2;
        for(int i=1;i<=m;i++)
        {
            if(n%i==0)
            {
                c=c+1;
            }
        }
        if(c==2)
            return false;
        else
            return true;
    }

    public static void main(String[] args) {
        practise obj=new practise();
        System.out.println(obj.prime(27));
    }
}
