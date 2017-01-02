package io.github.mssjsg.pong.game.component;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by sing on 1/2/17.
 */

public class DisplayBody extends Body {

    public Color color;

    public DisplayBody(int id) {
        super(id);
    }

    public DisplayBody(int id, int shape) {
        super(id, shape);
    }
}
