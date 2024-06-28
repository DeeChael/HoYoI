package net.deechael.hoyoi.platform.services;

import net.deechael.hoyoi.config.HoYoIConfig;
import net.minecraft.client.gui.screens.PauseScreen;

public interface IPlatformHelper {

    void openModMenu(PauseScreen pauseScreen);

    HoYoIConfig getConfig();

    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

}