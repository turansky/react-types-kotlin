// Automatically generated - do not modify!

@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package js.intl

import seskar.js.JsValue
import seskar.js.JsVirtual

@JsVirtual
sealed external interface LocaleMatcher {
    companion object {
        @JsValue("best fit")
        val bestFit: LocaleMatcher

        @JsValue("lookup")
        val lookup: LocaleMatcher
    }
}
