package training

import scala.annotation.tailrec
// Lazy val

//class lazydemo
//{
//  val test1:String={
//    println(" Test1");
//    "test1"
//  }
//  val test2:String={
//    println("Test2");
//    "test2"
//  }
//  val test3:String={
//  println("val Test3");
//  "test3"
//}
//  val test4:String={
//  println("val Test4");
//  "test4"
//}
//  val test5:String={
//  println("val Test5");
//  "test5"
//}
//  lazy val test6:String={
//    println("val Test6");
//    "test6"
//  }
//}
//object Tuto  extends App {
//  val d=new lazydemo()
//  println(d.test6)
//  println(d.test6)
//}
  //Traits
  trait hello
  {
    def display():Any= println("Traits");
    def classmethod(): Unit;
  }


   class imptraits extends hello
  {
    // Override of Traits
    override def display():Unit = {
      println("CLass Traits");
    }
    override def classmethod():Unit={
      println("Class Method");
    }
  }
// Factorial Class
class fact
{
  def factorial(n:Int): Int =
    {
      if(n==1) 1
      else n * factorial(n-1)
    }
}

//Constructor
class demo(a:String="NA",b:String="NA")
{
  def this(c:String)= this("na", c)
  def this()= this("na")
  println(a+" "+b);

   class demo(value1:String,value2:String){
    println(value1+" "+value2);
  }
   val obj1=new demo("iNNER","Class");
}

object Da extends App {
  val t = new imptraits; //Object t for class imptraits.
  val t1: hello = new imptraits;

  t1.display();
  t1.classmethod();
  t.display();
  t.classmethod();

  println();
  // Any
  val a: Any = 10;
  //val a1:AnyRef=20;/* AnyRef is used only for Classes, List and Option*/
  val a2: AnyVal = 30;
  println(a + "  " + a2);

  // Prime or not
  def isprime(n: Int = 2003): Boolean = {
    var c = 0;
    var m=n/2;
    for (i <- 1 to m)
      if (n % i == 0) c += 1 //println("n: "+n+" i : "+i+" c : "+c)
    if (c != 2) false else true
  }

  println();

  if (isprime()) println("Prime");
  else println("Not a Prime");


  // Factorial of a number
  val f = new fact;
  println();
  println(f.factorial(6));

  println()
  // Constructor Class
  val obj1 = new demo;
  //val obj2=new demo("hello","called")
  println();

  //Factorial of a number using tail recursion

  def fact(n: Int): Int = {
    @tailrec
    def facthelp(n: Int, i: Int): Int = {
      if (n == 1) i
      else facthelp(n - 1, i * n)
    }
    facthelp(n, 1)
  }

  println("Factorial using Tail Recursion :" + fact(5));

  println();

  //basic loop function for Prime using recursive
  var c=0;
  def ispon(n:Int):Unit= {
    def loopfun(n: Int, i: Int): Int = {
      if (i == 0) {
        c;
      }
      else {
        if (n % i == 0) {
          c = c + 1
          //println(c)
        }
        loopfun(n, i - 1)
      }
    }
    loopfun(n,n);
    println(loopfun(27, 27))
    if (c == 2) println("prime")
    else println("Not Prime")
  }

  ispon(2003);
}


