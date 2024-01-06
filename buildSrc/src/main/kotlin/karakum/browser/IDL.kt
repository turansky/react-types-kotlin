package karakum.browser

import java.io.File

internal data class ParameterData(
    val className: String,
    val methodName: String,
    val parameterName: String,
    val parameterType: String,
)

internal object IDLRegistry {
    lateinit var rootDirectory: File

    private val idlData: List<String> by lazy {
        rootDirectory
            .listFiles { file -> file.extension == "idl" }!!
            .map { it.readText() }
    }

    private fun hasContent(
        memberContent: String,
    ): Set<String> =
        idlData.asSequence()
            .flatMap { content ->
                content
                    .splitToSequence("\ninterface ")
                    .drop(1)
                    .map { it.substringBefore("\n};") }
                    .filter { memberContent in it }
                    .map { it.substringBefore(" ") }
            }
            .toSet()

    private val typesWithEmptyConstructors: Set<String> by lazy {
        hasContent("  constructor();")
    }

    private val typesWithHtmlConstructors: Set<String> by lazy {
        hasContent("[HTMLConstructor] constructor();")
    }

    /* private */ val parameterData: List<ParameterData> by lazy {
        idlData.flatMap { content ->
            content
                .splitToSequence("\ninterface ", "\npartial interface ")
                .drop(1)
                .map { it.substringBefore("\n};") }
                .flatMap { classBody ->
                    val className = classBody.substringBefore(" ")
                    classBody.substringAfter(" {\n")
                        .splitToSequence("\n")
                        .flatMap { line -> getParameterData(className = className, line = line) }
                }
        }
    }

    private fun getParameterData(
        className: String,
        line: String,
    ): Sequence<ParameterData> {
        val source = line
            .substringAfter("(", "")
            .substringBefore(")", "")
            .ifEmpty { return emptySequence() }

        val methodName = line
            .substringBefore("(")
            .substringAfterLast(" ")

        return source
            .splitToSequence(", ")
            .mapNotNull { psource ->
                val type = when (psource.substringBeforeLast(" ")) {
                    "double",
                    "unrestricted double",
                    -> "Double"

                    "long",
                    "unsigned long",
                    -> "Int"

                    "long long",
                    "unsigned long long",
                    -> "JsLong"

                    else -> return@mapNotNull null
                }

                ParameterData(
                    className = className,
                    methodName = methodName,
                    parameterName = psource.substringAfterLast(" "),
                    parameterType = type,
                )
            }
    }

    fun hasEmptyConstructor(type: String): Boolean =
        type in typesWithEmptyConstructors

    fun hasHtmlConstructor(type: String): Boolean =
        type in typesWithHtmlConstructors
}
