package com.chethan.akka.actors.classicActor

/**
  * Created by $CapName on Oct 17, 2023.
  */

class TryingImplicits {


  implicit final val anyId: Int = 8055

}

class AnotherClass {

  def implyMethods(name: String)(implicit id: Int = 123): Unit = {
    println(s"this is the name $name with int $id")
  }
}
