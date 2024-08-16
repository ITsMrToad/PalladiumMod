package com.mr_toad.palladium.common.util;

import com.google.common.collect.UnmodifiableIterator;

import java.util.Map;

public class GoodEntrySetIterator<K, V> extends UnmodifiableIterator<Map.Entry<K, V>> {
    private final K[] key;
    private final V[] value;

    private int remaining;
    private int idx;

    public GoodEntrySetIterator(K[] key, V[] value, int remaining) {
        this.remaining = remaining;
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean hasNext() {
        return this.remaining > 0;
    }

    @Override
    public Map.Entry<K, V> next() {
        this.skipEmpty();

        Map.Entry<K, V> entry = new GoodImmutableMapEntry<>(this.key[this.idx], this.value[this.idx]);

        this.remaining--;
        this.idx++;

        return entry;
    }

    private void skipEmpty() {
        while (this.key[this.idx] == null) {
            this.idx++;
        }
    }
}