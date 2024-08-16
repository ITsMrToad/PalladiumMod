package com.mr_toad.palladium.client.shader;

import com.google.common.collect.Maps;
import org.lwjgl.opengl.GL20;

import java.util.Map;
import java.util.Objects;

public class ShaderProgramCache {

    private final int id;
    private final Map<String, Integer> uniforms = Maps.newHashMap();

    public ShaderProgramCache(int id) {
        this.id = id;
    }

    public int uniform(String uniformName) {
        return this.uniforms.computeIfAbsent(uniformName, value -> GL20.glGetUniformLocation(this.id, uniformName));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj.getClass() != this.getClass()) {
            return false;
        } else if (obj instanceof ShaderProgramCache cache) {
            return Objects.equals(this.id, cache.id);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 * this.id + this.uniforms.size();
    }

    @Override
    public String toString() {
        return "SPC<" + this.id + ">";
    }
}
