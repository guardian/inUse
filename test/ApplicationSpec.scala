import models.ServiceCall
import org.joda.time.DateTime
import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._
import services.InUseMemoryService

class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "InMemoryBackendService" should {

    "register services" in  {

      val service = new InUseMemoryService()

      service.registerService("hello")

      service.calls("hello") mustBe List()

    }

    "registers calls" in {

      val service = new InUseMemoryService()

      service.registerService("hello")

      service.calls("hello").length mustBe 0

      service.registerCall("hello", ServiceCall("hello", new DateTime(), ""))

      service.calls("hello").length mustBe 1

    }

  }


}
