package io.github.mssjsg.pong.game.component;

import com.badlogic.gdx.graphics.Color;

import io.github.mssjsg.pong.game.shape.Shape;

/**
 * Created by sing on 1/2/17.
 */

public class DisplayBody extends Body<DisplayBody> {

    public Color color;

    public DisplayBody() {
    }

    public DisplayBody(Body body) {
        super(body);
    }

    @Override
    protected DisplayBody createObject() {
        return new DisplayBody();
    }

    @Override
    public DisplayBody copy() {
        DisplayBody displayBody = super.copy();
        displayBody.color = new Color(color);
        return displayBody;
    }
}
