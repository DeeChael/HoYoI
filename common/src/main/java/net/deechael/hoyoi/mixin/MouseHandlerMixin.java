package net.deechael.hoyoi.mixin;

import net.deechael.hoyoi.HoYoI;
import net.deechael.hoyoi.HoYoICaching;
import net.deechael.hoyoi.style.GenshinStyle;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Inject(method = "onPress", at = @At("HEAD"))
    private void inject$onPress$head(long pWindowPointer, int pButton, int pAction, int pModifiers, CallbackInfo ci) {
        // so actually whenever you clicked your mouse, the tip will be refreshed xD
        if (!HoYoICaching.FIRST_LOADING && (System.currentTimeMillis() - GenshinStyle.LAST_TIP_TIME) > 200L) {
            GenshinStyle.TIP = HoYoI.getTipsManager().randomTip();
            GenshinStyle.LAST_TIP_TIME = System.currentTimeMillis();
        }
    }

}
