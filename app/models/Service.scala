package models

import com.amazonaws.services.dynamodbv2.document.Item
import play.api.libs.json.{Format, JsValue, Json}
import org.cvogt.play.json.Jsonx

case class Service(service: String) {

  def toItem = Item.fromJSON(Json.toJson(this).toString())

}

object Service {
  implicit val serviceFormat: Format[Service] = Jsonx.formatCaseClassUseDefaults[Service]

  def fromItem(item: Item) = Json.parse(item.toJSON).as[Service]

  def fromJson(json: JsValue) = json.as[Service]
}
