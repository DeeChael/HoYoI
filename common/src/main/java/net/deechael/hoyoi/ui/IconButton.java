package net.deechael.hoyoi.ui;

import com.google.common.base.Preconditions;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class IconButton extends AbstractWidget {

    private final ResourceLocation resourceLocation;
    private final OnPress onPress;

    public IconButton(ResourceLocation resourceLocation, int pX, int pY, int pWidth, int pHeight, OnPress onPress) {
        super(pX, pY, pWidth, pHeight, Component.empty());

        this.resourceLocation = resourceLocation;
        this.onPress = onPress;
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        this.onPress.onPress(this);
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int x, int y, float partialTick) {
        graphics.blit(
                this.resourceLocation,
                this.getX(),
                this.getY(),
                0,
                0,
                this.getWidth(),
                this.getHeight(),
                this.getWidth(),
                this.getHeight()
        );
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ResourceLocation resourceLocation;
        private OnPress onPress;
        private int x = -1;
        private int y = -1;
        private int width = -1;
        private int height = -1;

        private Tooltip tooltip;

        public Builder resourceLocation(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
            return this;
        }

        public Builder onPress(OnPress onPress) {
            this.onPress = onPress;
            return this;
        }

        public Builder x(int x) {
            this.x = x;
            return this;
        }

        public Builder y(int y) {
            this.y = y;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder tooltip(Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public IconButton build() {
            Preconditions.checkNotNull(this.resourceLocation);
            Preconditions.checkNotNull(this.onPress);
            Preconditions.checkState(this.x >= 0);
            Preconditions.checkState(this.y >= 0);
            Preconditions.checkState(this.width >= 0);
            Preconditions.checkState(this.height >= 0);

            IconButton instance = new IconButton(resourceLocation, x, y, width, height, onPress);

            instance.setTooltip(this.tooltip);

            return instance;
        }

    }

    @FunctionalInterface
    public interface OnPress {

        void onPress(IconButton button);

    }

}
