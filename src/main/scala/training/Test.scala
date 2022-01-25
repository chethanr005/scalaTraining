package training

import scala.collection.mutable.ArrayBuffer

object Test extends App {

  val a=new Rational(15)
  val b= new Rational (10)
  println(a compare b)

val  a1=new stack
a1.put(10)
  a1.put(20)
  a1.put(30)

  println(a1.get)
  println(a1.get)
  println(a1.get)

  var abc =1

  val queue=new Basic with Incrementing with Doubling
  queue.aaaa
  println(""""""""""""""""""""""")
 val q=new MyQueue
  q.aaaa

println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  val c1= new Cat  with FourLegged with HasLegs with Furry
  c1.ani

  val obj=new BB with XX
  obj.aa

}
class Rational(val n: Int) extends Ordered[Rational] {
  // ...
  def compare(that: Rational) = this.n - that.n

}
class stack{
  private val ar= new ArrayBuffer[Int]
  def get=ar.remove(0)
  def put(x:Int) = ar+=x
}
abstract class IntQueue{
  def aaaa
}
class Basic extends IntQueue{
   override  def aaaa={

      println("Basic")}
}
trait Doubling extends IntQueue{
//abstract override def aaaa={
//  println("Doubling")
//   super.aaaa
//   }
}
trait Incrementing extends IntQueue{
abstract  override   def aaaa:Unit={
    println("Incrementing")
  super.aaaa

}
}
class MyQueue extends Basic with Incrementing with Doubling {

  override def aaaa = {
    super.aaaa
    println("MyQueue")
  }

}
abstract  class Animal{
    def ani
  }
  trait Furry extends Animal {
   abstract override def ani={
      println("Furry")
      super.ani
    }
  }
  trait HasLegs extends Animal{
   abstract override  def ani={
      println("HasLegs")
      super.ani
    }

  }
   trait FourLegged extends HasLegs{
   abstract  override def ani={
       println("FourLegged")
       super.ani
     }
   }
  class Cat extends Animal{
     override def ani ={
      println("Cat")

    }
  }



class AA{
  println("A")
  def aa=println("AAAAAAAAA")
}

trait ZZ extends AA {
  println("Z")
  override def aa={println("ZZZZZZZZZZZZZZ")
    super.aa}
}
trait YY extends ZZ {
  println("Y"  )
  override def aa={println("YYYYYYYYYYYYY")
    super.aa}
}

trait XX extends YY   {
  println("X")
 override def aa={println("XXXXXXXXXXXXX")
    super.aa}
}

class BB extends AA {
  println("B")
  override def aa={println("BBBBBBBBBBBBB")
 super.aa
     }
}