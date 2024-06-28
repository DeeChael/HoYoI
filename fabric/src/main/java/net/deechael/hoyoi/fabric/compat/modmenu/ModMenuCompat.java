package net.deechael.hoyoi.fabric.compat.modmenu;

import com.terraformersmc.modmenu.gui.ModsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;

public class ModMenuCompat {

    public static void openModMenu(PauseScreen pauseScreen) {
        Minecraft.getInstance().setScreen(new ModsScreen(pauseScreen));
    }

}
