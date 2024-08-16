package com.mr_toad.palladium.core.config;

import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;

public record PalladiumConfigStorage(PalladiumConfig config) implements OptionStorage<PalladiumConfig> {

    @Override
    public PalladiumConfig getData() {
        return this.config();
    }

    @Override
    public void save() {
        this.config().save();
    }
}
