package training

/**
  * Created by Kartik on Jan 06, 2022.
  */

class Temp {

  val files = new java.io.File("C:\\Users\\chethan\\IdeaProjects\\ScalaProjDemo\\src\\main\\scala\\OoopsConcept").listFiles


  def higherOrderFunction(a: String, func: (String, String) => Boolean) = {
    for (i <- files; if (func(i.getName, a))) yield {
      println(i)
      i
    }
  }


  def higOrdFunc(num: Int, body: (Int, Int) => Int): Int = {

    val square = body(num, num)
    println(square)
    square

  }

  def hasNegative(num: List[Int]): Boolean = {
    num.exists(_ % 2 == 0)
  }


  // Currying

  def curry(a: String, b: Int)(x:String, y:Int)={
    println("there are "+b+" "+a+" in the box")
    println(y+" of "+b+" "+x+" has been damaged")
  }

  def first(x:Int)= (y:Int) =>x+y


  def prime(a:Int)={
    var count=0

    for(i <- 1 to a){
      if(a%i==0) count += 1
    }
    println(count)

    if(count>2) false
    else true

  }


}

object Temp extends App {


    val obj1=new Temp
    obj1.higherOrderFunction(".scala", _.endsWith(_))
    obj1.higherOrderFunction("t", _.contains(_))
    obj1.higherOrderFunction("Co", _.matches(_))

    obj1.higOrdFunc(9, _*_)

  println(obj1.hasNegative(List(1,3,5,7,81,5,2,-1,1)))

  obj1.curry("apple", 10)("laptop", 3)

  println(obj1.first(10))


  println(obj1.prime(2003))


 // val d= new Demo1


}


class Demo1{
  println("this is a parent class")

  class Demo1{
    println("this is a nested class")
  }

}