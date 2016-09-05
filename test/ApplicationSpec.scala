import controllers.IndexController
import models.ServiceCall
import org.joda.time.DateTime
import org.scalatestplus.play._
import play.api.test.FakeRequest
import play.api.test._
import play.api.test.Helpers._
import services.{InUseMemoryService, InUseService}

class ApplicationSpec extends PlaySpec with OneAppPerTest {

  val jsonData = """{"category" : "Test Cat",
                     "user" : "Test User",
                     "target" : "Test Target",
                     "datetime" : "Test Date",
                     "function" : "Test Function",
                     "pkg" : "Test pkg",
                     "description" : "Description in here",
                     "asset" : "Test Asset",
                     "ip" : "xxx.xxx.xxx.xxx"}""".stripMargin

  "InMemoryBackendService" should {

    "register services" in  {

      val service: InUseService = new InUseMemoryService()

      service.registerService("hello")

      service.getRecentServiceCallsMap()("hello") mustBe List()

    }

    "registers calls" in {

      val service: InUseService = new InUseMemoryService()

      service.registerService("hello")

      service.getRecentServiceCallsMap()("hello").length mustBe 0

      service.registerCall("hello", ServiceCall("hello", DateTime.now.getMillis, jsonData))

      service.getRecentServiceCallsMap()("hello").length mustBe 1

      service.getRecentServiceCallsMap()("hello")(0).data mustBe jsonData

      service.getRecentServiceCallsMap()("hello")(0).toServiceData().category mustBe Some("Test Cat")

      service.getRecentServiceCallsMap()("hello")(0).toServiceData().user mustBe Some("Test User")

      service.getRecentServiceCallsMap()("hello")(0).toServiceData().target mustBe Some("Test Target")

      service.getRecentServiceCallsMap()("hello")(0).toServiceData().datetime mustBe Some("Test Date")

      service.getRecentServiceCallsMap()("hello")(0).toServiceData().function mustBe Some("Test Function")

      service.getRecentServiceCallsMap()("hello")(0).toServiceData().pkg mustBe Some("Test pkg")

      service.getRecentServiceCallsMap()("hello")(0).toServiceData().description mustBe Some("Description in here")

      service.getRecentServiceCallsMap()("hello")(0).toServiceData().asset mustBe Some("Test Asset")

      service.getRecentServiceCallsMap()("hello")(0).toServiceData().ip mustBe Some("xxx.xxx.xxx.xxx")

      service.registerCall("hello", ServiceCall("hello", DateTime.now.getMillis, jsonData))

      service.getRecentServiceCallsMap()("hello").length mustBe 2

    }

    "be serializable to Dyno format" in {

      val item = ServiceCall("hello", DateTime.now.getMillis, jsonData).toItem

      item.get("service").toString() mustBe "hello"

    }

    "respond to the index Action" in {

      val controller = new IndexController(new InUseMemoryService())

      val result = controller.index()(FakeRequest())

      status(result) mustBe OK

    }

    "respond to the search Action" in {

      val controller = new IndexController(new InUseMemoryService())

      val result = controller.search("example service", "Three")(FakeRequest())

      status(result) mustBe OK

      contentAsString(result) must include("example service")
      contentAsString(result) must include("Three")
      contentAsString(result) mustNot include("One")
      contentAsString(result) mustNot include("Two")

    }

  }


}
