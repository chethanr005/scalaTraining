package training

import java.util.concurrent.atomic.LongAccumulator
import scala.::

import scala.Console.println

/**
  * Created by Kartik on Jan 06, 2022.
  */

class Temp {

}

object Temp extends App {
  var a = List[Any](1.1,2d,3/8d,4,"efd",'a',new Temp)
  var x = List.fill(3)("_") //prints (aaa,aaa,aaa)
  var y = List.tabulate(3)(n=>n*n) //prints (0*0=0,1*1=1,2*2=4)
  val names = List("Bob", "Fred", "Joe", "Julia", "Kim")
  //val li = names.map(name => <li>{name}</li>)
  //names.foreach{println}
//  for (name <- names if name.startsWith("j"))
//      println(name)
  //println(io.Source.stdin.getLines().take(3).map(a => a * 3).foreach{println})
  println(a)
}