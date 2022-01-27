package scalaTraining.packagingAndImports.innerPack


class Mission {

  val a=10
  //val b1= new Empty

  val apple=99999
}

object Mission {
  val ball=10000

}


object chain extends App{
  val a1=new Mission
  println(a1.apple)

  //val z= new _root_.scalaTraining.A
}
