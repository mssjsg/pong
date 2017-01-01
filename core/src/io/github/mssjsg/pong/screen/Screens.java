package io.github.mssjsg.pong.screen;

/**
 * Created by sing on 1/1/17.
 */

public final class Screens {

    private Screens() {}

    public static Screen createMenuScreen(Screen context) {
        return new MenuScreen(context.mScreenManager);
    }

    public static Screen createGameScreen(Screen context) {
        return new GameScreen(context.mScreenManager);
    }
}
