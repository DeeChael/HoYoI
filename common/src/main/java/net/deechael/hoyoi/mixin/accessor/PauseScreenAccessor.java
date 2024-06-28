package net.deechael.hoyoi.mixin.accessor;

import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PauseScreen.class)
public interface PauseScreenAccessor {

    @Accessor("RETURN_TO_GAME")
    Component getReturnToGame();

    @Accessor("RETURN_TO_MENU")
    Component getReturnToMenu();

    @Accessor("SAVING_LEVEL")
    Component getSavingLevel();

    @Accessor("OPTIONS")
    Component getOptions();

}
