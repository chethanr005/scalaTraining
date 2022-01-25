package training
trait A {
  print("A")
  def foo(): String = "A"
}

trait B extends A {
  print("B")
  abstract override def foo(): String = "B" + super.foo()
}

trait C extends B {
  print("C")
  abstract override def foo(): String = "C" + super.foo()
}

trait D extends A {
  print("D")
  abstract override def foo(): String = "D" + super.foo()
}

class E extends A {
  print("E")
  override def foo(): String = "E" + super.foo()
}
class F extends A with D with C with B {
  print("F")
  override def foo(): String = "F" + super.foo()
}
class AA{
  println("A")
  def aa=println("AAAAAAAAA")}

trait ZZ extends AA {
  println("Z")
  override def aa={println("ZZZZZZZZZZZZZZ")
    super.aa}
}
trait YY extends AA {
  println("Y"  )
  override def aa={println("YYYYYYYYYYYYY")
    super.aa}
}
trait XX extends AA {
  println("X")
  override def aa={println("XXXXXXXXXXXXX")
    super.aa}
}
class BB extends AA with XX {
  println("B")
  override def aa={println("BBBBBBBBBBBBB")
    super.aa}
}
//class AA{
//  println("A")
//  def aa=println("AAAAAAAAA")}
//
//trait ZZ extends AA {
//  println("Z")
//  override def aa={println("ZZZZZZZZZZZZZZ")
//    super.aa}
//}
//trait YY extends ZZ {
//  println("Y"  )
//  override def aa={println("YYYYYYYYYYYYY")
//    super.aa}
//}
//trait XX extends YY {
//  println("X")
//  override def aa={println("XXXXXXXXXXXXX")
//    super.aa}
//}
//class BB extends AA with XX {
//  println("B")
//  override def aa={println("BBBBBBBBBBBBB")
//    super.aa}
//}
//class AA{
//  println("A")
//  def aa=println("AAAAAAAAA")}
//
//trait ZZ extends AA {
//  println("Z")
//  override def aa={println("ZZZZZZZZZZZZZZ")
//    super.aa}
//}
//trait YY extends AA {
//  println("Y"  )
//  override def aa={println("YYYYYYYYYYYYY")
//    super.aa}
//}
//trait XX extends AA {
//  println("X")
//  override def aa: Unit ={println("XXXXXXXXXXXXX")
//    super.aa}
//}
//class BB extends AA with XX {
//  println("B")
//  override def aa: Unit ={println("BBBBBBBBBBBBB")
//    super.aa}
//}
object prime extends App{
//  var e = new E with D with C with B
//  println()
//  println(s"e >> ${e.foo()}")
//
//  val f = new F()
//  println()
//  println(s"f >> ${f.foo()}")

//  val obj=new BB with ZZ with YY
//  obj.aa
//val obj=new BB with ZZ with YY
//  obj.aa
val obj=new BB with ZZ with YY
}
