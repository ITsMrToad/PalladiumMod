package com.mr_toad.palladium.common.util;

@FunctionalInterface
public interface ToBooleanFunction<T> {
    boolean applyAsBoolean(T obj);
}
