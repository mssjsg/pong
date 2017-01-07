package io.github.mssjsg.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import io.github.mssjsg.pong.game.system.Box2dSystem;
import io.github.mssjsg.pong.game.system.RenderShapeSystem;

/**
 * Created by sing on 1/1/17.
 */

public class GameController {

    private Entity mStage;
    private Array<Entity> mBalls;
    private Array<Entity> mRackets;
    private int mKeysPressed;

    private OrthographicCamera mCamera;
    private OrthographicCamera mBoxCamera;
    private SpriteBatch mSpriteBatch;
    private ShapeRenderer mShapeRenderer;

    private StageInfo mStageInfo;
    private Texture mLogo;

    private GameState mGameState;


    private RenderShapeSystem mRenderShapeSystem; //render shapes
    private Box2dSystem mBox2dSystem; //physics

    private PongEntityFactory mPongEntityFactory;

    private float mTimeStep = 1/24f;

    public GameController() {

        mPongEntityFactory = new PongEntityFactory();
        mGameState = new GameState();
        mSpriteBatch = new SpriteBatch();
        mShapeRenderer = new ShapeRenderer();

        mStageInfo = new StageInfo();
        mStageInfo.stageHeight = 600;
        mStageInfo.stageWidth = 600;

        mLogo = new Texture("badlogic.jpg");

        initCamera(mStageInfo.stageWidth / 2, mStageInfo.stageHeight / 2);

        //init systems
        mRenderShapeSystem = new RenderShapeSystem(mCamera, mShapeRenderer);
        mBox2dSystem = new Box2dSystem(mBoxCamera);

        mBalls = new Array<Entity>();
        mRackets = new Array<Entity>();
        mKeysPressed = 0;

        mGameState.state = States.PLAYING;
        startGame(mStageInfo);
    }

    private void startGame(StageInfo stageInfo) {
        setupStage(stageInfo);
        mBox2dSystem.applyForce(mBalls.get(0), 5, 5);
    }

    private void setupStage(StageInfo stageInfo) {
        mStage = mPongEntityFactory.createStage(stageInfo.stageWidth, stageInfo.stageHeight);
        mRenderShapeSystem.addEntity(mStage);

        float centerX = stageInfo.stageWidth / 2f;
        float centerY = stageInfo.stageHeight / 2f;

        Entity ball = mPongEntityFactory.createBall(10, centerX, centerY, Color.YELLOW);
        mBalls.add(ball);

        float length = 100;
        float thickness = 10;

        mRackets.add(mPongEntityFactory.createRacket(PongEntityFactory.RacketSide.LEFT, length, thickness, 0, centerY, Color.RED));
        mRackets.add(mPongEntityFactory.createRacket(PongEntityFactory.RacketSide.RIGHT, length, thickness, stageInfo.stageWidth, centerY, Color.RED));
        mRackets.add(mPongEntityFactory.createRacket(PongEntityFactory.RacketSide.TOP, length, thickness, centerX, 0, Color.RED));
        mRackets.add(mPongEntityFactory.createRacket(PongEntityFactory.RacketSide.BOTTOM, length, thickness, centerX, stageInfo.stageHeight, Color.RED));

        for (Entity racket : mRackets) {
            mBox2dSystem.addEntity(racket, true);
            mRenderShapeSystem.addEntity(racket);
        }

        mBox2dSystem.addEntity(ball, true);
        mRenderShapeSystem.addEntity(ball);
    }

    private void initCamera(float cameraPosX, float cameraPosY) {
        float width = mStageInfo.stageWidth;
        float height = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth() * width;

        mCamera = new OrthographicCamera(width, height);
        mCamera.position.set(cameraPosX, cameraPosY, 0);
        mCamera.update();

        mBoxCamera = new OrthographicCamera(width * Box2dSystem.PX_TO_BOX, height * Box2dSystem.PX_TO_BOX);
        mBoxCamera.position.set(cameraPosX * Box2dSystem.PX_TO_BOX, cameraPosY * Box2dSystem.PX_TO_BOX, 0);
        mBoxCamera.update();
    }


    private void resumeGame() {
        mGameState.state = States.PLAYING;
    }

    private void pauseGame() {
        mGameState.state = States.PAUSED;
    }

    public void resize(int width, int height) {
        mCamera.viewportWidth = mStageInfo.stageWidth;
        mCamera.viewportHeight = (float)height / (float) width * mStageInfo.stageWidth;
        mCamera.update();

        mBoxCamera.viewportWidth = mStageInfo.stageWidth * Box2dSystem.PX_TO_BOX;
        mBoxCamera.viewportHeight = (float)height / (float) width * mStageInfo.stageWidth * Box2dSystem.PX_TO_BOX;
        mBoxCamera.update();
    }

    public void render(float delta) {
        stepTime(delta);
    }

    public void update(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        mSpriteBatch.setProjectionMatrix(mCamera.combined);
//        mSpriteBatch.begin();
//        mSpriteBatch.draw(mLogo, (mStageInfo.stageWidth - mLogo.getWidth()) / 2, (mStageInfo.stageHeight - mLogo.getHeight()) / 2);
//        mSpriteBatch.end();

        mRenderShapeSystem.update(delta);
        mBox2dSystem.update(delta);
    }

    private float accumulator = 0;

    private void stepTime(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= mTimeStep) {
            update(mTimeStep);
            accumulator -= mTimeStep;
        }
    }

    public void pause() {
        pauseGame();
    }

    public void resume() {
        resumeGame();
    }

    public void dispose() {
        mSpriteBatch.dispose();
        mShapeRenderer.dispose();
        mBox2dSystem.dispose();

        mLogo.dispose();
    }

    private boolean isKeyPressed(int key) {
        return (mKeysPressed & key) > 0;
    }

    public void press(int key) {
        mKeysPressed |= key;
    }

    public void unpress(int key) {
        mKeysPressed &= ~key;
    }
}
