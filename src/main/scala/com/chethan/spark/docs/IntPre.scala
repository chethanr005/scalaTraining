package com.chethan.spark.docs

import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext

/**
  * Created by $CapName on Jan 18, 2025.
  */

object IntPre extends App {

  val executionContext = Executors.newCachedThreadPool()

  implicit val exe = ExecutionContext.fromExecutor(executionContext)

  //
  //  val ft = for {
  //
  //
  //    _ <- Future.unit
  //  } yield ""
  //
  //  ft.onComplete{
  //    case Failure(exception) => ???
  //    case Success(value)     => ???
  //  }
  //

  //  Option("abc").flatMap(a => a.length)

  val abc = List("abc").flatMap(a => Option(a + "de"))
  println(abc)

  println(Some(null).flatMap(Option(_)).isDefined)


  implicit val sname = "abc"


  def impName(implicit name: String) = {
    println(name)
  }

  impName

//  implicit def actImpDef(name: String): Int = name.length

//  val count: Int = "anc"


//  case class Person(name: String) extends Ordered[Person]{
//    override def compare(that: Person): Int = {
//      this.name <= that.name
//    }
//  }

//  implicit class ImpClass(name: String) {
//    def convertToPerson = Person(name)
//  }
//
//
//  val p: Person = " ".convertToPerson


  implicit val impInt = 1023

  implicit def impNameV2(name: String)(implicit c: Int): Int = {
    name.length + c
  }

  val res: Int = " "
  println(res)

  def longMethod(
                a1:Int,
                a2:Int,
                a3:Int,
                a4:Int,
                a6:Int,
                a5:Int,
                a7:Int,
                a8:Int,

                a9:Int,
                a10:Int,
                a11:Int,
                a12:Int,
                a13:Int,
                a14:Int,
                a15:Int,
                a16:Int,
                a17:Int,
                a18:Int,
                a19:Int,
                a20:Int,
                a21:Int,
                a22:Int,
                a23:Int,
                a24:Int,
                a25:Int,
                a26:Int,a27:Int,



                )={
    10
  }

  longMethod(10,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,1,1,1,1,1,1,1,1,1)


// implicit val ordering:Ordering[Person] = Ordering.by(a => a.name.length)


//  new Ordered[Person] {
//    override def compare(that: Person): Int = {
//      that.name
//    }
//  }
//
//
//    List(Person("abc"),Person("mkl")).sorted
}