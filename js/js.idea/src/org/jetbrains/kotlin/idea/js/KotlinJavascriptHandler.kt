/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.js

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.impl.ArchiveHandler
import com.intellij.util.ArrayUtil
import gnu.trove.THashMap
import org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtil
import org.jetbrains.kotlin.utils.KotlinJavascriptMetadataUtils
import java.io.File
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

public open class KotlinJavascriptHandler(path: String) : ArchiveHandler(path) {

    private val mapMeta: MutableMap<String, ByteArray> = hashMapOf()
    private val dirSet: MutableSet<String> = hashSetOf()
    private var initialized: Boolean = false

    private fun initialize() {
        if (initialized) return;

        val file = getFileToUse()
        val listMetadata = KotlinJavascriptMetadataUtils.loadMetadata(file)
        for(metadata in listMetadata) {
            val moduleName = metadata.moduleName
            dirSet.add(moduleName)
            KotlinJavascriptSerializationUtil.writeFiles(metadata.body) {
                filePath, fileContent ->
                mapMeta[moduleName + "/" + stripFirstSymbolIfSep(filePath)] = fileContent
            }
        }

        mapMeta.keySet().forEach {
            addToDirSet(it.substringBeforeLast('/'))
        }

        initialized = true
    }

    private fun stripFirstSymbolIfSep(path: String): String {
        if (path.startsWith('/')) return path.substring(1) else return path
    }

    private fun addToDirSet(path: String) {
        dirSet.add(path)
        val lastIndexOfSep = path.lastIndexOf('/')
        if (lastIndexOfSep >= 0) {
            val dirName = path.substring(0, lastIndexOfSep)
            dirSet.add(dirName)
            addToDirSet(dirName.substringBeforeLast('/'))
        }
    }

    throws(IOException::class)
    override fun createEntriesMap(): Map<String, ArchiveHandler.EntryInfo> {
        initialize()

        val map = THashMap<String, ArchiveHandler.EntryInfo>()
        map.put("", createRootEntry())

        dirSet.forEach {
            getOrCreate1(it, map)
        }

        mapMeta.forEach {
            val (entryName, entryBody) = it
            getOrCreate(entryName, map)
        }

        return map
    }

    protected open fun getFileToUse(): File {
        val path = getFile().getPath()
        val localPath = path.substringBeforeLast(".kjmsarchive")
        return File(localPath)
    }

    private fun getOrCreate(entry: String, map: MutableMap<String, ArchiveHandler.EntryInfo>): ArchiveHandler.EntryInfo {
        var isDirectory = dirSet.contains(entry)
        var entryName = entry
        if (StringUtil.endsWithChar(entryName, '/')) {
            entryName = entryName.substring(0, entryName.length() - 1)
            isDirectory = true
        }

        var info: ArchiveHandler.EntryInfo? = map.get(entryName)
        if (info != null) return info

        val path = splitPath(entryName)
        val parentInfo = getOrCreate(path.first, map)
        if ("." == path.second) {
            return parentInfo
        }
        info = ArchiveHandler.EntryInfo(parentInfo, path.second, isDirectory, ArchiveHandler.DEFAULT_LENGTH, ArchiveHandler.DEFAULT_TIMESTAMP)
        map.put(entryName, info)
        return info
    }

    private fun getOrCreate1(entryName: String, map: MutableMap<String, ArchiveHandler.EntryInfo>): ArchiveHandler.EntryInfo {
        var info: ArchiveHandler.EntryInfo? = map.get(entryName)

        if (info == null) {
            val path = splitPath(entryName)
            val parentInfo = getOrCreate(path.first, map)
            info = ArchiveHandler.EntryInfo(parentInfo, path.second, true, ArchiveHandler.DEFAULT_LENGTH, ArchiveHandler.DEFAULT_TIMESTAMP)
            map.put(entryName, info)
        }

        if (!info.isDirectory) {
            Logger.getInstance(javaClass).info(": " + entryName + " should be a directory")
            info = ArchiveHandler.EntryInfo(info.parent, info.shortName, true, info.length, info.timestamp)
            map.put(entryName, info)
        }

        return info
    }

    private fun getOrCreate(entry: ZipEntry, map: MutableMap<String, ArchiveHandler.EntryInfo>, zip: ZipFile): ArchiveHandler.EntryInfo {
        var isDirectory = entry.isDirectory()
        var entryName = entry.getName()
        if (StringUtil.endsWithChar(entryName, '/')) {
            entryName = entryName.substring(0, entryName.length() - 1)
            isDirectory = true
        }

        var info: ArchiveHandler.EntryInfo? = map.get(entryName)
        if (info != null) return info

        val path = splitPath(entryName)
        val parentInfo = getOrCreate(path.first, map, zip)
        if ("." == path.second) {
            return parentInfo
        }
        info = ArchiveHandler.EntryInfo(parentInfo, path.second, isDirectory, entry.getSize(), entry.getTime())
        map.put(entryName, info)
        return info
    }

    private fun getOrCreate(entryName: String, map: MutableMap<String, ArchiveHandler.EntryInfo>, zip: ZipFile): ArchiveHandler.EntryInfo {
        var info: ArchiveHandler.EntryInfo? = map.get(entryName)

        if (info == null) {
            val entry = zip.getEntry(entryName + "/")
            if (entry != null) {
                return getOrCreate(entry, map, zip)
            }

            val path = splitPath(entryName)
            val parentInfo = getOrCreate(path.first, map, zip)
            info = ArchiveHandler.EntryInfo(parentInfo, path.second, true, ArchiveHandler.DEFAULT_LENGTH, ArchiveHandler.DEFAULT_TIMESTAMP)
            map.put(entryName, info)
        }

        if (!info.isDirectory) {
            Logger.getInstance(javaClass).info(zip.getName() + ": " + entryName + " should be a directory")
            info = ArchiveHandler.EntryInfo(info.parent, info.shortName, true, info.length, info.timestamp)
            map.put(entryName, info)
        }

        return info
    }

    throws(IOException::class)
    override fun contentsToByteArray(relativePath: String): ByteArray {
        initialize()
        return mapMeta[relativePath] ?: ArrayUtil.EMPTY_BYTE_ARRAY
    }
}