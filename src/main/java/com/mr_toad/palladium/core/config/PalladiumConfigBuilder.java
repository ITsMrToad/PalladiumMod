package com.mr_toad.palladium.core.config;

import com.google.common.collect.ImmutableList;
import com.mr_toad.palladium.core.Palladium;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpact;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.network.chat.Component;
import org.embeddedt.embeddium.client.gui.options.OptionIdentifier;

import org.jetbrains.annotations.ApiStatus;
import java.util.List;

@ApiStatus.Internal
public class PalladiumConfigBuilder {

    private final PalladiumConfigStorage storage;

    public PalladiumConfigBuilder(PalladiumConfig cfg) {
        this.storage = new PalladiumConfigStorage(cfg);
    }

    public void build(List<OptionPage> pages) {
        OptionGroup.Builder group = OptionGroup.createBuilder();

        OptionImpl<PalladiumConfig, Boolean> shaderCache = OptionImpl.createBuilder(boolean.class, this.storage)
                .setName(Component.translatable("palladium.config.shader_uniform_caching"))
                .setTooltip(Component.translatable("palladium.config.shader_uniform_caching.tooltip"))
                .setFlags().setImpact(OptionImpact.MEDIUM).setControl(TickBoxControl::new)
                .setBinding((cfg, b) -> cfg.enableShaderUniformCaching = b, cfg -> cfg.enableShaderUniformCaching).build();

        OptionImpl<PalladiumConfig, Boolean> modernStateHolder = OptionImpl.createBuilder(boolean.class, this.storage)
                .setName(Component.translatable("palladium.config.modern_state_holder"))
                .setTooltip(Component.translatable("palladium.config.modern_state_holder.tooltip"))
                .setFlags().setImpact(OptionImpact.MEDIUM).setControl(TickBoxControl::new)
                .setBinding((cfg, b) -> cfg.enableModernStateHolder = b, cfg -> cfg.enableModernStateHolder).build();

        OptionImpl<PalladiumConfig, Boolean> rkDeduplication = OptionImpl.createBuilder(boolean.class, this.storage)
                .setName(Component.translatable("palladium.config.resource_key_deduplication"))
                .setTooltip(Component.translatable("palladium.config.resource_key_deduplication.tooltip"))
                .setFlags().setImpact(OptionImpact.LOW).setControl(TickBoxControl::new)
                .setBinding((cfg, b) -> cfg.enableResourceKeyDedup = b, cfg -> cfg.enableResourceKeyDedup).build();

        OptionImpl<PalladiumConfig, ResourceLocationDeduplication> rlDeduplication = OptionImpl.createBuilder(ResourceLocationDeduplication.class, this.storage)
                .setName(Component.translatable("palladium.config.resource_location_deduplication"))
                .setTooltip(Component.translatable("palladium.config.resource_key_deduplication.tooltip"))
                .setFlags().setImpact(OptionImpact.LOW).setControl(cfg -> new CyclingControl<>(cfg, ResourceLocationDeduplication.class, new Component[]{
                        Component.translatable("palladium.resource_loc_dedup.none"),
                        Component.translatable("palladium.resource_loc_dedup.all"),
                        Component.translatable("palladium.resource_loc_dedup.only_rl"),
                        Component.translatable("palladium.resource_loc_dedup.only_mrl")
                })).setBinding((cfg, b) -> cfg.resourceLocationDeduplication = b, cfg -> cfg.resourceLocationDeduplication).build();

        pages.add(new OptionPage(OptionIdentifier.create(Palladium.makeRl("general")), Component.translatable("palladium.config"), ImmutableList.of(group.add(shaderCache).add(modernStateHolder).add(rkDeduplication).add(rlDeduplication).build())));
    }

}
