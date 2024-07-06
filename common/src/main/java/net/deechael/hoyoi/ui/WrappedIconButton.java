package net.deechael.hoyoi.ui;

import com.google.common.base.Preconditions;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WrappedIconButton extends AbstractWidget {

    private final ResourceLocation resourceLocation;
    private final Component name;
    private final OnPress onPress;

    public WrappedIconButton(ResourceLocation resourceLocation, Component name, int pX, int pY, int pWidth, int pHeight, OnPress onPress) {
        super(pX, pY, pWidth, pHeight, Component.empty());

        this.resourceLocation = resourceLocation;
        this.name = name;
        this.onPress = onPress;

        this.setTooltip(Tooltip.create(this.name));
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        this.onPress.onPress(this);
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int x, int y, float partialTick) {
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/menu/background.png"),
                this.getX(),
                this.getY(),
                0,
                0,
                this.getWidth(),
                this.getHeight(),
                this.getWidth(),
                this.getHeight()
        );
        graphics.blit(
                this.resourceLocation,
                this.getX() + (int) (this.getWidth() * 0.25 / 2),
                this.getY() + (int) (this.getHeight() * 0.25 / 2),
                0,
                0,
                (int) (this.getWidth() * 0.75),
                (int) (this.getHeight() * 0.75),
                (int) (this.getWidth() * 0.75),
                (int) (this.getHeight() * 0.75)
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
        private Component name;
        private int x = -1;
        private int y = -1;
        private int width = -1;
        private int height = -1;

        public Builder resourceLocation(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
            return this;
        }

        public Builder onPress(OnPress onPress) {
            this.onPress = onPress;
            return this;
        }

        public Builder name(Component name) {
            this.name = name;
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

        public WrappedIconButton build() {
            Preconditions.checkNotNull(this.resourceLocation);
            Preconditions.checkNotNull(this.name);
            Preconditions.checkNotNull(this.onPress);
            Preconditions.checkState(this.x >= 0);
            Preconditions.checkState(this.y >= 0);
            Preconditions.checkState(this.width >= 0);
            Preconditions.checkState(this.height >= 0);

            return new WrappedIconButton(resourceLocation, name, x, y, width, height, onPress);
        }

    }

    @FunctionalInterface
    public interface OnPress {

        void onPress(WrappedIconButton button);

    }

}
