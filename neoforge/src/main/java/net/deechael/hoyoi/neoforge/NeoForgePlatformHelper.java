package net.deechael.hoyoi.neoforge;

import net.deechael.hoyoi.config.HoYoIConfig;
import net.deechael.hoyoi.platform.services.IPlatformHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.ModListScreen;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public void openModMenu(PauseScreen pauseScreen) {
        Minecraft.getInstance().setScreen(new ModListScreen(pauseScreen));
    }

    @Override
    public HoYoIConfig getConfig() {
        return NeoForgeHoYoIConfig.ensureLoaded();
    }

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

}