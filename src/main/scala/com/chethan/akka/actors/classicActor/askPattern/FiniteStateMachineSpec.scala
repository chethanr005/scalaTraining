package com.chethan.akka.actors.classicActor.askPattern

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Cancellable, FSM, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import FSMSpec._
import FiniteStateMachineSpec._
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, OneInstancePerTest}

import scala.concurrent.duration._

/**
  * Created by $Chethan on Jan 12, 2024.
  */

class FiniteStateMachineSpec extends TestKit(ActorSystem("FSMDemo")) with AnyWordSpecLike with ImplicitSender with BeforeAndAfterAll with BeforeAndAfterEach with OneInstancePerTest{
  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)


  override protected def afterEach(): Unit = Thread.sleep(1000)

  val inventory = Map("coke" -> 10, "bread" -> 15, "butter" -> 5, "chickenNuggets" -> 20)
  val prices    = Map("coke" -> 20, "bread" -> 35, "butter" -> 15, "chickenNuggets" -> 20)
  "FiniteStateMachineSpec" should {
    "throw initializer error if the product has been requested before initializing" in {
      val vendingMachine = system.actorOf(Props[VendingMachine])
      vendingMachine ! RequestProduct("coke")
      expectMsg(VendingError("Machine Not Initialized"))
    }

    "Request for the product that doesn't exist" in {
      val vendingMachine = system.actorOf(Props[VendingMachine])
      vendingMachine ! Initialize(inventory, prices)
      vendingMachine ! RequestProduct("pepsi")
      expectMsg(VendingError("Product Not Available"))
    }

    "request gets an instruction to insert money" in {
      val vendingMachine = system.actorOf(Props[VendingMachine])
      vendingMachine ! Initialize(inventory, prices)
      vendingMachine ! RequestProduct("coke")
      expectMsg(Instruction("Please Insert ₹20 rupees"))
    }

    "request should receive timeout if requester failed to insert money" in {
      val vendingMachine = system.actorOf(Props[VendingMachine])
      vendingMachine ! Initialize(inventory, prices)
      vendingMachine ! RequestProduct("coke")
      expectMsg(Instruction("Please Insert ₹20 rupees"))
      within(3.1 seconds){
        expectMsg(VendingError("Money Request Time out"))
      }
    }

    "insufficient funds" in {

      val vendingMachine = system.actorOf(Props[VendingMachine])
      val vendingProbe   = TestProbe()
      vendingProbe.send(vendingMachine, Initialize(inventory, prices))
      vendingProbe.send(vendingMachine, RequestProduct("coke"))
      vendingProbe.expectMsg(Instruction("Please Insert ₹20 rupees"))
      vendingProbe.send(vendingMachine, TakeMyMoney(15))
      vendingProbe.expectMsg(VendingError("Insufficient amount : Order Cancelled"))
      vendingProbe.expectMsg(Instruction("Please enter the remaining amount : ₹5"))
      vendingProbe.within(3.2 seconds)(VendingError("Money Request Time out"))
    }

    "insufficient funds 2" in {

      val vendingMachine = system.actorOf(Props[VendingMachine])
      val vendingProbe   = TestProbe()
      vendingProbe.send(vendingMachine, Initialize(inventory, prices))
      vendingProbe.send(vendingMachine, RequestProduct("coke"))
      vendingProbe.expectMsg(Instruction("Please Insert ₹20 rupees"))
      vendingProbe.send(vendingMachine, TakeMyMoney(15))
      vendingProbe.expectMsg(VendingError("Insufficient amount : Order Cancelled"))
      vendingProbe.expectMsg(Instruction("Please enter the remaining amount : ₹5"))
      vendingProbe.within(3.2 seconds)(VendingError("Money Request Time out"))
      vendingProbe.send(vendingMachine, TakeMyMoney(4))
      vendingProbe.expectMsg(VendingError("Insufficient amount : Order Cancelled"))
      vendingProbe.expectMsg(Instruction("Please enter the remaining amount : ₹1"))
      vendingProbe.send(vendingMachine, TakeMyMoney(5))
      vendingProbe.expectMsg(Deliver("coke"))
      vendingProbe.expectMsg(GiveBackChange(4))
    }

    "successful delivery" in {
      val vendingMachine = system.actorOf(Props[VendingMachine])
      val vendingProbe   = TestProbe()
      vendingProbe.send(vendingMachine, Initialize(inventory, prices))
      vendingProbe.send(vendingMachine, RequestProduct("coke"))
      vendingProbe.expectMsg(Instruction("Please Insert ₹20 rupees"))
      vendingProbe.send(vendingMachine, TakeMyMoney(25))
      vendingProbe.expectMsg(Deliver("coke"))
      vendingProbe.expectMsg(GiveBackChange(5))
    }
  }
  //
  //  def getListOfProducts(productList: List[String], amount: List[String]) = {
  //    "any" should {
  //      val vendingMachine = system.actorOf(Props[VendingMachine])
  //      val vendingProbe   = TestProbe()
  //      vendingProbe.send(vendingMachine, Initialize(inventory, prices))
  //
  //      vendingProbe.send(vendingMachine, RequestProduct("coke"))
  //      vendingProbe.expectMsg(Instruction("Please Insert ₹20 rupees"))
  //      vendingProbe.send(vendingMachine, TakeMyMoney(25))
  //      vendingProbe.expectMsg(Deliver("coke"))
  //      vendingProbe.expectMsg(GiveBackChange(5))
  //    }
  //  }


  "actual FSM " should {
    "throw initializer error if the product has been requested before initializing" in {
      val vendingMachine = system.actorOf(Props[VendingMachineFSM])
      vendingMachine ! RequestProduct("coke")
      expectMsg(VendingError("Machine Not Initialized"))
    }

    "Request for the product that doesn't exist" in {
      val vendingMachine = system.actorOf(Props[VendingMachineFSM])
      vendingMachine ! Initialize(inventory, prices)
      vendingMachine ! RequestProduct("pepsi")
      expectMsg(VendingError("Product Not Available"))
    }

    "request gets an instruction to insert money" in {
      val vendingMachine = system.actorOf(Props[VendingMachineFSM])
      vendingMachine ! Initialize(inventory, prices)
      vendingMachine ! RequestProduct("coke")
      expectMsg(Instruction("Please Insert ₹20 rupees"))
    }

    "request should receive timeout if requester failed to insert money" in {
      val vendingMachine = system.actorOf(Props[VendingMachineFSM])
      vendingMachine ! Initialize(inventory, prices)
      vendingMachine ! RequestProduct("coke")
      expectMsg(Instruction("Please Insert ₹20 rupees"))
      within(3.1 seconds){
        expectMsg(VendingError("Money Request Time out"))
      }
    }

    "insufficient funds" in {

      val vendingMachine = system.actorOf(Props[VendingMachineFSM])
      val vendingProbe   = TestProbe()
      vendingProbe.send(vendingMachine, Initialize(inventory, prices))
      vendingProbe.send(vendingMachine, RequestProduct("coke"))
      vendingProbe.expectMsg(Instruction("Please Insert ₹20 rupees"))
      vendingProbe.send(vendingMachine, TakeMyMoney(15))
      vendingProbe.expectMsg(VendingError("Insufficient amount"))
      vendingProbe.expectMsg(Instruction("Please enter the remaining amount : ₹5"))
      vendingProbe.expectMsg(5 seconds, VendingError("Money Request Time out"))
      vendingProbe.expectMsg(GiveBackChange(15))
    }

    "insufficient funds 2" in {

      val vendingMachine = system.actorOf(Props[VendingMachineFSM])
      val vendingProbe   = TestProbe()
      vendingProbe.send(vendingMachine, Initialize(inventory, prices))
      vendingProbe.send(vendingMachine, RequestProduct("coke"))
      vendingProbe.expectMsg(Instruction("Please Insert ₹20 rupees"))
      vendingProbe.send(vendingMachine, TakeMyMoney(15))
      vendingProbe.expectMsg(VendingError("Insufficient amount"))
      vendingProbe.expectMsg(Instruction("Please enter the remaining amount : ₹5"))
      vendingProbe.within(3.2 seconds)(VendingError("Money Request Time out"))
      vendingProbe.send(vendingMachine, TakeMyMoney(4))
      vendingProbe.expectMsg(VendingError("Insufficient amount"))
      vendingProbe.expectMsg(Instruction("Please enter the remaining amount : ₹1"))
      vendingProbe.send(vendingMachine, TakeMyMoney(5))
      vendingProbe.expectMsg(Deliver("coke"))
      vendingProbe.expectMsg(GiveBackChange(4))
    }

    "successful delivery" in {
      val vendingMachine = system.actorOf(Props[VendingMachineFSM])
      val vendingProbe   = TestProbe()
      vendingProbe.send(vendingMachine, Initialize(inventory, prices))
      vendingProbe.send(vendingMachine, RequestProduct("coke"))
      vendingProbe.expectMsg(Instruction("Please Insert ₹20 rupees"))
      vendingProbe.send(vendingMachine, TakeMyMoney(25))
      vendingProbe.expectMsg(Deliver("coke"))
      vendingProbe.expectMsg(GiveBackChange(5))
    }
  }
}

object FiniteStateMachineSpec {
  case class Initialize(inventory: Map[String, Int], prices: Map[String, Int])

  case class RequestProduct(product: String)

  case class Instruction(instruction: String)

  case class TakeMyMoney(amount: Int)

  case class Deliver(product: String)

  case class GiveBackChange(amount: Int)

  case class VendingError(reason: String)

  case object ReceiveMoneyTimeout

  class VendingMachine extends Actor with ActorLogging {
    override def receive: Receive = {
      case Initialize(inventory, prices) => context.become(operational(inventory, prices))
      case _                             => sender() ! VendingError("Machine Not Initialized")
    }


    def operational(inventory: Map[String, Int], prices: Map[String, Int]): Receive = {
      case RequestProduct(product) => val actualProduct = inventory.get(product)
        actualProduct match {
          case Some(count) => val productPrice = prices(product)
            sender() ! Instruction(s"Please Insert ₹${productPrice} rupees")
            context.become(waitForMoney(inventory, prices, product, count, productPrice, receiveMoneyTimeoutWindow, 0, sender()))
          case None        => sender() ! VendingError("Product Not Available")
        }
    }

    def waitForMoney(inventory: Map[String, Int],
                     prices: Map[String, Int],
                     product: String,
                     productCount: Int,
                     productAmount: Int,
                     moneyTimeout: Cancellable,
                     balance: Int,
                     requester: ActorRef): Receive = {

      case ReceiveMoneyTimeout => requester ! VendingError("Money Request Time out")
        context.become(operational(inventory, prices))

      case TakeMyMoney(money) =>
        moneyTimeout.cancel()
        val totalAmount = money + balance
        if (productAmount > totalAmount) {
          requester ! VendingError("Insufficient amount : Order Cancelled")
          requester ! Instruction(s"Please enter the remaining amount : ₹${productAmount - totalAmount}")
          context.become(waitForMoney(inventory, prices, product, productCount, productAmount, receiveMoneyTimeoutWindow, totalAmount, requester))
        }
        else {
          requester ! Deliver(product)
          val difference = totalAmount - productAmount
          if (difference > 0) requester ! GiveBackChange(difference)
          context.become(operational(inventory.updated(product, productCount - 1), prices))
        }

    }

    def receiveMoneyTimeoutWindow: Cancellable = context.system.scheduler.scheduleOnce(3 seconds){
      self ! ReceiveMoneyTimeout
    }(context.system.dispatcher)
  }
}


object FSMSpec {
  trait VendingState

  case object Idle extends VendingState

  case object Operational extends VendingState

  case object WaitForMoney extends VendingState


  trait VendingData

  case object UnInitialized extends VendingData

  case class Initialized(inventory: Map[String, Int], prices: Map[String, Int]) extends VendingData

  case class WaitForMoneyData(inventory: Map[String, Int],
                              prices: Map[String, Int],
                              product: String,
                              productCount: Int,
                              productAmount: Int,
                              balance: Int,
                              requester: ActorRef) extends VendingData


}


class VendingMachineFSM extends FSM[FSMSpec.VendingState, FSMSpec.VendingData] {

  startWith(Idle, UnInitialized)

  when(Idle){
    case Event(Initialize(inventory, prices), UnInitialized) =>
      goto(Operational) using (Initialized(inventory, prices))
    case _                                                   =>
      sender() ! VendingError("Machine Not Initialized")
      stay()
  }


  when(Operational){
    case Event(RequestProduct(product), Initialized(inventory, prices)) =>
      val actualProduct = inventory.get(product)
      actualProduct match {
        case None | Some(0)     => sender() ! VendingError("Product Not Available")
          stay()
        case Some(productCount) => val productPrice = prices(product)
          sender() ! Instruction(s"Please Insert ₹${productPrice} rupees")
          goto(WaitForMoney) using WaitForMoneyData(inventory, prices, product, productCount, productPrice, 0, sender())
      }
  }


  when(WaitForMoney, 3 seconds){
    case Event(StateTimeout, WaitForMoneyData(inventory, prices, product, productCount, productPrice, balance, requester)) =>
      println("timeout")
      requester ! VendingError("Money Request Time out")
      if (balance > 0) requester ! GiveBackChange(balance)
      goto(Operational) using Initialized(inventory, prices)

    case Event(TakeMyMoney(money), WaitForMoneyData(inventory, prices, product, productCount, productPrice, balance, requester)) =>
      val totalAmount = money + balance
      if (productPrice > totalAmount) {
        requester ! VendingError("Insufficient amount")
        requester ! Instruction(s"Please enter the remaining amount : ₹${productPrice - totalAmount}")
        stay() using WaitForMoneyData(inventory, prices, product, productCount, productPrice, totalAmount, requester)
      }
      else {
        requester ! Deliver(product)
        val difference = totalAmount - productPrice
        if (difference > 0) requester ! GiveBackChange(difference)
        goto(Operational) using Initialized(inventory.updated(product, productCount - 1), prices)
      }
  }


  whenUnhandled{
    case Event(_, _) => sender() ! VendingError("CommandNotFound")
      stay()
  }

  onTransition{
    case stateA -> stateB => log.info(s"Transitioning from $stateA to $stateB")
  }

  initialize()
}
