package io.github.mssjsg.pong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by sing on 12/31/16.
 */

public class GameScreen extends Screen {

    private GameInputProcessor mGameInputProcessor;

    public GameScreen(ScreenManager screenManager) {
        super(screenManager);
        mGameInputProcessor = new GameInputProcessor();
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
    }

    private void back() {
        setScreen(Screens.createMenuScreen(this));
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
