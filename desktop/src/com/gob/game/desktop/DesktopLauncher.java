package com.gob.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gob.game.GamePlane;

public class DesktopLauncher {

    public static void main (String[] arg) {
       
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = GamePlane.SCREEN_WIDTH;
                config.height = GamePlane.SCREEN_HEIGHT;
                config.fullscreen = true;
                config.vSyncEnabled = true;
                config.resizable = false;
		new LwjglApplication(new GamePlane(), config);
	}
}