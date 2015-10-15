package org.notmandatory.util

import java.lang.reflect.Method
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.util.concurrent.atomic.AtomicReference

import akka.actor.ExtendedActorSystem
import akka.serialization.Serializer
import spray.json._

import scala.annotation.tailrec

object SprayJsonSerializer {
  val UTF8: Charset = Charset.forName("UTF-8")
  val JSVALUE = classOf[JsValue]
  val ID: Int = ByteBuffer.wrap("SprayJsonSerializer".getBytes(UTF8)).getInt
}

class SprayJsonSerializer(val system: ExtendedActorSystem) extends Serializer {

  import SprayJsonSerializer._

  def identifier = ID

  def includeManifest = true

  private val parsingMethodBindingRef = new AtomicReference[Map[Class[_], Method]](Map.empty)
  private val toByteArrayMethodBindingRef = new AtomicReference[Map[Class[_], Method]](Map.empty)

  def fromBinary(bytes: Array[Byte], manifest: Option[Class[_]]) = {
    manifest match {
      case Some(clazz) ⇒
        @tailrec
        def parsingMethod(method: Method = null): Method = {
          val parsingMethodBinding = parsingMethodBindingRef.get()
          parsingMethodBinding.get(clazz) match {
            case Some(cachedParsingMethod) ⇒ cachedParsingMethod
            case None ⇒
              val unCachedParsingMethod =
                if (method eq null) clazz.getDeclaredMethod("fromJsValue", JSVALUE)
                else method
              if (parsingMethodBindingRef.compareAndSet(parsingMethodBinding, parsingMethodBinding.updated(clazz, unCachedParsingMethod)))
                unCachedParsingMethod
              else
                parsingMethod(unCachedParsingMethod)
          }
        }
        parsingMethod().invoke(null, new String(bytes, UTF8).parseJson)

      case None ⇒ throw new IllegalArgumentException("Need a manifest to be able to serialize bytes")
    }
  }

  def toBinary(x: AnyRef) = x match {
    case js: SprayJsonWriter => js.toJsValue.toString().getBytes(UTF8)
  }

}
