package io.github.mssjsg.pong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

import io.github.mssjsg.pong.game.PongController;

/**
 * Created by sing on 12/31/16.
 */

public class GameScreen extends Screen {

    private static final int VIEWPORT_WIDTH = 640;
    private static final int VIEWPORT_HEIGHT = 400;

    private GameInputProcessor mGameInputProcessor;
    private PongController mPongController;

    public GameScreen(ScreenManager screenManager) {
        super(screenManager);
        mGameInputProcessor = new GameInputProcessor();
        mPongController = new PongController();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return mGameInputProcessor;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mPongController.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        mPongController.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
        mPongController.pause();
    }

    @Override
    public void resume() {
        super.resume();
        mPongController.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        mPongController.dispose();
    }

    private void back() {
        setScreen(Screens.createMenuScreen(mScreenManager));
    }

    private class GameInputProcessor implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.UP:
                    return true;
                case Input.Keys.DOWN:
                    return true;
                case Input.Keys.LEFT:
                    return true;
                case Input.Keys.RIGHT:
                    return true;
            }
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case Input.Keys.BACK:
                case Input.Keys.BACKSPACE:
                    back();
                    return true;
            }
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
