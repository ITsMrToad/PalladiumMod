package com.mr_toad.palladium.common;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;

import java.util.Objects;

public class Deduplicator<T> {

    private final ObjectOpenCustomHashSet<T> pool;

    private int attemptedInsertions = 0;
    private int deduplicated = 0;

    public Deduplicator() {
        this(new Hash.Strategy<>() {
            @Override
            public int hashCode(T o) {
                return Objects.hashCode(o);
            }

            @Override
            public boolean equals(T a, T b) {
                return Objects.equals(a, b);
            }
        });
    }

    public Deduplicator(Hash.Strategy<T> strategy) {
        this.pool = new ObjectOpenCustomHashSet<>(strategy);
    }

    public synchronized T deduplicate(T item) {
        this.attemptedInsertions++;
        T result = this.pool.addOrGet(item);
        if (result != item) this.deduplicated++;
        return result;
    }

    public synchronized void clearCache() {
        this.attemptedInsertions = 0;
        this.deduplicated = 0;
        this.pool.clear();
    }

    @Override
    public synchronized String toString() {
        return String.format("DeduplicationCache ( %d/%d de-duplicated, %d pooled )", this.deduplicated, this.attemptedInsertions, this.pool.size());
    }
}
