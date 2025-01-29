package com.chethan.scalaBasics

/**
  * Created by Chethan on Jan 28, 2025.
  */

object AdvancedPatternMatchingPart2 {

  case class Or[A, B](a: A, b: B)

  val either = Or(2, "Two")
  either match {
    //Infix pattern - Only works when we have 2 params
    case n Or String =>
  }

  List(, 1, 2, 3, 4, 5) match {
    case List(1, _*) =>
  }


  abstract class MyList[+A] {
    def head: A = ???

    def tail: MyList[A] = ???
  }

  case object Empty extends MyList[Nothing]

  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] = {
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
    }

  }

}

