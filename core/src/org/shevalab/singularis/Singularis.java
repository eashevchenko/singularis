package org.shevalab.singularis;

import com.badlogic.gdx.Game;
import org.shevalab.singularis.screen.LoadingScreen;

public class Singularis extends Game{

	@Override
	public void create() {
		setScreen(new LoadingScreen(this));
	}
}
