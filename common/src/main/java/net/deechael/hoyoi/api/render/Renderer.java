package net.deechael.hoyoi.api.render;

import net.deechael.hoyoi.api.animation.AnimationContext;

@FunctionalInterface
public interface Renderer {

    void render(AnimationContext context, int x, int y);

}
