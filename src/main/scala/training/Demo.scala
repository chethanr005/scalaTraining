package training

import Element.elem


class Demo (val a:Int){


}

abstract class Element {
  def contents: Array[String]
  def width: Int = contents(0).length
  def height: Int = contents.length
  def above(that: Element): Element = {
    val this1 = this widen that.width
    val that1 = that widen this.width
    elem(this1.contents ++ that1.contents)
  }
  def beside(that: Element): Element = {
    val this1 = this heighten that.height
    val that1 = that heighten this.height
    elem(
      for ((line1, line2) <- this1.contents zip that1.contents)
        yield {
          println("pinpinpinpin")
          this1.contents foreach println
          println("panpanpanpan")
          that1.contents foreach println
          line1 + line2
        })
  }

  def widen(w: Int): Element =
    if (w <= width) this
    else {
      val left =elem(' ', (w - width) / 2, height)
      var right = elem(' ', w - width - left.width, height)
     // left beside this beside right
      (left.beside(this)).beside(right)
    }
  def heighten(h: Int): Element =
    if (h <= height) this
    else {
      val top = elem(' ', width, (h - height) / 2)
      var bot = elem(' ', width, h - height - top.height)
      top above this above bot
    }
  override def toString = contents mkString "\n"
}

object Element {
  private class ArrayElement(val contents: Array[String]) extends Element

  private class LineElement(s: String) extends Element {
    val contents = Array(s)
    override def width = s.length
    override def height = 1
  }
  private class UniformElement(ch: Char,override val width: Int,override val height: Int) extends Element {
    private val line = ch.toString * width
    def contents = Array.fill(height)(line)
  }

  def elem(contents: Array[String]): Element =
    new ArrayElement(contents)
  def elem(chr: Char, width: Int, height: Int): Element =
    new UniformElement(chr, width, height)
  def elem(line: String): Element =
    new LineElement(line)
}

object Spiral {

  val space = elem(" ")
  val corner = elem("+")

  def spiral(nEdges: Int, direction: Int): Element ={
    if (nEdges == 1) {
      println("final if")
      println(nEdges+" " +direction)
      elem("+")
    } else {
      //println("before sp")
      val sp:Element = spiral(nEdges - 1, (direction + 3) % 4)
      println(sp)
      println(nEdges+" "+direction)
     //println("after sp")
      def verticalBar = elem('|', 1, sp.height)
     println(sp.height)
      println("after vertical")
      def horizontalBar = elem('-', sp.width, 1)


      if (direction == 0) {
      println("if")
        (corner beside horizontalBar) above (sp beside space)
      }
      else if (direction == 1) {
        println("else 1")
        (sp above space) beside (corner above verticalBar)
      } else if (direction == 2) {
      println("else 2")
        (space beside sp) above (horizontalBar beside corner)
      } else {println("else")
        (verticalBar above corner) beside (space above sp)
      }
    }
  }

  def main(args: Array[String]) {
    val nSides = 6
    println(spiral(nSides, 0))


    println(1/2)

  }
}



