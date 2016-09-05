package services

import javax.inject.Singleton

import models.ServiceCall
import org.joda.time.DateTime

@Singleton
class InUseMemoryService extends InUseService {

  var calls: Map[String, List[ServiceCall]] = Map()

  override def registerService(service: String): Unit = {

    // append service into map with no records

    calls = calls + (service -> List())

  }

  override def registerCall(service: String, record: ServiceCall): Unit = {

    // append record into map(service)

    calls.get(service) match {

      case Some(records) =>  {
        val newRecords: List[ServiceCall] = records :+ record
        calls = calls + (service -> newRecords)
      }

      case None => {
        // no service, do nothing
      }

    }

  }


  def getRecentServiceCallsMap(): Map[String, List[ServiceCall]] = calls

  // stub data

  val jsonDataOne = """{"category" : "Test Cat One",
      |                 "user" : "Test User One",
      |                 "target" : "Test Target One",
      |                 "datetime" : "Test Date One",
      |                 "function" : "Test Function One",
      |                 "pkg" : "Test pkg One",
      |                 "description" : "Description one in here",
      |                 "asset" : "Test Asset One",
      |                 "ip" : "xxx.xxx.xxx.xxx"}""".stripMargin

  val jsonDataTwo = """{"category" : "Test Cat Two",
                      | "user" : "Test User Two",
                      | "target" : "Test Target Two",
                      | "datetime" : "Test Date Two",
                      | "function" : "Test Function Two",
                      | "pkg" : "Test pkg Two",
                      | "description" : "Description two in here",
                      | "asset" : "Test Asset Two",
                      | "ip" : "xxx.xxx.xxx.xxx"}""".stripMargin

  val jsonDataThree = """{"category" : "Test Cat Three",
                      | "user" : "Test User Three",
                      | "target" : "Test Target Three",
                      | "datetime" : "Test Date Three",
                      | "function" : "Test Function Three",
                      | "pkg" : "Test pkg Three",
                      | "description" : "Description three in here",
                      | "asset" : "Test Asset Three",
                      | "ip" : "xxx.xxx.xxx.xxx"}""".stripMargin

  registerService("example service")
  registerService("empty service")
  registerService("another service")
  registerCall("example service", ServiceCall("example service", DateTime.now.getMillis, jsonDataOne))
  registerCall("example service", ServiceCall("example service", DateTime.now.getMillis, jsonDataTwo))
  registerCall("example service", ServiceCall("example service", DateTime.now.getMillis, jsonDataThree))

}
