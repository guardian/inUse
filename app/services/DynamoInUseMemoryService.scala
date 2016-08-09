package services
import models.{Service, ServiceCall}

object DynamoInUseMemoryService extends InUseService {

  override def registerService(service: String): Unit = Dynamo.services.putItem(Service(service).toItem)

  override def registerCall(service: String, record: ServiceCall): Unit = ???

}
