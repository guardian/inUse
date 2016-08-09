package services

import models.ServiceCall

class InUseMemoryService() extends InUseService {

  var calls: Map[String, Seq[ServiceCall]] = Map()

  override def registerService(service: String): Unit = {

    // append service into map with no records

    calls = calls + (service -> Seq())

  }

  override def registerCall(service: String, record: ServiceCall): Unit = {

    // append record into map(service)

    calls.get(service) match {

      case Some(records) =>  {
        val newRecords: Seq[ServiceCall] = records :+ record
        calls = calls + (service -> newRecords)
      }

      case None => {
        // no service, do nothing
      }

    }


  }

  // string -> list

  // map (x)




}
