package io.github.mssjsg.pong.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import io.github.mssjsg.pong.game.component.DisplayBody;

/**
 * Created by sing on 1/2/17.
 */

public class DisplayBodySystem extends ComponentSystem<DisplayBody> {

    private ShapeRenderer mShapeRenderer;

    public DisplayBodySystem(ShapeRenderer shapeRenderer) {
        super(DisplayBody.class);
        mShapeRenderer = shapeRenderer;
    }

    @Override
    public void onUpdate(float delta, Array<DisplayBody> component) {

    }
}
