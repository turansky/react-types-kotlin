// Automatically generated - do not modify!

@file:Suppress(
"NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package web.cssom.atrule

import web.cssom.Length
import web.cssom.MediaQuery
import web.cssom.SizeQuery
import seskar.js.JsIntValue
import seskar.js.JsVirtual
import seskar.js.JsValue

        @JsVirtual
        sealed external interface MediaType : MediaQuery {
            companion object {
            @JsValue("all")
val all: MediaType
@JsValue("print")
val print: MediaType
@JsValue("screen")
val screen: MediaType
            }
        }
