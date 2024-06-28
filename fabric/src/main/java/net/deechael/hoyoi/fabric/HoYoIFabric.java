package net.deechael.hoyoi.fabric;

import net.deechael.hoyoi.HoYoI;
import net.fabricmc.api.ModInitializer;

public class HoYoIFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        HoYoI.init();
    }

}
