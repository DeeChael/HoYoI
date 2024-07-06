package net.deechael.hoyoi.config;

import net.deechael.hoyoi.platform.Services;
import net.deechael.hoyoi.style.HoYoIStyle;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Arrays;

public class HoYoIConfigScreen extends ConfigScreen {

    public HoYoIConfigScreen(Component title, Screen parent) {
        super(title, parent);
    }

    @Override
    public void save() {
        Services.PLATFORM.getConfig().save();
    }

    @Override
    public void addElements() {
        addOption(
                new OptionInstance<>(
                        "hoyoi.config.style",
                        OptionInstance.noTooltip(),
                        OptionInstance.forOptionEnum(),
                        new OptionInstance.Enum<>(Arrays.asList(HoYoIStyle.values()), HoYoIStyle.CODEC),
                        HoYoIConfig.get().getStyle(),
                        style -> {
                            HoYoIConfig.get().setStyle(style);
                            HoYoIConfig.get().save();
                        })
        );
        addOption(
                OptionInstance.createBoolean("hoyoi.config.reloading",
                        HoYoIConfig.get().getReloading(),
                        HoYoIConfig.get()::setReloading)
        );
        addOption(
                OptionInstance.createBoolean("hoyoi.config.loading_world",
                        HoYoIConfig.get().getLoadingWorld(),
                        HoYoIConfig.get()::setLoadingWorld)
        );
        addOption(
                OptionInstance.createBoolean("hoyoi.config.pause_screen",
                        HoYoIConfig.get().getPauseScreen(),
                        HoYoIConfig.get()::setPauseScreen)
        );
    }

}
