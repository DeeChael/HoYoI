package net.deechael.hoyoi.config;

import net.deechael.hoyoi.platform.Services;
import net.deechael.hoyoi.style.HoYoIStyle;

public interface HoYoIConfig {

    HoYoIStyle getStyle();

    void setStyle(HoYoIStyle style);

    boolean getReloading();

    void setReloading(boolean reloading);

    boolean getLoadingWorld();

    void setLoadingWorld(boolean loadingWorld);

    boolean getPauseScreen();

    void setPauseScreen(boolean pauseScreen);

    void save();

    static HoYoIConfig get() {
        return Services.PLATFORM.getConfig();
    }

}
