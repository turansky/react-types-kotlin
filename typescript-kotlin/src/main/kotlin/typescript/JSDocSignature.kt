// Automatically generated - do not modify!

package typescript

sealed external interface JSDocSignature : JSDocType, Declaration, Union.JSDocSignature {
    override val kind: SyntaxKind.JSDocSignature
    val typeParameters: ReadonlyArray<JSDocTemplateTag>?
    val parameters: ReadonlyArray<JSDocParameterTag>
    val type: JSDocReturnTag?
}
