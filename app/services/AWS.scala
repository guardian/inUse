package services

import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document._
import models.{Service, ServiceCall}

import scala.collection.JavaConversions._


object AWS {

  lazy val region = Option(Regions.getCurrentRegion()).getOrElse(Region.getRegion(Regions.EU_WEST_1))
  lazy val dynamoClient = AWS.region.createClient(classOf[AmazonDynamoDBClient], null, null)

}

object Dynamo {

  lazy val dynamoDb = new DynamoDB(AWS.dynamoClient)
  lazy val services = dynamoDb.getTable(Config.servicesTableName)
  lazy val serviceCalls = dynamoDb.getTable(Config.serviceCallsTableName)

  def deleteService(service: String) = {
    Dynamo.services.deleteItem("service", service)
  }

  def deleteServiceCall(service: String, createdAt: Long) = {
    Dynamo.serviceCalls.deleteItem("service", service, "createdAt", createdAt)
  }

  def deleteAllServicesAndCalls = {
    Dynamo.serviceCalls.scan()
      .map(ServiceCall.fromItem)
      .foreach(e => deleteServiceCall(e.service, e.createdAt))

    Dynamo.services.scan()
      .map(Service.fromItem)
      .foreach(e => deleteService(e.service))
  }

}
