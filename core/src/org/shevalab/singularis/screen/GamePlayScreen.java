package org.shevalab.singularis.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;
import org.shevalab.singularis.Controller;

public class GamePlayScreen implements Screen {

    int screenHeigth;
    int screenWidth;

    final float PPM = 100;

    OrthographicCamera orthographicCamera;
    Viewport viewport;
    SpriteBatch batch;
    AnimatedSprite sprite;

    World world;
    Box2DDebugRenderer renderer;
    Body playerBody, landBody;

    Texture bg, bg2;

    TextureAtlas textureAtlas;
    Animation animation;


    Controller controller;

    String vertexShader;
    String fragmentShader;
    ShaderProgram shaderProgram;


    @Override
    public void show() {
        world = new World(new Vector2(0f, -9.8f), true);
        renderer = new Box2DDebugRenderer();

        batch = new SpriteBatch();
        controller = new Controller(batch);

        initShaders();
        setupCamera();
        createPlayerPhysicsBody();
        createLandPhysicsBody();
        initPlayerSprite();
        initBackground();
        setupMusic();
    }

    private void setupMusic() {
        Music music = Gdx.audio.newMusic(Gdx.files.internal("mfx/1.mp3"));
        music.setVolume(0.3f);
        music.setLooping(true);
        music.play();
    }


    private void initBackground() {
        bg = new Texture("gfx/bg/bg.png");
        bg2 = new Texture("gfx/bg/bg2.png");
    }

    private void initPlayerSprite() {
        textureAtlas = new TextureAtlas(Gdx.files.internal("gfx/hero/singularis.atlas"));
        animation = new Animation(1f / 14f, textureAtlas.findRegions("Idle"), Animation.PlayMode.LOOP_PINGPONG);
        sprite = new AnimatedSprite(animation);
    }

    private void initShaders() {
        vertexShader = Gdx.files.internal("shaders/vertex.glsl").readString();
        fragmentShader = Gdx.files.internal("shaders/fragment.glsl").readString();
        shaderProgram = new ShaderProgram(vertexShader, fragmentShader);
        //    batch.setShader(shaderProgram);
        System.out.println(shaderProgram.isCompiled() ? "shader compile..." : "shaders error! " + shaderProgram.getLog());

    }

    private void setupCamera() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeigth = Gdx.graphics.getHeight();
        orthographicCamera = new OrthographicCamera(screenWidth, screenHeigth);
        viewport = new FillViewport(screenWidth / PPM, screenHeigth / PPM, orthographicCamera);
        viewport.apply();
        orthographicCamera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        batch.setProjectionMatrix(orthographicCamera.combined);
    }


    private void createPlayerPhysicsBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(viewport.getWorldWidth() / 2, 80 / PPM);
        playerBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(40 / PPM, 60 / PPM);

        fixtureDef.shape = shape;
        playerBody.createFixture(fixtureDef);

        playerBody.setUserData(sprite);

        /*MassData data = playerBody.getMassData();
        data.center.set(10, 10);
        playerBody.setMassData(data);*/

    }

    private void createLandPhysicsBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(viewport.getWorldWidth() / 2, 0);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        landBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(viewport.getWorldWidth() / 2, 80 / PPM);

        fixtureDef.shape = polygonShape;
        landBody.createFixture(fixtureDef);
    }

    private void handleInput() {
        if (controller.isLeftPressed()) {
            playerBody.setLinearVelocity(new Vector2(-1f, playerBody.getLinearVelocity().y));
        } else if (controller.isRightPressed()) {
            playerBody.setLinearVelocity(new Vector2(1f, playerBody.getLinearVelocity().y));
        } else if (controller.isUpPressed() && playerBody.getLinearVelocity().y == 0) {
            playerBody.applyLinearImpulse(new Vector2(0, 5f), playerBody.getWorldCenter(), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            batch.setColor(1, 0, 1, 1f);
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, screenWidth, screenHeigth);

        Vector2 spritePos = new Vector2(playerBody.getPosition().x * PPM - sprite.getWidth() / 3, playerBody.getPosition().y * PPM - sprite.getHeight() / 2);

        world.step(1 / 60f, 6, 2);
        orthographicCamera.unproject(new Vector3(spritePos.x, spritePos.y, 0));
        orthographicCamera.update();


  //      batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.draw(bg2, 0, 0);
        sprite.draw(batch);
        batch.end();
        sprite.setPosition(spritePos.x, spritePos.y);
        sprite.update(delta);

        renderer.render(world, orthographicCamera.combined);

        controller.draw();

        handleInput();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        controller.resize(width, height);
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
        if (shaderProgram != null) {
            shaderProgram.dispose();
        }
    }
}
