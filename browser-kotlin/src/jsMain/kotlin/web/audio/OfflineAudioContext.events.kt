// Automatically generated - do not modify!

package web.audio

import web.events.EventInstance
import web.events.EventTarget
import web.events.EventType

inline val <C : OfflineAudioContext> C.completeEvent: EventInstance<OfflineAudioCompletionEvent, C, EventTarget>
    get() = EventInstance(this, EventType("complete"))
