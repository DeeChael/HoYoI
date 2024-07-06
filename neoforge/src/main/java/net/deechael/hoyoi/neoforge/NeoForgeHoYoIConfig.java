package net.deechael.hoyoi.neoforge;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.deechael.hoyoi.config.HoYoIConfig;
import net.deechael.hoyoi.style.HoYoIStyle;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.nio.file.Path;

public class NeoForgeHoYoIConfig implements HoYoIConfig {

    public final static NeoForgeHoYoIConfig INSTANCE = new NeoForgeHoYoIConfig();

    public static final ModConfigSpec SPECS;
    public static final ModConfigSpec.EnumValue<HoYoIStyle> STYLE;
    public static final ModConfigSpec.BooleanValue RELOADING;
    public static final ModConfigSpec.BooleanValue LOADING_WORLD;
    public static final ModConfigSpec.BooleanValue PAUSE_SCREEN;

    private static boolean loaded = false;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("hoyoi");

        STYLE = builder.comment("HoYoI UI Style")
                .defineEnum("style", HoYoIStyle.VANILLA);
        RELOADING = builder.comment("Reloading screen (like startup or reload resources) animation replacement")
                .define("reloading", true);
        LOADING_WORLD = builder.comment("Loading world animation replacement")
                .define("loading_world", true);
        PAUSE_SCREEN = builder.comment("Pause screen replacement")
                .define("pause_screen", true);

        builder.pop();

        SPECS = builder.build();
    }

    public static NeoForgeHoYoIConfig ensureLoaded() {
        if (!loaded) {
            Path path = FMLPaths.CONFIGDIR.get().resolve("hoyoi-client.toml");
            CommentedFileConfig config = CommentedFileConfig.builder(path)
                    .sync()
                    .autosave()
                    .writingMode(WritingMode.REPLACE)
                    .build();
            config.load();
            SPECS.setConfig(config);

            loaded = true;
        }
        return INSTANCE;
    }

    @Override
    public HoYoIStyle getStyle() {
        return STYLE.get();
    }

    @Override
    public void setStyle(HoYoIStyle style) {
        STYLE.set(style);
    }

    @Override
    public boolean getReloading() {
        return RELOADING.get();
    }

    @Override
    public void setReloading(boolean reloading) {
        RELOADING.set(reloading);
    }

    @Override
    public boolean getLoadingWorld() {
        return LOADING_WORLD.get();
    }

    @Override
    public void setLoadingWorld(boolean loadingWorld) {
        LOADING_WORLD.set(loadingWorld);
    }

    @Override
    public boolean getPauseScreen() {
        return PAUSE_SCREEN.get();
    }

    @Override
    public void setPauseScreen(boolean pauseScreen) {
        PAUSE_SCREEN.set(pauseScreen);
    }

    @Override
    public void save() {
        SPECS.save();
    }

    private NeoForgeHoYoIConfig() {
    }

}
