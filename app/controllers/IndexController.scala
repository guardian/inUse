package controllers

import play.api.mvc._
import services.InUseMemoryService

class IndexController  extends Controller {

  def index = Action {

    val backend = InUseMemoryService

    Ok(views.html.main(backend.getCalls()))

  }


}
