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

import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.JarFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.VfsImplUtil
import com.intellij.util.Function
import org.jetbrains.kotlin.idea.js
import kotlin.platform.platformStatic

public class JsMetaFileSystem : com.intellij.openapi.vfs.newvfs.ArchiveFileSystem() {
    companion object {
        platformStatic
        public val PROTOCOL: String = "js-meta"

        platformStatic
        fun getInstance(): JsMetaFileSystem = VirtualFileManager.getInstance().getFileSystem(PROTOCOL) as JsMetaFileSystem
    }

    override fun getProtocol(): String {
        return PROTOCOL
    }

    override fun extractRootPath(path: String): String {
        val jarSeparatorIndex = path.indexOf(JarFileSystem.JAR_SEPARATOR)
        assert(jarSeparatorIndex >= 0) { "Path passed to JarFileSystem must have jar separator '!/': " + path }
        return path.substring(0, jarSeparatorIndex + JarFileSystem.JAR_SEPARATOR.length())
    }

    override fun extractLocalPath(rootPath: String): String {
        return StringUtil.trimEnd(rootPath, JarFileSystem.JAR_SEPARATOR)
    }

    override fun composeRootPath(localPath: String): String {
        return localPath + JarFileSystem.JAR_SEPARATOR
    }

    override fun findFileByPath(path: String): VirtualFile? {
        return VfsImplUtil.findFileByPath(this, path)
    }

    override fun findFileByPathIfCached(path: String): VirtualFile? {
        return VfsImplUtil.findFileByPathIfCached(this, path)
    }

    override fun refreshAndFindFileByPath(path: String): VirtualFile? {
        return VfsImplUtil.refreshAndFindFileByPath(this, path)
    }

    override fun getHandler(entryFile: VirtualFile): org.jetbrains.kotlin.idea.js.KotlinJavascriptHandler {
        val localPath = extractLocalPath(this.extractRootPath(entryFile.getPath()))

        return VfsImplUtil.getHandler<org.jetbrains.kotlin.idea.js.KotlinJavascriptHandler>(this, localPath + ".kjmsarchive", object : Function<String, org.jetbrains.kotlin.idea.js.KotlinJavascriptHandler> {
            override fun `fun`(localPath: String): org.jetbrains.kotlin.idea.js.KotlinJavascriptHandler {
                return js.KotlinJavascriptHandler(localPath.substringBeforeLast(".kjmsarchive"))
            }
        })
    }

    override fun refresh(asynchronous: Boolean) {
        VfsImplUtil.refresh(this, asynchronous)
    }
}