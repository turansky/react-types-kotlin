// Automatically generated - do not modify!

package typescript

/**
 * Signals that the user manually requested signature help.
 * The language service will unconditionally attempt to provide a result.
 */
external sealed interface SignatureHelpInvokedReason: Union.SignatureHelpInvokedReason_ {
var kind: String /* "invoked" */
var triggerCharacter: Nothing?
}
