// Automatically generated - do not modify!

@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package web.cssom

import seskar.js.JsUnion
import seskar.js.JsValue

@JsUnion
sealed external interface PrintColorAdjust {
    companion object {
        @JsValue("economy")
        val economy: PrintColorAdjust

        @JsValue("exact")
        val exact: PrintColorAdjust
    }
}
