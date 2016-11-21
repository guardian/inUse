import controllers.{CSVController, IndexController}
import models.ServiceCall
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.scalatestplus.play._
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.{InUseMemoryService, InUseService}

class ApplicationSpec extends PlaySpec with OneAppPerTest {

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

      service.registerCall("hello", ServiceCall("hello", DateTime.now.getMillis, "testing"))

      service.getRecentServiceCallsMap()("hello").length mustBe 1

      service.getRecentServiceCallsMap()("hello")(0).data mustBe "testing"

      service.registerCall("hello", ServiceCall("hello", DateTime.now.getMillis, "testing 2"))

      service.getRecentServiceCallsMap()("hello").length mustBe 2

    }

    "be serializable to Dyno format" in {

      val item = ServiceCall("hello", DateTime.now.getMillis, "{\"testing\":\"blah\"}").toItem

      item.getString("service") mustBe "hello"

    }

    "respond to the index Action" in {

      val controller = new IndexController(new InUseMemoryService())

      val result = controller.index()(FakeRequest())

      status(result) mustBe OK

    }

    "respond to the search Action" in {

      val controller = new IndexController(new InUseMemoryService())

      val result = controller.search("example service", "three")(FakeRequest())

      status(result) mustBe OK

      contentAsString(result) must include("example service")
      contentAsString(result) must include("three")
      contentAsString(result) mustNot include("one")
      contentAsString(result) mustNot include("two")

    }

    "Be able to CSV some calls" in {

      val controller = new CSVController(new InUseMemoryService())
      val result = controller.forService("example service")(FakeRequest())

      val today = DateTime.now.getMillis
      val yesterday = DateTime.now.minusDays(1).getMillis

      def millisToDate(ms: Long): String = DateTimeFormat.forPattern("yyyy-MM-dd").print(ms)

      status(result) mustBe OK

      contentAsString(result) must include(millisToDate(today)+",2")
      contentAsString(result) must include(millisToDate(yesterday)+",1")

    }

  }

}