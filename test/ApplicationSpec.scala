import models.ServiceCall
import org.joda.time.DateTime
import org.scalatestplus.play._
import services.InUseMemoryService

class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "InMemoryBackendService" should {

    "register services" in  {

      val service = InUseMemoryService

      service.registerService("hello")

      service.calls("hello") mustBe List()

    }

    "registers calls" in {

      val service = InUseMemoryService

      service.registerService("hello")

      service.calls("hello").length mustBe 0

      service.registerCall("hello", ServiceCall("hello", new DateTime(), "testing"))

      service.calls("hello").length mustBe 1

      service.calls("hello")(0).data mustBe "testing"

      service.registerCall("hello", ServiceCall("hello", new DateTime(), "testing 2"))

      service.calls("hello").length mustBe 2

    }

  }


}
