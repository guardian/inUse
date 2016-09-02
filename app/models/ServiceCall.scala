package models

import com.amazonaws.services.dynamodbv2.document.Item
import org.cvogt.play.json.Jsonx
import play.api.libs.json._

case class ServiceData(
                        category: Option[String] = None,
                        user: Option[String] = None,
                        target: Option[String] = None,
                        datetime: Option[String],
                        function: Option[String],
                        pkg: Option[String],
                        description: Option[String],
                        asset: Option[String],
                        ip: Option[String]
                      )

object ServiceData {

  implicit val serviceDataFormat = Json.format[ServiceData]

}

case class ServiceCall(service: String, createdAt: Long, data: String) {

  def toItem = Item.fromJSON(Json.toJson(this).toString())

  def toServiceData(data: String = data): ServiceData = (Json.parse(data).as[ServiceData])

}

object ServiceCall {

  implicit val serviceCallFormat: Format[ServiceCall] = Jsonx.formatCaseClassUseDefaults[ServiceCall]

  def fromItem(item: Item) = Json.parse(item.toJSON).as[ServiceCall]

  def fromJson(json: JsValue) = json.as[ServiceCall]

}
