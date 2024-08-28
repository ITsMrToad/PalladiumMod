package com.mr_toad.palladium.core.mixin;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
import com.mojang.util.UUIDTypeAdapter;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Mixin(CompoundTag.class)
public abstract class CompoundTagMixin {

    @Unique private static final Gson palladium$GSON = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();

    @Mutable @Shadow @Final private Map<String, Tag> tags;

    @Inject(method = "<init>(Ljava/util/Map;)V", at = @At("RETURN"))
    private void straightElementMap(Map<String, Tag> tags, CallbackInfo ci) {
        this.tags = tags instanceof Object2ObjectMap ? tags : new Object2ObjectOpenHashMap<>(tags);
    }

  @SuppressWarnings("unchecked")
    @Redirect(method = "equals", at = @At(value = "INVOKE", target = "java/util/Objects.equals(Ljava/lang/Object;Ljava/lang/Object;)Z"))
    private boolean skullEquals(Object object1, Object object2) {
        if (Objects.equal(object1, object2)) {
            return true;
        }

        Tag skullOwner1 = ((Map<String, Tag>) object1).get("SkullOwner");
        Tag skullOwner2 = ((Map<String, Tag>) object2).get("SkullOwner");

        if (!(skullOwner1 instanceof CompoundTag) || !(skullOwner2 instanceof CompoundTag)) {
            return false;
        }

        GameProfile profile1 = NbtUtils.readGameProfile((CompoundTag) skullOwner1);
        GameProfile profile2 = NbtUtils.readGameProfile((CompoundTag) skullOwner2);

        if (profile1 == null || !profile1.equals(profile2)) {
            return false;
        }

        Optional<MinecraftProfileTexture> texture1 = this.palladium$profile(profile1);
        Optional<MinecraftProfileTexture> texture2 = this.palladium$profile(profile2);

        return texture1.isPresent() && texture2.isPresent() && texture1.get().getUrl().equals(texture2.get().getUrl());
    }

    @Unique
    private Optional<MinecraftProfileTexture> palladium$profile(GameProfile profile) {
        return this.palladium$getTexturesPayload(profile).map(p -> p.getTextures().get(MinecraftProfileTexture.Type.SKIN));
    }

    @Unique
    private Optional<MinecraftTexturesPayload> palladium$getTexturesPayload(GameProfile profile) {
        Property textureProperty = Iterables.getFirst(profile.getProperties().get("textures"), null);
        if (textureProperty == null) {
            return Optional.empty();
        }
        return Optional.of(palladium$GSON.fromJson(new String(Base64.decodeBase64(textureProperty.getValue()), StandardCharsets.UTF_8), MinecraftTexturesPayload.class));
    }
}
