package net.deechael.hoyoi.style;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.PauseScreen;

import java.util.List;

public class StarRailStyle implements StyleInstance {

    public static final StarRailStyle INSTANCE = new StarRailStyle();

    private StarRailStyle() {
    }

    @Override
    public int getBackgroundColorR() {
        return 0; // TODO
    }

    @Override
    public int getBackgroundColorG() {
        return 0; // TODO
    }

    @Override
    public int getBackgroundColorB() {
        return 0; // TODO
    }

    @Override
    public void renderProgress(float progress, GuiGraphics graphics) {
        // TODO
    }

    @Override
    public void renderPauseScreenBackground(GuiGraphics graphics) {
        // TODO
    }

    @Override
    public List<AbstractWidget> renderPauseScreenWidgets(PauseScreen pauseScreen) {
        return List.of(); // TODO
    }

}
