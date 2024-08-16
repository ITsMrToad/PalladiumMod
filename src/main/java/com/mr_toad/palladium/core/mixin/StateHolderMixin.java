package com.mr_toad.palladium.core.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;
import com.mojang.serialization.MapCodec;
import com.mr_toad.palladium.common.state.StateHolderCache;
import com.mr_toad.palladium.common.util.GoodImmutableTable;
import com.mr_toad.palladium.common.util.GoodImmutableMap;
import com.mr_toad.palladium.core.Palladium;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;

@Mixin(StateHolder.class)
public abstract class StateHolderMixin<O, S> {

    @Unique private GoodImmutableMap<Property<?>, Comparable<?>> palladium$newValues;

    @Mutable @Shadow @Final private ImmutableMap<Property<?>, Comparable<?>> values;
    @Shadow private Table<Property<?>, Comparable<?>, S> neighbours;
    @Shadow @Final protected O owner;

    @Shadow public abstract <T extends Comparable<T>> T getValue(Property<T> p_61144_);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void reinit(O owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<S> mapCodec, CallbackInfo ci) {
        if (Palladium.checkConfigAndGetValue(cfg -> cfg.enableModernStateHolder)) {
            this.palladium$newValues = new GoodImmutableMap<>(this.values);
        } else {
            this.palladium$newValues = new GoodImmutableMap<>();
        }
    }

    @Inject(method = "hasProperty", at = @At("RETURN"), cancellable = true)
    public <T extends Comparable<T>> void hasProperty(Property<T> property, CallbackInfoReturnable<Boolean> cir) {
        if (Palladium.checkConfigAndGetValue(cfg -> cfg.enableModernStateHolder)) {
            cir.setReturnValue(this.palladium$newValues.containsKey(property));
        }
    }

    @Inject(method = "getValue", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/properties/Property;getValueClass()Ljava/lang/Class;", shift = At.Shift.BEFORE), cancellable = true)
    public <T extends Comparable<T>> void getValue(Property<T> property, CallbackInfoReturnable<T> cir) {
        if (Palladium.checkConfigAndGetValue(cfg -> cfg.enableModernStateHolder)) {
            Comparable<?> comparable = this.values.get(property);
            cir.setReturnValue(property.getValueClass().cast(comparable));
            cir.cancel();
        }
    }

    @Inject(method = "getOptionalValue", at = @At(value = "INVOKE", target = "Ljava/util/Optional;of(Ljava/lang/Object;)Ljava/util/Optional;"), cancellable = true)
    public <T extends Comparable<T>> void getOptionalValue(Property<T> property, CallbackInfoReturnable<Optional<T>> cir) {
        if (Palladium.checkConfigAndGetValue(cfg -> cfg.enableModernStateHolder)) {
            cir.setReturnValue(Optional.of(this.getValue(property)));
        }
    }

    @Inject(method = "populateNeighbours", at = @At("RETURN"))
    private void postPopulateNeighbours(Map<Map<Property<?>, Comparable<?>>, S> states, CallbackInfo ci) {
        if (Palladium.checkConfigAndGetValue(cfg -> cfg.enableModernStateHolder)) {
            this.neighbours = new GoodImmutableTable<>(this.neighbours, StateHolderCache.getTableCache(this.owner));
        }
    }

}
