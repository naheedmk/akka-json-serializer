package org.notmandatory.util

import java.util.UUID

import spray.json._

// commands

sealed trait Command

case class AddContact(name: String) extends Command

case class DeleteContact(id: UUID)

case class AddAddressToContact(id: UUID, address: String)

// events

sealed trait Event

object ContactAdded extends SprayJsonReader[ContactAdded] with TestJsonProtocol {

  override def fromJsValue(json: JsValue): ContactAdded = json.convertTo[ContactAdded]
}

case class ContactAdded(contact: Contact) extends SprayJsonWriter with TestJsonProtocol with Event {

  override def toJsValue: JsValue = this.toJson
}

object ContactDeleted extends SprayJsonReader[ContactDeleted] with TestJsonProtocol {

  override def fromJsValue(json: JsValue): ContactDeleted = json.convertTo[ContactDeleted]
}

case class ContactDeleted(id: UUID) extends SprayJsonWriter with TestJsonProtocol with Event {
  override def toJsValue: JsValue = this.toJson
}

object AddressAdded extends SprayJsonReader[AddressAdded] with TestJsonProtocol {

  override def fromJsValue(json: JsValue): AddressAdded = json.convertTo[AddressAdded]
}

case class AddressAdded(id: UUID, address: String) extends SprayJsonWriter with TestJsonProtocol with Event {
  override def toJsValue: JsValue = this.toJson
}