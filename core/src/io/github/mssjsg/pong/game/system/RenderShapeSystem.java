package io.github.mssjsg.pong.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import io.github.mssjsg.pong.game.Entity;
import io.github.mssjsg.pong.game.component.Body;
import io.github.mssjsg.pong.game.component.DisplayBody;
import io.github.mssjsg.pong.game.component.Position;
import io.github.mssjsg.pong.game.component.ShapeStyle;

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
            ShapeStyle shapeStyle = entity.getComponent(ShapeStyle.class);
            DisplayBody displayBody = entity.getComponent(DisplayBody.class);
            Position position = entity.getComponent(Position.class);
            mShapeRenderer.setColor(shapeStyle.color);

            switch (displayBody.shape) {
                case Body.SHAPE_CIRCLE:
                    renderCircle(position, displayBody);
                    break;
                case Body.SHAPE_RECT:
                    renderRectangle(position, displayBody);
                    break;
            }
        }
        mShapeRenderer.end();
    }

    private void renderCircle(Position position, DisplayBody displayBody) {
//        ...
//        float alphaMultiplier = 0.5f; //you may play with different coefficients
//        float radiusStep = radius/200;
//        int sampleRate = 3;
//        ...
//
////do not forget to enable blending
//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//
//        shapeRenderer.begin(ShapeType.Filled);
//
////first rendering
//        shapeRenderer.setColor(r, g, b, a);
//        shapeRenderer.circle(x, y, radius);
//
////additional renderings
//        for(int i=0; i<sampleRate; i++) {
//            a *= alphaMultiplier;
//            radius += radiusStep;
//            shapeRenderer.setColor(r, g, b, a);
//            shapeRenderer.circle(x, y, radius);
//        }
//
//        shapeRenderer.end();
//        ...

        mShapeRenderer.ellipse(position.x - displayBody.centerX, position.y - displayBody.centerY, displayBody.width, displayBody.height, 100);
    }

    private void renderRectangle(Position position, DisplayBody displayBody) {
        mShapeRenderer.rect(position.x - displayBody.centerX, position.y - displayBody.centerY, displayBody.width, displayBody.height);
    }
}
