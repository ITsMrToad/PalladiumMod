package com.mr_toad.palladium.core.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

import javax.annotation.Nullable;
import javax.json.Json;
import java.lang.reflect.Type;

@MethodsReturnNonnullByDefault
public enum ResourceLocationDeduplication implements StringRepresentable {

    NONE("none"),
    ALL("all"),
    ONLY_RESOURCE_LOCATION("only_rl"),
    ONLY_MODEL_RESOURCE_LOCATION("only_mrl");

    private final String name;

    ResourceLocationDeduplication(String name) {
        this.name = name;
    }

    public static ResourceLocationDeduplication byName(String name) {
        return StringRepresentable.fromEnum(ResourceLocationDeduplication::values).byName(name, ALL);
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static class Adapter implements JsonSerializer<ResourceLocationDeduplication>, JsonDeserializer<ResourceLocationDeduplication> {
        @Override
        @Nullable
        public ResourceLocationDeduplication deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            if (element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();
                String name = jsonObject.getAsJsonPrimitive("resource_location_deduplication").getAsString();
                return ResourceLocationDeduplication.byName(name);
            } else {
                return null;
            }
        }

        @Override
        public JsonElement serialize(ResourceLocationDeduplication deduplication, Type type, JsonSerializationContext ctx) {
            JsonObject object = new JsonObject();
            object.addProperty("resource_location_deduplication", deduplication.getSerializedName());
            return object;
        }
    }
}
