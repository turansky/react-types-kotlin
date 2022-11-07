package karakum.browser

internal const val EVENT_TYPE = "EventType"

private val PACKAGE_MAP = mapOf(
    "AbortSignal" to "web.abort",
    "Animation" to "web.animations",
    "AudioScheduledSourceNode" to "web.audio",
    "AudioWorkletNode" to "web.audio",
    "BaseAudioContext" to "web.audio",
    "Document" to "dom.events",
    "Element" to "dom.events",
    "EventSource" to "web.sse",
    "FontFaceSet" to "dom.events",
    "GlobalEventHandlers" to "dom.events",
    "HTMLMediaElement" to "dom.events",
    "HTMLVideoElement" to "dom.events",
    "HTMLVideoElement" to "dom.events",
    "IDBDatabase" to "web.idb",
    "IDBOpenDBRequest" to "web.idb",
    "IDBRequest" to "web.idb",
    "IDBTransaction" to "web.idb",
    "MediaDevices" to "media.capture",
    "MediaKeySession" to "media.key",
    "MediaRecorder" to "media.capture",
    "MediaSource" to "media.source",
    "MediaStreamTrack" to "media.stream",
    "Notification" to "web.notifications",
    "PaymentRequest" to "web.payment",
    "Performance" to "web.performance",
    "PermissionStatus" to "web.permissions",
    "PictureInPictureWindow" to "dom.events",
    "RTCDataChannel" to "webrtc",
    "RTCDtlsTransport" to "webrtc",
    "RTCIceTransport" to "webrtc",
    "RTCPeerConnection" to "webrtc",
    "RTCSctpTransport" to "webrtc",
    "RemotePlayback" to "remoteplayback",
    "ScreenOrientation" to "web.screen",
    "ServiceWorker" to "serviceworkers",
    "ServiceWorkerContainer" to "serviceworkers",
    "ServiceWorkerRegistration" to "serviceworkers",
    "ShadowRoot" to "dom.events",
    "SourceBuffer" to "media.source",
    "SourceBufferList" to "media.source",
    "SpeechSynthesis" to "web.speech",
    "TextTrack" to "webvtt",
    "TextTrackCue" to "webvtt",
    "TextTrackList" to "webvtt",
    "VisualViewport" to "dom.events",
    "WebSocket" to "websockets",
    "Window" to "dom.events",
    "WindowEventHandlers" to "dom.events",
    "XMLHttpRequest" to "web.xhr",
)

private data class EventData(
    val name: String,
    val type: String,
    val pkg: String,
) {
    val typeName: String = type.substringBefore("<")
}

private val ADDITIONAL_EVENTS = listOf(
    EventData(
        name = "webkitfullscreenchange",
        type = "Event",
        pkg = "dom.events", // ???
    ),
)

private val EXCLUDED = setOf(
    "MessageEvent",

    // deprecated
    "AudioProcessingEvent",
    "MediaRecorderErrorEvent",
    "SecurityPolicyViolationEvent",
)

internal fun eventDeclarations(
    content: String,
): List<ConversionResult> =
    eventTypes(content)
        .plus(eventPlaceholders(content))

private fun eventPlaceholders(
    source: String,
): List<ConversionResult> =
    EVENT_DATA
        .filter { !it.existed }
        .mapNotNull { info ->
            event(
                source = source,
                name = info.name,
                pkg = info.pkg,
            )
        }

private fun event(
    source: String,
    name: String,
    pkg: String,
): ConversionResult {
    val initName = "${name}Init"
    val initSource = source
        .substringAfter("\ninterface $initName ", "")
        .substringBefore(";\n}\n")

    val initBody = if (initSource.isNotEmpty()) {
        val parentDeclaration = initSource
            .substringBefore("{\n")
            .replace("extends ", ": ")

        val typeProvider = TypeProvider(initName)

        val members = initSource.substringAfter("{\n")
            .trimIndent()
            .splitToSequence(";\n")
            .mapNotNull { convertMember(it, typeProvider) }
            .joinToString("\n")

        "external interface $initName $parentDeclaration {\n$members\n}"
    } else ""

    val eventSource = source
        // ProgressEvent
        .replace("<T extends EventTarget = EventTarget>", "")
        .substringAfter("\ninterface $name extends ")
        .substringBefore(";\n}\n")

    val eventParent = eventSource.substringBefore(" {\n")
    val typeProvider = TypeProvider(name)

    val eventMembers = eventSource.substringAfter(" {\n")
        .trimIndent()
        .splitToSequence(";\n")
        .mapNotNull { convertMember(it, typeProvider) }
        .joinToString("\n")
        // ProgressEvent
        .replace("val target: T?", "override val target: T?")

    val eventClassBody = source
        .substringAfter("\ndeclare var $name: {\n")
        .substringBefore(";\n};")
        .trimIndent()
        .removePrefix("prototype: $name;\n")

    val constructorSource = eventClassBody
        .removePrefix("new(")
        .substringBefore("): $name")

    val eventConstructor = if (constructorSource.isNotEmpty()) {
        constructorSource
            .split(", ")
            .map { p ->
                if ("?: " in p) {
                    p.replace("?: ", ": ") + " = definedExternally"
                } else p.replace(": string", ": String")
            }
            .map {
                if ("InitDict: " in it || "Init: " in it) {
                    "init: " + it.substringAfter(": ")
                } else it
            }
            .joinToString(",\n", "(\n", "\n)")
    } else ""

    val companionSource = eventClassBody
        .substringAfter("\n", "")

    val companion = if (companionSource.isNotEmpty()) {
        val members = companionSource
            .splitToSequence(";\n")
            .mapNotNull { convertMember(it, typeProvider) }
            .joinToString("\n")

        "companion object {\n$members\n}"
    } else "companion object"

    val modifier = if (eventConstructor.isNotEmpty()) "open" else "sealed"
    val typeParameters = if (name == "ProgressEvent") {
        "<T : EventTarget>"
    } else ""

    val eventBody = """    
    $modifier external class $name$typeParameters $eventConstructor : $eventParent {
        $eventMembers
    
        $companion
    }            
    """.trimIndent()

    val body = sequenceOf(
        """
        import web.events.Event
        import web.events.EventInit
        """.trimIndent(),
        initBody,
        eventBody,
    ).filter { it.isNotEmpty() }
        .joinToString("\n\n")


    return ConversionResult(
        name = name,
        body = body,
        pkg = pkg,
    )
}

private fun eventTypes(
    content: String,
): List<ConversionResult> =
    Regex("""interface .+?EventMap \{\n    "[\s\S]+?\n\}""")
        .findAll(content)
        .flatMap { parseEvents(it.value) }
        .filter { it.name != "orientationchange" }
        .plus(ADDITIONAL_EVENTS)
        .distinct()
        .groupBy { it.typeName }
        .filter { it.key !in EXCLUDED }
        .map { it.value }
        .flatMap { it.groupBy { it.pkg }.values }
        .map { items -> eventTypes(items) }

private fun eventTypes(
    items: List<EventData>,
): ConversionResult {
    val firstItem = items.first()
    val typeName = firstItem.typeName

    val info = EVENT_INFO_MAP.getValue(typeName)
    val imports = "import ${info.fqn}"

    val members = items
        .sortedBy { it.name }
        .map { (name, type) ->
            val memberName = EVENT_CORRECTION_MAP
                .getOrDefault(name, name)
                .toUpperCase()

            val finalType = if (type == "ProgressEvent") "$type<*>" else type

            """
            inline val $typeName.Companion.$memberName : $EVENT_TYPE<$finalType>
                get() = $EVENT_TYPE("$name")                        
            """.trimIndent()
        }

    val body = sequenceOf(imports)
        .plus(members)
        .joinToString("\n\n")

    val pkg = firstItem.pkg
        .takeIf { it.isNotEmpty() }

    return ConversionResult(
        name = "$typeName.types",
        body = body,
        pkg = pkg,
    )
}

private fun parseEvents(
    source: String,
): Sequence<EventData> {
    val mapId = source.substringBefore("EventMap")
        .substringAfterLast(" ")

    return source.splitToSequence("\n")
        .mapNotNull { parseEventData(it, mapId) }
}

private fun parseEventData(
    source: String,
    mapId: String,
): EventData? {
    if (!source.endsWith(";")) return null
    if (mapId == "AbortSignal") return null
    if (mapId == "Performance") return null

    val (name, type) = source
        .removeSurrounding("    \"", ";")
        .split("\": ", "<")

    val pkg = if (type == "Event") {
        PACKAGE_MAP.getValue(mapId)
    } else ""

    return EventData(
        name = name,
        type = type,
        pkg = pkg,
    )
}
