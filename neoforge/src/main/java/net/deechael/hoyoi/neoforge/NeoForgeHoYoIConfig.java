package net.deechael.hoyoi.neoforge;

import net.deechael.hoyoi.config.HoYoIConfig;
import net.deechael.hoyoi.style.HoYoIStyle;

public class NeoForgeHoYoIConfig implements HoYoIConfig {

    @Override
    public HoYoIStyle getStyle() {
        return HoYoIStyle.GENSHIN; // TODO
    }

}
