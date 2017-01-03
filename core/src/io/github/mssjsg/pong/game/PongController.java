package io.github.mssjsg.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.mssjsg.pong.game.component.DisplayBody;
import io.github.mssjsg.pong.game.component.HitBody;
import io.github.mssjsg.pong.game.component.Position;
import io.github.mssjsg.pong.game.shape.Ellipse;
import io.github.mssjsg.pong.game.shape.Rectangle;
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

        Rectangle rectangle = new Rectangle();
        rectangle.width = mStageInfo.stageWidth;
        rectangle.height = mStageInfo.stageHeight;

        DisplayBody displayBody = new DisplayBody();
        displayBody.shape = rectangle;
        displayBody.color = Color.CLEAR;

        Entity entity = new Entity()
                .addComponent(new Position(0, 0))
                .addComponent(displayBody);

        mRenderShapeSystem.addEntity(entity);
    }

    private void initGame() {
        initStage();

        float stageCenterX = mStageInfo.stageWidth / 2;
        float stageCenterY = mStageInfo.stageHeight / 2;

        //create ball
        Ellipse ellipse = new Ellipse();
        ellipse.width = 10;
        ellipse.height = 10;

        DisplayBody displayBody = new DisplayBody();
        displayBody.shape = ellipse;
        displayBody.centerX = 5;
        displayBody.centerY = 5;
        displayBody.color = Color.YELLOW;

        Entity entity = new Entity()
                .addComponent(new Position(stageCenterX, stageCenterY))
                .addComponent(displayBody)
                .addComponent(new HitBody(displayBody));

        mRenderShapeSystem.addEntity(entity);

        int length = 50;
        int thickness = 10;

        Rectangle rectLeft = new Rectangle();
        rectLeft.width = length;
        rectLeft.height = thickness;

        Rectangle rectTop = new Rectangle();
        rectTop.width = thickness;
        rectTop.height = length;

        Rectangle rectRight = rectLeft.copy();
        Rectangle rectBottom = rectTop.copy();

        //create racket left
        displayBody = new DisplayBody();
        displayBody.shape = rectLeft;
        displayBody.centerX = length / 2;
        displayBody.centerY = 0;
        displayBody.color = Color.RED;

        addRacket(0, mStageInfo.stageHeight / 2, displayBody);
        //right
        displayBody = new DisplayBody();
        displayBody.shape = rectRight;
        displayBody.centerX = thickness;
        displayBody.centerY = length / 2;
        displayBody.color = Color.RED;

        addRacket(mStageInfo.stageWidth, mStageInfo.stageHeight / 2, displayBody);

        //top
        displayBody = new DisplayBody();
        displayBody.shape = rectTop;
        displayBody.centerX = length / 2;
        displayBody.centerY = thickness;
        displayBody.color = Color.RED;

        addRacket(mStageInfo.stageWidth / 2, 0, displayBody);

        //bottom
        displayBody = new DisplayBody();
        displayBody.shape = rectBottom;
        displayBody.centerX = length / 2;
        displayBody.centerY = 0;
        displayBody.color = Color.RED;

        addRacket(mStageInfo.stageWidth / 2, mStageInfo.stageHeight, displayBody);
    }

    private void addRacket(float x, float y, DisplayBody displayBody) {
        Entity entity = new Entity()
                .addComponent(displayBody)
                .addComponent(new Position(x, y))
                .addComponent(new HitBody(displayBody));
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
