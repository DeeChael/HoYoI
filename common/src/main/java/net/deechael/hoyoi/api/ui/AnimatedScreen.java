package net.deechael.hoyoi.api.ui;

import net.deechael.hoyoi.animation.AnimationPlayer;
import net.deechael.hoyoi.api.animation.Animation;
import net.deechael.hoyoi.api.animation.ScreenAnimationContext;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class AnimatedScreen extends Screen {

    private final AnimationPlayer animationPlayer;

    public AnimatedScreen(Component title, Animation animation) {
        super(title);
        this.animationPlayer = new AnimationPlayer(animation, System.currentTimeMillis());
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.animationPlayer.render(new ScreenAnimationContext(graphics, mouseX, mouseY, partialTick));
    }

}
