package scalaTraining

import scala.collection.mutable.ListBuffer
import scala.runtime.Nothing$

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

  abstract class Element{

     def contents: Array[String]
  }




}

object Temp1 extends App {


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

  def lazyvalue(x:() => Int)={

  }


val ar1=(Array(1,2,3))
  println(ar1)
  println(ar1.toString)

  val t=new Temp
  println(t.toString)


  val ch=1
  val c=ch.toString*3
  println(c)

  //val ele=new UniformElement('c', 1,1)

  val con=Array("this is a string")
   println(con.length)

  val aa=Array("this", "is","a", "array")
  val bb=Array("type", "of", "array", "is", "string")

//  val ae1=new ArrayElement(aa)
//  val ae2=new ArrayElement(bb)
//  val r1=ae1.above(ae2)


  def test=Array(1,2,3,4,5)

  println(test.length)



//  def many(n:Int, l:List[Int]):List[Int]={
//    var ls=ListBuffer()
//   // for(i <- l)
//    //  ls.add(i)
//
//
//
//
//
//  }



  var ls1=ListBuffer(1)
  ls1(0)=12
  println(ls1)


  val l1=Array.fill(10)('$')
    l1.foreach(println)

  val l2=List.range(0,100,1)
  println(l2)

 val ary=new Array[Double](10)
 ary.foreach(println)

  val ary1=new A(Array("this", "is", "scala", "scalaTraining", null, null))
  val  ary2= new  A(Array("and", "the", "scalaTraining", "is" ,"lead", "by", "karthik"))

    (ary1.cat(ary2)).foreach(println)


  (for((i,j) <- ary zip ary) yield i+""+j).foreach(println)


  println(ary2)

  val bb1= new B
  println(bb1 )
  println(bb1.wide(10))

  println('1'.toString*3)
  (Array("|")++Array("+")).foreach(println)

  println("jijiiijij")

  val c1=new C(1,2)
  val c2= new C(9,8)
  c2 ccc c1

  println('a'.toString*0)


  val arai1= Array(" ","  ","   ","    ","     ")
  val arai2=Array("1          10","2        9","3      8","4    7","5  6")

  (for((i, j )<- arai1 zip arai2) yield i+" "+j).foreach(println)





}

//
//abstract class Element {
//  def contents: Array[String]
//  def height = contents.length
//  def width = if (height == 0) 0 else contents(0).length
//  def above(that:Element):Element={
//    new ArrayElement(this.contents ++ that.contents)
//
//  }
//
//  def beside(that: Element): Element = {
//    val contents = new Array[String](this.contents.length)
//    for (i <- 0 until this.contents.length)
//      contents(i) = this.contents(i) + that.contents(i)
//    new ArrayElement(contents)
//  }
//}
//
//class ArrayElement(conts: Array[String]) extends Element {
//  def contents: Array[String] = conts
//}
//
//class LineElement(s: String) extends ArrayElement(Array(s)) {
//  override val contents = Array(s)
//  override def width = s.length
//   override def height = 100
//}
//
//
//
//class UniformElement(ch:Char, override val width:Int, override val height:Int) extends Element{
//  private  val line=ch.toString*width
//  def contents=Array.fill(height)(line)
//
//}





class A(val that:Array[String]) {

  def sep=".o"

  override def toString: String = that mkString sep

  def cat(a:A):IndexedSeq[String]={

  for(i <- 0 to this.that.length-1) yield{
    this.that(i) + (a.that(i))
  }
  }
}



class B {

  def wide(a:Int):B={
    if(1==1) this
    else this
  }


}

class C(val x:Int, val y:Int){

  def ccc(that:C)={
    println(x)
    println(that.x)
  }


}



