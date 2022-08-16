package com.chethan.assignment1

import scala.concurrent.Future

/**
  * Created by Chethan on Jul 22, 2022.
  */

trait DataBase[A <: BaseClass] {
  def getAllData: Future[List[A]]

  def getDataById(id: Any): Future[A]

  def addData(data: A): Future[Boolean]

  def updateData(data: A): Future[Boolean]

  def deleteData(id: Any): Future[Boolean]
}

class A extends DataBase[StringContainer]{
  override def getAllData: Future[List[StringContainer]] = ???

  override def getDataById(id: Any): Future[StringContainer] = ???

  override def addData(data: StringContainer): Future[Boolean] = ???

  override def updateData(data: StringContainer): Future[Boolean] = ???

  override def deleteData(id: Any): Future[Boolean] = ???
}


trait BaseClass{
  def id:String// Primary key
}

case class StringContainer(id:String, pkId:Long, value:String) extends BaseClass with BaseClass2

trait BaseClass2 {
  def pkId:Long
}

trait DataBase2[A <: BaseClass2] {
  def getAllData: Future[List[A]]

  def getDataById(id: String): Future[A]

  def addData(data: A): Future[Boolean]

  def updateData(data: A): Future[Boolean]

  def deleteData(id: String): Future[Boolean]
}

case class SomeClass(pkId:Long) extends BaseClass2
class LongPk extends DataBase2[StringContainer]{
  override def getAllData: Future[List[SomeClass]] = ???

  override def getDataById(id: String): Future[SomeClass] = ???

  override def addData(data: SomeClass): Future[Boolean] = ???

  override def updateData(data: SomeClass): Future[Boolean] = ???

  override def deleteData(id: String): Future[Boolean] = ???
}


trait DataBase[A] {
  def getAllData: Future[List[A]]

  def getDataById(id: Any): Future[A]

  def addData(data: A): Future[Boolean]

  def updateData(data: A): Future[Boolean]

  def deleteData(id: Any): Future[Boolean]
}