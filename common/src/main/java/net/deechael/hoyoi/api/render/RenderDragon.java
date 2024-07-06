package net.deechael.hoyoi.api.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public final class RenderDragon {

    private final static GuiGraphics GRAPHICS;

    static {
        Minecraft minecraft = Minecraft.getInstance();
        GRAPHICS = new GuiGraphics(minecraft, minecraft.renderBuffers().bufferSource());
    }

    public static void drawCenteredString(Font font, String text, int x, int y, int color, boolean shadow) {
        GRAPHICS.drawString(font, text, x - font.width(text), y, color, shadow);
    }

    public static void drawCenteredString(Font font, Component text, int x, int y, int color, boolean shadow) {
        GRAPHICS.drawString(font, text, x - font.width(text), y, color, shadow);
    }

    public static void drawCenteredString(Font font, FormattedCharSequence text, int x, int y, int color, boolean shadow) {
        GRAPHICS.drawString(font, text, x - font.width(text), y, color, shadow);
    }

    private RenderDragon() {
    }

}
