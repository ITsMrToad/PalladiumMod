package com.mr_toad.palladium.common.util;

import com.google.errorprone.annotations.DoNotCall;
import com.mojang.datafixers.util.Pair;

import java.util.function.Function;

public class ImmutablePair<F, S> extends Pair<F, S> {

    public ImmutablePair(F first, S second) {
        super(first, second);
    }

    public static<F, S> ImmutablePair<F, S> of(F first, S second) {
        return new ImmutablePair<>(first, second);
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final <F2> Pair<F2, S> mapFirst(Function<? super F, ? extends F2> function) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @DoNotCall("Always throws UnsupportedOperationException")
    @Override
    public final  <S2> Pair<F, S2> mapSecond(Function<? super S, ? extends S2> function) {
        throw new UnsupportedOperationException();
    }
}
