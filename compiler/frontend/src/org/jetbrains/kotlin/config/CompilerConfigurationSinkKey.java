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

package org.jetbrains.kotlin.config;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class CompilerConfigurationSinkKey<S extends Collection<T>, T> extends CompilerConfigurationKey<S> {
    protected CompilerConfigurationSinkKey(@NotNull @NonNls String name) {
        super(name);
    }

    abstract S createSink();
    abstract void add(S sink, T value);
    abstract void addAll(S sink, Collection<T> value);

    public static class ListKey<T> extends CompilerConfigurationSinkKey<List<T>, T> {
        protected ListKey(@NotNull @NonNls String name) {
            super(name);
        }

        @Override
        List<T> createSink() {
            return new ArrayList<T>();
        }

        @Override
        void add(List<T> sink, T value) {
            sink.add(value);
        }

        @Override
        void addAll(List<T> sink, Collection<T> value) {
            sink.addAll(value);
        }
    }

    public static class LinkedSetKey<T> extends CompilerConfigurationSinkKey<Set<T>, T> {
        protected LinkedSetKey(@NotNull @NonNls String name) {
            super(name);
        }

        @Override
        Set<T> createSink() {
            return new LinkedHashSet<T>();
        }

        @Override
        void add(Set<T> sink, T value) {
            sink.add(value);
        }

        @Override
        void addAll(Set<T> sink, Collection<T> value) {
            sink.addAll(value);
        }
    }

    public static <T> ListKey<T> createList(@NotNull @NonNls String name) {
        return new ListKey<T>(name);
    }

    public static <T> LinkedSetKey<T> createLinkedSet(@NotNull @NonNls String name) {
        return new LinkedSetKey<T>(name);
    }
}
