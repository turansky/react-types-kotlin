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
 * Represents a ray that extends infinitely from the provided origin in the provided direction.
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/Ray.html">Online Documentation</a>
 * 
 * @constructor
 * @property [origin] The origin of the ray.
 *   Default value - [Cartesian3.ZERO]
 * @property [direction] The direction of the ray.
 *   Default value - [Cartesian3.ZERO]
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/Ray.html">Online Documentation</a>
 */
external  class Ray (
 var origin: Cartesian3 = definedExternally,
 var direction: Cartesian3 = definedExternally
)  {


companion object  {
/**
 * Duplicates a Ray instance.
 * @param [ray] The ray to duplicate.
 * @param [result] The object onto which to store the result.
 * @return The modified result parameter or a new Ray instance if one was not provided. (Returns undefined if ray is undefined)
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/Ray.html#.clone">Online Documentation</a>
 */
 fun clone (
 ray: Ray,
 result: Ray? = definedExternally
): Ray

/**
 * Computes the point along the ray given by r(t) = o + t*d,
 * where o is the origin of the ray and d is the direction.
 * ```
 * //Get the first intersection point of a ray and an ellipsoid.
 * const intersection = IntersectionTests.rayEllipsoid(ray, ellipsoid);
 * const point = Ray.getPoint(ray, intersection.start);
 * ```
 * @param [ray] The ray.
 * @param [t] A scalar value.
 * @param [result] The object in which the result will be stored.
 * @return The modified result parameter, or a new instance if none was provided.
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/Ray.html#.getPoint">Online Documentation</a>
 */
 fun getPoint (
 ray: Ray,
 t: Double,
 result: Cartesian3? = definedExternally
): Cartesian3
}
}
