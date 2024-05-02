package com.chethan.akka.assignment.typedActors

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import com.chethan.akka.assignment.typedActors.Zomato.{Cancel, ConfirmOrder, Login, ProceedToPayment, SignUp}
import org.scalatest.wordspec.AnyWordSpecLike

/**
  * Created by Chethan on Feb 13, 2024.
  */

class ZomatoTest extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  //  private val timeout = Timeout(4 seconds)

  "TacoBallTest" should {

    "welcome page should display Login/SignUp message and successful instantiation all the child actors and change in behaviour to authentication" in {
      testKit.spawn(Zomato(), "tacoBall")
    }

    "Sending a SignUp will return SignUpSuccess/SignUpFailure" in {
      val tacoBall = testKit.spawn(Zomato(), "tacoBall")
//      tacoBall ! SignUp("chethan", "chethan2021")
      tacoBall ! SignUp("ajay", "ajay007")
      Thread.sleep(4000)
    }

    "Sending a Login message will return SignUpSuccess/SignUpFailure" in {
      val tacoBall = testKit.spawn(Zomato(), "tacoBall")
//      tacoBall ! Login("chethan", "chethan2021") // success
//      tacoBall ! Login("chethan", "chethan2021") // wrong user name
      tacoBall ! Login("chethan", "chethan2021") // incorrect password
      Thread.sleep(4000)
    }

    "Successful login/signin will result in change in behaviour( authentication ----> homePage_foodBrowser) and " +
    "provides access to the home page i.e food browser and will ask for a Confirm Order message" in {
      val tacoBall = testKit.spawn(Zomato(), "tacoBall")
      tacoBall ! Login("chethan", "chethan2021") // success
      Thread.sleep(8000)
    }

    "On confirming the order behaiouvr will be changed(homePage_foodBrowser ----> checkout) and will ask to proceed for the payment" in {
      val tacoBall = testKit.spawn(Zomato(), "tacoBall")
      tacoBall ! Login("chethan", "chethan2021") // success
      Thread.sleep(8000)
      tacoBall ! ConfirmOrder(1) // success
      Thread.sleep(4000)
    }

    "during checkout behaviour, No other commands are processed except ProceedToPayment or Cancel." in {
      val tacoBall = testKit.spawn(Zomato(), "tacoBall")
      tacoBall ! Login("chethan", "chethan2021") // success
      Thread.sleep(8000)
      tacoBall ! ConfirmOrder(1) // success
      tacoBall ! Login("", "") // success
      Thread.sleep(4000)
    }

    "Successful/failure/Cancellation of payment result in change in behaviour back to (checkout ----> homePage_foodBrowser)." in {
      val tacoBall = testKit.spawn(Zomato(), "tacoBall")
      tacoBall ! Login("chethan", "chethan2021") // success
      Thread.sleep(8000)
      tacoBall ! ConfirmOrder(1) // success
      tacoBall ! Cancel
      tacoBall ! ProceedToPayment("454", "8545")
      Thread.sleep(4000)
    }

    "Successful payment result in change in behaviour back to (checkout ----> homePage_foodBrowser)." in {
      val tacoBall = testKit.spawn(Zomato(), "tacoBall")
      tacoBall ! Login("chethan", "chethan2021") // success
      Thread.sleep(8000)
      tacoBall ! ConfirmOrder(1) // success
      tacoBall ! ProceedToPayment("123456789", "007")
      Thread.sleep(4000)
    }

    "Read for delivery when the food is prepared and Delivery request message for delivery man" in {
      val tacoBall = testKit.spawn(Zomato(), "tacoBall")
      tacoBall ! Login("chethan", "chethan2021") // success
      Thread.sleep(8000)
      tacoBall ! ConfirmOrder(1) // success
      tacoBall ! ProceedToPayment("123456789", "007")
      Thread.sleep(6000)
    }

    "when the order is picked by the delivery man from the resturant, Order Picked message is received" in {
      val tacoBall = testKit.spawn(Zomato(), "tacoBall")
      tacoBall ! Login("chethan", "chethan2021") // success
      Thread.sleep(8000)
      tacoBall ! ConfirmOrder(1) // success
      tacoBall ! ProceedToPayment("123456789", "007")
      Thread.sleep(23000)
    }

    "when the delivery has reached the location, At location message is received" in {
      val tacoBall = testKit.spawn(Zomato(), "tacoBall")
      tacoBall ! Login("chethan", "chethan2021") // success
      Thread.sleep(8000)
      tacoBall ! ConfirmOrder(1) // success
      tacoBall ! ProceedToPayment("123456789", "007")
      Thread.sleep(22000)
    }

    "Once the order is delivered to the customer, Order Complete Successful message will be received and OrderItems list will be updated " in {
      val tacoBall = testKit.spawn(Zomato(), "tacoBall")
      tacoBall ! Login("chethan", "chethan2021") // success
      Thread.sleep(8000)
      tacoBall ! ConfirmOrder(1) // success
      tacoBall ! ProceedToPayment("123456789", "007")
      Thread.sleep(30000)
    }

    "Booking Multiple orders " in {
      val tacoBall = testKit.spawn(Zomato(), "tacoBall")
      tacoBall ! Login("chethan", "chethan2021") // success
      Thread.sleep(8000)
      tacoBall ! ConfirmOrder(1) // success
      tacoBall ! ProceedToPayment("123456789", "007")
      Thread.sleep(5000)
      tacoBall ! ConfirmOrder(5) // success
      tacoBall ! ProceedToPayment("123456789", "007")
      Thread.sleep(30000)
    }
  }
}
