package com.mr_toad.palladium.core.config.control;

import net.minecraft.network.chat.Component;

@FunctionalInterface
public interface LongControlValueFormatter {
    Component format(long value);
}
