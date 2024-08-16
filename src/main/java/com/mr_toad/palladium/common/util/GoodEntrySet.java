package com.mr_toad.palladium.common.util;

import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.DoNotCall;
import net.minecraft.MethodsReturnNonnullByDefault;

import org.jetbrains.annotations.Nullable;
import javax.annotation.CheckForNull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GoodEntrySet<K, V> extends AbstractSet<Map.Entry<K, V>> {

    private final K[] key;
    private final V[] value;
    private final int size;

    public GoodEntrySet(K[] key, V[] value, int size) {
        this.key = key;
        this.value = value;
        this.size = size;
    }

    @Override
    public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
        return new GoodEntrySetIterator<>(this.key, this.value, this.size);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean contains(@Nullable Object o) {
        return false;
    }

    @Deprecated
    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    public final boolean add(Map.Entry<K, V> e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    public final boolean remove(@CheckForNull Object object) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    public final boolean addAll(Collection<? extends Map.Entry<K, V>> newElements) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    public final boolean removeAll(Collection<?> oldElements) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    public final boolean removeIf(Predicate<? super Map.Entry<K, V>> filter) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    public final boolean retainAll(Collection<?> elementsToKeep) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    public final void clear() {
        throw new UnsupportedOperationException();
    }


}
