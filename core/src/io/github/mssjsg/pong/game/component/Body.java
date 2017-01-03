package io.github.mssjsg.pong.game.component;

import io.github.mssjsg.pong.game.shape.Shape;
import io.github.mssjsg.pong.util.Copyable;

/**
 * Created by sing on 1/3/17.
 */

public abstract class Body<B extends Body<B>> implements Component, Copyable<B> {
    public float centerX;
    public float centerY;
    public Shape<?> shape;

    public Body() {
    }

    public Body(Body body) {
        centerX = body.centerX;
        centerY = body.centerY;
        shape = (Shape<?>)body.shape.copy();
    }

    protected abstract B createObject();

    @Override
    public B copy() {
        B body = createObject();
        body.centerX = centerX;
        body.centerY = centerY;
        body.shape = (Shape<?>)shape.copy();
        return body;
    }
}
