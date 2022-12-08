package com.chethan.assignment2

import com.chethan.assignment2.Test.UserProfileContainer

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Chethan on Sep 29, 2022.
  */

class TaskImplementation {


  ////////////////////////////////////////////////////////////////////////////////////////\
  //Task-1

  //1.get each asset total tonnage irrespective of the output type and activity
  def totalTonnage() = {
    for {
      listOfTripDetails <- Test.getAllTripDetails
      //result = listOfTripDetails.groupBy(_.assetId).map(a => (a._1, a._2.map(_.totalTonnage.getOrElse(0D)).sum)).toList
      result = listOfTripDetails.groupBy(_.assetId).map{ case (assetId, listOfTripDetails) => (assetId, listOfTripDetails.map(_.totalTonnage.getOrElse(0D)).sum) }.toList
    } yield result
  }

  //2.get the total tonnage of "ROM"
  def totalTonnageOfRom(outputTypeString: String) = {
    for {
      listOfOutputType <- Test.getAllOutputType
      outputType = listOfOutputType.find(_.name == outputTypeString)
      _ = if (outputType.isEmpty) throw new RuntimeException()
      outputId = outputType.get.id
      listOfOutputMapping <- Test.getAllOutputTypeActivityMapping
      listOfOutputMappingTypeId = listOfOutputMapping.filter(_.outputTypeId == outputId).map(_.id)
      listOfTripDetails <- Test.getAllTripDetails
      totalTonnage = listOfOutputMappingTypeId.map(romOutputId => listOfTripDetails.filter(_.outputTypeActivityMappingId == romOutputId).map(_.totalTonnage.getOrElse(0D)).sum).sum
    } yield totalTonnage
  }

  //3.get the total tonnage of each activity
  def totalTonnageOfActivity() = {
    for {
      listOfActivities <- Test.getAllActivities
      listOfOutputMapping <- Test.getAllOutputTypeActivityMapping
      listOfTripDetails <- Test.getAllTripDetails
      listOfOutputMappingIds = listOfActivities.map(activityType => (activityType.name, listOfOutputMapping.filter(outputMapper => outputMapper.activityId == activityType.id).map(_.id)))
      result = listOfOutputMappingIds.map{ case (activity, listOfMappingIds) => (activity, listOfMappingIds.map(mappingId => listOfTripDetails.filter(_.outputTypeActivityMappingId == mappingId).map(_.totalTonnage.getOrElse(0D)).sum).sum) }.toMap
    } yield result
  }

  //////////////////////////////////////////////////////////////////////////
  //Task-2

  def updatePassword(username: String, password: String) = {

    for {
      userAccountDetailsList <- Test.getUserAccountDetails
      updatedUserAccount = userAccountDetailsList.find(_.userName == username).map{ userAccount =>
        if (userAccount.password == password)
          throw new RuntimeException("Existing and new password, are same please enter the different password")
        else userAccount.copy(password = password)
      }
    } yield if (updatedUserAccount.isDefined) updatedUserAccount.get else throw new RuntimeException("User Account not found")
  }


  def userAccountDetailsByProfileId(jobProfile: String) = {
    for {
      listOfJobProfile <- Test.getAllProfiles
      jobProfileId = listOfJobProfile.find(_.name == jobProfile).map(_.id)
      _ = if (jobProfileId.isEmpty) throw new RuntimeException("job profile not found")
      listOfEmployees <- Test.getAllEmployees
      listOfVerifiedEmployeesIds = listOfEmployees.filter(_.jobProfileId == jobProfileId.get).map(_.id)
      listOfUserAccountDetails <- Test.getUserAccountDetails
      userAccountDetails = listOfVerifiedEmployeesIds.flatMap(empId => listOfUserAccountDetails.filter(userAccount => userAccount.employeeId == empId))
    } yield userAccountDetails
  }


  def userProfileContainerByTokenId(tokenId: Long) = {

    for {
      listOfTokens <- Test.getTokens
      userAccountId = listOfTokens.find(_.id == tokenId).map(_.userAccountId)
      _ = if (userAccountId.isEmpty) throw new RuntimeException("token not found")
      listOfUserAccount <- Test.getUserAccountDetails
      employeeId = listOfUserAccount.find(_.id == userAccountId.get).map(_.employeeId)
      listOfEmployees <- Test.getAllEmployees
      employee = listOfEmployees.find(_.id == employeeId.get)
      _ = if (employee.isEmpty) throw new RuntimeException("employee not found")
      listOfJobProfile <- Test.getAllProfiles
      jobProfile = listOfJobProfile.find(_.id == employee.get.jobProfileId)
    } yield UserProfileContainer(userAccountId.get, employee.get, jobProfile.get)
  }

  def userProfileContainerFromUserId(userId: Long) = {
    for {
      listOfUserAccountDetails <- Test.getUserAccountDetails
      employeeId = listOfUserAccountDetails.find(_.id == userId).map(_.employeeId)
      _ = if (employeeId.isEmpty) throw new RuntimeException("employeeId not found")
      listOfEmployees <- Test.getAllEmployees
      employee = listOfEmployees.find(_.id == employeeId.get)
      _ = if (employee.isEmpty) throw new RuntimeException("employee not found")
      listOfJobProfile <- Test.getAllProfiles
      jobProfile = listOfJobProfile.find(_.id == employee.get.jobProfileId)
      _ = if (jobProfile.isEmpty) throw new RuntimeException("Job profile not found")
    }yield UserProfileContainer(userId, employee.get, jobProfile.get)
  }

  def authenticationUsingTokenId(tokenId:Long)={
    for{
      listOfTokens <- Test.getTokens
      token=listOfTokens.find(_.id == tokenId)
      _=require(token.isDefined,"token not found")
      listOfUserAccount <- Test.getUserAccountDetails
      userAccount = listOfUserAccount.find(_.id == token.get.userAccountId)
      _=require(userAccount.isDefined,"user account not found")
      listOfEmployees <- Test.getAllEmployees
      employee = listOfEmployees.find(_.id == userAccount.get.employeeId)
      _=require(employee.isDefined,"user account not found")
    } yield  true
  }

}
