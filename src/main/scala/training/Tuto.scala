package training
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


object Da extends App
{
  val t= new imptraits;//Object t for class imptraits.
  val t1:hello=new imptraits;

  t1.display();
  t1.classmethod();
  t.display();
  t.classmethod();

  println();
  // Any
  val a:Any=10;
  //val a1:AnyRef=20;/* AnyRef is used only for Classes, List and Option*/
  val a2:AnyVal=30;
  println(a+"  "+a2);

  // Prime or not
  def isprime(n:Int):Boolean={
    var c=0;
    for(i<-1 to n)
      if(n%i==0) c+=1 //println("n: "+n+" i : "+i+" c : "+c)
    if(c!=2) false else true
  }

  println();

  if(isprime(29)) print("Prime");
  else print("Not a Prime");

  // Factorial of a number
  val f=new fact;
  println();
  println(f.factorial(6));

}


