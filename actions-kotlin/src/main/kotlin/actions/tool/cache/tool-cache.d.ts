/// <reference types="node" />
import {OutgoingHttpHeaders} from 'http';

export declare class HTTPError extends Error {
    readonly httpStatusCode: number | undefined;

    constructor(httpStatusCode: number | undefined);
}

/**
 * Download a tool from an url and stream it into a file
 *
 * @param url       url of tool to download
 * @param dest      path to download tool
 * @param auth      authorization header
 * @param headers   other headers
 * @returns         path to downloaded tool
 */
export declare function downloadTool(url: string, dest?: string, auth?: string, headers?: OutgoingHttpHeaders): Promise<string>;

/**
 * Extract a .7z file
 *
 * @param file     path to the .7z file
 * @param dest     destination directory. Optional.
 * @param _7zPath  path to 7zr.exe. Optional, for long path support. Most .7z archives do not have this
 * problem. If your .7z archive contains very long paths, you can pass the path to 7zr.exe which will
 * gracefully handle long paths. By default 7zdec.exe is used because it is a very small program and is
 * bundled with the tool lib. However it does not support long paths. 7zr.exe is the reduced command line
 * interface, it is smaller than the full command line interface, and it does support long paths. At the
 * time of this writing, it is freely available from the LZMA SDK that is available on the 7zip website.
 * Be sure to check the current license agreement. If 7zr.exe is bundled with your action, then the path
 * to 7zr.exe can be pass to this function.
 * @returns        path to the destination directory
 */
export declare function extract7z(file: string, dest?: string, _7zPath?: string): Promise<string>;

/**
 * Extract a compressed tar archive
 *
 * @param file     path to the tar
 * @param dest     destination directory. Optional.
 * @param flags    flags for the tar command to use for extraction. Defaults to 'xz' (extracting gzipped tars). Optional.
 * @returns        path to the destination directory
 */
export declare function extractTar(file: string, dest?: string, flags?: string | string[]): Promise<string>;

/**
 * Extract a xar compatible archive
 *
 * @param file     path to the archive
 * @param dest     destination directory. Optional.
 * @param flags    flags for the xar. Optional.
 * @returns        path to the destination directory
 */
export declare function extractXar(file: string, dest?: string, flags?: string | string[]): Promise<string>;

/**
 * Extract a zip
 *
 * @param file     path to the zip
 * @param dest     destination directory. Optional.
 * @returns        path to the destination directory
 */
export declare function extractZip(file: string, dest?: string): Promise<string>;

/**
 * Caches a directory and installs it into the tool cacheDir
 *
 * @param sourceDir    the directory to cache into tools
 * @param tool          tool name
 * @param version       version of the tool.  semver format
 * @param arch          architecture of the tool.  Optional.  Defaults to machine architecture
 */
export declare function cacheDir(sourceDir: string, tool: string, version: string, arch?: string): Promise<string>;

/**
 * Caches a downloaded file (GUID) and installs it
 * into the tool cache with a given targetName
 *
 * @param sourceFile    the file to cache into tools.  Typically a result of downloadTool which is a guid.
 * @param targetFile    the name of the file name in the tools directory
 * @param tool          tool name
 * @param version       version of the tool.  semver format
 * @param arch          architecture of the tool.  Optional.  Defaults to machine architecture
 */
export declare function cacheFile(sourceFile: string, targetFile: string, tool: string, version: string, arch?: string): Promise<string>;

/**
 * Finds the path to a tool version in the local installed tool cache
 *
 * @param toolName      name of the tool
 * @param versionSpec   version of the tool
 * @param arch          optional arch.  defaults to arch of computer
 */
export declare function find(toolName: string, versionSpec: string, arch?: string): string;

/**
 * Finds the paths to all versions of a tool that are installed in the local tool cache
 *
 * @param toolName  name of the tool
 * @param arch      optional arch.  defaults to arch of computer
 */
export declare function findAllVersions(toolName: string, arch?: string): string[];

export declare type IToolRelease = mm.IToolRelease;
export declare type IToolReleaseFile = mm.IToolReleaseFile;

export declare function getManifestFromRepo(owner: string, repo: string, auth?: string, branch?: string): Promise<IToolRelease[]>;

export declare function findFromManifest(versionSpec: string, stable: boolean, manifest: IToolRelease[], archFilter?: string): Promise<IToolRelease | undefined>;

/**
 * Check if version string is explicit
 *
 * @param versionSpec      version string to check
 */
export declare function isExplicitVersion(versionSpec: string): boolean;

/**
 * Get the highest satisfiying semantic version in `versions` which satisfies `versionSpec`
 *
 * @param versions        array of versions to evaluate
 * @param versionSpec     semantic version spec to satisfy
 */
export declare function evaluateVersions(versions: string[], versionSpec: string): string;
