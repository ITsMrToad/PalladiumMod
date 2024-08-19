package com.mr_toad.palladium.common.block.entity;

import com.mr_toad.palladium.core.Palladium;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Set;

public class BlockEntityFreezerManager {

    public static final Object2BooleanMap<BlockState> ACTIVE_OR_FREEZE_MAP = new Object2BooleanOpenHashMap<>();

    @Nullable public static BlockEntityRendererFreezer BLOCK_ENTITY_REPLACER;
    public static boolean LOAD = false;

    public static void tick(Level level, Set<BlockEntity> rendered) {
        BLOCK_ENTITY_REPLACER = new BlockEntityRendererFreezer(level);
        BLOCK_ENTITY_REPLACER.tickInRender(rendered);
        if (!BLOCK_ENTITY_REPLACER.getFrozen().isEmpty()) {
            rendered.stream().findAny().ifPresent(blockEntity -> ACTIVE_OR_FREEZE_MAP.putIfAbsent(blockEntity.getBlockState(), BLOCK_ENTITY_REPLACER.getFrozen().contains(blockEntity)));
        }
    }

    public static void load(Level level, Set<BlockEntity> rendered) {
        BLOCK_ENTITY_REPLACER = new BlockEntityRendererFreezer(level);
        rendered.stream().filter(blockEntity -> blockEntity instanceof Container).forEach(blockEntity -> ACTIVE_OR_FREEZE_MAP.putIfAbsent(blockEntity.getBlockState(), true));
    }

    public static void clear() {
        if (!ACTIVE_OR_FREEZE_MAP.isEmpty()) {
            ACTIVE_OR_FREEZE_MAP.clear();
        }
    }

    public static boolean canReplace(BlockState state) {
        if (Palladium.checkConfigAndGetValue(cfg -> cfg.enableBlockChest) && !BlockEntityFreezerManager.ACTIVE_OR_FREEZE_MAP.isEmpty()) {
            return BlockEntityFreezerManager.ACTIVE_OR_FREEZE_MAP.getOrDefault(state, false);
        }
        return false;
    }
}
