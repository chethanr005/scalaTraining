package com.chethan.assignment1

import scala.concurrent.Future

/**
  * Created by Chethan on Jul 22, 2022.
  */

trait DataBase[A <: Human] {
  def getAllData: Future[List[A]]

  def getDataById(id: AnyVal): Future[A]

  def addData(data: A): Future[Boolean]

  def updateData(data: A): Future[Boolean]

  def deleteData(id: AnyVal): Future[Boolean]
}

trait Human


trait SuperEmployees extends Human {
  val id: Int
}

trait SuperStudents extends Human {
  val id: String
}




//class A extends DataBase[StringContainer] {
//  override def getAllData: Future[List[StringContainer]] = ???
//
//  override def getDataById(id: Any): Future[StringContainer] = ???
//
//  override def addData(data: StringContainer): Future[Boolean] = ???
//
//  override def updateData(data: StringContainer): Future[Boolean] = ???
//
//  override def deleteData(id: Any): Future[Boolean] = ???
//}


//trait BaseClass {
//  val id: String // Primary key
//}
//trait IntegerTrait{
//  val id:Int
//}
//
//trait StringClass {
//  val id:String
//}

//case class Emp(id:Int) extends Humans with IntegerTrait
//case class StringContainer(id: String, pkId: Long, value: String) extends BaseClass with BaseClass2
//
//
//trait BaseClass2 {
//  def pkId: Long
//}
//
//trait DataBase2[A <: BaseClass] {
//  def getAllData: Future[List[A]]
//
//  def getDataById(id: Int): Future[A]
//
//  def addData(data: A): Future[Boolean]
//
//  def updateData(data: A): Future[Boolean]
//
//  def deleteData(id: Int): Future[Boolean]
//}
object Main extends App {

}
//case class SomeClass(pkId: Long) extends BaseClass2

//class LongPk extends DataBase2[StringContainer] {
//  override def getAllData: Future[List[SomeClass]] = ???
//
//  override def getDataById(id: String): Future[SomeClass] = ???
//
//  override def addData(data: SomeClass): Future[Boolean] = ???
//
//  override def updateData(data: SomeClass): Future[Boolean] = ???
//
//  override def deleteData(id: String): Future[Boolean] = ???
//}


//trait DataBase[A] {
//  def getAllData: Future[List[A]]
//
//  def getDataById(id: Any): Future[A]
//
//  def addData(data: A): Future[Boolean]
//
//  def updateData(data: A): Future[Boolean]
//
//  def deleteData(id: Any): Future[Boolean]
//}