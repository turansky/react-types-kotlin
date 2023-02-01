/// <reference types="node" />
import * as fs from 'fs';

export declare function createTempDirectory(): Promise<string>;

export declare function getArchiveFileSizeInBytes(filePath: string): number;

export declare function resolvePaths(patterns: string[]): Promise<string[]>;

export declare function unlinkFile(filePath: fs.PathLike): Promise<void>;

export declare function getCompressionMethod(): Promise<CompressionMethod>;

export declare function getCacheFileName(compressionMethod: CompressionMethod): string;

export declare function getGnuTarPathOnWindows(): Promise<string>;

export declare function assertDefined<T>(name: string, value?: T): T;

export declare function isGhes(): boolean;
