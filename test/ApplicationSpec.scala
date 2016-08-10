import models.ServiceCall
import org.joda.time.DateTime
import org.scalatestplus.play._
import services.{InUseMemoryService, InUseService}

class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "InMemoryBackendService" should {

    "register services" in  {

      val service: InUseService = InUseMemoryService

      service.registerService("hello")

      service.getRecentServiceCallsMap()("hello") mustBe List()

    }

    "registers calls" in {

      val service: InUseService = InUseMemoryService

      service.registerService("hello")

      service.getRecentServiceCallsMap()("hello").length mustBe 0

      service.registerCall("hello", ServiceCall("hello", DateTime.now.getMillis, "testing"))

      service.getRecentServiceCallsMap()("hello").length mustBe 1

      service.getRecentServiceCallsMap()("hello")(0).data mustBe "testing"

      service.registerCall("hello", ServiceCall("hello", DateTime.now.getMillis, "testing 2"))

      service.getRecentServiceCallsMap()("hello").length mustBe 2

    }

    "be serializable to Dyno format" in {

      val item = ServiceCall("hello", DateTime.now.getMillis, "testing").toItem

      item.get("name").toString() mustBe "hello"

    }

  }


}
