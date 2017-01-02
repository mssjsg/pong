package io.github.mssjsg.pong.game.component;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by sing on 1/2/17.
 */

public class Body extends Component {
    public static final int SHAPE_RECT = 0;
    public static final int SHAPE_CIRCLE = 1;

    public float width;
    public float height;
    public float centerX;
    public float centerY;
    public int shape;

    public Body() {
        this(SHAPE_RECT);
    }

    public Body(int shape) {;
        this.shape = shape;
    }
}
