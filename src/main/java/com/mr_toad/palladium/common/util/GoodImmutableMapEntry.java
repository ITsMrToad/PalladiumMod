package com.mr_toad.palladium.common.util;

import com.google.errorprone.annotations.DoNotCall;

import java.util.Map;

public record GoodImmutableMapEntry<K, V>(K key, V value) implements Map.Entry<K, V> {

    @Override
    public K getKey() {
        return this.key();
    }

    @Override
    public V getValue() {
        return this.value();
    }

    @Override
    @Deprecated
    @DoNotCall("Always throw UnsupportedOperationException")
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }
}