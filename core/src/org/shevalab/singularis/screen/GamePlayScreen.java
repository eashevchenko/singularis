package org.shevalab.singularis.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GamePlayScreen implements Screen {

    float screenHeigth;
    float screenWidth;

    OrthographicCamera orthographicCamera;
    Viewport viewport;
    SpriteBatch batch;

    TextureAtlas textureAtlas;
    Animation animation;
    float time;

    @Override
    public void show() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeigth = Gdx.graphics.getHeight();
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(screenWidth, screenHeigth, orthographicCamera);
        viewport.apply();
        orthographicCamera.position.set(orthographicCamera.viewportWidth / 2, orthographicCamera.viewportHeight / 2, 0);

        batch = new SpriteBatch();

        textureAtlas = new TextureAtlas(Gdx.files.internal("gfx/hero/singularis.atlas"));
        animation = new Animation(1f / 15f, textureAtlas.getRegions());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        time += Gdx.graphics.getDeltaTime();
        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        batch.draw(animation.getKeyFrame(time, true), 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
    }
}
