package karakum.typescript

import java.io.File

internal data class ConversionResult(
    val name: String,
    val body: String,
)

internal fun convertDefinitions(
    definitionsFile: File,
): Sequence<ConversionResult> =
    definitionsFile.readText()
        .splitToSequence("declare namespace ts {\n")
        .map { it.substringBefore("\n}\n") }
        .map { it.trimIndent() }
        .flatMap { convertDefinitions(it) }

private const val DELIMITER = "<!--DELIMITER-->"
private val KEYWORDS = setOf(
    "const",
    "function",
    "type",
    "interface",
    "class",
)

private fun convertDefinitions(
    source: String,
): Sequence<ConversionResult> {
    var content = source.replace("\n/**", "\n$DELIMITER/**")
    for (keyword in KEYWORDS) {
        content = content
            .replace("\nexport $keyword ", "\n${DELIMITER}$keyword ")
            .replace("\n$keyword ", "\n${DELIMITER}$keyword ")
    }

    var comment: String? = null
    val results = mutableListOf<ConversionResult>()
    for (part in content.splitToSequence(DELIMITER).drop(1)) {
        if (part.startsWith("/**")) {
            comment = part
        } else {
            results += convertDefinition(comment, part)
        }
    }

    return results.asSequence()
}

private fun convertDefinition(
    comment: String?,
    source: String,
): ConversionResult {
    val name = source.substringAfter(" ")
        .substringBefore(" ")
        .substringBefore("<")
        .substringBefore("(")
        .substringBefore(":")

    val body = sequenceOf(comment, source)
        .filterNotNull()
        .joinToString("\n") {
            it.removeSuffix("\n")
        }

    return ConversionResult(name, body)
}
