package io.github.mssjsg.pong.pong;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by sing on 1/1/17.
 */

public class PongController {
    private OrthographicCamera camera;
    private SpriteBatch mSpriteBatch;
    private ShapeRenderer mShapeRenderer;
    private int mViewportWidth;
    private int mViewportHeight;

    Texture mLogo;

    public PongController(int viewportWidth, int viewportHeight) {
        mViewportWidth = viewportWidth;
        mViewportHeight = viewportHeight;
        camera = new OrthographicCamera(viewportWidth, viewportHeight);
        camera.position.set(mViewportWidth / 2, mViewportHeight / 2, 0);
        camera.update();
        mSpriteBatch = new SpriteBatch();
        mShapeRenderer = new ShapeRenderer();

        mLogo = new Texture("badlogic.jpg");
    }

    public void resize(int width, int height) {
        camera.viewportWidth = mViewportWidth;
        camera.viewportHeight = (float)height / (float) width * mViewportWidth;
        camera.update();
    }

    public void render(float delta) {
        mSpriteBatch.setProjectionMatrix(camera.combined);
        mSpriteBatch.begin();
        mSpriteBatch.draw(mLogo, (mViewportWidth - mLogo.getWidth()) / 2, (mViewportHeight - mLogo.getHeight()) / 2);
        mSpriteBatch.end();

        mShapeRenderer.setProjectionMatrix(camera.combined);
        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mShapeRenderer.setColor(1, 1, 0, 0);
        mShapeRenderer.rect(0, 0, 10, 400);
        mShapeRenderer.end();
    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {
        mSpriteBatch.dispose();
        mShapeRenderer.dispose();
        mLogo.dispose();
    }
}
