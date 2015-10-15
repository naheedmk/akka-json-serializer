package org.notmandatory.util

import spray.json.JsValue

trait SprayJsonSerializable

trait SprayJsonReader[T] {
  def fromJsValue(json: JsValue): T
}

trait SprayJsonWriter extends SprayJsonSerializable {
  def toJsValue: JsValue
}
