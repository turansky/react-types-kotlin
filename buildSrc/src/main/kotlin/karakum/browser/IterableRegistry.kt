package karakum.browser

import java.io.File

private val ITERATOR_REGEX = Regex("""\ninterface (\w+) \{\n\s+\[Symbol.iterator]\(\): IterableIterator<(.+)>;""")
private val ADDITIONAL_PARENT_REGEX = Regex("""\ninterface (\w+) extends (.+?) \{\n""")

object IterableRegistry {
    private lateinit var map: Map<String, String>
    private lateinit var additionalParentMap: Map<String, String>

    fun fill(
        definitionsDir: File,
        vararg additionalFiles: File,
    ) {
        map = definitionsDir
            .listFiles { file -> file.name.endsWith(".d.ts") }!!
            .asSequence()
            .filter { file -> file.name.startsWith("lib.es") }
            .plus(additionalFiles)
            .map { it.readText() }
            .map { it.replace("\r\n", "\n") }
            .flatMap { ITERATOR_REGEX.findAll(it) }
            .map { it.groupValues }
            .associate { it[1] to it[2] }

        additionalParentMap = additionalFiles
            .asSequence()
            .map { it.readText() }
            .map { it.replace("\r\n", "\n") }
            .flatMap { ADDITIONAL_PARENT_REGEX.findAll(it) }
            .map { it.groupValues }
            .associate { it[1] to it[2] }
    }

    fun typeParameter(type: String): String? {
        val result = map[type]
            ?: return null

        return when (result) {
            "string" -> "String"
            else -> result
        }
    }

    fun additionalParent(type: String): String? {
        if (type == "FileSystemDirectoryHandle") {
            require(additionalParentMap[type] == null)
            return "AsyncMapLike<String, FileSystemHandle>"
        }

        var result = additionalParentMap[type]
            ?: return null

        result = sequenceOf(
            "Set<" to "MutableSetLike<",
            "Map<" to "MutableMapLike<",
        ).fold(result) { acc, (from, to) ->
            if (acc.startsWith(from)) {
                acc.replaceFirst(from, to)
            } else acc
        }

        return result
            .replace("string", "String")
            .replace("number", "Number")
            .replace(" any>", " Any?>")
    }
}
