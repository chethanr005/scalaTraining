package com.chethan.akka.assignment.classisActor

import akka.actor.{ActorRef, ActorSystem, Props}
import com.chethan.akka.assignment.classisActor.MineManagerSpec.InitiateShift
import com.chethan.akka.assignment.classisActor.MiningSpecs.Shift

/**
  * Created by $Chethan on Jan 15, 2024.
  */

object ClassActorsAssignment extends App {
  val mineOne = ActorSystem("MineOne_MiningCompany")

  val mineManager        = mineOne.actorOf(Props[MineManager], "MineManager1")
  val shiftConfiguration = Shift(10, 5, 2, 8)
  mineManager ! InitiateShift(shiftConfiguration)
  //  case "count" => println(trucks.size+" "+ excavators.size+" "+weighBridges.size)

}

object MiningSpecs {
  case class Shift(trucks: Int, excavators: Int, weighBridges: Int, shiftHours: Int)

  def getAssetName(actorRef: ActorRef): String = {
    val path = actorRef.path.name
    val dirs = path.split("\\.")
    dirs(dirs.size - 1)
  }


}


/**
  *Classic Actors Assignment
  *
  *10 Trucks
  *2 Excavator
  *1 WeighBridge
  *
  *Shifts(Morning, Evening)
  * By start of the Shift trips should start
  * When the Shift is about to end in 20mins, No more trips are accepted, But ongoing trips is to be completed.
  *
  *
  *
  *Process
  *                             10mins                1min
  *  ^^ ---->   Truck(parking)  ---->     Excavator  --->  WeighBridge  --->  DumpingSite ----|
  *  |<-------------------------------------------------------------------------------------< V
  *
  *
  *
  * */
