// Automatically generated - do not modify!

@file:JsModule("node:fs/promises")
@file:JsNonModule

package node.fs

import kotlin.js.Promise

/**
 * Opens a `FileHandle`.
 *
 * Refer to the POSIX [`open(2)`](http://man7.org/linux/man-pages/man2/open.2.html) documentation for more detail.
 *
 * Some characters (`< > : " / \ | ? *`) are reserved under Windows as documented
 * by [Naming Files, Paths, and Namespaces](https://docs.microsoft.com/en-us/windows/desktop/FileIO/naming-a-file). Under NTFS, if the filename contains
 * a colon, Node.js will open a file system stream, as described by [this MSDN page](https://docs.microsoft.com/en-us/windows/desktop/FileIO/using-streams).
 * @since v10.0.0
 * @param [flags='r'] See `support of file system `flags``.
 * @param [mode=0o666] Sets the file mode (permission and sticky bits) if the file is created.
 * @return Fulfills with a {FileHandle} object.
 */
external fun open(
    path: PathLike,
    flags: dynamic /* string | number */ = definedExternally,
    mode: Mode = definedExternally,
): Promise<FileHandle>
