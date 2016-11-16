package models

import com.amazonaws.services.dynamodbv2.document.Item
import org.cvogt.play.json.Jsonx
import play.api.libs.json.{Format, JsValue, Json}

case class ServiceCall(service: String, createdAt: Long, data: String) {
  def toItem = Item.fromJSON(data).withString("service", service).withLong("createdAt", createdAt)
}

object ServiceCall {

  implicit val serviceCallFormat: Format[ServiceCall] = Jsonx.formatCaseClassUseDefaults[ServiceCall]

  def fromItem(item: Item): ServiceCall = {
    ServiceCall(item.getString("service"), item.getLong("createdAt"), item.toJSON)
  }

  def fromJson(json: JsValue) = json.as[ServiceCall]

}
