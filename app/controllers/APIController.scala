package controllers

import javax.inject._
import play.api._
import play.api.mvc._

class APIController  extends Controller {

  def index = Action {
    Ok(views.html.main("Your new application is ready."))
  }


}
