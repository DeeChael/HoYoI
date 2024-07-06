package net.deechael.hoyoi.mixin;

import net.deechael.hoyoi.HoYoI;
import net.deechael.hoyoi.HoYoICaching;
import net.deechael.hoyoi.config.HoYoIConfig;
import net.deechael.hoyoi.platform.Services;
import net.deechael.hoyoi.style.GenshinStyle;
import net.deechael.hoyoi.style.HoYoIStyle;
import net.deechael.hoyoi.style.genshin.GenshinIcons;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.IntSupplier;

@Mixin(LoadingOverlay.class)
public class LoadingOverlayMixin {

    @Mutable
    @Shadow
    @Final
    private static IntSupplier BRAND_BACKGROUND;

    @Shadow
    private float currentProgress;

    @Shadow
    @Final
    private boolean fadeIn;

    // Start
    // Remove fade in and fade out to fit miHoYo games loading style
    @Redirect(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;fadeIn:Z", opcode = Opcodes.GETFIELD))
    private boolean redirect$render$fadeIn(LoadingOverlay instance) {
        return this.fadeIn;
    }

    @ModifyConstant(method = "render", constant = @Constant(floatValue = 500.F))
    private float modify$render$fadeIn(float old) {
        return 0L;
    }

    @ModifyConstant(method = "render", constant = @Constant(floatValue = 1000.F))
    private float modify$render$fadeOut(float old) {
        return 0L;
    }
    // End

    @Redirect(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;BRAND_BACKGROUND:Ljava/util/function/IntSupplier;"))
    private IntSupplier redirect$render$BRAND_BACKGROUND() {
        if (HoYoIConfig.get().getStyle() != HoYoIStyle.VANILLA && HoYoIConfig.get().getReloading()) {
            return () -> FastColor.ARGB32.color(
                    HoYoIConfig.get().getStyle().getInstance().getBackgroundColorR(),
                    HoYoIConfig.get().getStyle().getInstance().getBackgroundColorG(),
                    HoYoIConfig.get().getStyle().getInstance().getBackgroundColorB()
            ); // change the background color
        } else {
            return BRAND_BACKGROUND;
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;init(Lnet/minecraft/client/Minecraft;II)V", shift = At.Shift.AFTER))
    private void inject$render$head(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        if (HoYoICaching.FIRST_LOADING) {
            HoYoICaching.FIRST_LOADING = false;
        } // starting game and ingame loading screen is different

        // put here because i don't know why resource manager is null when loading mod
        // and another reason: for resource pack customization
        HoYoI.getTipsManager().reload();
        GenshinIcons.reload();
        GenshinStyle.ICON = GenshinIcons.randomIcon();
    }

    @Inject(method = "drawProgressBar", at = @At("HEAD"), cancellable = true)
    private void inject$drawProgressBar$head(GuiGraphics pGuiGraphics, int pMinX, int pMinY, int pMaxX, int pMaxY, float pPartialTick, CallbackInfo ci) {
        if (HoYoIConfig.get().getStyle() != HoYoIStyle.VANILLA && HoYoIConfig.get().getReloading()) {
            Services.PLATFORM.getConfig().getStyle().getInstance().renderProgress(this.currentProgress, pGuiGraphics);
            ci.cancel();
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIFFIIII)V"))
    private void redirect$render$blit(GuiGraphics instance, ResourceLocation pAtlasLocation, int pX, int pY, int pWidth, int pHeight, float pUOffset, float pVOffset, int pUWidth, int pVHeight, int pTextureWidth, int pTextureHeight) {
        // cancel mojang logo rendering, maybe can optimise because we actually can't see it
        // but if is Vanilla, don't cancel it
        if (HoYoIConfig.get().getStyle() == HoYoIStyle.VANILLA || !HoYoIConfig.get().getReloading()) {
            instance.blit(pAtlasLocation, pX, pY, pWidth, pHeight, pUOffset, pVOffset, pUWidth, pVHeight, pTextureWidth, pTextureHeight);
        }
    }

}
