package com.chethan.akka.classicActor.askPattern

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.pattern.{ask, pipe}
import akka.testkit.{ImplicitSender, TestKit}
import akka.util
import com.chethan.akka.classicActor.askPattern.AskSpec._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.Failure

/**
  * Created by $Chethan on Jan 10, 2024.
  */

class AskSpec extends TestKit(ActorSystem("AskSpec")) with ImplicitSender with AnyWordSpecLike with BeforeAndAfterAll {

  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)


  "AskSpec" should {
    authenticateTestSuite(Props[AuthManager])
  }

  "piped auth manager" should {
    authenticateTestSuite(Props[PipedAuthManager])
  }

  def authenticateTestSuite(props: Props) = {
    "failed to authenticate non authenticate user4" in {
      val authManager = system.actorOf(props)
      authManager ! Authenticate("chethan", "boss005")
      expectMsg(AuthFailure("Username not found"))
    }

    "should reply with authentication success for success full authentication" in {
      val authManager = system.actorOf(props)
      authManager ! RegisterUser("chethan", "boss005")
      authManager ! Authenticate("chethan", "boss005")
      expectMsg(AuthSuccess)
    }

    "should reply with auth failure for incorrect paassword" in {
      val authManager = system.actorOf(props)
      authManager ! RegisterUser("chethan", "boss005")
      authManager ! Authenticate("chethan", "boss0015")
      expectMsg(AuthFailure("Incorrect Password"))
    }
  }

  "failed to authenticate non authenticate user4" in {
    val authManager = system.actorOf(Props[PipedAuthManager])
    authManager ! Authenticate("chethan", "boss005")
    expectMsg(AuthFailure("Username not found"))
  }
}

object AskSpec {
  case class AddData(userName: String, password: String)

  case class GetData(userName: String)

  case object AuthenticationSuccess

  case object AuthenticationFailure


  class DataFetcher extends Actor with ActorLogging {
    override def receive: Receive = online(Map.empty)


    def online(database: Map[String, String]): Receive = {
      case AddData(userName, password) =>
        log.info(s"Adding the Credentials to the database userName : $userName")
        context.become(online(database.updated(userName, password)))
      case GetData(userName) =>

        throw new Exception()
        log.info(s"Trying to read the password for the userName : $userName")
        println(self.path + " -=-==-=-= " + sender().path)
        sender() ! database.get(userName)
    }
  }


  case class RegisterUser(userName: String, password: String)

  case class Authenticate(userName: String, password: String)

  case class AuthFailure(message: String)

  case object AuthSuccess

  class AuthManager extends Actor with ActorLogging {

    protected val authDb                             = context.actorOf(Props[DataFetcher], "dataFetcher")
    implicit  val executionContext: ExecutionContext = context.dispatcher
    implicit  val timeout                            = util.Timeout(1 second)

    override def receive: Receive = {
      case RegisterUser(userName, password) => authDb ! AddData(userName, password)
      case Authenticate(userName, password) => authenticate(userName, password)

    }

    def authenticate(userName: String, password: String): Unit = {
      val originalSender = sender()
      val future         = authDb ? GetData(userName)

      future.onComplete{
        case scala.util.Success(None)             => originalSender ! AuthFailure("Username not found")
        case scala.util.Success(Some(dbPassword)) =>
          if (dbPassword == password) originalSender ! AuthSuccess
          else originalSender ! AuthFailure("Incorrect Password")
        case Failure(_)                           => sender ! AuthFailure("Error 404: Everything went wrong")
      }
    }
  }


  class PipedAuthManager extends AuthManager {
    override def authenticate(userName: String, password: String): Unit = {
      val future         = authDb ? (GetData(userName))
      val futurePassword = future.mapTo[Option[String]]
      val status         = futurePassword.map{
        case None             => AuthFailure("Username not found")
        case Some(dbPassword) => if (dbPassword == password) AuthSuccess
                                 else AuthFailure("Incorrect Password")

      }
      futurePassword.pipeTo(sender())
      status.pipeTo(sender())
      status.pipeTo(self)
    }
  }
}