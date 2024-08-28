package com.mr_toad.palladium.core.mixin;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Animal.class)
public abstract class AnimalMixin {

    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private void addParticle(Level instance, ParticleOptions options, double x, double y, double z, double xOffset, double yOffset, double zOffset) {
        if (!instance.isClientSide()) {
            ((ServerLevel) instance).sendParticles(options, x, y, z, 1, xOffset, yOffset, zOffset, 0.0);
        } else {
            instance.addParticle(options, x, y, z, xOffset, yOffset, zOffset);
        }
    }

}
