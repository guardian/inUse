package controllers

import play.api.mvc.{Action, Controller}
import services.Dynamo

class Management extends Controller {
  def healthCheck = Action {
    Ok("OK")
  }

  def deleteAllHistory = Action {
    Dynamo.deleteAllServicesAndCalls
    Ok("Ok")
  }

}
