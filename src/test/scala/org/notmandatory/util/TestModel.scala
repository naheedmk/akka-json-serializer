package org.notmandatory.util

import java.net.URL
import java.util.UUID

case class PhoneNumber(phoneType: String, countryCode: Int, localNumber: String)

case class Contact(id:UUID = UUID.randomUUID(), name: String,
                   address: Option[String] = None,
                   phoneNumbers: Array[PhoneNumber] = Array[PhoneNumber](),
                   url: Option[URL] = None)