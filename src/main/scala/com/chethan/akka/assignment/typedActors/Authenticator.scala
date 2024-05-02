package com.chethan.akka.assignment.typedActors

import java.sql.{DriverManager, Statement}

import akka.actor.Cancellable
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import com.chethan.akka.assignment.typedActors.Zomato.{LoginFailed, LoginSuccess, SignUpFailure, SignUpSuccess, TacoBallSpecs}

import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

/**
  * Created by $Chethan on Jan 22, 2024.
  */

object Authenticator {

  def apply(): Behaviors.Receive[LoggingSpecs] = {
    Behaviors.receive[LoggingSpecs]{ (context, message) =>
      message match {
        case LoginRequest(username, password, requester) =>
          val loginStatus = authenticateLogin(username, password)
          nextProcedure(context, requester, loginStatus)
          Behaviors.same


        case SignUpRequest(username, password, requester) =>
          val signUpStatus = signUpProcess(username, password)
          nextProcedure(context, requester, signUpStatus)
          Behaviors.same
      }
    }

  }

  private def authenticateLogin(username: String, password: String): TacoBallSpecs = {
    Database.get(username) match {
      case (`username`, `password`) => LoginSuccess
      case ("", "")                 => LoginFailed("Invalid Username. Try Signing In.")
      case (`username`, _)          => LoginFailed("Incorrect password.")
    }
  }

  private def signUpProcess(username: String, password: String): TacoBallSpecs = {
    Database.insert(username, password) match {
      case true  => SignUpSuccess
      case false => SignUpFailure("Duplicate Username. Please try logging in.")
    }
  }


  trait LoggingSpecs

  case class LoginRequest(username: String, password: String, requester: ActorRef[TacoBallSpecs]) extends LoggingSpecs

  case class SignUpRequest(username: String, password: String, requester: ActorRef[Zomato.TacoBallSpecs]) extends LoggingSpecs


  private def nextProcedure(context: ActorContext[LoggingSpecs],
                            requester: ActorRef[TacoBallSpecs],
                            message: TacoBallSpecs): Cancellable = context.scheduleOnce(3 seconds, requester, message)
}


private object Database {
  def get(username: String): (String, String) = {
    Try{
      val statement = connectToDatabase
      val resultSet = statement.executeQuery(s"""SELECT *  FROM public."Credentials" WHERE username = '$username'""")
      resultSet.next()
      (resultSet.getString(2), resultSet.getString(1))
    } match {
      case Failure(exception) => ("", "")
      case Success(value)     => value
    }
  }

  def insert(username: String, password: String): Boolean = {
    Try{
      val statement = connectToDatabase
      statement.executeUpdate(s"""INSERT INTO public."Credentials" (username,password) VALUES ('$username', '$password')""")
      true
    } match {
      case Failure(exception) => false
      case Success(value)     => true
    }
  }

  private def connectToDatabase: Statement = {
    val url = "jdbc:postgresql://localhost:5432/training"
    Class.forName("org.postgresql.Driver")
    val connection = DriverManager.getConnection(url, "postgres", "admin")
    connection.createStatement()
  }
}
