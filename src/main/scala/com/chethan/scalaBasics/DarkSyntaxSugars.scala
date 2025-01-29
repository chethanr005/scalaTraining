package com.chethan.scalaBasics

/**
  * Created by Chethan on Jan 28, 2025.
  */

object DarkSyntaxSugars extends App {

  trait Action {
    def act(x: Int): Int
  }

  val act1 = new Action() {
    override def act(x: Int): Int = x + 1
  }

  //Works only if the trait has only one abstract method with one param.
  val act2: Action = (x: Int) => x + 1

  val thread1: Thread = new Thread(() => println("this is a thread"))

  //////////////////////////////////////////////////////////////////////////////////////////

  //Right Associative
  val list = List(5, 6)
  2 :: list // syntactical sugar
  list.::(2) // actual call


  class MyStream[T] {
    //Declaring a right associative method -> char : should be in the last position of the method name as seen below.
    def -->:(value: T): MyStream[T] = this
  }

  val mStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  ////////////////////////////////////////////////////////////////////////////////////////////

  class Composite[A, B]

  val composite: String Composite Int = new Composite[String, Int]

  class -->[A, B]

  val -> : Int --> String = new-->

  /////////////////////////////////////////////////////////////////////////////////////////////////

  // syntactical sugar for update() and apply()
  class UpdateAndApply {
    private var total = 0

    def update(a: Int, b: Int): Int = {
      total += a * b
      total
    }

    def apply(a: Int): Int = a
  }

  private val updateAndApply = new UpdateAndApply
  updateAndApply(2) = 10
  updateAndApply(89)

  ///////////////////////////////////////////////////////////////////////////////////////////////////

  //syntactical sugar getters and setters

  class Mutable {
    private var mute: Int = 0

    def member = mute

    def member_=(value: Int) = {
      mute = value
    }
  }

  private val mutable = new Mutable
  mutable.member = 10 // compiler rewrites the syntax to mutable.member_=(10)
  //Works only if getters and setters are declared and have same name.
  ////////////////////////////////////////////////////////////////////////////////////////////////////////
}
