package controllers

import models.ServiceCall
import org.joda.time.DateTime
import play.api.mvc._
import services.{InUseDynamoService, InUseService}

class APIController extends Controller {

  val backend: InUseService = InUseDynamoService

  // PUT request
  def addService(service: String) = Action {

    backend.registerService(service)

    Ok

  }

  // POST request
  def addRecord(service: String) = Action { request =>

    backend.registerCall(
      service,
      ServiceCall(service, DateTime.now.getMillis, request.body.asText.getOrElse("") )
    )

    Ok

  }


}
