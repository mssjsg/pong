package io.github.mssjsg.pong.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import io.github.mssjsg.pong.game.component.Body;
import io.github.mssjsg.pong.game.component.DisplayBody;
import io.github.mssjsg.pong.game.component.Position;

/**
 * Created by sing on 1/1/17.
 */

public class PongController {
    private OrthographicCamera camera;
    private SpriteBatch mSpriteBatch;
    private ShapeRenderer mShapeRenderer;

    private Texture mLogo;

    private GameState mGameState;

    private int mStageWidth = 600;
    private int mStageHeight = 600;

    private ComponentSystemManager mComponentSystemManager;

    public PongController() {
        mComponentSystemManager = new ComponentSystemManager();
        mComponentSystemManager.addSystem(new DisplayBodySystem(mShapeRenderer));

        mGameState = new GameState();
        mSpriteBatch = new SpriteBatch();
        mShapeRenderer = new ShapeRenderer();

        mLogo = new Texture("badlogic.jpg");

        initCamera(mStageWidth / 2, mStageHeight / 2);
        initGame();
    }

    private void initCamera(float cameraPosX, float cameraPosY) {
        camera = new OrthographicCamera();
        camera.position.set(cameraPosX, cameraPosY, 0);
        camera.update();
    }

    private void initStage() {

        DisplayBody displayBody = new DisplayBody(Body.SHAPE_RECT, Color.YELLOW);

        new GameObjectBuilder(mComponentSystemManager)
                .addComponent(new Position(0, 0))
                .addComponent(displayBody).build();
    }

    private void initGame() {
        initStage();

        float stageCenterX = mStageWidth / 2;
        float stageCenterY = mStageHeight / 2;

        //create ball
        DisplayBody displayBody = new DisplayBody(Body.SHAPE_CIRCLE, Color.BLUE);
        displayBody.width = 10;
        displayBody.height = 10;
        displayBody.centerX = 5;
        displayBody.centerY = 5;

        new GameObjectBuilder(mComponentSystemManager)
                .addComponent(new Position(stageCenterX, stageCenterY))
                .addComponent(displayBody).build();

        //create racket left
        displayBody = new DisplayBody(Body.SHAPE_RECT, Color.RED);
        displayBody.width = 10;
        displayBody.height = 50;
        displayBody.centerX = 0;
        displayBody.centerY = 25;

        new GameObjectBuilder(mComponentSystemManager)
                .addComponent(new Position(10, 300))
                .addComponent(displayBody).build();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = mStageWidth;
        camera.viewportHeight = (float)height / (float) width * mStageWidth;
        camera.update();
    }

    public void render(float delta) {
        mSpriteBatch.setProjectionMatrix(camera.combined);
        mSpriteBatch.begin();
        mSpriteBatch.draw(mLogo, (mStageWidth - mLogo.getWidth()) / 2, (mStageHeight - mLogo.getHeight()) / 2);
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
