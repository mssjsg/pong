package io.github.mssjsg.pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import io.github.mssjsg.pong.screen.MenuScreen;
import io.github.mssjsg.pong.screen.Screen;
import io.github.mssjsg.pong.screen.ScreenManager;

/**
 * Created by sing on 12/31/16.
 */

public class GameController extends ApplicationAdapter {
    private Screen mScreen;

    private ScreenManager mScreenManager = new ScreenManagerImpl();

    @Override
    public void create() {
        super.create();
        mScreenManager.setScreen(new MenuScreen(mScreenManager));
    }

    @Override
    public void render() {
        super.render();
        mScreen.render(getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        mScreen.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        mScreen.dispose();
    }

    @Override
    public void pause() {
        super.pause();
        mScreen.pause();
    }

    @Override
    public void resume() {
        super.resume();
        mScreen.resume();
    }

    public float getDeltaTime() {
        // get delta time and ensure an upper limit of one 60th second
        return Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);
    }

    private class ScreenManagerImpl implements ScreenManager {

        @Override
        public void setScreen(Screen screen) {
            if (mScreen != null) {
                mScreen.dispose();
            }
            mScreen = screen;
            Gdx.input.setInputProcessor(mScreen.getInputProcessor());
        }
    }
}
