package Java;

import sun.awt.windows.WPrinterJob;

public class Pattern {
    public static void main(String[] args) {
        int n=6;
        for(int i=1;i<=n;i++)
        {
            for(int j=1;j<=i;j++) {
                if (i == 1 || i == n)
                    System.out.print("+");
                else if(i==2)
                {
                    if(j>1)
                        System.out.print(" ");
                    else
                        System.out.print("|");
                }
                else if(i==3)
                {
                    if(j==i)
                        System.out.print("+");
                    else if(j==1)
                        System.out.print("|");
                    else
                        System.out.print(" ");
                }

                else {
                    if(j==1)
                        System.out.print("|");
                    else if(j==2)
                        System.out.print(" ");
                    else
                        System.out.print("-");
                }
            }
            for(int k=n-1;k>=i;k--)
            {
                if(i==1)
                System.out.print("-");
                else if(i==2)
                System.out.print(" ");
                else if(i==3)
                {
                    if(k==i)
                        System.out.print("+");
                    else
                        System.out.print("-");
                }
                else
                    System.out.print("|");
            }
            System.out.println();
        }
    }
}
