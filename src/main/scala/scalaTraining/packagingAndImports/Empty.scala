package scalaTraining {
  package packagingAndImports {

    import scalaTraining.packagingAndImports.innerPack._
    class Empty {

      val a2 = new innerPack.Mission
      println(a2.a)

     // val b1= new Full

    }


    object Empty extends App {
      val a1 = new Empty

      val a2 = Mission.ball
      val a3=new Full

      val a4= new Mission
        a4.apple
      println(a4)

      val b1= new Mission


    }
  }




}






