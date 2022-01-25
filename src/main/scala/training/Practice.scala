package training

import java.util
import scala.{::, List}
import scala.collection.immutable
import scala.runtime.Nothing$

object Practice extends App {
  //println(io.Source.stdin.getLines().take(2).map(_.toInt).sum)
//  def f(n:Int)= {
//    for (i <- 1 to n) {
//        println("Hellow World")
//    }
//  }
def f(num:Int,arr:List[Int]):List[Int] = arr.flatMap(List.fill(num)(_))

//  for(i <- arr)
//    yield{
//      println(i)
//      i.toString*num}

//    if (num <= 0 && arr.isEmpty) Nil
//  else {
//      val arr1 = List
//      for (i <- 0 to num) {
//        //arr1(i) arr(i)
//      }
//    }
//}
  val li = List(1,2,3,4,5)

  println(f(3,li))
  //println("a"*3)
}

/***
 * 3
 * 1
 * 2
 * 3
 *
 *
 * 111
 * 222
 * 333
 */