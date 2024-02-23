// Automatically generated - do not modify!

package web.cssom

import js.array.ArrayLike
import js.iterable.JsIterable
import web.cssom.MediaQuery

/**
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MediaList)
 */
sealed external class MediaList:
ArrayLike<MediaQuery>,
JsIterable<MediaQuery> {
/**
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MediaList/mediaText)
 */
var mediaText: String
/**
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MediaList/appendMedium)
 */
 fun appendMedium(medium: MediaQuery)
/**
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MediaList/deleteMedium)
 */
 fun deleteMedium(medium: MediaQuery)
/**
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MediaList/item)
 */
 fun item(index: Int): MediaQuery?
}
