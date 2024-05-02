package com.chethan

/**
  * Created by $CapName on Nov 14, 2023.
  */

object Dummy extends App {


  //  val records1 = Map("fanname" -> "UC_XC1_000_R9", "holenum" -> 4, "status" -> "planned")
  //  val records2 = Map("fanname" -> "UC_XC1_000_R9", "holenum" -> 5, "status" -> "drill")
  //  val records3 = Map("fanname" -> "UC_XC1_000_R9", "holenum" -> 1, "status" -> "planned")
  //  val records4 = Map("fanname" -> "UC_XC1_000_R9", "holenum" -> 2, "status" -> "blasted")
  //
  //  val records10 = Map("fanname" -> "UC_XC1_000_R1", "holenum" -> 4, "status" -> "planned")
  //  val records20 = Map("fanname" -> "UC_XC1_000_R1", "holenum" -> 5, "status" -> "drill")
  //  val records30 = Map("fanname" -> "UC_XC1_000_R1", "holenum" -> 1, "status" -> "planned")
  //  val records40 = Map("fanname" -> "UC_XC1_000_R1", "holenum" -> 2, "status" -> "blasted")

  //  val records = List(records1, records2, records3, records4, records10,
  //    records20,
  //    records30,
  //    records40)
  //
  //
  //  val fanname = "fanname"
  //  val holenum = "holenum"
  //  val status  = "status"
  //
  //  val distFans    = records.map(dataItem => dataItem(fanname)).distinct //2
  //  val distHolenum = records.map(dataItem => dataItem(holenum)).distinct //4
  //  val distStatus  = records.map(dataItem => dataItem(status)).distinct //3
  //
  //
  //  val aaa = for (i <- 1 to distFans.size; j <- 1 to distHolenum.size; k <- 1 to status.size)
  //    yield true
  //
  //  aaa.forall(_)
  //
  //  val aaaaa      = List(fanname, holenum, status)
  //  val uniqueData = aaaaa.map(unique => records.map(a => a(unique)).distinct)
  //
  //
  //
  //  val uniqueCount = uniqueData.map(_.size)
  //
  //
  //


  //  val abc = List[List[Any]](List(1, 2, 3, 1, 4), List("a", "b", "c"), List("da7", "5aa")).reduce{ (a, b) =>
  //    val li = a.zipAll(b, "qw", "po")
  //    print(a)
  //    print("  -=-  ")
  //    println(b)
  //    println(li)
  //    li
  //  }
  //
  //  println(abc)
  //
  //  //  val a=List((1,2,3),(1,"s",8,1+))

  //  List(List(1, 2), List("a", "b", "c", "4", "5")).combinations(100)
  val a: Iterator[String] = "".lines


  val datas1 = Map("fanname" -> "XC1_R01", "holenum" -> 7, "status" -> "planned", "cumpre" -> 5)
  val datas2 = Map("fanname" -> "XC1_R05", "holenum" -> 8, "status" -> "planned", "cumpre" -> 2)
  val datas3 = Map("fanname" -> "XC1_R01", "holenum" -> 5, "status" -> "unplanned", "cumpre" -> 6)
  val datas4 = Map("fanname" -> "XC1_R01", "holenum" -> 7, "status" -> "planned", "cumpre" -> 2)
  val datas5 = Map("fanname" -> "XC1_R02", "holenum" -> 4, "status" -> "unplanned", "cumpre" -> 6)
  val datas6 = Map("fanname" -> "XC1_R01", "holenum" -> 3, "status" -> "planned", "cumpre" -> 2)
  val datas7 = Map("fanname" -> "XC1_R01", "holenum" -> 7, "status" -> "unplanned", "cumpre" -> 6, "num" -> 123)
  val datas8 = Map("fanname" -> "XC1_R01", "holenum" -> 7, "status" -> "unplanned", "cumpre" -> 6, "num" -> 789)


  val distinctFields = "fanname,holenum,status"

  val extra = Map("fanname" -> "XC1_R99", "holenum" -> 7, "status" -> "unplanned", "cumpre" -> 6, "num" -> 789)

  val setA: List[Map[String, Any]] = List(datas1, datas2, datas3, datas4, datas5, datas6, datas7, datas8)
  val setB                         = List(datas1, datas3, datas7, datas5, datas1, datas7, extra)


  val fieldNames: Array[String]                = distinctFields.split(",")
  val getValues : (Map[String, Any] => String) = (record) => fieldNames.map(x => record.getOrElse(x,"")).mkString


  /** Finding all the possible combination of the Given fields */
  val distinctRecordsSetA = setA.groupBy(getValues)
  val distinctRecordsSetB = setB.groupBy(getValues)

  distinctRecordsSetA

  println(Console.BLUE + "all possible combination of set A" + Console.YELLOW)
  distinctRecordsSetA.foreach(println)
  println(Console.WHITE)

  println(Console.BLUE + "all possible combination of set B" + Console.YELLOW)
  distinctRecordsSetB.foreach(println)

   val ia= distinctRecordsSetA.filter(x => distinctRecordsSetB.contains(x._1)).flatMap(_._2)
   val ib= distinctRecordsSetB.filter(x => distinctRecordsSetA.contains(x._1))
  println()
  val intersection = distinctRecordsSetA.keys.toList.intersect(distinctRecordsSetB.keys.toList)
  println(Console.GREEN + "Intersected Combinations => " + intersection)


  IntersectionOfDataTables
  UnionOfDataTables
  differenceOfDatatables
  complementOfIntersection


  def IntersectionOfDataTables = {
    println()
    println(Console.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=- Intersection (A ^ B)-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
    val intersectionOfA = distinctRecordsSetA.filter{ case (k, v) => intersection.contains(k) }.flatMap(_._2)
    val intersectionOfB = distinctRecordsSetB.filter{ case (k, v) => intersection.contains(k) }.flatMap(_._2)

    //TODO: Return records from which set???
    val aIntersectB = intersectionOfA ++ intersectionOfB

    aIntersectB.foreach(x => println(Console.MAGENTA + x + Console.RED + "                       # (A^B)"))
    println("Total Count => " + aIntersectB.size)
    println(Console.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=- Intersection (A ^ B)-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
  }

  def UnionOfDataTables = {
    println()
    println()
    println(Console.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=- Union (A U B)-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
    val union = setA union setB

    union.foreach(x => println(Console.MAGENTA + x + Console.RED + "                       # (A U B)"))
    println("Total Count => " + union.size)
    println(Console.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=- Union (A U B)-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
  }

  def differenceOfDatatables = {
    println()
    println(Console.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=- Difference (A - B)-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
    val intersectionOfA = distinctRecordsSetA.filterNot{ case (k, v) => intersection.contains(k) }.flatMap(_._2)

    intersectionOfA.foreach(x => println(Console.MAGENTA + x + Console.RED + "                       # (A - B)"))
    println("Total Count => " + intersectionOfA.size)
    println(Console.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=- Difference (A - B)-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")


    println()
    println(Console.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=- Difference (B - A)-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
    val intersectionOfB = distinctRecordsSetB.filterNot{ case (k, v) => intersection.contains(k) }.flatMap(_._2)

    intersectionOfA.foreach(x => println(Console.MAGENTA + x + Console.RED + "                       # (A - B)"))
    println("Total Count => " + intersectionOfB.size)
    println(Console.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=- Difference (B - A)-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
  }


  def complementOfIntersection  = {
    println()
    println()
    println(Console.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=- Complement (A ^ B)` -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
    val intersectionOfA = distinctRecordsSetA.filterNot{ case (k, v) => intersection.contains(k) }.flatMap(_._2)

    val intersectionOfB = distinctRecordsSetB.filterNot{ case (k, v) => intersection.contains(k) }.flatMap(_._2)

    val aIntersectB = intersectionOfA ++ intersectionOfB

    aIntersectB.foreach(x => println(Console.MAGENTA + x + Console.RED + "                       # (A ^ B)`"))
    println("Total Count => " + aIntersectB.size)
    println(Console.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=- Complement (A ^ B)` -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
  }


}


/**
  *functions
  *
  *1. union (A U B)
  *2. intersection (A ^ B)
  *3. difference (A-B)
  *4. ComplementOfIntersection (A ^ B)`
  *
  *
  *
  *
  *
  *
  *
  * */




