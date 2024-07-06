package net.deechael.hoyoi.neoforge;

import net.deechael.hoyoi.HoYoI;
import net.deechael.hoyoi.HoYoICaching;
import net.deechael.hoyoi.HoYoIConstants;
import net.deechael.hoyoi.config.HoYoIConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

@Mod(value = HoYoIConstants.MOD_ID, dist = Dist.CLIENT)
public class HoYoINeoForge {

    public HoYoINeoForge(IEventBus eventBus) {
        HoYoI.init();
        HoYoICaching.FIRST_LOADING = false; // FIXME: neoforge early display cannot be replaced, so the first loading won't be set to false after start the game

        if (FMLEnvironment.dist.isClient()) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> new IConfigScreenFactory() {
                @Override
                public @NotNull Screen createScreen(@NotNull Minecraft minecraft, @NotNull Screen parent) {
                    return new HoYoIConfigScreen(Component.literal(HoYoIConstants.MOD_NAME), parent);
                }
            });
        }
    }

}