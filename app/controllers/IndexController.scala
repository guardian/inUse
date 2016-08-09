package controllers

import play.api.mvc._
import services.InUseMemoryService

class IndexController  extends Controller {

  def index = Action {

    val backend = InUseMemoryService

    Ok(views.html.main(backend.getCalls()))

  }

  def service(service: String) = Action {

    val backend = InUseMemoryService

    backend.getCalls().get(service) match {
      case Some(calls) => Ok(views.html.service(service, calls))
      case None => Ok(views.html.service(service, Seq()))
    }

  }


}
