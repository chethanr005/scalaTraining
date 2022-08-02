package com.chethan.assignment1

import scala.concurrent.Future

/**
  * Created by Chethan on Jul 22, 2022.
  */

trait DataBase[A] {
  def getAllData: Future[List[A]]

  def getDataById(id: Any): Future[A]

  def addData(data: A): Future[Boolean]

  def updateData(data: A): Future[Boolean]

  def deleteData(id: Any): Future[Boolean]
}
