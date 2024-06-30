// Automatically generated - do not modify!

package web.rtc

import web.events.Event
import web.events.EventInstance
import web.events.EventTarget
import web.events.EventType
import web.messaging.MessageEvent

inline val <C : RTCDataChannel> C.bufferedAmountLowEvent: EventInstance<Event, C, EventTarget>
    get() = EventInstance(this, EventType("bufferedamountlow"))

inline val <C : RTCDataChannel> C.closeEvent: EventInstance<Event, C, EventTarget>
    get() = EventInstance(this, EventType("close"))

inline val <C : RTCDataChannel> C.closingEvent: EventInstance<Event, C, EventTarget>
    get() = EventInstance(this, EventType("closing"))

inline val <C : RTCDataChannel> C.errorEvent: EventInstance<RTCErrorEvent, C, EventTarget>
    get() = EventInstance(this, EventType("error"))

inline val <C : RTCDataChannel> C.messageEvent: EventInstance<MessageEvent<Any?>, C, EventTarget>
    get() = EventInstance(this, EventType("message"))

inline val <C : RTCDataChannel> C.openEvent: EventInstance<Event, C, EventTarget>
    get() = EventInstance(this, EventType("open"))
