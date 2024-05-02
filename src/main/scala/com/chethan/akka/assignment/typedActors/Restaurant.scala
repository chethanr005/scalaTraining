package com.chethan.akka.assignment.typedActors

import java.util.UUID

import akka.actor.Cancellable
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import com.chethan.akka.assignment.typedActors.Delivery.{DeliveryRequest, DeliverySpecs, OrderDispatched}
import com.chethan.akka.assignment.typedActors.Zomato.{OrderAccepted, OrderRejected, ReadForDelivery, TacoBallSpecs}

import scala.concurrent.duration._
import scala.util.Random

/**
  * Created by $Chethan on Jan 22, 2024.
  */

object Restaurant {

  def apply(originalRequester: ActorRef[TacoBallSpecs]): Behavior[ResturantSpecs] = hotelManagement(originalRequester, Nil, Map.empty)

  private def hotelManagement(originalRequester: ActorRef[TacoBallSpecs], orderList: List[OrderRequest], deliveryCounter: Map[String, String]): Behavior[ResturantSpecs] = {
    Behaviors.setup[ResturantSpecs]{ context =>

      def kitchenManager: Cancellable = context.scheduleOnce(2 seconds, context.self, CheckForPendingOrderAndProceed)

      Behaviors.receiveMessage[ResturantSpecs]{

        case OrderRequest(orderId, requester: ActorRef[TacoBallSpecs]) =>
          val acceptOrder = orderVerifier
          if (acceptOrder) {
            requester ! OrderAccepted
            if (orderList.isEmpty) context.self ! CheckForPendingOrderAndProceed
            hotelManagement(originalRequester, orderList :+ OrderRequest(orderId, requester), deliveryCounter)
          }
          else {
            requester ! OrderRejected
            Behaviors.same
          }


        case CheckForPendingOrderAndProceed =>
          if (orderList.nonEmpty) {
            val orderRequest = orderList.head
            nextProcedure(context, DoneCooking(orderRequest))
          }
          Behaviors.same

        case DoneCooking(orderRequest) =>
          println(orderList)
          originalRequester ! ReadForDelivery(orderRequest.orderId)
          kitchenManager
          hotelManagement(originalRequester, orderList.tail, deliveryCounter + (orderRequest.orderId -> UUID.randomUUID().toString))

        case OrderHandOver(orderId, transporter) =>
          //          val food = deliveryCounter(orderId)
          transporter ! OrderDispatched(DeliveryRequest(orderId, context.self, "food"))
          hotelManagement(originalRequester, orderList, deliveryCounter - orderId)


        case any =>
          println("i came to any")
          Behaviors.same


      }
    }
  }


  trait ResturantSpecs

  case class OrderRequest(orderId: String, requester: ActorRef[TacoBallSpecs]) extends ResturantSpecs

  private case object CheckForPendingOrderAndProceed extends ResturantSpecs

  private case class DoneCooking(orderRequest: OrderRequest) extends ResturantSpecs

  case class OrderHandOver(orderId: String, transporter: ActorRef[DeliverySpecs]) extends ResturantSpecs

  private def orderVerifier: Boolean = !(Random.nextInt(10) % 5 == 0)

  private def nextProcedure(context: ActorContext[ResturantSpecs], message: ResturantSpecs): Unit = {

    context.system.scheduler.scheduleOnce(3 second, () => context.self ! message)(context.executionContext)

  }

}