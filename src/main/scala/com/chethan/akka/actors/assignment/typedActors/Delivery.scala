package com.chethan.akka.actors.assignment.typedActors

import akka.actor.Cancellable
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import akka.util.Timeout
import Restaurant.{OrderHandOver, ResturantSpecs}
import Zomato._

import scala.concurrent.duration._
import scala.util._

/**
  * Created by Chethan on Jan 22, 2024.
  */

object Delivery {

  def apply(requester: ActorRef[TacoBallSpecs]): Behavior[DeliverySpecs] = deliveryManagement(requester, Nil)

  private def deliveryManagement(requester: ActorRef[TacoBallSpecs], deliveryList: List[DeliveryRequest]): Behavior[DeliverySpecs] = {
    Behaviors.setup[DeliverySpecs]{ context =>
      implicit val timeout = Timeout(3 second)

      def deliveryScheduler: Cancellable = context.scheduleOnce(2 seconds, context.self, CheckPendingDeliveriesAndProceed)

      Behaviors.receiveMessage[DeliverySpecs]{
        case deliverRequest: DeliveryRequest =>
          if (deliveryList.isEmpty) deliveryScheduler
          deliveryManagement(requester, deliveryList :+ deliverRequest)

        case CheckPendingDeliveriesAndProceed =>
          if (deliveryList.nonEmpty) {
            val deliveryRequest = deliveryList.head
            requester ! DeliveryAccepted(deliveryRequest.orderId)
            nextProcedure(context, ReachedHotel(deliveryRequest))
          }
          Behaviors.same

        case ReachedHotel(deliveryRequest) =>
          context.ask(deliveryRequest.restoRef, selfRef => OrderHandOver(deliveryRequest.orderId, selfRef)){
            case Failure(exception) => throw new Exception()
            case Success(value)     => Thread.sleep(3000); OrderDispatched(deliveryRequest)
          }
          Behaviors.same

        case OrderDispatched(deliveryRequest) =>
          requester ! OrderPicked(deliveryRequest.orderId)
          nextProcedure(context, ReachedLocation(deliveryRequest))
          Behaviors.same

        case ReachedLocation(deliveryRequest) =>
          requester ! AtLocation(deliveryRequest.orderId)
          context.ask(requester, selfRef => OrderDelivered(deliveryRequest.orderId, selfRef)){
            case Failure(exception) => throw new Exception()
            case Success(value)     => Thread.sleep(3000); value
          }
          Behaviors.same

        case DeliveryConfirmation(orderId) =>
          requester ! OrderDeliverySuccess(orderId)
          deliveryScheduler
          deliveryManagement(requester, deliveryList.tail)
      }
    }
  }


  trait DeliverySpecs

  case class DeliveryRequest(orderId: String, restoRef: ActorRef[ResturantSpecs], location: String) extends DeliverySpecs

  case class DeliveryConfirmation(orderId: String) extends DeliverySpecs

  private case object CheckPendingDeliveriesAndProceed extends DeliverySpecs

  private case class ReachedHotel(deliveryRequest: DeliveryRequest) extends DeliverySpecs

  private case class ReachedLocation(deliveryRequest: DeliveryRequest) extends DeliverySpecs

  case class OrderDispatched(deliveryRequest: DeliveryRequest) extends DeliverySpecs

  private def nextProcedure(context: ActorContext[DeliverySpecs], message: DeliverySpecs): Unit = context.scheduleOnce(5 seconds, context.self, message)
}
