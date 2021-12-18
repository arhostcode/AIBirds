package space.ardyc.game_gdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import space.ardyc.game_gdx.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "AI Birds";
		config.width = 500;
		config.height = 600;
		config.resizable = false;
		new LwjglApplication(new Game(), config);
	}
}
