package net.deechael.hoyoi.style.genshin;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenshinIcons {

    private final static List<ResourceLocation> ICONS = new ArrayList<>();

    public static void load() {
        ICONS.addAll(Minecraft.getInstance()
                .getResourceManager()
                .listResources("textures/ui/regions", location -> true)
                .keySet());
    }

    public static ResourceLocation randomIcon() {
        return ICONS.get(new Random().nextInt(ICONS.size()));
    }

}
