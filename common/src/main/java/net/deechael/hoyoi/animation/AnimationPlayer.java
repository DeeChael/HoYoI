package net.deechael.hoyoi.animation;

import net.deechael.hoyoi.api.animation.Animation;
import net.deechael.hoyoi.api.animation.AnimationContext;

public record AnimationPlayer(Animation animation, long startAt) {

    public void render(AnimationContext context) {
        this.animation.render(context, this.startAt);
    }

}
