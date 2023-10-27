package com.chethan.slick


/**
  * Created by Chethan on Jan 06, 2023.
  */

object Test extends App {

  val list = List(1, 2, 3, 4, 5, 6)

  val withFilter = list.filter(a => {

    val test = Test
    a % 2 == 0
  })


  println(withFilter.size)
  list.sliding(4, 5).foreach(println)
  val flat = List(list, list)

  println(flat.flatMap(a => a))
  println(flat.flatten)

  println("this is a map".charAt(10))

  //List[Int]().min


  val casesList = List(Cases(10), Cases(1), Cases(99), Cases(52), Cases(45))



  //  implicit def ordered[Cases <% Comparable[Cases]]: Ordering[Cases] = new Ordering[A] {
  //    def compare(x: Cases, y: Cases): Int = x compareTo y
  //  }

 // implicit val ordered: Ordering[Cases] = Ordering.by(_.a)
  implicit val ordered1: Ordering[Cases] = Ordering.fromLessThan(_.a < _.a)

  private val function: Ordering[Cases] = Ordering.fromLessThan(_.a < _.a)
  // implicit val ordered = Sorting.quickSort(List[Int]())
  //
  //
    println(casesList.sorted)


  println(Map(2 -> 10, 1 -> 45, 3 -> 99).min)
  println(Map(2 -> 10, 1 -> 45, 3 -> 99).keys)
  Numeric
  println(Map('a' -> 10, 'b' -> 45, 'v' -> 99).keys.sum)

  //
  println(Map(1 -> 10, 2 -> 20).toList)
  println(Map(1 -> 10, 2 -> 20).head)
  //  println(Map(1 -> 10,2 -> 20).reduce((a,b) => a+""+b))
  println(Map("a" -> 10).get("b"))
  println(Map(2 -> 10, 1 -> 45, 3 -> 99).init)

  println(Map("1" -> 10, "2" -> 10) == Map("1" -> 10, "2" -> 10))
  println(Map("1" -> 10, "2" -> 10).equals(Map("1" -> 10, "2" -> 10)))
  println("matacahes".replace("a", "b"))
  println("matacahes".intern())

  println(Array(1, 2, 3, 4, 5).size)
  println(Array(1, 2, 3, 4, 5).length)
  println(None.map{ a: Int => a + 1 })

  println()


  List(1, 2, 3, 4).iterator

  for {i <- 1 to 10; result = for {j <- 1 to 10} yield i + j
       } yield result


 // def aaa[A](a:A,b:String)= ???


  println("-=-=-=-=- Output -=-==--=")
  println("a" == "a")


}




case class Cases(a: Int) {
}