package org.notmandatory.util

import java.util.UUID

import akka.actor.{ActorSystem, Props}
import akka.event.Logging
import akka.persistence.{PersistentActor, SnapshotOffer}

object TestContactManager {

  val props = Props(new TestContactManager)
  val name = "testContactManager"
  val persId = s"$name-persister"

  def actorOf(implicit system: ActorSystem) = system.actorOf(props, name)

  case class State(contacts: Map[UUID, Contact] = Map()) {

    def updated(msg: Event): State = msg match {

      case ContactAdded(c) =>
        this.copy(contacts = contacts + (c.id -> c))

      case ContactDeleted(id) =>
        this.copy(contacts = contacts.filter(kv => kv._1 != id))

      case _ =>
        this // do nothing
    }
  }

}

class TestContactManager extends PersistentActor {

  import org.notmandatory.util.TestContactManager._

  var state = State()

  def updateState(event: Event): Unit =
    state = state.updated(event)

  val log = Logging(context.system, this)

  override def persistenceId: String = persId

  override val receiveRecover: Receive = {

    case evt: Event =>
      updateState(evt)

    case SnapshotOffer(_, snapshot: State) =>
      state = snapshot
  }

  override val receiveCommand: Receive = {

    // handlers for manager commands

    case AddContact(n) =>
      val ca = ContactAdded(Contact(name = n))
      persist(ca)(updateState)
      sender ! ca

    case DeleteContact(i) =>
      val cd = ContactDeleted(i)
      persist(cd)(updateState)
      sender ! cd

    case DeleteAllEvents =>
      deleteMessages(Long.MaxValue)
      sender ! AllEventsDeleted

    case "snap" => saveSnapshot(state)

    case "print" => println(state)
  }

}
