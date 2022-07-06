package karakum.table

import java.io.File

internal data class ConversionResult(
    val name: String,
    val body: String,
)

private val EXCLUDED_ITEMS = setOf(
    "IfDefined",
    "memo",
    "Overwrite",

    "NoInfer",
    "PartialKeys",
    "RequiredKeys",
    "UnionToIntersection",
)

internal fun convertDefinitions(
    definitionFile: File,
): Sequence<ConversionResult> =
    definitionFile.readText()
        .substringBefore("\n\nexport {")
        .splitToSequence("\ndeclare ")
        .drop(1)
        .map { it.removeSuffix(";") }
        .filter { " = keyof typeof " !in it }
        .map { convertDefinition(it) }
        .filter { it.name !in EXCLUDED_ITEMS }

private fun convertDefinition(
    source: String,
): ConversionResult {
    val type = source.substringBefore(" ")
    val body = source.substringAfter(" ")

    return when (type) {
        "const" -> convertConst(body)
        "function" -> convertFunction(body)
        "type" -> convertType(body)
        else -> TODO()
    }
}

private fun convertConst(
    source: String,
): ConversionResult {
    val name = source.substringBefore(":")
    val type = if (": {" in source) "object" else "val"

    val body = source.replace(": {", " {")
        .replace("\n    ", "\n    val ")
        .replace("<any>", "<*>")
        .replace("number", "Double")

    val content = "external $type $body"
    return ConversionResult(name, content)
}

private fun convertFunction(
    source: String,
): ConversionResult {
    val name = source
        .substringBefore(" ")
        .substringBefore(":")
        .substringBefore("<")
        .substringBefore("(")

    val body = source.removePrefix(name)
        .replaceFirst("(", " $name(")
        .replace(" extends ", " : ")
        .replace(" => void", " -> Unit")
        .replace(" => ", " -> ")
        .replace(": string[]", ": ReadonlyArray<String>")
        .replace(" TNode[]", " ReadonlyArray<TNode>")
        .replace(": Column<TData>[]", ": ReadonlyArray<Column<TData>>")
        .replace(": HeaderGroup<TData>[]", ": ReadonlyArray<HeaderGroup<TData>>")
        .replace("?: Column<TData>", ": Column<TData>?")
        .replace(": string", ": String")
        .replace(": number", ": Number")

    return ConversionResult(name, "external fun " + body)
}

private fun convertType(
    source: String,
): ConversionResult =
    if (" = {\n" in source) {
        convertInterface(source)
    } else {
        convertTypealias(source)
    }

private fun convertTypealias(
    source: String,
): ConversionResult {
    val name = source
        .substringBefore(" ")
        .substringBefore(":")
        .substringBefore("<")
        .substringBefore("(")

    var declaration = source.substringBefore(" = ")
        .replace(" extends ", " : ")

    var body = source.substringAfter(" = ")
        .replace(" => ", " -> ")

    if ("&" in body && "{" !in body) {
        val interfaceBody = body
            .removeSurrounding("Partial<", ">")
            .replace(" & ", ",\n")

        return ConversionResult(name, "external interface $declaration :\n$interfaceBody")
    }

    declaration = declaration
        .replace(": RowData>", "/* : RowData */>")
        .replace(": object>", "/* : Any */>")

    body = body
        .replace("string[]", "ReadonlyArray<String>")
        .replace("ColumnFilter[]", "ReadonlyArray<ColumnFilter>")
        .replace("ColumnSort[]", "ReadonlyArray<ColumnSort>")
        .replace("Row<TData>[]", "ReadonlyArray<Row<TData>>")
        .replace("string", "String")
        .replace("boolean", "Boolean")
        .replace("number", "Number")
        .replace(": any", ": Any")
        .replace(" -> any", " -> Any")
        .replace("<any>", "<*>")
        .replace(" -> void", " -> Unit")

    return ConversionResult(name, "typealias $declaration = $body")
}

private fun convertInterface(
    source: String,
): ConversionResult {
    val declaration = source.substringBefore(" = {")
        .replace(" extends ", " : ")
    val name = declaration.substringBefore("<")

    val body = "{\n" + convertMembers(source.substringAfter(" = {")) + "\n}\n"
    return ConversionResult(name, "external interface $declaration$body")
}

private fun convertMembers(
    source: String,
): String {
    val content = source
        .removeSuffix("\n")
        .substringBeforeLast("\n}")
        .trimIndent()

    if ("\ngetContext: () => {\n" in content) {
        val contextBody = content
            .substringAfter("\ngetContext: () => {\n")
            .substringBefore("\n}")

        return sequenceOf(
            convertMembers(content.replace("() => {\n$contextBody\n}", "() -> Context<TData>")),
            "interface Context<TData : RowData> {\n" + convertMembers(contextBody) + "\n}\n",
        ).joinToString("\n\n")
    }

    return content
        .splitToSequence("\n")
        .filter { !it.startsWith("_") }
        .map { it.removeSuffix(";") }
        .map { convertMember(it) }
        .joinToString("\n")
}

private fun convertMember(
    source: String,
): String {
    val optional = source.substringBefore(": ")
        .endsWith("?")

    val name = source.substringBefore(": ")
        .removeSuffix("?")

    var type = source.substringAfter(": ")
        .let { kotlinType(it, name) }

    if (optional) {
        type = if (type.startsWith("(")) {
            "($type)?"
        } else "$type?"
    }

    return "var $name: $type"
}
