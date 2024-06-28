package net.deechael.hoyoi;

import net.deechael.hoyoi.tips.TipsManager;

public class HoYoI {

    private static TipsManager MANAGER;

    public static void init() {
        MANAGER = new TipsManager();
    }

    public static TipsManager getTipsManager() {
        return MANAGER;
    }

}