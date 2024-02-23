// Automatically generated - do not modify!

@file:JsModule("cesium")

@file:Suppress(
"NON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE",
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
 * A description of the outline of a sphere.
 * ```
 * const sphere = new SphereOutlineGeometry({
 *   radius : 100.0,
 *   stackPartitions : 6,
 *   slicePartitions: 5
 * });
 * const geometry = SphereOutlineGeometry.createGeometry(sphere);
 * ```
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/SphereOutlineGeometry.html">Online Documentation</a>
 */
external  class SphereOutlineGeometry ( options: ConstructorOptions? = definedExternally)  {
/**
 * @property [radius] The radius of the sphere.
 *   Default value - `1.0`
 * @property [stackPartitions] The count of stacks for the sphere (1 greater than the number of parallel lines).
 *   Default value - `10`
 * @property [slicePartitions] The count of slices for the sphere (Equal to the number of radial lines).
 *   Default value - `8`
 * @property [subdivisions] The number of points per line, determining the granularity of the curvature .
 *   Default value - `200`
 */
 interface ConstructorOptions {
var  radius: Double?
var  stackPartitions: Int?
var  slicePartitions: Int?
var  subdivisions: Double?
}

companion object : Packable<SphereOutlineGeometry> {
/**
 * The number of elements used to pack the object into an array.
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/SphereOutlineGeometry.html#.packedLength">Online Documentation</a>
 */
override val packedLength: Int

/**
 * Stores the provided instance into the provided array.
 * @param [value] The value to pack.
 * @param [array] The array to pack into.
 * @param [startingIndex] The index into the array at which to start packing the elements.
 *   Default value - `0`
 * @return The array that was packed into
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/SphereOutlineGeometry.html#.pack">Online Documentation</a>
 */
override  fun pack (
 value: SphereOutlineGeometry,
 array: ReadonlyArray<Double>,
 startingIndex: Int?
): ReadonlyArray<Double>

/**
 * Retrieves an instance from a packed array.
 * @param [array] The packed array.
 * @param [startingIndex] The starting index of the element to be unpacked.
 *   Default value - `0`
 * @param [result] The object into which to store the result.
 * @return The modified result parameter or a new SphereOutlineGeometry instance if one was not provided.
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/SphereOutlineGeometry.html#.unpack">Online Documentation</a>
 */
override  fun unpack (
 array: ReadonlyArray<Double>,
 startingIndex: Int?,
 result: SphereOutlineGeometry?
): SphereOutlineGeometry

/**
 * Computes the geometric representation of an outline of a sphere, including its vertices, indices, and a bounding sphere.
 * @param [sphereGeometry] A description of the sphere outline.
 * @return The computed vertices and indices.
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/SphereOutlineGeometry.html#.createGeometry">Online Documentation</a>
 */
 fun createGeometry ( sphereGeometry: SphereOutlineGeometry): Geometry?
}
}
inline fun SphereOutlineGeometry(
     block: SphereOutlineGeometry.ConstructorOptions.() -> Unit,
): SphereOutlineGeometry =
    SphereOutlineGeometry(options = jso(block))