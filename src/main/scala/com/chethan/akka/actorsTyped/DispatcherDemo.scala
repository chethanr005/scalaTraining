package com.chethan.akka.actorsTyped

import akka.actor.typed.scaladsl.Behaviors

/**
  * Created by Chethan on Jan 30, 2024.
  */

object DispatcherDemo {

  Behaviors.setup[Unit]{
    context =>

      Behaviors.same
    //      val child = context.spawn()
  }

}
