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

package org.jetbrains.kotlin.util.slicedMap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SlicedMapImpl implements MutableSlicedMap {

    public static SlicedMapImpl create() {
        return new SlicedMapImpl();
    }
    private static final Map<Key, WritableSlice> mapKeyToSlice = new IdentityHashMap<Key, WritableSlice>();

    // TODO replace Object with PsiElement?
    private final Map<Object, MyUserDataHolder> map = new HashMap<Object, MyUserDataHolder>();
    private final Multimap<WritableSlice<?, ?>, Object> collectiveSliceKeys = ArrayListMultimap.create();

    @Override
    public <K, V> void put(WritableSlice<K, V> slice, K key, V value) {
        if (!slice.check(key, value)) {
            return;
        }

        MyUserDataHolder holder = map.get(key);
        if (holder == null) {
            holder = new MyUserDataHolder();
            map.put(key, holder);
        }

        Key<V> mapKey = slice.getKey();

        RewritePolicy rewritePolicy = slice.getRewritePolicy();
        if (rewritePolicy.rewriteProcessingNeeded(key)) {
            V oldValue = holder.getUserData(mapKey);
            if (oldValue != null) {
                //noinspection unchecked
                if (!rewritePolicy.processRewrite(slice, key, oldValue, value)) {
                    return;
                }
            }
        }

        if (slice.isCollective()) {
            collectiveSliceKeys.put(slice, key);
        }

        mapKeyToSlice.put(mapKey, slice);
        holder.putUserData(mapKey, value);
        slice.afterPut(this, key, value);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public <K, V> V get(ReadOnlySlice<K, V> slice, K key) {
        MyUserDataHolder holder = map.get(key);
        Key<V> mapKey = slice.getKey();

        V value = null;
        if (holder != null) {
            value = holder.getUserData(mapKey);
        }

        //???
        return slice.computeValue(this, key, value, value == null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <K, V> Collection<K> getKeys(WritableSlice<K, V> slice) {
        assert slice.isCollective() : "Keys are not collected for slice " + slice;
        return (Collection<K>) collectiveSliceKeys.get(slice);
    }

    @Override
    public <K, V> V remove(RemovableSlice<K, V> slice, K key) {
        UserDataHolder holder = map.get(key);

        if (holder == null) return null;

        Key<V> mapKey = slice.getKey();
        V value = holder.getUserData(mapKey);
        holder.putUserData(mapKey, null);

        //TODO remove holder if empty?

        return value;
    }

    @NotNull
    @Override
    public Iterator<Map.Entry<SlicedMapKey<?, ?>, ?>> iterator() {
        Map<SlicedMapKey<?, ?>, Object> mmap = new HashMap<SlicedMapKey<?, ?>, Object>();

        for (Map.Entry<Object, MyUserDataHolder> entry : map.entrySet()) {

            MyUserDataHolder holder = entry.getValue();

            Object key = entry.getKey();

            BitSet keyset = holder.getKeyset();

            for (int i = keyset.nextSetBit(0); i >= 0; i = keyset.nextSetBit(i + 1)) {
                Key<?> keyByIndex = Key.getKeyByIndex(i);
                WritableSlice slice = mapKeyToSlice.get(keyByIndex);
                Object value = holder.getUserData(keyByIndex);

                mmap.put(new SlicedMapKey(slice, key), value);
            }
        }

        return (Iterator) mmap.entrySet().iterator();
    }

    @NotNull
    @Override
    public <K, V> ImmutableMap<K, V> getSliceContents(@NotNull ReadOnlySlice<K, V> slice) {
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();

        for (Map.Entry<Object, MyUserDataHolder> entry : map.entrySet()) {

            UserDataHolder holder = entry.getValue();

            V value = holder.getUserData(slice.getKey());

            if (value != null) {
                //noinspection unchecked
                builder.put((K) entry.getKey(), value);
            }
        }
        return builder.build();
    }
}
