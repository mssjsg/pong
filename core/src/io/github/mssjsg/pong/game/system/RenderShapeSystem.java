package io.github.mssjsg.pong.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import io.github.mssjsg.pong.game.Entity;
import io.github.mssjsg.pong.game.component.DisplayBody;
import io.github.mssjsg.pong.game.component.Position;
import io.github.mssjsg.pong.game.shape.Ellipse;
import io.github.mssjsg.pong.game.shape.Rectangle;

/**
 * Created by sing on 1/2/17.
 */

public class RenderShapeSystem extends EntitySystem {

    private OrthographicCamera mCamera;
    private ShapeRenderer mShapeRenderer;

    public RenderShapeSystem(OrthographicCamera camera, ShapeRenderer shapeRenderer) {
        mCamera = camera;
        mShapeRenderer = shapeRenderer;
    }

    @Override
    protected void onUpdate(float delta, Array<Entity> components) {
        mShapeRenderer.setProjectionMatrix(mCamera.combined);
        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity entity : components) {
            DisplayBody displayBody = entity.getComponent(DisplayBody.class);
            Position position = entity.getComponent(Position.class);
            mShapeRenderer.setColor(displayBody.color);

            if (displayBody.shape instanceof Ellipse) {
                renderEllipse(position, displayBody);
            } else if (displayBody.shape instanceof Rectangle) {
                renderRectangle(position, displayBody);
            }
        }
        mShapeRenderer.end();
    }

    private void renderEllipse(Position position, DisplayBody displayBody) {

        mShapeRenderer.ellipse(position.x - displayBody.centerX, position.y - displayBody.centerY,
                ((Ellipse)displayBody.shape).width, ((Ellipse)displayBody.shape).height, 100);
    }

    private void renderRectangle(Position position, DisplayBody displayBody) {
        mShapeRenderer.rect(position.x - displayBody.centerX, position.y - displayBody.centerY,
                ((Rectangle)displayBody.shape).width, ((Rectangle)displayBody.shape).height);
    }
}
