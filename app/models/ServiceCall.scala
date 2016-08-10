package models

import com.amazonaws.services.dynamodbv2.document.Item
import org.cvogt.play.json.Jsonx
import play.api.libs.json.{Format, JsValue, Json}

case class ServiceCall(name: String, createdAt: Long, data: String) {

  def toItem = Item.fromJSON(Json.toJson(this).toString())

}

object ServiceCall {

  implicit val serviceCallFormat: Format[ServiceCall] = Jsonx.formatCaseClassUseDefaults[ServiceCall]

  def fromItem(item: Item) = Json.parse(item.toJSON).as[ServiceCall]

  def fromJson(json: JsValue) = json.as[ServiceCall]

}
