package controllers

import akka.actor.Actor

class SampleActor extends Actor {
  def receive = {
    case msg: String => println(msg)
  }
}
