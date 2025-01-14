package com.chethan.akka.http

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.IncomingConnection
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.Location
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, RunnableGraph, Sink, Source}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Chethan on Jun 10, 2024.
  */

object LowLevelApi extends App {


  private implicit val actorSystem  = ActorSystem("LoweLevelServerAPI")
  private implicit val materializer = ActorMaterializer()

  private val serverSource: Source[Http.IncomingConnection, Future[Http.ServerBinding]] = Http().bind("localhost", 8000)
  val connectionSink = Sink.foreach[IncomingConnection](connection => println(s"Accepted incoming connection from :${connection.remoteAddress}"))


//  private val httpServer: RunnableGraph[Future[Http.ServerBinding]] = serverSource.to(connectionSink)

  //  val serverFuture = httpServer.run()

  //  serverFuture.onComplete{
  //    case Failure(exception) => println(exception)
  //    case Success(binding)   => println(binding)
  //      binding.terminate(2 seconds)
  //  }


  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(HttpMethods.GET, _, _, _, _) => HttpResponse(StatusCodes.OK,
      entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
        """<html>
          |<body>
          |Your request has been successfully processed. Thank you!
          |</body>
          |</html>""".stripMargin))

    case request =>
      request.discardEntityBytes()
      HttpResponse(StatusCodes.NotFound,
        entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
          """<html>
            |<body>
            |Your request failed. No Thank you!
            |</body>
            |</html>""".stripMargin))
  }


  //    val responsiveConnectionSink = Sink.foreach[IncomingConnection](_.handleWithSyncHandler(requestHandler))
  //
  //     val responsiveServer = serverSource.to(responsiveConnectionSink)
  //
  //
  //    responsiveServer.run().map(println)


  //  Http().bind("localhost",8080).runWith(responsiveConnectionSink)


  val aSyncRequestHandler: HttpRequest => Future[HttpResponse] = {
    case HttpRequest(HttpMethods.GET, _, _, _, _) =>
      Future{
        HttpResponse(StatusCodes.OK,
          entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
            """<html>
              |<body>
              |Your request has been successfully processed ASync. Thank you!
              |</body>
              |</html>""".stripMargin))
      }

    case request =>
      Future{
        request.discardEntityBytes()
        HttpResponse(StatusCodes.NotFound,
          entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
            """<html>
              |<body>
              |Your request failed ASync. No Thank you!
              |</body>
              |</html>""".stripMargin))
      }
  }
  //  val responsiveConnectionSink                                 = Sink.foreach[IncomingConnection](_.handleWithAsyncHandler(aSyncRequestHandler))

  //  serverSource.to(responsiveConnectionSink).run().map(println)


  val aSyncRequestHandlerWithURI: HttpRequest => Future[HttpResponse] = {
    case HttpRequest(HttpMethods.GET, Uri.Path("/custom"), _, _, _) =>
      Future{
        HttpResponse(StatusCodes.OK,
          entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
            """<html>
              |<body>
              |Your request has been successfully processed Custom. Thank you!
              |</body>
              |</html>""".stripMargin))
      }

    case request =>
      Future{
        request.discardEntityBytes()
        HttpResponse(StatusCodes.NotFound,
          entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
            """<html>
              |<body>
              |Your request failed Custom. No Thank you!
              |</body>
              |</html>""".stripMargin))
      }
  }

  //  val responsiveConnectionSink                                 = Sink.foreach[IncomingConnection](_.handleWithAsyncHandler(aSyncRequestHandlerWithURI))
  //  Http().bind("localhost",8000).runWith(responsiveConnectionSink)


  val streamBasedRequestHandler: Flow[HttpRequest, HttpResponse, _] = Flow[HttpRequest].map{
    case HttpRequest(HttpMethods.GET, Uri.Path("/custom"), _, _, _) =>
      HttpResponse(StatusCodes.OK,
        entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
          """<html>
            |<body>
            |Your request has been successfully processed Custom. Thank you!
            |</body>
            |</html>""".stripMargin))


    case request =>
      request.discardEntityBytes()
      HttpResponse(StatusCodes.NotFound,
        entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
          """<html>
            |<body>
            |Your request failed Custom. No Thank you!
            |</body>
            |</html>""".stripMargin))

  }

  //  Http().bind("localhost",8888).runForeach(_.handleWith(streamBasedRequestHandler))
  //  Http().bindAndHandle(streamBasedRequestHandler, "localhost", 8888)


  def exerciseSyncResponse: HttpRequest => HttpResponse = {
    case HttpRequest(HttpMethods.GET, Uri.Path("/about"), _, _, _) => HttpResponse(StatusCodes.OK, entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
      """
        |Welcome to about section!!!
        |""".stripMargin))

    case HttpRequest(HttpMethods.GET, _, _, _, _) => HttpResponse(StatusCodes.OK, entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
      """
        |Hello, Welcome to custom server.
        |""".stripMargin))

    case HttpRequest(_, _, _, _, _) => HttpResponse(StatusCodes.NotFound, entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
      """
        |Wrong address, No content found !!!
        |""".stripMargin))
  }


  def exerciseASyncResponse: HttpRequest => Future[HttpResponse] = {
    case HttpRequest(HttpMethods.GET, Uri.Path("/about"), _, _, _) =>
      Future{
        HttpResponse(StatusCodes.OK, entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
          """
            |Welcome to about section!!!
            |""".stripMargin))
      }

    case HttpRequest(HttpMethods.GET, _, _, _, _) =>
      Future{
        HttpResponse(StatusCodes.OK, entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
          """
            |Hello, Welcome to custom server.
            |""".stripMargin))
      }

    case HttpRequest(_, _, _, _, _) =>
      Future{
        HttpResponse(StatusCodes.NotFound, entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
          """
            |Wrong address, No content found !!!
            |""".stripMargin))
      }
  }

  def exerciseStreamResponse: Flow[HttpRequest, HttpResponse, _] = Flow[HttpRequest].map{
    case HttpRequest(HttpMethods.GET, Uri.Path("/about"), _, _, _) =>

      HttpResponse(StatusCodes.OK, entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
        """
          |Welcome to about section!!!
          |""".stripMargin))
    case HttpRequest(HttpMethods.GET, Uri.Path("/search"), _, _, _) =>

      HttpResponse(StatusCodes.Found,headers = List(Location("http://google.com")))


    case HttpRequest(HttpMethods.GET, _, _, _, _) =>
      HttpResponse(StatusCodes.OK, entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
        """
          |Hello, Welcome to custom server.
          |""".stripMargin))


    case HttpRequest(_, _, _, _, _) =>
      HttpResponse(StatusCodes.NotFound, entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
        """
          |Wrong address, No content found !!!
          |""".stripMargin))
  }


  //  Http().bindAndHandleSync(exerciseResponse,"localhost",8080)

//  val exerciseServerSource = Http().bind("localhost", 8388)

  //  val exersiceSyncSink = Sink.foreach[IncomingConnection](_.handleWithSyncHandler(exerciseSyncResponse))
  //  val exerciseGraph = exerciseServerSource.to(exersiceSyncSink)
  //  exerciseGraph.run().map(println)

//  val exersiceASyncSink = Sink.foreach[IncomingConnection](_.handleWithAsyncHandler(exerciseASyncResponse))
//  val exerciseGraph     = exerciseServerSource.to(exersiceASyncSink)
//  exerciseGraph.run().map(println)

  val bindingFuture = Http().bindAndHandle(exerciseStreamResponse,"localhost",8388)

  bindingFuture.flatMap(_.unbind()).onComplete(_ => actorSystem.terminate())
  
}
