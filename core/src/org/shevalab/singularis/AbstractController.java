package org.shevalab.singularis;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import org.shevalab.singularis.enums.Navigation;

public abstract class AbstractController {

	protected float screenHeight;
	protected float screenWidth;
	protected Navigation direction;
	protected OrthographicCamera camera;

	public AbstractController(float screenHeight, float screenWidth, OrthographicCamera camera) {
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.camera = camera;
	}

	protected InputListener keyPressListener = new InputListener() {
		@Override
		public boolean keyDown(InputEvent event, int keycode) {
			direction = Navigation.byInputKey(keycode);
			return true;
		}

		@Override
		public boolean keyUp(InputEvent event, int keycode) {
			direction = Navigation.NONE;
			return true;
		}
	};

	public InputListener buildListener(final Navigation navigation){
		return new  InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				direction = navigation;
				return true;

			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				direction = Navigation.NONE;
			}
		};
	}
}
