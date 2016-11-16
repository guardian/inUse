package controllers

import java.io.File

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

  def deleteSingleService(service: String) = Action {
    Dynamo.deleteSingleServiceAndAllCalls(service)
    Ok("Ok")
  }

  def generateCsvDownload() = Action {
    Dynamo.getServiceCallsCsv()
    Ok.sendFile(new File("export.csv"))
  }

  def generateCsvString() = Action {
    val content = Dynamo.getServiceCallsCsv()
    Ok(content)
  }

}
