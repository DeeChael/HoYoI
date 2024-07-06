package net.deechael.hoyoi.fabric;

import net.deechael.hoyoi.config.HoYoIConfig;
import net.deechael.hoyoi.fabric.compat.modmenu.ModMenuCompat;
import net.deechael.hoyoi.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.PauseScreen;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public void openModMenu(PauseScreen pauseScreen) {
        if (this.isModLoaded("modmenu")) {
            ModMenuCompat.openModMenu(pauseScreen);
        }
    }

    @Override
    public HoYoIConfig getConfig() {
        return FabricHoYoIConfig.getInstance();
    }

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

}
