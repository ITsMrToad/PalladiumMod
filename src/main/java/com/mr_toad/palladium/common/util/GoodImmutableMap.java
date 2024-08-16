package com.mr_toad.palladium.common.util;

import com.google.errorprone.annotations.DoNotCall;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import org.checkerframework.checker.nullness.qual.Nullable;

import org.jetbrains.annotations.NotNull;
import javax.annotation.CheckForNull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

@ParametersAreNonnullByDefault
public class GoodImmutableMap<K, V> extends AbstractMap<K, V> {

    @LazyInit @RetainedWith protected transient K[] key;
    @LazyInit @RetainedWith protected transient V[] value;
    @LazyInit @RetainedWith protected transient int mask;
    @LazyInit @RetainedWith protected transient int size;

    public GoodImmutableMap() {}

    public GoodImmutableMap(Map<K, V> map) {
        this(map.size(), Hash.DEFAULT_LOAD_FACTOR);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.putInternal(entry.getKey(), entry.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private GoodImmutableMap(final int size, final float loadFactor) {
        if (loadFactor <= 0 || loadFactor > 1) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
        }

        if (size < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }

        int n = HashCommon.arraySize(size, loadFactor);

        this.key = (K[]) new Object[n];
        this.value = (V[]) new Object[n];
        this.mask = n - 1;
        this.size = size;
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new GoodEntrySet<>(this.key, this.value, this.size);
    }

    @Override
    public V get(final Object k) {
        int pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
        K curr = this.key[pos];

        if (curr == null) {
            return null;
        } else if (k == curr) {
            return this.value[pos];
        }

        while (true) {
            pos = pos + 1 & this.mask;
            curr = this.key[pos];

            if (curr == null) {
                return null;
            } else if (k == curr) {
                return this.value[pos];
            }
        }
    }

    private void putInternal(final K k, final V v) {
        final int pos = this.find(k);

        if (pos < 0) {
            int n = -pos - 1;

            this.key[n] = k;
            this.value[n] = v;
        } else {
            this.value[pos] = v;
        }
    }

    private int find(final K k) {
        int pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
        K curr = this.key[pos];

        if (curr == null) {
            return -(pos + 1);
        } else if (k == curr) {
            return pos;
        }

        while (true) {
            pos = pos + 1 & this.mask;
            curr = this.key[pos];
            if (curr == null) {
                return -(pos + 1);
            } else if (k == curr) {
                return pos;
            }
        }
    }

    @Deprecated
    @CheckForNull
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @CheckForNull
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final V putIfAbsent(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final boolean replace(K key, V oldValue, V newValue) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @CheckForNull
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final V replace(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final V compute(K key, BiFunction<? super K, ? super @Nullable V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @CheckForNull
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final V remove(@CheckForNull Object o) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final boolean remove(@CheckForNull Object key, @CheckForNull Object value) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }
}
