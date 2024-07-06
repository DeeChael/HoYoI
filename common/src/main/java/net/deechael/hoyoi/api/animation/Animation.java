package net.deechael.hoyoi.api.animation;

public interface Animation {

    void render(AnimationContext context, long start);

    static MovingAnimation.Builder moving() {
        return new MovingAnimation.Builder();
    }

}
