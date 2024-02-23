// Automatically generated - do not modify!

@file:JsModule("cesium")

@file:Suppress(
"NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package cesium

import js.promise.Promise
import web.canvas.ImageData
import web.dom.Document
import web.dom.Element
import web.html.HTMLCanvasElement
import web.html.HTMLElement
import web.html.HTMLIFrameElement
import web.html.HTMLImageElement
import web.html.HTMLVideoElement
import web.xml.XMLDocument
import js.buffer.ArrayBuffer
import js.objects.jso
import js.array.ReadonlyArray
import js.objects.ReadonlyRecord
import js.core.Void
import js.errors.JsError
import js.typedarrays.Float32Array
import js.typedarrays.Float64Array
import js.typedarrays.Uint16Array
import js.typedarrays.Uint8Array
import web.blob.Blob

/**
 * Constants for identifying well-known reference frames.
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/global.html#ReferenceFrame">Online Documentation</a>
 */
sealed external interface ReferenceFrame {
companion object {

/**
 * The fixed frame.
 */
val FIXED: ReferenceFrame

/**
 * The inertial frame.
 */
val INERTIAL: ReferenceFrame
}
}