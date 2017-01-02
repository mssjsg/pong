package io.github.mssjsg.pong.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * Created by sing on 1/1/17.
 */

public class PongController {
    private OrthographicCamera camera;
    private SpriteBatch mSpriteBatch;
    private ShapeRenderer mShapeRenderer;

    private Texture mLogo;

    private GameState mGameState;

    private PongStage mStage;
    private Ball mBall;
    private Array<Racket> mRackets;

    private DisplayBodySystem mDisplayBodySystem;

    public PongController() {

        mDisplayBodySystem = new DisplayBodySystem();

        mGameState = new GameState();

        mStage = new PongStage();

        mSpriteBatch = new SpriteBatch();
        mShapeRenderer = new ShapeRenderer();

        mLogo = new Texture("badlogic.jpg");
    }

    private void initCamera(float cameraPosX, float cameraPosY) {
        camera = new OrthographicCamera();
        camera.position.set(cameraPosX, cameraPosY, 0);
        camera.update();
    }

    private void initStage() {
        mStage.body.width = 600;
        mStage.body.width = 600;
    }

    private void initGame() {
        float stageWidth = mStage.body.width;
        float stageHeight = mStage.body.height;

        mBall = new Ball(0, 0);
        mRackets = new Array<Racket>();

        Racket leftRacket = new Racket();
        Racket topRacket = new Racket();
        Racket rightRacket = new Racket();
        Racket bottomRacket = new Racket();

        mRackets.add(leftRacket);
        mRackets.add(topRacket);
        mRackets.add(rightRacket);
        mRackets.add(bottomRacket);

        mBall.position.set(stageWidth, stageHeight);
        leftRacket.position.set(0, );
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
