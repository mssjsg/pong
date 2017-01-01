package io.github.mssjsg.pong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import io.github.mssjsg.pong.GameController;
import io.github.mssjsg.pong.Pong;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pong!";
		config.width = 640;
		config.height = 400;
		new LwjglApplication(new GameController(), config);
	}
}
