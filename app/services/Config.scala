package services

import com.typesafe.config.ConfigFactory

object Config {

  val config = ConfigFactory.load()

  val servicesTableName = config.getString("services_table_name")
  val serviceCallsTableName = config.getString("service_calls_table_name")

}
