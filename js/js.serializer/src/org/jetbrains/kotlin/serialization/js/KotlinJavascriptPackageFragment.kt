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

package org.jetbrains.kotlin.serialization.js

import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.descriptors.impl.PackageFragmentDescriptorImpl
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.serialization.ProtoBuf
import org.jetbrains.kotlin.serialization.builtins.BuiltInsProtoBuf
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents
import org.jetbrains.kotlin.serialization.deserialization.NameResolver
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPackageMemberScope
import org.jetbrains.kotlin.storage.StorageManager
import java.io.InputStream
import kotlin.properties.Delegates

public class KotlinJavascriptPackageFragment(
        fqName: FqName,
        storageManager: StorageManager,
        module: ModuleDescriptor,
        private val loadResource: (path: String) -> InputStream?
) : PackageFragmentDescriptorImpl(module, fqName) {

    val nameResolver = NameResolver.read(
            loadResourceSure(KotlinJavascriptSerializationUtil.getStringTableFilePath(fqName))
    )

    private var components: DeserializationComponents by Delegates.notNull()

    public fun setDeserializationComponents(components: DeserializationComponents) {
        this.components = components
    }

    private val memberScope = storageManager.createLazyValue {
        val packageStream = loadResourceSure(KotlinJavascriptSerializationUtil.getPackageFilePath(fqName))
        val packageProto = ProtoBuf.Package.parseFrom(packageStream, KotlinJavascriptSerializationUtil.EXTENSION_REGISTRY)

        val classesStream = loadResourceSure(KotlinJavascriptSerializationUtil.getClassesInPackageFilePath(fqName))
        val classesProto = JsProtoBuf.Classes.parseFrom(classesStream, KotlinJavascriptSerializationUtil.EXTENSION_REGISTRY)

        DeserializedPackageMemberScope(this, packageProto, nameResolver, components, classNames = {
            classesProto.getClassNameList()?.map { id -> nameResolver.getName(id) } ?: listOf()
        })
    }

    override fun getMemberScope() = memberScope()

    private fun loadResourceSure(path: String): InputStream =
            loadResource(path) ?: throw IllegalStateException("Resource not found in classpath: $path")
}
