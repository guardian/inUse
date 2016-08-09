package services
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException
import models.{Service, ServiceCall}
import org.joda.time.DateTime

import scala.collection.JavaConversions._

object DynamoInUseMemoryService extends InUseService {

  override def registerService(service: String): Unit = Dynamo.services.putItem(Service(service).toItem)

  override def registerCall(name: String, record: ServiceCall): Unit = {

    doesServiceExist(name) match {
      case true =>
        println(record.toItem)
        Dynamo.serviceCalls.putItem(record.toItem)
      case false => throw new ResourceNotFoundException(s"Unable to locate $name in services")
    }

  }

  def doesServiceExist(name: String): Boolean = {
    getServices().map(item => item.name).contains(name)
  }

  def getServices(): List[Service] = {
    Dynamo.services.scan().map(Service.fromItem).toList
  }

  def getRecentServiceCalls(service: String, since: Long = (DateTime.now.getMillis - 86400000)): List[ServiceCall] = {
    val spec = new QuerySpec()
      .withKeyConditionExpression("name = :v_name and createdAt > :v_since")
      .withValueMap(new ValueMap()
        .withString(":v_name", service)
        .withNumber(":v_since", since))
    Dynamo.serviceCalls.query(spec).map(ServiceCall.fromItem).toList
  }

}
