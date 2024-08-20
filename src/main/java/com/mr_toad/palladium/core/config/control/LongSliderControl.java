package com.mr_toad.palladium.core.config.control;

import me.jellysquid.mods.sodium.client.gui.options.Option;
import me.jellysquid.mods.sodium.client.gui.options.control.Control;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlElement;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.Validate;

public class LongSliderControl implements Control<Long> {

    private final Option<Long> option;
    private final long min;
    private final long max;
    private final long interval;
    private final LongControlValueFormatter mode;

    public LongSliderControl(Option<Long> option, long min, long max, long interval, LongControlValueFormatter mode) {
        Validate.isTrue(max > min, "The maximum value must be greater than the minimum value");
        Validate.isTrue(interval > 0, "The slider interval must be greater than zero");
        Validate.isTrue((max - min) % interval == 0, "The maximum value must be dividable by the interval");

        Validate.notNull(mode, "The slider mode must not be null");

        this.option = option;
        this.min = min;
        this.max = max;
        this.interval = interval;
        this.mode = mode;
    }

    public ControlElement<Long> createElement(Dim2i dim) {
        return new Button(this.option, dim, this.min, this.max, this.interval, this.mode);
    }

    public Option<Long> getOption() {
        return this.option;
    }

    public int getMaxWidth() {
        return 130;
    }

    private static class Button extends ControlElement<Long> {
        private final Rect2i sliderBounds;
        private final LongControlValueFormatter formatter;

        private final long min;
        private final long max;
        private final long range;
        private final long interval;

        private double thumbPosition;
        private boolean sliderHeld;

        public Button(Option<Long> option, Dim2i dim, long min, long max, long interval, LongControlValueFormatter formatter) {
            super(option, dim);
            this.min = min;
            this.max = max;
            this.range = max - min;
            this.interval = interval;
            this.thumbPosition = this.getThumbPositionForValue(option.getValue());
            this.formatter = formatter;
            this.sliderBounds = new Rect2i(dim.getLimitX() - 96, dim.getCenterY() - 5, 90, 10);
            this.sliderHeld = false;
        }

        public void render(GuiGraphics drawContext, int mouseX, int mouseY, float delta) {
            super.render(drawContext, mouseX, mouseY, delta);
            if (!this.option.isAvailable() || !this.hovered && !this.isFocused()) {
                this.renderStandaloneValue(drawContext);
            } else {
                this.renderSlider(drawContext);
            }
        }

        private void renderStandaloneValue(GuiGraphics drawContext) {
            int sliderX = this.sliderBounds.getX();
            int sliderY = this.sliderBounds.getY();
            int sliderWidth = this.sliderBounds.getWidth();
            int sliderHeight = this.sliderBounds.getHeight();
            Component label = this.formatter.format(this.option.getValue());
            int labelWidth = this.font.width(label);
            this.drawString(drawContext, label, sliderX + sliderWidth - labelWidth, sliderY + sliderHeight / 2 - 4, -1);
        }

        private void renderSlider(GuiGraphics drawContext) {
            int sliderX = this.sliderBounds.getX();
            int sliderY = this.sliderBounds.getY();
            int sliderWidth = this.sliderBounds.getWidth();
            int sliderHeight = this.sliderBounds.getHeight();
            this.thumbPosition = this.getThumbPositionForValue(this.option.getValue());
            double thumbOffset = Mth.clamp((double)(this.getLongValue() - this.min) / (double)this.range * (double)sliderWidth, 0.0, (double)sliderWidth);
            int thumbX = (int)((double)sliderX + thumbOffset - 2.0);
            int trackY = (int)((double)((float)sliderY + (float)sliderHeight / 2.0F) - 0.5);
            this.drawRect(drawContext, thumbX, sliderY, thumbX + 4, sliderY + sliderHeight, -1);
            this.drawRect(drawContext, sliderX, trackY, sliderX + sliderWidth, trackY + 1, -1);
            String label = this.formatter.format(this.getLongValue()).getString();
            int labelWidth = this.font.width(label);
            this.drawString(drawContext, label, sliderX - labelWidth - 6, sliderY + sliderHeight / 2 - 4, -1);
        }

        public long getLongValue() {
            return this.min + this.interval * (int)Math.round(this.getSnappedThumbPosition() / (double)this.interval);
        }

        public double getSnappedThumbPosition() {
            return this.thumbPosition / (1.0 / (double)this.range);
        }

        public double getThumbPositionForValue(long value) {
            return (double)(value - this.min) * (1.0 / (double)this.range);
        }

        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            this.sliderHeld = false;
            if (this.option.isAvailable() && button == 0 && this.dim.containsCursor(mouseX, mouseY)) {
                if (this.sliderBounds.contains((int)mouseX, (int)mouseY)) {
                    this.setValueFromMouse(mouseX);
                    this.sliderHeld = true;
                }

                return true;
            } else {
                return false;
            }
        }

        private void setValueFromMouse(double d) {
            this.setValue((d - (double)this.sliderBounds.getX()) / (double)this.sliderBounds.getWidth());
        }

        public void setValue(double d) {
            this.thumbPosition = Mth.clamp(d, 0.0, 1.0);
            long value = this.getLongValue();
            if (this.option.getValue() != value) {
                this.option.setValue(value);
            }
        }

        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (!this.isFocused()) {
                return false;
            } else if (keyCode == 263) {
                this.option.setValue((long) Mth.clamp(this.option.getValue() - this.interval, this.min, this.max));
                return true;
            } else if (keyCode == 262) {
                this.option.setValue((long) Mth.clamp(this.option.getValue() + this.interval, this.min, this.max));
                return true;
            } else {
                return false;
            }
        }

        public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
            if (this.option.isAvailable() && button == 0) {
                if (this.sliderHeld) {
                    this.setValueFromMouse(mouseX);
                }
                return true;
            } else {
                return false;
            }
        }
    }
}
