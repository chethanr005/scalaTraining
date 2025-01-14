package com.chethan.akka.actors.classicActor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * Created by $CapName on Oct 19, 2023.
  */

object VotingSystem extends App {

  case class Vote(candidate: String)

  case object VoteStatusRequest

  case class VoteStatusReply(candidate: Option[String])

  case class VoteAggregator(votes: Set[ActorRef])

  class Citizen extends Actor {

    override def receive: Receive = {
      case Vote(c)           => context.become(votedReceive(Some(c)))
      case VoteStatusRequest => sender() ! VoteStatusReply(None)
    }

    def votedReceive(candidate: Option[String]): Receive = {
      case Vote(a)           => println("Citizen already voted")
      case VoteStatusRequest => sender() ! VoteStatusReply(candidate)
    }
  }

  class VoteCalculator extends Actor {

    override def receive: Receive = {
      case VoteAggregator(votes) =>
        context.become(aggregatedReceive(votes , Map.empty))
        votes.foreach(candidate => candidate ! VoteStatusRequest)
      case _                     => println("Invalid request")
    }

    def aggregatedReceive(candidates: Set[ActorRef], voteCount: Map[String, Int]): Receive = {
      case VoteStatusReply(None)        => context.become(aggregatedReceive(candidates - sender(), voteCount))
      case VoteStatusReply(Some(candy)) =>
        val actualCount = voteCount.getOrElse(candy, 0)
        val updatedMap  = voteCount.updated(candy, actualCount + 1)
        if (candidates.tail.isEmpty) println(s"[VoteAggregator] => the votes count:- $updatedMap")
        else context.become(aggregatedReceive(candidates - sender(), updatedMap))
    }
  }

  val actorSystem = ActorSystem("votingSystem")

  val alice   = actorSystem.actorOf(Props[Citizen], "alice")
  val bob     = actorSystem.actorOf(Props[Citizen], "bob")
  val charlie = actorSystem.actorOf(Props[Citizen], "charlie")
  val daniel  = actorSystem.actorOf(Props[Citizen], "daniel")


    alice ! Vote("apple")
    bob ! Vote("Boss")
    charlie ! Vote("charlie")
    daniel ! Vote("Boss")
  val voteAggregator = actorSystem.actorOf(Props[VoteCalculator])
    voteAggregator ! VoteAggregator(Set(alice, bob, charlie, daniel))
//  voteAggregator ! VoteStatusReply


  actorSystem.terminate()
}

