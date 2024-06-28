package net.deechael.hoyoi.style;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.PauseScreen;

import java.util.List;

public interface StyleInstance {

    int getBackgroundColorR();

    int getBackgroundColorG();

    int getBackgroundColorB();

    void renderProgress(float progress, GuiGraphics graphics);

    void renderPauseScreenBackground(GuiGraphics graphics);

    List<AbstractWidget> renderPauseScreenWidgets(PauseScreen pauseScreen);

}
