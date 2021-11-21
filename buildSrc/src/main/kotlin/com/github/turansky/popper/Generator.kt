package com.github.turansky.popper

import com.github.turansky.common.GENERATOR_COMMENT
import com.github.turansky.common.Suppress
import com.github.turansky.common.fileSuppress
import java.io.File
import java.io.FileFilter

fun generateKotlinDeclarations(
    definitionsDir: File,
    sourceDir: File,
) {
    generateCoreDeclarations(
        definitionsDir = definitionsDir,
        sourceDir = sourceDir,
    )

    generateModifiersDeclarations(
        definitionsDir = definitionsDir,
        sourceDir = sourceDir,
    )
}

private fun generateCoreDeclarations(
    definitionsDir: File,
    sourceDir: File,
) {
    val targetDir = sourceDir
        .resolve(Package.CORE.path)
        .also { it.mkdirs() }

    val types = convertDefinitions(definitionsDir.resolve("types.d.ts").readText())
        .plus(ModifierName())
        .plus(enums())

    for ((name, body) in types) {
        val suppresses = mutableListOf<Suppress>().apply {
            if ("JsName(\"\"\"(" in body)
                add(Suppress.NAME_CONTAINS_ILLEGAL_CHARS)
        }.toTypedArray()

        val annotations = when {
            suppresses.isNotEmpty()
            -> fileSuppress(*suppresses)

            else -> ""
        }

        targetDir.resolve("$name.kt")
            .writeText(fileContent(Package.CORE, annotations, body))
    }
}

private fun generateModifiersDeclarations(
    definitionsDir: File,
    sourceDir: File,
) {
    val targetDir = sourceDir
        .resolve(Package.MODIFIERS.path)
        .also { it.mkdirs() }

    val modifierFiles = definitionsDir
        .resolve("modifiers")
        .listFiles(FileFilter { it.name.endsWith(".d.ts") && it.name != "index.d.ts" })
        ?: return

    val modifiers = modifierFiles
        .flatMap { convertModifier(it.readText()) }

    for ((name, body) in modifiers) {
        val suppresses = mutableListOf<Suppress>().apply {
            if ("JsName(\"\"\"(" in body)
                add(Suppress.NAME_CONTAINS_ILLEGAL_CHARS)
        }.toTypedArray()

        val annotations = when {
            suppresses.isNotEmpty()
            -> fileSuppress(*suppresses)

            else -> ""
        }

        targetDir.resolve("$name.kt")
            .writeText(fileContent(Package.MODIFIERS, annotations, body))
    }
}

private fun fileContent(
    pkg: Package,
    annotations: String,
    body: String,
): String {
    return sequenceOf(
        "// $GENERATOR_COMMENT",
        annotations,
        pkg.pkg,
        body,
    ).filter { it.isNotEmpty() }
        .joinToString("\n\n")
}
