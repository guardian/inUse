package services

import com.google.inject.ImplementedBy
import models.ServiceCall

@ImplementedBy(classOf[InUseDynamoService])
trait InUseService {

  def registerService(key: String)
  def registerCall(key: String, record: ServiceCall)
  def getRecentServiceCallsMap(): Map[String, List[ServiceCall]]

}
