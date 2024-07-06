package net.deechael.hoyoi.fabric;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.deechael.hoyoi.config.HoYoIConfig;
import net.deechael.hoyoi.style.HoYoIStyle;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class FabricHoYoIConfig implements HoYoIConfig {

    private static final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("hoyoi.json");

    private static FabricHoYoIConfig INSTANCE = null;

    public static FabricHoYoIConfig getInstance() {
        if (INSTANCE == null) {
            Gson gson = new Gson();
            try (FileReader reader = new FileReader(configFile.toFile())) {
                INSTANCE = gson.fromJson(reader, FabricHoYoIConfig.class);
            } catch (IOException ignored) {
            }
            if (INSTANCE == null) {
                INSTANCE = new FabricHoYoIConfig();
                INSTANCE.save();
            }
        }
        return INSTANCE;
    }

    public HoYoIStyle style = HoYoIStyle.VANILLA;
    public boolean reloading = true;
    public boolean loadingWorld = true;
    public boolean pauseScreen = true;

    @Override
    public HoYoIStyle getStyle() {
        return this.style;
    }

    @Override
    public void setStyle(HoYoIStyle style) {
        this.style = style;
    }

    @Override
    public boolean getReloading() {
        return this.reloading;
    }

    @Override
    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    @Override
    public boolean getLoadingWorld() {
        return this.loadingWorld;
    }

    @Override
    public void setLoadingWorld(boolean loadingWorld) {
        this.loadingWorld = loadingWorld;
    }

    @Override
    public boolean getPauseScreen() {
        return this.pauseScreen;
    }

    @Override
    public void setPauseScreen(boolean pauseScreen) {
        this.pauseScreen = pauseScreen;
    }

    @Override
    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(configFile.toFile())) {
            gson.toJson(this, FabricHoYoIConfig.class, writer);
        } catch (IOException ignored) {
        }
    }

    private FabricHoYoIConfig() {
    }

}