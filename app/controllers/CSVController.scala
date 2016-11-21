package controllers

import java.io.File

import play.api.mvc.{Action, Controller}
import services.{Config, Dynamo}


class CSVController extends Controller {

  def generateCsvDownload() = Action {
    Dynamo.getServiceCallsCsv()
    Ok.sendFile(new File(s"${Config.homeDirectory}/export.csv"))
  }

  def generateCsvString() = Action {
    val content = Dynamo.getServiceCallsCsv()
    Ok(content)
  }

}
