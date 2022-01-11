package Java;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class practise {
//    public boolean prime(int n)
//    {
//        int c=0;
//        int m=n/2;
//        for(int i=1;i<=m;i++)
//        {
//            if(n%i==0)
//            {
//                c=c+1;
//            }
//        }
//        if(c==2)
//            return false;
//        else
//            return true;
//    }
    public static Boolean iseven(int a)
  {
     if(a%2==0)
        return true;
     else
         return false;
  }

  public void tables(int n)
  {
      for(int i=1;i<=10;i++)
      {
          System.out.println(n+" * "+i+" = "+n*i);
      }
  }
    public static void main(String[] args) {
          practise obj=new practise();
          //System.out.println(obj.prime(27));
        String s="hello";
        //Consumer
        Consumer<Integer> c = (a) -> System.out.println(-a);
        c.accept(30);
        BiConsumer<Integer, Integer> biC1 = (a, b) -> System.out.println("Sum : " + (a + b));
        //BiConsumer
        BiConsumer<Integer, Integer> biC2 = (a, b) -> System.out.println("Multiply : " + (a * b));
        biC1.accept(40,20);//BiConsumer
        biC1.andThen(biC2).accept(20, 10);//andThen BiConsumer
        //Predicate
        Predicate<Integer> p1=(a)-> a % 2 == 0;
        Predicate<Integer> p2=(a)-> a % 5 == 0;
        System.out.println("Predicate "+p1.test(6));//Predicate
        System.out.println("Predicate and "+p1.and(p2).test(6));//Predicate and
        System.out.println("Predicate or "+p1.or(p2).test(6));//Predicate or
        System.out.println("Predicate or negate "+p1.or(p2).negate().test(6));
        System.out.println("Predicate and negate "+p1.and(p2).negate().test(6));//Predicate negate
        //BiPredicate
        BiPredicate<Integer,Integer> bip1=(a, b)-> a < b;
        BiPredicate<Integer,Integer> bip2=(a, b)-> a % b==0;
        System.out.println("Is 20 less than 50 :"+bip1.test(20,50)); // BiPredicate
        System.out.println("Is BiPredicate and :"+bip1.and(bip2).test(10,50)); // BiPredicate and
        // BiPredicate or
        // BiPredicate negate()
        //Function
        Function<Integer,Boolean> isPositiveNo=(a)->a >= 0;
        Function<String,String> at=(a)-> a.concat(" AndThen");
        Function<String,String> comp=(a)->a.concat(" Compose");
        System.out.println(isPositiveNo.apply(-1));
        //Function has andThen And compose
        System.out.println(at.andThen(comp).apply("DA"));//andThen
        System.out.println(at.compose(comp).apply("DA"));//Compose
        //Unary Operator
        UnaryOperator<Integer> uo=(a)-> a=a+a;
        System.out.println(uo.apply(10));
        //Binary Operator
        BinaryOperator<Integer> bio=(a,b)->a-b;
        System.out.println(bio.apply(20,40));
        //maxBy and minBy
        Comparator<Integer> cmp= (a,b)->a.compareTo(b);//Comparator as max and minBy accepts only cmp.
        BinaryOperator<Integer> max=BinaryOperator.maxBy(cmp);
        BinaryOperator<Integer> min=BinaryOperator.minBy(cmp);
        System.out.println(max.apply(20,10));
        System.out.println(min.apply(20,10));
        //Supplier
        Supplier<String> sup=()->"Hello";
        System.out.println(sup.get());

        //Method Reference
        Function <String,String> sm=(s1)->s1.concat(" Hello"); //Function supports only for function with Single argument.
        Function <String,String> smr=String::toLowerCase;
        BiFunction<String,String,String> bismr=String::concat;
        System.out.println(sm.apply("Hello"));
        System.out.println(smr.apply("Hello"));
        System.out.println(bismr.apply("Hello"," Concat"));//BiFUnction supports function with 2 arguments.

        System.out.println(iseven(8));//Static Function implementation
        Predicate<Integer> isev=practise::iseven;//creating our own class method reference.
        System.out.println(isev.test(11));

        // Consumer<Integer> tab=practise::tables;// Should make the tables function as static.
        //tab.accept(7);
        Consumer<Integer> tab1=obj::tables;//Method references by object , then no need to mention tables as static.
        tab1.accept(8);

        List<String> names=Arrays.asList("xyz","yzx","zxy");
        System.out.println(names);
        System.out.println(names.stream());
        System.out.println(names.parallelStream());

        //List
        List<Integer> numlist=new ArrayList<>();
        numlist.add(1);
        numlist.add(2);
        numlist.add(3);
        numlist.add(4);
        numlist.add(5);
        System.out.println(numlist);
        numlist.remove(2);
        numlist.add(2,5);
        System.out.println(numlist);
        System.out.println(numlist.get(2));


        //Stream Example
        List<Integer> check= Arrays.asList(1, 45, 25, 23, 10,1,23,25,25);
        List<Integer> num=check.stream().
                filter(i->i%5==0).collect(Collectors.toList());// Filter()


        List<Integer> multi=check.stream()
                        .map(i->i*2).collect(Collectors.toList());// Map()

        List<Integer> unq=check.stream()
                .distinct().collect(Collectors.toList());//Distinct()

        long count= check.stream().count();//stream Count();
        long ccount=check.size();// collection size to find count.

        List<Integer> srt=check.stream().
        sorted().collect(Collectors.toList());// Sorting

        long res=check.stream()
                        .reduce(0,(a,b)->a+b);// reducer to find sum of list.

        int res1=check.stream()
                .reduce(1,(a,b)->a*b);// reducer to find sum of list.

        int maxno=check.stream()
                .reduce(0,(a,b)->{
                    if(a>b)
                        return a;
                    else
                        return b;
                });


        System.out.println("Numbers : "+check);
        System.out.println("%5 : "+num);
        System.out.println("*2 : "+multi);
        System.out.println("Distinct : "+unq);
        System.out.println("Count : "+count);
        System.out.println("Collection Count : "+ccount);
        System.out.println("Sorted : "+srt);
        System.out.println("Sum of elements : "+res);
        System.out.println("Product of elements : "+res1);
        System.out.println("Maximum of elements : "+maxno);
    }
}
