package com.mr_toad.palladium.common.block.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockEntityRendererFreezer {

    private final ExecutorService tick = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("CRRT-%s").build());
    private final ObjectList<BlockEntity> frozen = new ObjectArrayList<>();

    private final Level level;

    public BlockEntityRendererFreezer(Level level) {
        this.level = level;
    }

    public void tickInRender(Set<BlockEntity> rendered) {
        if (rendered.isEmpty()) {
            return;
        }

        this.getTickService().submit(() -> rendered.stream().filter(blockEntity -> blockEntity instanceof Container).forEach(blockEntity -> ForgeRegistries.BLOCKS.getEntries().stream().map(Map.Entry::getValue).filter(block -> blockEntity.getBlockState().is(block)).forEach(block -> {
            BlockPos blockPos = blockEntity.getBlockPos();
            if (!this.getLevel().getBlockState(blockPos).is(blockEntity.getBlockState().getBlock())) {
                return;
            }
            this.getLevel().getEntitiesOfClass(Player.class, new AABB(blockPos).inflate(5.0D), player -> {
                if (!player.isAlive()) {
                    return false;
                }

                if (blockEntity.getLevel() != null) {
                    return blockEntity.getLevel().dimension() == player.level().dimension();
                }

                return true;
            }).forEach(player -> {
                if (player.distanceToSqr(blockPos.getCenter()) > 5.0D) {
                    this.frozen.add(blockEntity);
                } else {
                    this.frozen.remove(blockEntity);
                }
            });
        })));

    }

    public ImmutableList<BlockEntity> getFrozen() {
        return ImmutableList.copyOf(this.frozen);
    }

    public ExecutorService getTickService() {
        return this.tick;
    }

    public Level getLevel() {
        return this.level;
    }

}
