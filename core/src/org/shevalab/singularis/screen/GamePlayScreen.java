package org.shevalab.singularis.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public class GamePlayScreen implements Screen {

    float screenHeigth;
    float screenWidth;

    private static final float WORLD_TO_BOX = 1f;

    OrthographicCamera orthographicCamera;
    Viewport viewport;
    SpriteBatch batch;
    AnimatedSprite sprite;

    World world;
    Box2DDebugRenderer renderer;
    Body playerBody, landBody;

    TextureAtlas textureAtlas;
    Animation animation;
    float time;


    @Override
    public void show() {
        setupCamera();
        renderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();

        textureAtlas = new TextureAtlas(Gdx.files.internal("gfx/hero/singularis.atlas"));
        animation = new Animation(1f / 15f, textureAtlas.getRegions(), Animation.PlayMode.LOOP_PINGPONG);

        sprite = new AnimatedSprite(animation);
        sprite.play();

        world = new World(new Vector2(0f, -9.8f), true);
        createPlayerPhysicsBody();
        createLandPhysicsBody();
    }

    private void createPlayerPhysicsBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(screenWidth/2, screenHeigth/2);

        playerBody = world.createBody(bodyDef);

        MassData data = playerBody.getMassData();
        data.center.set(10, 10);
        playerBody.setMassData(data);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/3, sprite.getHeight()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = playerBody.createFixture(fixtureDef);
        shape.dispose();
    }

    private void createLandPhysicsBody() {
      BodyDef  groundDef = new BodyDef();
        groundDef.position.set(new Vector2((Gdx.graphics.getWidth() / 2) * WORLD_TO_BOX, 16f * WORLD_TO_BOX));
        landBody = world.createBody(groundDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox((Gdx.graphics.getWidth() / 2) * WORLD_TO_BOX, 16f * WORLD_TO_BOX);
        landBody.createFixture(groundShape, 0f);
        groundShape.dispose();
    }


    private void setupCamera() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeigth = Gdx.graphics.getHeight();
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(screenWidth, screenHeigth, orthographicCamera);
        viewport.apply();
        orthographicCamera.position.set(orthographicCamera.viewportWidth / 2, orthographicCamera.viewportHeight / 2, 0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        orthographicCamera.update();

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        sprite.setPosition(playerBody.getPosition().x - sprite.getWidth()/2, playerBody.getPosition().y - sprite.getHeight()/2);

        time += Gdx.graphics.getDeltaTime();
        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        batch.draw(sprite, sprite.getX(), sprite.getY());
        batch.end();

        sprite.update(delta);

        renderer.render(world, orthographicCamera.combined);


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
        world.dispose();
    }
}
