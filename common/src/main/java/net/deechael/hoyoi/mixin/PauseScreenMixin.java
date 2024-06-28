package net.deechael.hoyoi.mixin;

import net.deechael.hoyoi.platform.Services;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {

    @Shadow
    @Final
    private static Component RETURN_TO_GAME;

    protected PauseScreenMixin(Component pTitle) {
        super(pTitle);
    }

    @Inject(method = "init", at = @At(value = "HEAD"), cancellable = true)
    private void inject$initVoid(CallbackInfo ci) {
        List<AbstractWidget> widgets = Services.PLATFORM.getConfig().getStyle().getInstance().renderPauseScreenWidgets((PauseScreen) (Object) this);
        for (AbstractWidget widget : widgets) {
            this.addRenderableWidget(widget);
        }
        ci.cancel();
    }

    @Inject(method = "renderBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", shift = At.Shift.AFTER), cancellable = true)
    private void inject$render$head(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        Services.PLATFORM.getConfig().getStyle().getInstance().renderPauseScreenBackground(graphics);
        ci.cancel();
    }

}
