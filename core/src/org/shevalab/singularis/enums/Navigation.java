package org.shevalab.singularis.enums;

import com.badlogic.gdx.Input;

public enum Navigation {
	NONE, UP,LEFT,RIGHT;

	public static Navigation byInputKey(int key){
		switch (key){
			case Input.Keys.RIGHT:
				return Navigation.RIGHT;
			case Input.Keys.LEFT:
				return Navigation.LEFT;
			case Input.Keys.UP:
				return Navigation.UP;
		}
		return Navigation.NONE;
	}
}
