package io.github.mssjsg.pong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import io.github.mssjsg.pong.ApplicationController;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pong!";
		config.width = 360;
		config.height = 640;
		new LwjglApplication(new ApplicationController(), config);
	}
}
