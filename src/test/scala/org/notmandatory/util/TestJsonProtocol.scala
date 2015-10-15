package org.notmandatory.util

import java.net.URL
import java.util.UUID

import spray.json._

trait TestJsonProtocol extends DefaultJsonProtocol {

  import spray.json._

  implicit object URLJsonFormat extends JsonFormat[URL] {

    def read(value: JsValue) = value match {
      case JsString(u) => new URL(u)
      case _ => deserializationError("URL expected")
    }

    def write(u: URL) = JsString(u.toString)
  }

  implicit object UUIDFormat extends JsonFormat[UUID] {

    def read(value: JsValue) = value match {
      case JsString(uuid) => UUID.fromString(uuid)
      case _ => deserializationError("UUID expected")
    }

    def write(uuid: UUID) = JsString(uuid.toString)
  }

  implicit val phoneNumberJsonFormat = jsonFormat3(PhoneNumber)

  implicit val contactJsonFormat = jsonFormat5(Contact)

  implicit val contactAddedJsonFormat = jsonFormat(ContactAdded.apply(_), "contact")

  implicit val contactDeletedJsonFormat = jsonFormat(ContactDeleted.apply(_), "id")

  implicit val addressAddedJsonFormat = jsonFormat(AddressAdded.apply, "id", "address")
}
