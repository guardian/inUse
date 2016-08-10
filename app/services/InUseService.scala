package services

import models.ServiceCall

trait InUseService {

  def registerService(key: String)
  def registerCall(key: String, record: ServiceCall)
  def getRecentServiceCallsMap(): Map[String, List[ServiceCall]]

}
