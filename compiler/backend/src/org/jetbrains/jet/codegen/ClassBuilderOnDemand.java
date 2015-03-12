/*
 * Copyright 2010-2013 JetBrains s.r.o.
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

package org.jetbrains.jet.codegen;

import kotlin.Function0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.storage.LockBasedStorageManager;
import org.jetbrains.jet.storage.NotNullLazyValue;

public class ClassBuilderOnDemand extends DelegatingClassBuilder {
    private final NotNullLazyValue<ClassBuilder> classBuilder;

    public ClassBuilderOnDemand(@NotNull Function0<ClassBuilder> createClassBuilder) {
        this.classBuilder = LockBasedStorageManager.NO_LOCKS.createLazyValue(createClassBuilder);
    }

    @Override
    @NotNull
    protected ClassBuilder getDelegate() {
        return classBuilder.invoke();
    }

    @Override
    public void done() {
        if (classBuilder.isComputed()) {
            classBuilder.invoke().done();
        }
    }
}