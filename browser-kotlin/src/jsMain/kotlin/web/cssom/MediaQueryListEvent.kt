// Automatically generated - do not modify!

@file:Suppress(
    "EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER",
)

package web.cssom

import seskar.js.JsAlias
import seskar.js.JsAlias.Companion.THIS
import seskar.js.JsValue
import web.events.Event
import web.events.EventType

/**
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MediaQueryListEvent)
 */
open external class MediaQueryListEvent(
    override val type: EventType<MediaQueryListEvent>,
    init: MediaQueryListEventInit = definedExternally,
) : Event {
    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MediaQueryListEvent/matches)
     */
    val matches: Boolean

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MediaQueryListEvent/media)
     */
    val media: MediaQuery

    @JsAlias(THIS)
    override fun asInit(): MediaQueryListEventInit

    companion object {
        @JsValue("change")
        val CHANGE: EventType<MediaQueryListEvent>
    }
}
