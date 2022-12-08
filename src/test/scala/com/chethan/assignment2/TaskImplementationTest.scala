//package com.chethan.assignment2
//
//import com.chethan.assignment2.Test.{Employee, JobProfile, UserAccountDetail, UserProfileContainer}
//import org.scalatest.concurrent.PatienceConfiguration.Timeout
//import org.scalatest.concurrent.ScalaFutures
//import org.scalatest.{Matchers, WordSpec}
//
//import scala.concurrent.duration._
//
///**
//  * Created by Chethan on Sep 29, 2022.
//  */
//
//class TaskImplementationTest extends WordSpec with ScalaFutures with Matchers {
//  val timeout            = Timeout(10.seconds)
//  val taskImplementation = new TaskImplementation
//
//  "Tonnage Task" should {
//
//    "1. get each asset total tonnage irrespective of the output type and activity" in {
//      val result = taskImplementation.totalTonnage().futureValue(timeout);
//
//      result should not be empty
//      result.size shouldBe 4
//      result shouldBe List((1, 34.0), (2, 3.0), (3, 55.0), (4, 0))
//    }
//
//    "2. get the total tonnage of ROM" in {
//      val result = taskImplementation.totalTonnageOfRom("ROM").futureValue(timeout)
//      result shouldBe 10
//    }
//
//    "3. get the total tonnage of each activity" in {
//      val result = taskImplementation.totalTonnageOfActivity().futureValue(timeout)
//      result should not be empty
//      result shouldBe Map("Excavation" -> 15.0, "Drilling" -> 77.0, "Blasting" -> 0)
//    }
//  }
//
//  "Employee Task" should {
//    "1. Update Password using Username" in {
//      val result = taskImplementation.updatePassword("tom@reactore.com", "tomAndJerry").futureValue(timeout)
//      result shouldBe UserAccountDetail(2L, 2L, "tom@reactore.com", "tomAndJerry")
//    }
//
//    "2. Get user profile container from user id" in {
//      val result= taskImplementation.userProfileContainerByTokenId(9).futureValue(timeout)
//      result shouldBe UserProfileContainer(9,Employee(9,"Stephen",3,true),JobProfile(3,"Tester",true))
//    }
//
//    "3. Authenticate By Token Id" in {
//      val result = taskImplementation.authenticationUsingTokenId(11).futureValue(timeout)
//      result shouldBe true
//    }
//
//    "4. Get user Account Detail BY Job Profile Id" in {
//      val result = taskImplementation.userAccountDetailsByProfileId("Business Analyst").futureValue(timeout)
//      result should not be empty
//      result.size shouldBe 3
//      List(UserAccountDetail(10L, 10L, "penny@reactore.com", "penny123"),
//        UserAccountDetail(11L, 11L, "rohan@reactore.com", "rohan123"),
//        UserAccountDetail(12L, 12L, "ram@reactore.com", "ram123"))
//    }
//
//    "5. Get userProfile Container By Taken Id" in {
//      val result = taskImplementation.userProfileContainerByTokenId(3).futureValue(timeout)
//      result shouldBe UserProfileContainer(3,Employee(3,"Mark",1,true),JobProfile(1,"Developer",true))
//    }
//  }
//}
