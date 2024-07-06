package net.deechael.hoyoi.fabric.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.gui.ModsScreen;
import net.deechael.hoyoi.HoYoIConstants;
import net.deechael.hoyoi.config.HoYoIConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.Component;

public class ModMenuCompat implements ModMenuApi {

    public static void openModMenu(PauseScreen pauseScreen) {
        Minecraft.getInstance().setScreen(new ModsScreen(pauseScreen));
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new HoYoIConfigScreen(Component.literal(HoYoIConstants.MOD_NAME), parent);
    }

}
