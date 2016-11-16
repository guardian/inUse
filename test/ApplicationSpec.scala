import controllers.IndexController
import models.ServiceCall
import org.joda.time.DateTime
import org.scalatestplus.play._
import play.api.test.FakeRequest
import play.api.test._
import play.api.test.Helpers._
import services.{InUseDynamoService, InUseMemoryService, InUseService}

class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "InMemoryBackendService" should {

    "be able to read from dynamo DB" in  {

      val service: InUseService = new InUseDynamoService()

      service.registerService("hello")
      service.getRecentServiceCallsMap()


    }


  }


}
