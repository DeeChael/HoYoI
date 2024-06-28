package net.deechael.hoyoi.style.genshin;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class GenshinIcons {

    private final static List<ResourceLocation> ICONS = new ArrayList<>();

    public static void reload() {
        Collection<ResourceLocation> newIcons = Minecraft.getInstance()
                .getResourceManager()
                .listResources("textures/ui/regions", location -> true)
                .keySet();
        ICONS.clear();
        ICONS.addAll(newIcons);
    }

    public static ResourceLocation randomIcon() {
        return ICONS.get(new Random().nextInt(ICONS.size()));
    }

}
