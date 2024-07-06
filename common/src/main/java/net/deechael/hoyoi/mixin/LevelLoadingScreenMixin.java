package net.deechael.hoyoi.mixin;

import net.deechael.hoyoi.config.HoYoIConfig;
import net.deechael.hoyoi.platform.Services;
import net.deechael.hoyoi.style.HoYoIStyle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.server.level.progress.StoringChunkProgressListener;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLoadingScreen.class)
public class LevelLoadingScreenMixin {

    @Shadow
    @Final
    private StoringChunkProgressListener progressListener;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void inject$render$head(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        if (HoYoIConfig.get().getStyle() != HoYoIStyle.VANILLA && HoYoIConfig.get().getLoadingWorld()) {
            pGuiGraphics.fill(
                    0,
                    0,
                    pGuiGraphics.guiWidth(),
                    pGuiGraphics.guiHeight(),
                    FastColor.ARGB32.color(
                            Services.PLATFORM.getConfig().getStyle().getInstance().getBackgroundColorR(),
                            Services.PLATFORM.getConfig().getStyle().getInstance().getBackgroundColorG(),
                            Services.PLATFORM.getConfig().getStyle().getInstance().getBackgroundColorB()
                    )
            );
            Services.PLATFORM.getConfig().getStyle().getInstance().renderProgress(
                    Mth.clamp(this.progressListener.getProgress() / 100f, 0.0f, 1.0f),
                    pGuiGraphics
            );
            ci.cancel();
        }
    }

}
