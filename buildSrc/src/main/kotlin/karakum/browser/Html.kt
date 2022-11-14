package karakum.browser

import karakum.common.UnionConstant
import karakum.common.objectUnionBody
import karakum.common.unionBody

internal const val VIDEO_FRAME_REQUEST_ID = "VideoFrameRequestId"
internal const val RENDERING_CONTEXT_ID = "RenderingContextId"
internal const val GEOLOCATION_WATCH_ID = "GeolocationWatchId"

private val DEPRECATED = setOf(
    "HTMLDirectoryElement",
    "HTMLFontElement",
    "HTMLFrameElement",
    "HTMLFrameSetElement",
    "HTMLMarqueeElement",

    "HTMLTableHeaderCellElement",
    "HTMLTableDataCellElement",

    "HTMLAllCollection",

    // TEMP
    "CanvasCaptureMediaStreamTrack",
)

private val ANIMATION_TYPES = setOf(
    "Animation",
    "AnimationEffect",
    "AnimationTimeline",

    "ComputedEffectTiming",
    "EffectTiming",
    "OptionalEffectTiming",
)

private val DOM_TYPES = setOf(
    "DOMStringList",

    "Attr",
    "CDATASection",
    "CharacterData",
    "Comment",
    "ElementCreationOptions",
    "NonDocumentTypeChildNode",
    "NonElementParentNode",
    "ChildNode",
    "DocumentType",
    "NodeFilter",
    "TreeWalker",
    "AbstractRange",
    "Range",
    "Slottable",
    "Text",
    "NodeIterator",
    "InnerHTML",

    // special
    "HTMLOrSVGElement",
    "FocusOptions",

    "Node",
    "NodeFilter",
    "NodeList",
    "NamedNodeMap",
    "GetRootNodeOptions",
    "DOMImplementation",

    "ChildNode",
    "ParentNode",
    "Element",
    "FullscreenOptions",

    "ScrollOptions",
    "ScrollToOptions",
    "ScrollIntoViewOptions",

    "DocumentAndElementEventHandlers",
    "GlobalEventHandlers",
)

internal val DOM_CSS_TYPES = listOf(
    "ElementCSSInlineStyle",
    "LinkStyle",
)

private val DOM_DATA_TYPES = listOf(
    "DataTransfer",
    "DataTransferItem",
    "DataTransferItemList",
)

internal val DOM_GEOMETRY_TYPES = listOf(
    "DOMPointReadOnly",
    "DOMPoint",
    "DOMPointInit",

    "DOMRectReadOnly",
    "DOMRect",
    "DOMRectInit",
    "DOMRectList",

    "DOMMatrixReadOnly",
    "DOMMatrix",
    "DOMMatrixInit",
    "DOMMatrix2DInit",

    "DOMQuad",
)

private val DOM_PARSING_TYPES = listOf(
    "DOMParser",
    "XMLSerializer",
)

private val CANVAS_TYPES = listOf(
    "ImageBitmap",
    "ImageBitmapOptions",
    "ImageBitmapRenderingContext",
    "ImageBitmapRenderingContextSettings",

    "ImageDataSettings",
    "TextMetrics",
)

internal fun htmlDeclarations(
    source: String,
): Sequence<ConversionResult> {
    val (content, additionalType) = prepareContent(
        source.replace(";\n     *", ";--\n     *")
    )

    val getStaticSource = { name: String ->
        when (name) {
            "ElementInternals" -> null
            else -> getStaticSource(name, source)
        }
    }

    val patterns = sequenceOf(
        "HTML.+?",
        "SVG.+?",
        "HTMLCollection .+?",

        "ShadowRoot .+?",
        "ShadowRootInit",
        "WindowEventHandlers",

        "ARIAMixin",

        "ElementContentEditable",
        "ElementInternals .+?",
        "ValidityStateFlags",

        "Node .+?",
        "ChildNode .+?",
        "ParentNode .+?",
        "Element .+?",
        "Attr .+?",
        "CDATASection .+?",
        "Comment .+?",
        "Range .+?",
        "CharacterData .+?",
        "Text .+?",

        "Document .+?",
        "DocumentType .+?",
        "DocumentFragment .+?",
        "DocumentOrShadowRoot",
        "XMLDocument .+?",
        "XPath.+?",

        "DOMMatrix .+?",
        "DOMMatrixInit .+?",
        "DOMPoint .+?",
        "DOMRect .+?",

        "ScrollToOptions .+?",
        "ScrollIntoViewOptions .+?",

        "Selection",

        "Touch",
        "TouchInit",
        "TouchList",

        "Canvas.+?",
        "CanvasRenderingContext2D .+?",

        "Window .+?",
        "WindowPostMessageOptions .+?",

        "Animation .+?",
        "ComputedEffectTiming .+?",

        "FileReader .+?",

        "CSS.+?",
        "StyleSheet",
        "StyleSheetList",
        "MediaList",
        "MediaQueryList .+?",

        "FontFace",
        "FontFaceDescriptors",
        "FontFaceSet .+?",
        "FontFaceSource",

        "EventModifierInit .+?",

        "Clipboard .+?",
        "ClipboardItemOptions",

        "History",
        "Location",

        "Screen",
        "ScreenOrientation .+?",

        "SpeechReco.+?",
        "SpeechSynt.+?",

        "Geolocation",
        "Geolocation.+?",
        "PositionOptions",

        "DeviceMotionEventAcceleration",
        "DeviceMotionEventAccelerationInit",
        "DeviceMotionEventRotationRate",
        "DeviceMotionEventRotationRateInit",

        "ShareData",

        "EventSourceInit",
        "EventSource .+?",

        "XMLHttp.+?",

        "Storage",
        "StorageEstimate",
        "StorageManager",
        "Permission.+?",
        "Gamepad",
        "Gamepad.+?",

        "File",
        "File.+?",

        "FileSystem",
        "FileSystem.+?",

        "Lock",
        "Lock.+?",
        "Navigator.+?",
        "PictureInPictureWindow.+?",
        "ValidityState",
        "AssignedNodesOptions",
        "VideoFrameMetadata",
        "VideoPlaybackQuality",
        "RemotePlayback .+?",
        "DOMMatrix2DInit",
    ).plus(ANIMATION_TYPES)
        .plus(DOM_TYPES)
        .plus(DOM_CSS_TYPES)
        .plus(DOM_DATA_TYPES)
        .plus(DOM_GEOMETRY_TYPES)
        .plus(DOM_PARSING_TYPES)
        .plus(CANVAS_TYPES)
        .joinToString("|")

    val interfaces =
        Regex("""interface ($patterns) \{[\s\S]+?\}""")
            .findAll(content)
            .map { it.value }
            .mapNotNull { convertInterface(it, getStaticSource) }

    return interfaces
        .plus(additionalType)
        .plus(clipboardDeclarations())
        .plus(
            ConversionResult(
                name = "NodeFilter",
                body = "typealias NodeFilter = (node: Node) -> Short",
                pkg = "dom",
            )
        )
        .plus(
            ConversionResult(
                name = VIDEO_FRAME_REQUEST_ID,
                body = "sealed external interface $VIDEO_FRAME_REQUEST_ID",
                pkg = "dom.html",
            )
        )
        .plus(
            ConversionResult(
                name = GEOLOCATION_WATCH_ID,
                body = "sealed external interface $GEOLOCATION_WATCH_ID",
                pkg = "web.geolocation",
            )
        )
        .plus(
            ConversionResult(
                name = "CanvasImageSource",
                body = "typealias CanvasImageSource = Any /* HTMLOrSVGImageElement | HTMLVideoElement | HTMLCanvasElement | ImageBitmap */",
                pkg = "canvas",
            )
        )
        .plus(
            ConversionResult(
                name = "HTMLCollectionOf",
                body = "typealias HTMLCollectionOf<T> = HTMLCollection",
                pkg = "dom.html",
            )
        )
        .plus(
            ConversionResult(
                name = "CSSNumberish",
                body = "typealias CSSNumberish = Double",
                pkg = "web.animations",
            )
        )
        .plus(
            ConversionResult(
                name = "XPathNSResolver",
                body = "typealias XPathNSResolver = (prefix: String?) -> String?",
                pkg = "dom.xpath",
            )
        )
        .plus(
            ConversionResult(
                name = "XMLHttpRequestBodyInit",
                body = "typealias XMLHttpRequestBodyInit = Any /* Blob | BufferSource | FormData | URLSearchParams | string */",
                pkg = "web.xhr",
            )
        )
}

private fun prepareContent(
    source: String,
): Pair<String, ConversionResult> {
    val ids = Regex("""getContext\(contextId\: "([\w\d]+)"\, """)
        .findAll(source)
        .map { it.groupValues[1] }
        .toList()

    fun kotlinName(id: String): String =
        if (id == "2d") "canvas" else id

    val contextIdBody = objectUnionBody(
        name = RENDERING_CONTEXT_ID,
        constants = ids.map { id ->
            val name = kotlinName(id)

            UnionConstant(
                kotlinName = name,
                jsName = name,
                value = id,
                comment = if (id != name) "// `$id`" else null
            )
        }
    )

    val contextId = ConversionResult(
        RENDERING_CONTEXT_ID,
        contextIdBody,
        "dom.html",
    )

    val content = ids.fold(source) { acc, id ->
        val name = kotlinName(id)

        acc.replace(
            """getContext(contextId: "$id"""",
            """getContext(contextId: $RENDERING_CONTEXT_ID.$name""",
        )
    }

    return content to contextId
}

private fun convertInterface(
    source: String,
    getStaticSource: (String) -> String?,
): ConversionResult? {
    val name = source
        .substringAfter(" ")
        .substringBefore(" ")
        .substringBefore("<")

    when {
        name in HTML_ALIAS_CLASSES -> return null
        name in DEPRECATED -> return null
        name.endsWith("NameMap") -> return null
        name.endsWith("Event") -> return null
        name.endsWith("EventInit") -> return null
        name.endsWith("EventMap") -> return null

        // TEMP
        name == "HTMLCollectionOf" -> return null
    }

    val staticSource = getStaticSource(name)
    val type = if (staticSource != null) "class" else "interface"

    var declaration = source.substringBefore(" {\n")
        .replace(", AnimationFrameProvider", "")
        .replace(", WindowLocalStorage, WindowOrWorkerGlobalScope, WindowSessionStorage", "")
        .replace("interface ", "$type ")

    declaration = when (name) {
        "ChildNode",
        "ParentNode",
        -> declaration.replace("extends Node", "/* : Node */")

        else -> {
            declaration
                .replace(" extends ", " :\n")
                .replace(", ", ",\n")
                .replace("\nAnimatable,", "\n/* Animatable, */")
        }
    }

    var memberSource = source
        .substringAfter(" {\n")
        .removeSuffix("}")
        .removeSuffix(";\n")
        .trimIndent()

    if (memberSource.startsWith("("))
        return null

    val arrayType = if ("readonly length: number;" in memberSource) {
        val result = Regex("""\[index\: number\]\: (\w+)""")
            .find(memberSource)

        if (result != null) {
            when (val t = result.groupValues[1]) {
                "string" -> "String"
                else -> t
            }
        } else null
    } else null

    if (arrayType != null && name != "Window") {
        declaration += if (":" in declaration) "," else ":"
        declaration += "\nArrayLike<$arrayType>"
    }

    val typeProvider = TypeProvider(name, arrayType)

    val mainConstructor: String
    val additionalConstructors: String
    if (staticSource != null) {
        val constructors = getConstructors(name, staticSource)
        val firstConstructor = constructors.firstOrNull()
        mainConstructor = if (firstConstructor != null) {
            var result = firstConstructor.removePrefix("constructor")
            if ("\n" !in result && result != "()") {
                result = "(\n" + result.removeSurrounding("(", ")") + "\n)"
            }
            result
        } else ""

        additionalConstructors = constructors
            .drop(1)
            .joinToString("\n")
    } else {
        mainConstructor = ""
        additionalConstructors = ""
    }

    if (mainConstructor.isNotEmpty()) {
        declaration = if (":\n" in declaration) {
            declaration.replaceFirst(":\n", "$mainConstructor:")
        } else declaration + mainConstructor
    }

    var members = if (memberSource.isNotEmpty()) {
        var result = memberSource
            .splitToSequence(";\n")
            .mapNotNull { convertMember(it, typeProvider) }
            .joinToString("\n")

        result = when (name) {
            "Node",
            -> result
                .replace("val ownerDocument:", "open val ownerDocument:")

            "DOMMatrixReadOnly",
            "DOMPointReadOnly",
            "DOMRectReadOnly",
            -> result
                .replace("val ", "open val ")

            "DOMMatrix",
            "DOMPoint",
            "DOMRect",
            -> result
                .replace("var ", "override var ")

            "Document",
            "DocumentFragment",
            "DocumentType",
            "Attr",
            "CharacterData",
            "Element",
            -> result
                .replace("val ownerDocument:", "override val ownerDocument:")
                .replace("fun getElementById(", "override fun getElementById(")

            "HTMLSelectElement",
            -> result
                .replace("fun remove()", "override fun remove()")

            "HTMLOptionsCollection",
            -> result
                .replace("var length: Int", "override var length: Int")

            "CanvasPathDrawingStyles",
            -> result
                .replace("Number[]", "ReadonlyArray<Double>")
                .replace("<number>", "<Double>")

            else -> result
        }

        result
    } else ""

    members = sequenceOf(additionalConstructors, members)
        .filter { it.isNotEmpty() }
        .joinToString("\n\n")

    val modifier = when {
        name == "Animation" ||
                name == "DOMMatrixReadOnly" ||
                name == "DOMPointReadOnly" ||
                name == "DOMRectReadOnly"
        -> "open"

        // TEMP WA
        name == "Text" ||
                name == "Comment"
        -> "sealed"

        name in DOM_CSS_TYPES ||
                name in DOM_PARSING_TYPES ||
                name == "DataTransfer" ||
                name == "FileReader" ||
                name == "FontFaceSource" ||
                name == "XPathEvaluatorBase" ||
                name == "ARIAMixin" ||
                name == "HTMLOrSVGElement" ||
                name == "InnerHTML" ||
                name == "DocumentOrShadowRoot" ||
                name == "XMLHttpRequest" ||
                name == "XMLHttpRequestUpload" ||
                name.endsWith("Handlers") ||
                mainConstructor.isNotEmpty()
        -> ""

        name == "Element" ||
                name == "Document" ||
                name == "DocumentFragment"
        -> "abstract"

        type == "class" &&
                name.startsWith("HTML") &&
                name.endsWith("Element")
        -> "abstract"

        else -> "sealed"
    }

    val body = "$modifier external $declaration {\n$members\n}"

    val pkg = when {
        name == "RemotePlayback" -> "remoteplayback"

        name.startsWith("Touch") -> "dom.events"
        name in DOM_PARSING_TYPES -> "dom.parsing"
        name.startsWith("SVG") -> "dom.svg"

        name.startsWith("CSS") -> "cssom"
        name.startsWith("StyleSheet") -> "cssom"
        name == "MediaList" -> "cssom"
        name == "MediaQueryList" -> "cssom"
        name.startsWith("FontFace") -> "cssom.fonts"

        name.startsWith("Canvas") -> "canvas"
        name in CANVAS_TYPES -> "canvas"

        name in ANIMATION_TYPES -> "web.animations"

        name.startsWith("Clipboard") -> "web.clipboard"

        name == "History" -> "web.history"
        name == "Location" -> "web.location"

        name.startsWith("DeviceMotion") -> "web.device"

        name.startsWith("FileSystem") -> "web.filesystem"
        name.startsWith("File") -> "web.file"
        name.startsWith("Gamepad") -> "web.gamepad"

        name.startsWith("Geolocation") -> "web.geolocation"
        name.startsWith("Position") -> "web.geolocation"

        name.startsWith("Lock") -> "web.locks"
        name.startsWith("Navigator") -> "web.navigator"
        name.startsWith("Permission") -> "web.permissions"
        name.startsWith("Screen") -> "web.screen"

        name.startsWith("Speech") -> "web.speech"

        name == "ShareData" -> "web.share"
        name.startsWith("Storage") -> "web.storage"

        name.startsWith("EventSource") -> "web.sse"
        name.startsWith("XMLHttp") -> "web.xhr"

        name == "ARIAMixin" -> "dom.aria"
        name == "Selection" -> "dom.selection"

        name == "EventModifierInit" -> "dom.events"

        name.startsWith("Document") -> "dom"
        name in DOM_TYPES -> "dom"
        name in DOM_CSS_TYPES -> "dom.css"
        name in DOM_DATA_TYPES -> "dom.data"
        name in DOM_GEOMETRY_TYPES -> "dom.geometry"
        name == "XMLDocument" -> "dom.xml"

        name.startsWith("XPath") -> "dom.xpath"

        else -> "dom.html"
    }

    return ConversionResult(
        name = name,
        body = body,
        pkg = pkg,
    )
}

private fun getStaticSource(
    name: String,
    source: String,
): String? =
    source
        .substringAfter("\ndeclare var $name: {\n", "")
        .substringBefore(";\n};")
        .trimIndent()
        .removePrefix("prototype: $name;\n")
        .takeIf { it.isNotEmpty() }

private fun getConstructors(
    name: String,
    source: String,
): List<String> {
    val constructorSources = source
        .split(";\n")
        .filter { it.startsWith("new(") }
        .map { it.removePrefix("new(") }
        .map { it.substringBefore("): $name") }

    if (constructorSources.isEmpty())
        return emptyList()

    if (constructorSources.singleOrNull() == "")
        return emptyList()

    return constructorSources
        .map { convertConstructor(it) }
}

private fun convertConstructor(
    parametersSource: String,
): String {
    if (parametersSource == "")
        return ""

    val parameters = convertFunctionParameters(parametersSource)
    return "constructor($parameters)"
}

internal fun convertMember(
    source: String,
    typeProvider: TypeProvider,
): String? {
    if ("\n" in source) {
        val comment = source.substringBeforeLast("\n")
            .replace(";--\n", ";\n") // RESTORE

        if ("@deprecated" in comment)
            return null

        val member = convertMember(source.substringAfterLast("\n"), typeProvider)
            ?: return null

        return comment + "\n" + member
    }

    when {
        // TODO: fix later
        source.startsWith("closest<") -> return null

        source.startsWith("createElement<") -> return null
        source.startsWith("createElementNS") && ("namespaceURI:" in source) -> return null
        source.startsWith("getElementsByTagName<") -> return null
        source.startsWith("getElementsByTagNameNS(namespaceURI: \"") -> return null
        source.startsWith("get location(") -> return null
        source.startsWith("set location(") -> return null

        source.startsWith("addEventListener<") -> return null
        source.startsWith("addEventListener(") -> return null
        source.startsWith("removeEventListener<") -> return null
        source.startsWith("removeEventListener(") -> return null
        source.startsWith("createEvent(") -> return null
        source.startsWith("toString()") -> return null
    }

    if ((source.startsWith("on") && "(this: " in source)) {
        val handlerName = source.substringBefore(": ")
            .removeSuffix("?")

        var eventType = source
            .substringAfter(": ")
            .substringAfter("ev: ")
            .substringBefore(")")

        when (eventType) {
            "MessageEvent",
            "ProgressEvent",
            -> eventType += "<*>"
        }

        return "var $handlerName: EventHandler<$eventType>?"
    }

    if (source.startsWith("[index: number]:") && typeProvider.isArrayLike())
        return null

    if (source.startsWith("["))
        return "    // $source"

    if ("(" in source) {
        val isFun = if (": " in source) {
            source.indexOf(": ") > source.indexOf("(")
        } else true

        if (isFun)
            return convertFunction(source, typeProvider)
    }

    return convertProperty(source, typeProvider)
}

private fun convertProperty(
    source: String,
    typeProvider: TypeProvider,
): String? {
    val modifier = if (source.startsWith("readonly ")) "val" else "var"
    var (name, type) = source.removePrefix("readonly ").split(": ")

    val optional = type.endsWith(" | null")
    type = type.removeSuffix(" | null")

    val safeName = name.removeSuffix("?")
    if (!typeProvider.accepted(safeName))
        return null

    // Window
    if (safeName == "frames" && type == "WindowProxy")
        type = "ArrayLike<Window>"

    type = when (type) {
        "null" -> "Void"

        "any" -> "Any?"
        "string" -> "String"
        "boolean" -> "Boolean"

        "number",
        "number | string",
        -> typeProvider.numberType(safeName)

        "OnErrorEventHandler",
        -> "Function<Unit>? /* $type */"

        "ChildNode" -> "Node /* ChildNode */"
        "ParentNode" -> "Node /* ParentNode */"
        "NodeListOf<ChildNode>" -> "NodeListOf<Node /* ChildNode */>"

        // TEMP
        "CredentialsContainer",
        "MediaCapabilities",
        "MediaSession",

        "DocumentTimeline",
        -> "dynamic /* $type */"

        // TEMP
        "AudioBuffer",
        -> "Any /* $type */"

        // TEMP
        "StaticRange[]",
        -> "ReadonlyArray<Any /* StaticRange */>"

        // TODO: check
        "DOMException",
        -> "Throwable /* DOMException */"

        "Promise<any>" -> "Promise<*>"
        "DOMHighResTimeStamp" -> "HighResTimeStamp"
        "ReadonlyArray<string>" -> "ReadonlyArray<String>"
        "ReadonlyArray<number>" -> "ReadonlyArray<Double>"
        "LockInfo[]" -> "ReadonlyArray<LockInfo>"
        "File[]" -> "ReadonlyArray<File>"
        "CSSStyleSheet[]" -> "ReadonlyArray<CSSStyleSheet>"
        "MediaStream[]" -> "ReadonlyArray<MediaStream>"
        "Touch[]" -> "ReadonlyArray<Touch>"
        "PointerEvent[]" -> "ReadonlyArray<PointerEvent>"
        "MediaList | string" -> "Any /* MediaList | string */"
        "Element | ProcessingInstruction" -> "Any /* Element | ProcessingInstruction */"
        "string | CanvasGradient | CanvasPattern" -> "Any /* string | CanvasGradient | CanvasPattern */"
        "string | ArrayBuffer" -> "Any /* string | ArrayBuffer */"
        "(WindowProxy & typeof globalThis)" -> "WindowProxy"

        "HTMLCollectionOf<HTMLAnchorElement | HTMLAreaElement>",
        -> "HTMLCollectionOf<HTMLElement /* HTMLAnchorElement | HTMLAreaElement */>"

        else -> if (type.startsWith("\"")) {
            "String /* $type */"
        } else type
    }

    if (name.endsWith("?") || optional) {
        name = safeName

        if (!type.endsWith("?")) {
            type += "?"
        }
    }

    name = when (name) {
        "is", "as",
        -> "`$name`"

        else -> name
    }

    return "$modifier $name: $type"
}

private fun convertFunction(
    source: String,
    typeProvider: TypeProvider,
): String? {
    val nameSource = source.substringBefore("(")
    val name = nameSource.substringBefore("<")
    val typeParameters = nameSource
        .removePrefix(name)
        .replace(" extends ", " : ")

    if (!typeProvider.accepted(name))
        return null

    val parametersSource = source
        .substringAfter("(")
        .substringBefore("):")

    val parameters = convertFunctionParameters(parametersSource)

    val result = (": " + source.substringAfter("): "))
        .removeSuffix(": void")
        .replace(
            "SVGCircleElement | SVGEllipseElement | SVGImageElement | SVGLineElement | SVGPathElement | SVGPolygonElement | SVGPolylineElement | SVGRectElement | SVGTextElement | SVGUseElement",
            "SVGElement /* SVGCircleElement | SVGEllipseElement | SVGImageElement | SVGLineElement | SVGPathElement | SVGPolygonElement | SVGPolylineElement | SVGRectElement | SVGTextElement | SVGUseElement */"
        )
        .replace(": RadioNodeList | Element | null", ": Any? /* RadioNodeList | Element */")
        .replace(": Promise<number>", ": Promise<Number>")
        .replace(": Promise<FontFace[]>", ": Promise<ReadonlyArray<FontFace>>")
        .replace(": WebGLShader[]", ": ReadonlyArray<WebGLShader>")
        .replace(": GLuint[]", ": ReadonlyArray<GLuint>")
        .replace(": string[]", ": ReadonlyArray<String>")
        .replace("<string[]", "<ReadonlyArray<String>")
        .replace(": Element[]", ": ReadonlyArray<Element>")
        .replace(": Node[]", ": ReadonlyArray<Node>")
        .replace(": PointerEvent[]", ": ReadonlyArray<PointerEvent>")
        .replace(": StaticRange[]", ": ReadonlyArray<Any /* StaticRange */>")
        .replace(": Animation[]", ": ReadonlyArray<Animation>")
        .replace(": (Gamepad | null)[]", ": ReadonlyArray<Gamepad?>")
        .replace(": SpeechSynthesisVoice[]", ": ReadonlyArray<SpeechSynthesisVoice>")
        .replace(": number", ": Number")
        .replace(": string", ": String")
        .replace("<string>", "<String>")
        .replace(": boolean", ": Boolean")
        .replace("<boolean>", "<Boolean>")
        .replace(": any", ": Any")
        .replace("<void>", "<Void>")
        .replace(" | null", "?")

    return "fun $typeParameters$name($parameters)$result"
}

private fun convertFunctionParameters(
    source: String,
): String {
    val parameters = when (source) {
        "...nodes: (Element | Text)[]",
        -> listOf(
            "vararg nodes: Element /* | Text */",
        )

        "...nodes: (Node | string)[]",
        -> listOf(
            "vararg nodes: Any /* Node | string */",
        )

        "...text: string[]",
        -> listOf(
            "vararg text: String",
        )

        "action: (item: FontFace) => void",
        -> listOf(
            "action: (item: FontFace) -> Unit",
        )

        "action: (item: Node) => void",
        -> listOf(
            "action: (item: Node) -> Unit",
        )

        else -> source
            .splitToSequence(", ")
            .filter { it.isNotEmpty() }
            .map {
                var (pname, ptype) = it.split(": ")
                ptype = getParameterType(pname.removeSuffix("?"), ptype)

                if (pname.endsWith("?")) {
                    pname = pname.removeSuffix("?")
                    ptype += " = definedExternally"
                }

                pname = when (pname) {
                    "eventSourceInitDict",
                    "touchInitDict",
                    -> "init"

                    else -> pname
                }

                "$pname: $ptype"
            }
            .toList()
    }

    return if (parameters.size > 1) {
        parameters.joinToString(",\n", "\n", ",\n")
    } else parameters.joinToString("\n")
}

private fun getParameterType(
    name: String,
    source: String,
): String {
    if (source.endsWith(" | null")) {
        var type = getParameterType(name, source.removeSuffix(" | null"))
        if ("? /* " !in type)
            type += "?"

        return type
    }

    return when {
        source == "string | number[]"
        -> return "ReadonlyArray<Double> /* | String */"

        source == "File | string | FormData"
        -> "Any /* File | String | FormData */"

        source.startsWith("\"")
        -> "String /* $source */"

        source == "any"
        -> "Any?"

        source == "string"
        -> "String"

        source == "string | URL"
        -> "URL /* | string */"

        source == "number"
        -> if (name == "index") "Int" else "Number"

        source == "boolean"
        -> "Boolean"

        source == "BodyInit"
        -> "Any /* BodyInit */"

        source == "VibratePattern"
        -> "ReadonlyArray<Int> /* VibratePattern */"

        source == "string | ElementCreationOptions"
        -> "ElementCreationOptions /* | String */"

        source == "HTMLOptionElement | HTMLOptGroupElement"
        -> "HTMLElement /* HTMLOptionElement | HTMLOptGroupElement */"

        source == "HTMLElement | number"
        -> "Any? /* HTMLElement | number */"

        source == "string | BinaryData"
        -> "String /* | BinaryData */"

        source == "Document | XMLHttpRequestBodyInit"
        -> "XMLHttpRequestBodyInit /* Document */"

        source.endsWith("[]") -> {
            var atype = source.removeSuffix("[]")
            if (atype == "string")
                atype = "String"

            "ReadonlyArray<$atype>"
        }

        else -> source
    }
}

private fun convertType(
    source: String,
): ConversionResult {
    require(" = \"" in source)

    val (name, body) = source
        .substringBefore(";")
        .split(" = ")

    val values = body
        .splitToSequence(" | ")
        .map { it.removeSurrounding("\"") }
        .toList()

    return ConversionResult(
        name = name,
        body = unionBody(name, values),
        pkg = "dom.html"
    )
}
