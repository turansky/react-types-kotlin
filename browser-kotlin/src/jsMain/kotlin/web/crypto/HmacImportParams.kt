// Automatically generated - do not modify!

package web.crypto

import js.objects.JsPlainObject
import web.crypto.Algorithm

@JsPlainObject
sealed external interface HmacImportParams :
Algorithm {
var hash: HashAlgorithmIdentifier
var length: Int?
}
