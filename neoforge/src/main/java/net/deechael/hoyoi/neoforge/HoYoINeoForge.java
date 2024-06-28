package net.deechael.hoyoi.neoforge;

import net.deechael.hoyoi.HoYoI;
import net.deechael.hoyoi.HoYoICaching;
import net.deechael.hoyoi.HoYoIConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = HoYoIConstants.MOD_ID, dist = Dist.CLIENT)
public class HoYoINeoForge {

    public HoYoINeoForge(IEventBus eventBus) {
        HoYoI.init();
        HoYoICaching.FIRST_LOADING = false; // FIXME: neoforge early display cannot be replaced, so the first loading won't be set to false after start the game
    }

}