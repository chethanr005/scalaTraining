package com.chethan.assignment2

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Chethan on Sep 29, 2022.
  */

object Test {


  case class OutputType(id: Long, name: String)

  case class Activity(id: Long, name: String)

  case class OutputTypeActivityMapping(id: Long, outputTypeId: Long, activityId: Long)

  case class TripDetails(id: Long, assetId: Long, outputTypeActivityMappingId: Long, totalTonnage: Option[Double])

  // use below data
  val output1          = OutputType(1L, "ROM")
  val output2          = OutputType(2L, "WASTE")
  val output3          = OutputType(3L, "BHQ")
  val allOutputsFuture = List(output1, output2, output3)


  def getAllOutputType = {
    Future(allOutputsFuture)
  }


  val activity1           = Activity(1L, "Excavation")
  val activity2           = Activity(2L, "Drilling")
  val activity3           = Activity(3L, "Blasting")
  val allActivitiesFuture = List(activity1, activity2, activity3)

  def getAllActivities = {
    Future(allActivitiesFuture)
  }

  val outputTypeActivityMapping1         = OutputTypeActivityMapping(1L, 1L, 1L)
  val outputTypeActivityMapping2         = OutputTypeActivityMapping(2L, 2L, 1L)
  val outputTypeActivityMapping3         = OutputTypeActivityMapping(3L, 3L, 1L)
  val outputTypeActivityMapping4         = OutputTypeActivityMapping(4L, 3L, 2L)
  val outputTypeActivityMapping5         = OutputTypeActivityMapping(5L, 3L, 3L)
  val allOutputTypeActivityMappingFuture = List(outputTypeActivityMapping1, outputTypeActivityMapping2, outputTypeActivityMapping3, outputTypeActivityMapping4, outputTypeActivityMapping5)

  def getAllOutputTypeActivityMapping = {
    Future(allOutputTypeActivityMappingFuture)
  }

  val trip1             = TripDetails(1, 1, 1, Some(10D))
  val trip2             = TripDetails(2, 1, 2, None)
  val trip3             = TripDetails(3, 1, 3, Some(2D))
  val trip4             = TripDetails(4, 1, 4, Some(22D))
  val trip5             = TripDetails(5, 2, 2, Some(3D))
  val trip6             = TripDetails(6, 2, 3, None)
  val trip7             = TripDetails(7, 3, 4, Some(55D))
  val trip8             = TripDetails(8, 4, 4, None)
  val tripDetailsFuture = List(trip1, trip2, trip3, trip4, trip5, trip6, trip7, trip8)

  def getAllTripDetails = {
    Future(tripDetailsFuture)
  }
  //1.get each asset total tonnage irrespective of the output type and activity
  //2.get the total tonnage of "ROM"
  //3.get the total tonnage of each activity


  //  ---------------------------------------------------------------------------------------------


  case class Employee(id: Long, name: String, jobProfileId: Long, enabled: Boolean = true)

  case class JobProfile(id: Long, name: String, enabled: Boolean = true)

  case class UserAccountDetail(id: Long, employeeId: Long, userName: String, password: String, enabled: Boolean = true)

  case class Token(id: Long, userAccountId: Long, enabled: Boolean = true)

  case class UserProfileContainer(userAccountId: Long, employee: Employee, jobProfile: JobProfile)

  case class UserAccountContainer(userName: String, newPassword: String, oldPassword: String)

  val developer        = JobProfile(1L, "Developer")
  val hr               = JobProfile(2L, "HR")
  val tester           = JobProfile(3L, "Tester")
  val analyst          = JobProfile(4L, "Business Analyst")
  val qualityAssurance = JobProfile(5L, "QualityAssurance")
  val allJobProfiles   = List(developer, hr, tester, analyst, qualityAssurance)

  def getAllProfiles = Future(allJobProfiles)

  val employee1    = Employee(1L, "Steve", 1L)
  val employee2    = Employee(2L, "Tom", 1L)
  val employee3    = Employee(3L, "Mark", 1L)
  val employee4    = Employee(4L, "Morgan", 2L)
  val employee5    = Employee(5L, "Howard", 2L)
  val employee6    = Employee(6L, "Raj", 2L)
  val employee7    = Employee(7L, "Sheldon", 3L)
  val employee8    = Employee(8L, "Leonard", 3L)
  val employee9    = Employee(9L, "Stephen", 3L)
  val employee10   = Employee(10L, "Penny", 4L)
  val employee11   = Employee(11L, "Rohan", 4L)
  val employee12   = Employee(12L, "Ram", 4L)
  val employee13   = Employee(13L, "Satya", 5L)
  val employee14   = Employee(14L, "Sundar", 5L)
  val employee15   = Employee(15L, "Shiv", 5L)
  val allEmployees = List(employee1, employee2, employee3, employee4, employee5, employee6,
    employee7, employee8, employee9, employee10, employee11, employee12, employee13, employee14, employee15)

  def getAllEmployees = Future(allEmployees)

  val employee1UserAccount  = UserAccountDetail(1L, 1L, "steve@reactore.com", "steve123")
  val employee2UserAccount  = UserAccountDetail(2L, 2L, "tom@reactore.com", "tom123")
  val employee3UserAccount  = UserAccountDetail(3L, 3L, "mark@reactore.com", "mark123")
  val employee4UserAccount  = UserAccountDetail(4L, 4L, "morgan@reactore.com", "morgan123")
  val employee5UserAccount  = UserAccountDetail(5L, 5L, "howard@reactore.com", "howard123")
  val employee6UserAccount  = UserAccountDetail(6L, 6L, "raj@reactore.com", "raj123")
  val employee7UserAccount  = UserAccountDetail(7L, 7L, "sheldon@reactore.com", "sheldon123")
  val employee8UserAccount  = UserAccountDetail(8L, 8L, "leonard@reactore.com", "leonard123")
  val employee9UserAccount  = UserAccountDetail(9L, 9L, "stephen@reactore.com", "stephen123")
  val employee10UserAccount = UserAccountDetail(10L, 10L, "penny@reactore.com", "penny123")
  val employee11UserAccount = UserAccountDetail(11L, 11L, "rohan@reactore.com", "rohan123")
  val employee12UserAccount = UserAccountDetail(12L, 12L, "ram@reactore.com", "ram123")
  val employee13UserAccount = UserAccountDetail(13L, 13L, "satya@reactore.com", "satya123")
  val employee14UserAccount = UserAccountDetail(14L, 14L, "sundar@reactore.com", "sundar123")
  val employee15UserAccount = UserAccountDetail(15L, 15L, "shiv@reactore.com", "shiv123")
  val allUserAccountDetails = List(employee1UserAccount, employee2UserAccount, employee3UserAccount, employee4UserAccount, employee5UserAccount
    , employee6UserAccount, employee7UserAccount, employee8UserAccount, employee9UserAccount, employee10UserAccount, employee11UserAccount
    , employee12UserAccount, employee13UserAccount, employee14UserAccount, employee15UserAccount)

  def getUserAccountDetails = Future(allUserAccountDetails)

  val token1         = Token(1L, 1L)
  val token2         = Token(2L, 2L)
  val token3         = Token(3L, 3L)
  val token4         = Token(4L, 4L)
  val token5         = Token(5L, 5L)
  val token6         = Token(6L, 6L)
  val token7         = Token(7L, 7L)
  val token8         = Token(8L, 8L)
  val token9         = Token(9L, 9L)
  val token10        = Token(10L, 10L)
  val token11        = Token(11L, 11L)
  val token12        = Token(12L, 12L)
  val token13        = Token(13L, 13L)
  val token14        = Token(14L, 14L)
  val token15        = Token(15L, 15L)
  val allTokenFuture = List(token1, token2, token3, token4, token5, token6, token7, token8, token9, token10, token11, token12, token13, token14, token15)


  def getTokens=Future(allTokenFuture)

  // 1. Update Password using Username
  // 2. Get User Profile Container from UserId
  // 3. Authenticate By Token Id
  // 4. Get User Account Detail By Job Profile Id
  // 5. Get User Profile Container By Token Id


}


object Dummy extends  App{
  import scala.util.Random

  object CustomerID {
    def apply(name: String) = s"$name--${Random.nextLong()}"
    def unapply(customerID: String): Option[String] = {
      val stringArray: Array[String] = customerID.split("--")

      if (stringArray.tail.nonEmpty) Some(stringArray.head) else None
    }
  }

  val customer1ID = CustomerID("Sukyoung")
  // Sukyoung--23098234908




  customer1ID match {
    case aa => println(aa)  // prints Sukyoung
    case CustomerID(name) => println(name)  // prints Sukyoung
    case _ => println("Could not extract a CustomerID")
  }
  println( CustomerID.unapply(customer1ID))
}
