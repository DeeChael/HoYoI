package net.deechael.hoyoi.api.animation;

import com.google.common.base.Preconditions;
import net.deechael.hoyoi.api.render.Renderer;

import java.awt.*;

public class MovingAnimation implements Animation {

    private final Renderer renderer;

    private final Point from;
    private final Point to;
    private final double deltaX;
    private final double deltaY;
    private final long durationMs;

    private MovingAnimation(Renderer renderer, Point from, Point to, long durationMs) {
        this.renderer = renderer;

        this.from = from;
        this.to = to;
        this.durationMs = durationMs;

        this.deltaX = (to.x - from.x) / (double) durationMs;
        this.deltaY = (to.y - from.y) / (double) durationMs;
    }

    @Override
    public final void render(AnimationContext context, long start) {
        long timeDelta = System.currentTimeMillis() - start;
        if (timeDelta >= durationMs) {
            this.renderer.render(context, to.x, to.y);
        } else {
            this.renderer.render(context, Math.min((int) (from.x + (timeDelta * deltaX)), to.x), Math.min((int) (from.y + (timeDelta * deltaY)), to.y));
        }
    }

    public static class Builder {

        private Renderer renderer;
        private Point from;
        private Point to;

        private Long durationMs;

        protected Builder() {
        }

        public Builder renderer(Renderer renderer) {
            this.renderer = renderer;
            return this;
        }

        public Builder from(Point from) {
            this.from = from;
            return this;
        }

        public Builder to(Point to) {
            this.to = to;
            return this;
        }

        public Builder duration(long durationMs) {
            this.durationMs = durationMs;
            return this;
        }

        public MovingAnimation build() {
            Preconditions.checkNotNull(this.renderer, "Renderer cannot be null");
            Preconditions.checkNotNull(this.from, "From point cannot be null");
            Preconditions.checkNotNull(this.to, "To point cannot be null");
            Preconditions.checkNotNull(this.durationMs, "Duration cannot be null");

            Preconditions.checkState(this.to.x >= this.from.x, "The x axis of from point cannot be larger than the x axis of to point");
            Preconditions.checkState(this.to.y >= this.from.y, "The y axis of from point cannot be larger than the x axis of to point");
            Preconditions.checkState(this.durationMs > 0, "Duration cannot be negative");

            return new MovingAnimation(this.renderer, this.from, this.to, this.durationMs);
        }

    }

}
