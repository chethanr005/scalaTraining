package com.chethan.assignment1

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Chethan on Aug 01, 2022.
  */

object StudentImplementation {

  val studentsInFuture: Future[List[Student]] = new StudentDatabase().getAllData

  def getMaleAndFemaleCount: Future[MaleAndFemaleContainer] = {
    for {
      studentsList <- studentsInFuture
      count = studentsList.groupBy(student => student.gender)
    } yield MaleAndFemaleContainer(count("male").size, count("female").size)
    for {
      studentsList <- studentsInFuture
      maleList = studentsList.filter(_.isMale)
      //      feMaleList = studentsList.filter(_.isFemale)
    } yield MaleAndFemaleContainer(maleList.size, studentsList.size - maleList.size)

    for {
      studentsList <- studentsInFuture
      (males, females) = studentsList.partition(_.isMale)
    } yield MaleAndFemaleContainer(males.size, females.size)
  }

  def getPrefixNames: Future[List[String]] = {
    for {
      studentsList <- studentsInFuture
    } yield studentsList.map{ student =>
      student.gender toUpperCase match {
        case "MALE"   => "Mr." + student.name
        case "FEMALE" => "Ms." + student.name
        case _        => student.name
      }
      if (student.isMale) {
        "Mr." + student.name
      } else {
        "Ms." + student.name
      }
    }
  }

  def getGradeLevelContainer(gradeLevel: Int): Future[GradeLevelContainer] = {
    for {
      studentsList <- studentsInFuture
      studentsCount = studentsList.count(student => student.gradeLevel == gradeLevel)
    } yield GradeLevelContainer(gradeLevel, studentsCount)
  }


  def getActivityContainer(activity: String): Future[ActivityContainer] = {
    for {
      studentsList <- studentsInFuture
      studentsCount = studentsList.count(student => student.activities.contains(activity))
    } yield ActivityContainer(activity, studentsCount)
  }

  def getPerformanceContainer(performanceLevel: String): Future[PerformanceContainer] = {
    for (studentsList <- studentsInFuture)
      yield performanceLevel toUpperCase match {
        case "POOR"      => PerformanceContainer("Poor", studentsList.count(student => 0.0 <= student.gpa && student.gpa <= 4.0))
        case "AVERAGE"   => PerformanceContainer("Average", studentsList.count(student => 4.0 < student.gpa && student.gpa <= 7.0))
        case "EXCELLENT" => PerformanceContainer("Excellent", studentsList.count(student => 7.0 < student.gpa && student.gpa <= 10.0))
        case _           => PerformanceContainer(s"performance level $performanceLevel not found", 0)
      }
  }
}

case class MaleAndFemaleContainer(maleCount: Int, femaleCount: Int)

case class GradeLevelContainer(gradeLevel: Int, students: Int)

case class ActivityContainer(activity: String, studentsCount: Int)

case class PerformanceContainer(level: String, students: Int)
