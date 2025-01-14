package com.chethan.akka.actors.assignment.typedActors

import akka.actor.Cancellable
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import akka.util.Timeout
import Authenticator.{LoginRequest, SignUpRequest}
import Bank.PaymentRequest
import Delivery.{DeliveryConfirmation, DeliveryRequest, DeliverySpecs}
import Restaurant.OrderRequest

import scala.collection.immutable.TreeMap
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by $Chethan on Jan 22, 2024.
  */

object Zomato {
  implicit val timeout = Timeout(4 seconds)

  def apply(): Behavior[TacoBallSpecs] = {
    Behaviors.setup[TacoBallSpecs]{ context =>
      welcomeMessage(context)
      val authenticator: ActorRef[Authenticator.LoggingSpecs] = context.spawn(Authenticator(), "Authenticator")
      val restaurant   : ActorRef[Restaurant.ResturantSpecs]  = context.spawn(Restaurant(context.self), "Restaurant")
      val bank         : ActorRef[Bank.BankSpecs]             = context.spawn(Bank(), "Bank")
      val deliveryMan  : ActorRef[DeliverySpecs]              = context.spawn(Delivery(context.self), "deliveryMan")
      authentication(authenticator, restaurant, bank, deliveryMan)
      //      homePage_foodBrowser(Nil, authenticator, restaurant, bank, deliveryMan)
    }
  }

  def authentication(authenticator: ActorRef[Authenticator.LoggingSpecs],
                     restaurant: ActorRef[Restaurant.ResturantSpecs],
                     bank: ActorRef[Bank.BankSpecs],
                     deliveryMan: ActorRef[DeliverySpecs]): Behavior[TacoBallSpecs] = {

    Behaviors.setup[TacoBallSpecs]{ context =>
      Behaviors.receiveMessage{

        //Login
        case Login(username, password) =>
          loginMessage(context)
          context.ask(authenticator, selfRef => LoginRequest(username, password, selfRef)){
            case Failure(exception)   => throw exception
            case Success(loginStatus) => loginStatus
          }
          Behaviors.same
        case LoginFailed(message)      =>
          loginFailureMessage(context, message)
          Behaviors.same
        case LoginSuccess              =>
          loginSuccessMessage(context)
          nextProcedure(context, DisplayMenu)
          homePage_foodBrowser(Nil, authenticator, restaurant, bank, deliveryMan)


        //SignUp
        case SignUp(username, password) =>
          signingInMessage(context)
          context.ask(authenticator, selfRef => SignUpRequest(username, password, selfRef)){
            case Failure(exception)    => throw exception
            case Success(signUpStatus) => signUpStatus
          }
          Behaviors.same
        case SignUpFailure(cause)       =>
          failureMessage(context, cause)
          Behaviors.same
        case SignUpSuccess              =>
          successMessage(context)
          nextProcedure(context, DisplayMenu)
          homePage_foodBrowser(Nil, authenticator, restaurant, bank, deliveryMan)

        //OtherCommand
        case unknownCommand =>
          context.log.info(s"Unsupported command : [${unknownCommand.toString}], please try to SignUp/Login.")
          Behaviors.same
      }
    }
  }


  def homePage_foodBrowser(orderedItems: List[String],
                           authenticator: ActorRef[Authenticator.LoggingSpecs],
                           restaurant: ActorRef[Restaurant.ResturantSpecs],
                           bank: ActorRef[Bank.BankSpecs],
                           deliveryMan: ActorRef[DeliverySpecs]): Behavior[TacoBallSpecs] = {

    Behaviors.setup[TacoBallSpecs]{ context =>
      orderedItemsDashboard(context, orderedItems)
      val foodMenu = getMenuItems
      Behaviors.receiveMessage{

        case DisplayMenu => displayMenuContents(context, foodMenu)
          Behaviors.same

        case ConfirmOrder(food) =>
          proceedToPaymentMessage(context)
          val foodId = foodMenu(food.toString)
          checkout(foodId.map(_.getNumericValue).sum.toString +: orderedItems, authenticator, restaurant, bank, deliveryMan)

        case PaymentSuccess        =>
          paymentSuccesstMessage(context)
          context.ask(restaurant, selfRef => OrderRequest(orderedItems.head, selfRef)){
            case Failure(exception) => throw exception
            case Success(value)     => value match {
              case OrderAccepted => OrderAccepted
              case OrderRejected => OrderRejected
            }
          }
          Behaviors.same
        case PaymentFailure(cause) =>
          paymentFailureMessage(context, cause)
          homePage_foodBrowser(orderedItems.tail, authenticator, restaurant, bank, deliveryMan)

        case OrderAccepted =>
          acceptMessage(context)
          Behaviors.same
        case OrderRejected =>
          rejectMessage(context)
          homePage_foodBrowser(orderedItems.tail, authenticator, restaurant, bank, deliveryMan)

        case ReadForDelivery(orderId) =>
          readyMessage(context, orderId)
          deliveryMan ! DeliveryRequest(orderId, restaurant, "location")
          Behaviors.same


        case DeliveryAccepted(orderId) =>
          deliveryManMessage(context, orderId)
          Behaviors.same

        case OrderPicked(orderId) =>
          orderPickedMessage(context, orderId)
          Behaviors.same

        case AtLocation(orderId) =>
          reachedMessage(context, orderId)
          Behaviors.same

        case OrderDelivered(orderId, delivery) =>
          delivery ! DeliveryConfirmation(orderId)
          Behaviors.same

        case OrderDeliverySuccess(orderId) =>
          deliverySuccessMessage(context, orderId)
          homePage_foodBrowser(Nil, authenticator, restaurant, bank, deliveryMan)

        case unknownCommand =>
          context.log.info("Unsupported command please try to sign up.")
          Behaviors.same

      }
    }
  }


  def checkout(orderedItems: List[String],
               authenticator: ActorRef[Authenticator.LoggingSpecs],
               restaurant: ActorRef[Restaurant.ResturantSpecs],
               bank: ActorRef[Bank.BankSpecs],
               deliveryMan: ActorRef[DeliverySpecs]): Behavior[TacoBallSpecs] = {

    Behaviors.setup[TacoBallSpecs]{ context =>

      Behaviors.receiveMessage[TacoBallSpecs]{

        case ProceedToPayment(cardNo, cvv) =>
          paymentRequestMessage(context)
          context.ask(bank, self => PaymentRequest(cardNo, cvv, self)){
            case Failure(exception)     => throw exception
            case Success(paymentStatus) =>
              paymentStatus
          }
          homePage_foodBrowser(orderedItems, authenticator, restaurant, bank, deliveryMan)

        case Cancel =>
          paymentCancelled(context)
          homePage_foodBrowser(orderedItems.tail, authenticator, restaurant, bank, deliveryMan)

        case otherCommand =>
          invalidCheckoutMessage(context)
          Behaviors.same
      }
    }
  }


  trait TacoBallSpecs

  case object Cancel extends TacoBallSpecs

  case class Login(username: String, password: String) extends TacoBallSpecs

  case class SignUp(username: String, password: String) extends TacoBallSpecs

  case class ConfirmOrder(food: Int) extends TacoBallSpecs

  case object DisplayMenu extends TacoBallSpecs

  case class ProceedToPayment(cardNo: String, cvv: String) extends TacoBallSpecs


  //Authenticator
  case object SignUpSuccess extends TacoBallSpecs

  case class SignUpFailure(cause: String) extends TacoBallSpecs

  case class LoginFailed(message: String) extends TacoBallSpecs

  case object LoginSuccess extends TacoBallSpecs

  //Bank
  case object PaymentSuccess extends TacoBallSpecs

  case class PaymentFailure(cause: String) extends TacoBallSpecs


  //Hotel
  case object OrderAccepted extends TacoBallSpecs

  case object OrderRejected extends TacoBallSpecs

  case class ReadForDelivery(orderId: String) extends TacoBallSpecs


  //Delivery
  case class DeliveryAccepted(orderId: String) extends TacoBallSpecs

  case class OrderPicked(orderId: String) extends TacoBallSpecs

  case class AtLocation(orderId: String) extends TacoBallSpecs

  case class OrderDelivered(orderId: String, delivery: ActorRef[DeliverySpecs]) extends TacoBallSpecs

  case class OrderDeliverySuccess(orderId: String) extends TacoBallSpecs

  private def displayMenuContents(context: ActorContext[TacoBallSpecs], menuItems: Map[String, String]): Unit = {
    context.log.info(s"${Console.BLUE}                        MENU ITEMS                                  " + Console.WHITE)
    menuItems.values.foreach(a => context.log.info(Console.BLUE + a + Console.WHITE))
  }

  private def orderedItemsDashboard(context: ActorContext[TacoBallSpecs], orderedItems: List[String]) = {
    if (orderedItems.isEmpty) {
      context.log.info(Console.MAGENTA + "               Your Order List is Empty. Please Order." + Console.WHITE)
      context.log.info("")
    } else {
      context.log.info(Console.MAGENTA + "                        Your   Order   List  " + Console.WHITE)
      orderedItems.foreach(a => context.log.info(Console.MAGENTA + s"                         Order Id : $a" + Console.WHITE))
      context.log.info("")
    }
  }

  private def getMenuItems: TreeMap[String, String] = {
    TreeMap("1" -> s"    [Food-1] : Idli         ||  [Price] : ₹30         || [MadeBy] : IDC",
      "2" -> s"    [Food-2] : Dose         ||  [Price] : ₹50         || [MadeBy] : IDC",
      "3" -> s"    [Food-3] : AppleJuice   ||  [Price] : ₹5000       || [MadeBy] : Apple",
      "4" -> s"    [Food-4] : Burger       ||  [Price] : ₹300        || [MadeBy] : BurgerQueen",
      "5" -> s"    [Food-5] : Pizza        ||  [Price] : ₹300        || [MadeBy] : PizzaHat",
      "6" -> s"    [Food-6] : Samosa       ||  [Price] : ₹20         || [MadeBy] : IDC",
      "7" -> "",
      "8" -> s"                    Please Confirm Your Order \uD83D\uDE0B ",
      "9" -> ""
    )
  }

  private def nextProcedure(context: ActorContext[TacoBallSpecs], message: TacoBallSpecs): Cancellable = context.scheduleOnce(3 seconds, context.self, message)

  val commonSpace = "                      "

  private def welcomeMessage(context: ActorContext[TacoBallSpecs]): Unit = {
    context.log.info(Console.BOLD + Console.BLACK + commonSpace + Console.RED_B + "LOGIN / SIGNUP" + Console.BOLD + Console.BLACK_B + Console.WHITE)
    context.log.info("")
  }

  private def loginMessage(context: ActorContext[TacoBallSpecs]): Unit = {
    context.log.info(Console.YELLOW + "                  " + "Logging In. Please Wait..." + Console.WHITE)
    context.log.info("")
  }

  private def loginSuccessMessage(context: ActorContext[TacoBallSpecs]): Unit = {
    context.log.info(Console.GREEN + "        Login successful!!! Loading Menu, Please Wait..." + Console.WHITE)
    context.log.info("")
  }


  private def loginFailureMessage(context: ActorContext[TacoBallSpecs], message: String): Unit = {
    context.log.info(Console.RED + s"            Login Failed : $message" + Console.WHITE)
    context.log.info("")
  }

  private def proceedToPaymentMessage(context: ActorContext[TacoBallSpecs]) = {
    context.log.info(Console.CYAN + s"                    Please proceed to payment ${Console.GREEN}" + "$$$" + Console.WHITE)
    context.log.info("")
  }

  private def paymentRequestMessage(context: ActorContext[TacoBallSpecs]) = {
    context.log.info("               Payment request received, Please Wait...")
    context.log.info("")
  }

  private def paymentSuccesstMessage(context: ActorContext[TacoBallSpecs]) = {
    context.log.info(Console.GREEN + s"       Payment Successful, Waiting for Resturant to accepted the order" + Console.WHITE)
    context.log.info("")
  }


  private def paymentFailureMessage(context: ActorContext[TacoBallSpecs], cause: String) = {
    context.log.info(Console.RED + s"                Payment Failed : $cause" + Console.WHITE)
    context.log.info("")
  }

  private def acceptMessage(context: ActorContext[TacoBallSpecs]) = {
    context.log.info(Console.YELLOW + "                Hurrah !!! Your order has been accepted." + Console.WHITE)
    context.log.info("")
  }

  private def readyMessage(context: ActorContext[TacoBallSpecs], orderId: String) = {
    context.log.info(Console.BLUE +s"                Yay! Your order($orderId) is ready for pickup." + Console.WHITE)
    context.log.info("")
  }

  private def deliveryManMessage(context: ActorContext[TacoBallSpecs], orderId: String) = {
    context.log.info(Console.MAGENTA + s"     Your order(${orderId}) is assigned to a deliveryman. He is going towards hotel" + Console.WHITE)
    context.log.info("")
  }

  private def orderPickedMessage(context: ActorContext[TacoBallSpecs], orderId: String) = {
    context.log.info(Console.CYAN + s"           Your order ($orderId) has been picked by the delivery man" + Console.WHITE)
    context.log.info("")
  }

  private def reachedMessage(context: ActorContext[TacoBallSpecs], orderId: String) = {
    context.log.info(Console.RED + "        Delivery man has reached your location. Please collect your order." + Console.WHITE)
    context.log.info("")
  }

  private def deliverySuccessMessage(context: ActorContext[TacoBallSpecs], orderId: String) = {
    context.log.info(Console.GREEN + "                      Order Completion Successful" + Console.WHITE)
    context.log.info("")
  }

  private def rejectMessage(context: ActorContext[TacoBallSpecs]) = {
    context.log.info(Console.RED + "          Sorry \uD83D\uDE42 Your order has been declined by the restaurant." + Console.WHITE)
    context.log.info("")
  }

  private def signingInMessage(context: ActorContext[TacoBallSpecs]) = {
    context.log.info(Console.BLUE + "                 Signing In. Please Wait..." + Console.WHITE)
    context.log.info("")
  }

  private def failureMessage(context: ActorContext[TacoBallSpecs], cause: String) = {
    context.log.info(Console.RED + s"     Sign Up Failed :  $cause" + Console.WHITE)
    context.log.info("")
  }

  private def successMessage(context: ActorContext[TacoBallSpecs]) = {
    context.log.info(Console.GREEN + "    Sign Up successful!!!  Loading Menu, Please Wait..." + Console.WHITE)
    context.log.info("")
  }

  private def invalidCheckoutMessage(context: ActorContext[TacoBallSpecs]) = {
    context.log.info(Console.YELLOW + "         Please Proceed To Payment Or Cancel the payment" + Console.WHITE)
    context.log.info("")
  }

  private def paymentCancelled(context: ActorContext[TacoBallSpecs]) = {
    context.log.info(Console.RED + "                     Payment Cancelled" + Console.WHITE)
    context.log.info("")
  }
}