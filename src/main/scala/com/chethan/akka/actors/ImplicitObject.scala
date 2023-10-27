package com.chethan.akka.actors

/**
  * Created by $CapName on Oct 17, 2023.
  */

object ImplicitObject {

  def main(args: Array[String]): Unit = {
//    implicit val anyId1: Int = 1134
    val tryImp = new AnotherClass
    tryImp.implyMethods("namskara")

  }
}
