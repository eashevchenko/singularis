package org.shevalab.singularis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Singularis extends ApplicationAdapter {

	float screenHeigth;
	float screenWidth;

	OrthographicCamera orthographicCamera;

	SpriteBatch batch;
	Sprite sprite;

	@Override
	public void create () {
		screenWidth = Gdx.graphics.getWidth();
		screenHeigth = Gdx.graphics.getHeight();
		orthographicCamera = new OrthographicCamera(screenWidth, screenHeigth);
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture("badlogic.jpg"));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(orthographicCamera.combined);
		batch.begin();
		batch.draw(sprite,0,0);
		batch.end();
	}
}
