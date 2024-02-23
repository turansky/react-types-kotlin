// Automatically generated - do not modify!

@file:JsModule("cesium")

@file:Suppress(
"EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER",
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
 * The SceneModePicker is a single button widget for switching between scene modes;
 * shown to the left in its expanded state. Programatic switching of scene modes will
 * be automatically reflected in the widget as long as the specified Scene
 * is used to perform the change.<p style="clear: both;"></p><br/>
 * ```
 * // In HTML head, include a link to the SceneModePicker.css stylesheet,
 * // and in the body, include: <div id="sceneModePickerContainer"></div>
 * // Note: This code assumes you already have a Scene instance.
 * 
 * const sceneModePicker = new SceneModePicker('sceneModePickerContainer', scene);
 * ```
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/SceneModePicker.html">Online Documentation</a>
 * 
 * @constructor
 * @property [container] The DOM element that will contain the widget.
 * @param [scene] The Scene instance to use.
 * @param [duration] The time, in seconds, it takes for the scene to transition.
 *   Default value - `2.0`
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/SceneModePicker.html">Online Documentation</a>
 */
external  class SceneModePicker (
 var container: Element,
 scene: Scene,
 duration: Double? = definedExternally
)  {
/**
 * Gets the view model.
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/SceneModePicker.html#viewModel">Online Documentation</a>
 */
var viewModel: SceneModePickerViewModel

/**
 * @return true if the object has been destroyed, false otherwise.
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/SceneModePicker.html#isDestroyed">Online Documentation</a>
 */
 fun isDestroyed (): Boolean

/**
 * Destroys the widget.  Should be called if permanently
 * removing the widget from layout.
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/SceneModePicker.html#destroy">Online Documentation</a>
 */
 fun destroy ()
}
