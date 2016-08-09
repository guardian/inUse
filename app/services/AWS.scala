package services

import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document._


object AWS {

  lazy val region = Option(Regions.getCurrentRegion()).getOrElse(Region.getRegion(Regions.EU_WEST_1))
  lazy val dynamoClient = AWS.region.createClient(classOf[AmazonDynamoDBClient], null, null)

}

object Dynamo {

  lazy val dynamoDb = new DynamoDB(AWS.dynamoClient)
  lazy val services = dynamoDb.getTable("services") //TODO: Pass out to config
  lazy val serviceCalls = dynamoDb.getTable("serviceRecords")  //TODO: Pass out to config

}
