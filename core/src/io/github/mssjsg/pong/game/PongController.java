package io.github.mssjsg.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.mssjsg.pong.game.component.Body;
import io.github.mssjsg.pong.game.component.DisplayBody;
import io.github.mssjsg.pong.game.component.Position;
import io.github.mssjsg.pong.game.component.ShapeStyle;
import io.github.mssjsg.pong.game.system.RenderShapeSystem;

/**
 * Created by sing on 1/1/17.
 */

public class PongController {
    private OrthographicCamera mCamera;
    private SpriteBatch mSpriteBatch;
    private ShapeRenderer mShapeRenderer;

    private Texture mLogo;

    private GameState mGameState;

    private StageInfo mStageInfo;

    private RenderShapeSystem mRenderShapeSystem;

    public PongController() {
        mGameState = new GameState();
        mSpriteBatch = new SpriteBatch();
        mShapeRenderer = new ShapeRenderer();

        mStageInfo = new StageInfo();
        mStageInfo.stageHeight = 600;
        mStageInfo.stageWidth = 600;

        mLogo = new Texture("badlogic.jpg");

        initCamera(mStageInfo.stageWidth / 2, mStageInfo.stageHeight / 2);
        mRenderShapeSystem = new RenderShapeSystem(mCamera, mShapeRenderer);

        initGame();
    }

    private void initCamera(float cameraPosX, float cameraPosY) {
        float width = mStageInfo.stageWidth;
        float height = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth() * width;

        mCamera = new OrthographicCamera(width, height);
        mCamera.position.set(cameraPosX, cameraPosY, 0);
        mCamera.update();
    }

    private void initStage() {

        DisplayBody displayBody = new DisplayBody();
        displayBody.width = mStageInfo.stageWidth;
        displayBody.height = mStageInfo.stageHeight;

        ShapeStyle shapeStyle = new ShapeStyle();
        shapeStyle.color = Color.CLEAR;

        Entity entity = new Entity()
                .addComponent(new Position(0, 0))
                .addComponent(displayBody)
                .addComponent(shapeStyle);

        mRenderShapeSystem.addEntity(entity);
    }

    private void initGame() {
        initStage();

        float stageCenterX = mStageInfo.stageWidth / 2;
        float stageCenterY = mStageInfo.stageHeight / 2;

        //create ball
        DisplayBody displayBody = new DisplayBody();
        displayBody.width = 100;
        displayBody.height = 100;
        displayBody.centerX = 5;
        displayBody.centerY = 5;
        displayBody.shape = Body.SHAPE_CIRCLE;

        ShapeStyle shapeStyle = new ShapeStyle();
        shapeStyle.color = Color.YELLOW;

        Entity entity = new Entity()
                .addComponent(new Position(stageCenterX, stageCenterY))
                .addComponent(displayBody)
                .addComponent(shapeStyle);

        mRenderShapeSystem.addEntity(entity);

        //create racket left
        displayBody = new DisplayBody();
        displayBody.width = 10;
        displayBody.height = 50;
        displayBody.centerX = 0;
        displayBody.centerY = 25;

        shapeStyle = new ShapeStyle();
        shapeStyle.color = Color.RED;

        entity = new Entity()
                .addComponent(new Position(10, 300))
                .addComponent(displayBody)
                .addComponent(shapeStyle);

        mRenderShapeSystem.addEntity(entity);
    }

    public void resize(int width, int height) {
        mCamera.viewportWidth = mStageInfo.stageWidth;
        mCamera.viewportHeight = (float)height / (float) width * mStageInfo.stageWidth;
        mCamera.update();
    }

    public void render(float delta) {
        mSpriteBatch.setProjectionMatrix(mCamera.combined);
        mSpriteBatch.begin();
        mSpriteBatch.draw(mLogo, (mStageInfo.stageWidth - mLogo.getWidth()) / 2, (mStageInfo.stageHeight - mLogo.getHeight()) / 2);
        mSpriteBatch.end();

        mRenderShapeSystem.update(delta);
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
