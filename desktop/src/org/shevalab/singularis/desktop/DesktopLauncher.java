package org.shevalab.singularis.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.shevalab.singularis.Singularis;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Singularis(), config);
		config.useGL30 = false;
		config.vSyncEnabled =  true;
	}
}
