package controllers

import org.joda.time.format.DateTimeFormat
import play.api.mvc._
import services.InUseService
import javax.inject.Inject

class IndexController @Inject() (backend: InUseService) extends Controller {

  def index = Action {

    Ok(views.html.main(backend.getRecentServiceCallsMap()))

  }

  def service(service: String) = Action {

    def dateFormatter(ms: Long): String = {
      val df = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss")
      df.print(ms)
    }

    backend.getRecentServiceCallsMap().get(service) match {
      case Some(calls) => Ok(views.html.service(service, calls, dateFormatter))
      case None => Ok(views.html.service(service, Seq(), dateFormatter))
    }

  }


}
