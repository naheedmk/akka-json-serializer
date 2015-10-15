package org.notmandatory.util

import akka.actor.ActorSystem
import akka.testkit.{DefaultTimeout, ImplicitSender, TestKit}
import com.typesafe.config.ConfigFactory
import org.scalatest._

import scala.concurrent.duration.Duration

class SprayJsonSerializerSpec
  extends TestKit(ActorSystem("SprayJsonSerializerSpec", ConfigFactory.parseString(SprayJsonSerializerSpec.config)))
  with DefaultTimeout with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  "TestContactManager" must {

    "persist ContactAdded event" in {

      val testRef = system.actorOf(TestContactManager.props)

      testRef ! AddContact("Joe Tester")

      expectMsgPF(Duration("1s")) {
        case ContactAdded(Contact(_, "Joe Tester", None, pn, None)) if pn.length == 0 => true
      }
    }
  }
}

object SprayJsonSerializerSpec {
  // Define your test specific configuration here
  val config =
    """
  akka {
  loglevel = DEBUG

  actor {

    serializers {
      json = "org.notmandatory.util.SprayJsonSerializer"
    }

    serialization-bindings {
      "org.notmandatory.util.SprayJsonSerializable" = json
      "java.io.Serializable"                        = none // not a great default
    }

    debug {
      receive = on
      autoreceive = on
      lifecycle = on
    }
  }

  persistence {
    snapshot-store {
      local.dir = "snapshots"
      plugin = "akka.persistence.snapshot-store.local"
    }

    journal {
      leveldb.dir = "journal"
      plugin = "akka.persistence.journal.leveldb"
    }
  }
}
    """
}
