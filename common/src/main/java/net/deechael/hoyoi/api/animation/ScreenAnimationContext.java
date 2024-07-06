package net.deechael.hoyoi.api.animation;

import net.minecraft.client.gui.GuiGraphics;

public record ScreenAnimationContext(GuiGraphics graphics, int mouseX, int mouseY,
                                     float partialTick) implements AnimationContext {

    @Override
    public GuiGraphics getGraphics() {
        return this.graphics;
    }

}
