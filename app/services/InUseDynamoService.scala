package services
import javax.inject.Singleton

import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException
import models.{Service, ServiceCall}
import org.joda.time.DateTime

import scala.collection.JavaConversions._

@Singleton
class InUseDynamoService extends InUseService {

  val DEFAULT_LIMIT = DateTime.now.getMillis - (86400000)

  override def registerService(service: String): Unit = {
    Dynamo.services.putItem(Service(service).toItem)
  }

  override def registerCall(name: String, record: ServiceCall): Unit = {

    doesServiceExist(name) match {
      case true => Dynamo.serviceCalls.putItem(record.toItem)
      case false => throw new ResourceNotFoundException(s"Unable to locate $name in services")
    }

  }

  def doesServiceExist(name: String): Boolean = {
    getServices().map(item => item.service).contains(name)
  }

  def getServices(): List[Service] = {
    Dynamo.services.scan().map(Service.fromItem).toList
  }

  def getRecentServiceCalls(service: String, since: Long = DEFAULT_LIMIT): List[ServiceCall] = {
    val spec = new QuerySpec()
      .withKeyConditionExpression("service = :v_service and createdAt > :v_since")
      .withValueMap(new ValueMap()
        .withString(":v_service", service)
        .withNumber(":v_since", since))
    Dynamo.serviceCalls.query(spec).map(ServiceCall.fromItem).toList
  }

  override def getRecentServiceCallsMap(): Map[String, List[ServiceCall]] = {

    getServices().map(service => {
      service.service -> getRecentServiceCalls(service.service).filter(call => call.service == service.service )
    }).toMap

  }

}
