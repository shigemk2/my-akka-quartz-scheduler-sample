package controllers

import javax.inject._
import akka.actor.{Props, ActorSystem}
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import play.api.inject.ApplicationLifecycle
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (
                                 actorSystem:      ActorSystem,
                                 lifecycle:        ApplicationLifecycle
                               ) extends Controller {
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    onStart()
    Ok(views.html.index("Your new application is ready."))
  }

  def onStart(): Unit = {
    val system = ActorSystem("SampleSystem")
    val scheduler = QuartzSchedulerExtension(actorSystem)
    val actor = system.actorOf(Props(classOf[SampleActor]))
    QuartzSchedulerExtension(system).schedule("Every1Second", actor, "1秒")
    QuartzSchedulerExtension(system).schedule("Every5Seconds", actor, "5秒")
  }
}
