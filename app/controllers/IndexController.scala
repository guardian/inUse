package controllers

import org.joda.time.format.DateTimeFormat
import play.api.mvc._
import services.InUseMemoryService

class IndexController  extends Controller {

  def index = Action {

    val backend = InUseMemoryService

    Ok(views.html.main(backend.getCalls()))

  }

  def service(service: String) = Action {

    val backend = InUseMemoryService

    def dateFormatter(ms: Long): String = {
      val df = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss")
      df.print(ms)
    }

    backend.getCalls().get(service) match {
      case Some(calls) => Ok(views.html.service(service, calls, dateFormatter))
      case None => Ok(views.html.service(service, Seq(), dateFormatter))
    }

  }


}
