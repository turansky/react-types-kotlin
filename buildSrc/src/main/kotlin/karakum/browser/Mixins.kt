package karakum.browser

internal object Mixins {
    private val SAFE: Set<String> = setOf(
        "LocaleOptions",
        "AbstractWorker",

        // CSS
        "LinkStyle",

        // DOM
        "ARIAMixin",
        "GlobalEventHandlers",
        "NonDocumentTypeChildNode",
        "Slottable",

        // Window
        "WindowEventHandlers",
    )

    val UNSAFE: Set<String> = setOf(
        "Body",
        "CanvasPath",

        "Animatable",
        "ElementCSSInlineStyle",
        "ChildNode",
        "HTMLHyperlinkElementUtils",
        "HTMLOrSVGElement",
        "InnerHTML",
        "ParentNode",
        "ElementContentEditable",
        "PopoverInvokerElement",

        "TextDecoderCommon",
        "TextEncoderCommon",

        "GenericTransformStream",
        "ReadableStreamGenericReader",
    )

    val ALL: Set<String> = SAFE + UNSAFE
}
