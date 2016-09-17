package com.waznop.paddlebear.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.waznop.paddlebear.PBGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Paddle Bear";
		config.width = 405;
		config.height = 720;
		new LwjglApplication(new PBGame(), config);
	}
}
