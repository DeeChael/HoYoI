package net.deechael.hoyoi.fabric;

import net.deechael.hoyoi.config.HoYoIConfig;
import net.deechael.hoyoi.style.HoYoIStyle;

public class FabricHoYoIConfig implements HoYoIConfig {

    @Override
    public HoYoIStyle getStyle() {
        return HoYoIStyle.GENSHIN; // TODO
    }

}