package controllers

import java.time.LocalTime
import javax.inject._

import models.ServiceCall
import org.joda.time.DateTime
import play.api._
import play.api.mvc._
import services.{InUseService, InUseMemoryService}

class APIController  extends Controller {

  val backend: InUseService = new InUseMemoryService()


  // PUT request
  def addService(service: String) = Action {

    backend.registerService(service)

    Ok("success")

  }

  // POST request
  def addRecord(service: String) = Action {

    backend.registerCall(
      service,
      ServiceCall(service, new DateTime(), "stub")
    )

    Ok("success")

  }


}
