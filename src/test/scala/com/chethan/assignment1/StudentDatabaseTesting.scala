package com.chethan.assignment1

import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success

/**
  * Created by Chethan on Aug 01, 2022.
  */

class StudentDatabaseTesting extends AnyWordSpec {

  "Student database testing" must {
    "getAllData testing " in {
      val getAllData = new StudentDatabase().getAllData
      assert(getAllData.value.get.get.size == 10)
    }
    "getById testing " in {
      val getById = new StudentDatabase().getDataById("r001")
      assert(getById.value.get.get == Student("r001", "rose", 7.5, "female", 10, List("swimming", "cycling")))
    }

    "addData testing " in {
      val getById = new StudentDatabase().addData(Student("r011", "rose", 7.5, "female", 10, List("swimming", "cycling")))
      assert(getById.value.get.get == true)
    }

    "updateData testing " in {
      val getById = new StudentDatabase().updateData(Student("r011", "moose", 7.5, "female", 10, List("eating")))
      assert(getById.value.get.get == true)
    }

    "deleteData testing " in {
      val getById = new StudentDatabase().deleteData("r011")
      assert(getById.value.get.get == true)
    }
  }

  "student implementation testing" must {
    "get male and female container" in {
      val maleAndFemaleContainer= StudentImplementation.getMaleAndFemaleCount
      Thread.sleep(1000)
      assert(maleAndFemaleContainer.value.get.get == MaleAndFemaleContainer(5,5))
    }

    "get prefix names of students" in {
      val prefixNames= StudentImplementation.getPrefixNames
      Thread.sleep(1000)
      assert(prefixNames.value.get.get == List("Mr.tony", "Ms.kail", "Mr.anthony", "Mr.andrew", "Mr.cooper", "Ms.hailey", "Ms.kate", "Ms.rose", "Ms.kate", "Mr.aaaa"))
    }

    "get grade level container" in {
      val gradeLevelContainer= StudentImplementation.getGradeLevelContainer(10)
      Thread.sleep(1000)
      assert(gradeLevelContainer.value.get.get == GradeLevelContainer(10,6))
    }

    "get activity container" in {
      val activityContainer= StudentImplementation.getActivityContainer("cycling")
      Thread.sleep(1000)
      assert(activityContainer.value.get.get == ActivityContainer("cycling",6))
    }

    "get performance container" in {
      val performanceContainer= StudentImplementation.getPerformanceContainer("aveRagE")
      Thread.sleep(1000)
      assert(performanceContainer.value.get.get == PerformanceContainer("Average",5))
    }
  }
}
