package org.notmandatory.util

abstract class JsonMessage[T] {

  def parseFrom(source: String): T

  def toJson: String
}
