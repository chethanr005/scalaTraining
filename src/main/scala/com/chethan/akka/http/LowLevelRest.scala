package com.chethan.akka.http

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * Created by Chethan on Jun 14, 2024.
  */

object LowLevelRest extends App with GuitarStoreJsonProtocol {


  val guitar1 = Guitar("yamaha", "loofy")

  val jsonGuitar = guitar1.toJson.sortedPrint
  println(jsonGuitar)

  val extractedGuitar = jsonGuitar.parseJson.convertTo[Guitar]
  println(extractedGuitar)


  implicit val actorSystem  = ActorSystem("LowLevelRest")
  implicit val materializer = ActorMaterializer()
  val guitarStore = actorSystem.actorOf(Props[GuitarDB], "GuitarStore")

  val guitarList = List(guitar1, Guitar("Marshello", "torous"), Guitar("Sonos", "BeamBox"))

  guitarList.foreach{ guitar => guitarStore ! CreateGuitar(guitar) }

  implicit val timeout = Timeout(2 seconds)

//  val response: HttpRequest => Future[HttpResponse] = {
//
//    case HttpRequest(HttpMethods.POST, Uri.Path("/guitars"), _, entity, _) =>
//
//
////      HttpResponse()
//
//
//
//    case HttpRequest(HttpMethods.GET, Uri.Path("/guitars"), _, _, _) =>
//      val futureGuitarList = (guitarStore ? FindAllGuitars).mapTo[List[Guitar]]
//      futureGuitarList.map(guitarList => HttpResponse(StatusCodes.OK, entity = HttpEntity(ContentTypes.`application/json`, guitarList.toJson.prettyPrint)))
//
//    case HttpRequest(HttpMethods.GET, _, _, _, _) =>
//      Future(HttpResponse(StatusCodes.OK, entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, "Hello Dear user, Please navigate to your favourite page")))
//
//
//    case request =>
//      request.discardEntityBytes()
//      Future(HttpResponse(StatusCodes.NotFound))
//  }
//
//  Http().bindAndHandleAsync(response, "localhost", 8899)

}

trait GuitarStoreJsonProtocol extends DefaultJsonProtocol {
  implicit val guitarFormat: RootJsonFormat[Guitar] = jsonFormat2(Guitar)
}

case class Guitar(make: String, model: String)

case class CreateGuitar(guitar: Guitar)

case class GuitarCreated(gid: Int)

case class FindGuitar(gid: Int)

case object FindAllGuitars

class GuitarDB extends Actor with ActorLogging {

  var guitarList: Map[Int, Guitar] = Map.empty
  var currentGid                   = 0


  override def receive: Receive = {
    case FindAllGuitars       =>
      log.info("Finding all guitars")
      sender() ! guitarList.values.toList
    case FindGuitar(gid)      =>
      log.info(s"Finding the guitar by id:$gid")
      sender() ! guitarList(gid)
    case CreateGuitar(guitar) =>
      log.info(s"Creating a guitar with id:$currentGid")
      guitarList = guitarList.updated(currentGid, guitar)
      sender() ! GuitarCreated(currentGid)
      currentGid += 1
  }
}
