package com.chethan.scalaBasics

/**
  * Created by Chethan on Jan 28, 2025.
  */

object AdvancedPatternMatchingPart1 extends App {

  //    Types supported for patter matching
  //constants
  //wildcards
  //case classes
  //tuples


  List(1) match {
    case head :: Nil => println("This case works only when a list has 1 element.")
  }

  class Person(val name: String, val age: Int)

  object PersonPattern {
    def unapply(person: Person): Option[(String, Int)] = Some(person.name, person.age)


    def unapply(age: Int): Option[String] = Some(age.toString)
  }

  private val bob = new Person("bob", 25)

  //  val greeting = bob match {
  //    case PersonPattern(n, a) => s"Hi, my name is $n and age is $a"
  //  }

  bob.age match {
    case PersonPattern(status) => println("this is my age")
  }


  //  println(greeting)

  val n: Int = 99

  val even        = (x: Int) => x % 2 == 0
  val singleDigit = (x: Int) => x > -1 && x < 10

  n match {
    case x if even(x)        => println("It is even")
    case x if singleDigit(x) => println("It is odd")
  }

  object EvenObject {
    def unapply(a: Int) = a % 2 == 0
  }

  object SingleObject {
    def unapply(x: Int) = x > -1 && x < 10
  }

  n match {
    case EvenObject()   => println("It is even")
    case SingleObject() => println("It is odd")
  }

}
