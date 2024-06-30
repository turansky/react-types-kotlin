// Automatically generated - do not modify!

package web.remoteplayback

import web.events.Event
import web.events.EventInstance
import web.events.EventTarget
import web.events.EventType

inline val <C : RemotePlayback> C.connectEvent: EventInstance<Event, C, EventTarget>
    get() = EventInstance(this, EventType("connect"))

inline val <C : RemotePlayback> C.connectingEvent: EventInstance<Event, C, EventTarget>
    get() = EventInstance(this, EventType("connecting"))

inline val <C : RemotePlayback> C.disconnectEvent: EventInstance<Event, C, EventTarget>
    get() = EventInstance(this, EventType("disconnect"))
