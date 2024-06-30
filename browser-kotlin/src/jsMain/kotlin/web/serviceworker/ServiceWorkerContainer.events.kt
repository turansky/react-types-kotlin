// Automatically generated - do not modify!

package web.serviceworker

import web.events.Event
import web.events.EventInstance
import web.events.EventTarget
import web.events.EventType

inline val <C : ServiceWorkerContainer> C.controllerChangeEvent: EventInstance<Event, C, EventTarget>
    get() = EventInstance(this, EventType("controllerchange"))

inline val <C : ServiceWorkerContainer> C.messageEvent: EventInstance<Event, C, EventTarget>
    get() = EventInstance(this, EventType("message"))

inline val <C : ServiceWorkerContainer> C.messageErrorEvent: EventInstance<Event, C, EventTarget>
    get() = EventInstance(this, EventType("messageerror"))
