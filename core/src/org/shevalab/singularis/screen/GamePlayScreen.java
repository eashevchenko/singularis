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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.shevalab.singularis.Controller;
import org.shevalab.singularis.model.Player;
import org.shevalab.singularis.model.StaticModel;

import static org.shevalab.singularis.utils.Constants.PPM;

public class GamePlayScreen implements Screen {

    int screenHeigth;
    int screenWidth;

    OrthographicCamera orthographicCamera;
    Viewport viewport;
    SpriteBatch batch;

    World world;
    Box2DDebugRenderer renderer;
    StaticModel land;

    Texture bg, bg2;

    TextureAtlas textureAtlas;


    Controller controller;

    String vertexShader;
    String fragmentShader;
    ShaderProgram shaderProgram;

    Player player;

    GameWorld gameWorld;


    @Override
    public void show() {
        world = new World(new Vector2(0f, -9.8f), true);
        renderer = new Box2DDebugRenderer();

        batch = new SpriteBatch();
        controller = new Controller(batch);

        initShaders();
        setupCamera();

        gameWorld = new GameWorld(world,renderer);
        gameWorld.initEnemies();

	    Vector2 playerPosition = createVector(2, 80);
	    player = new Player(world, playerPosition);
        player.updatePlayerSprite(getPlayerSprite());

	    Vector2 landPosition = createVector(2);
	    Vector2 landShapePosition = createVector(2, 80);
	    land = new StaticModel(world, BodyDef.BodyType.StaticBody, landPosition, landShapePosition);

   //   world.setContactListener(new GameContactListener(world));
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

    private Animation getPlayerSprite() {
        textureAtlas = new TextureAtlas(Gdx.files.internal("gfx/hero/singularis.atlas"));
        return new Animation(1f / 14f, textureAtlas.findRegions("Idle"), Animation.PlayMode.LOOP_PINGPONG);
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

	private Vector2 createVector(int xDivider, int yDivisor){
		return new Vector2(viewport.getWorldWidth() / xDivider, yDivisor / PPM);
	}

	private Vector2 createVector(int xDivider){
		return new Vector2(viewport.getWorldWidth() / xDivider, 0);
	}

    private void handleInput() {
        if (controller.isLeftPressed()) {
            player.getModelBody().setLinearVelocity(new Vector2(-1f, player.getBodyLinearVelocityY()));
        } else if (controller.isRightPressed()) {
            player.getModelBody().setLinearVelocity(new Vector2(1f, player.getBodyLinearVelocityY()));
        } else if (controller.isUpPressed() && player.getBodyLinearVelocityY() == 0) {
            player.getModelBody().applyLinearImpulse(new Vector2(0, 5f), player.getModelBody().getWorldCenter(), true);
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

        Vector2 spritePos = player.getBodyPosition();

        world.step(1 / 60f, 6, 2);
        orthographicCamera.unproject(new Vector3(spritePos.x, spritePos.y, 0));
        orthographicCamera.update();

        gameWorld.updateEnemies();


        //batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.draw(bg2, 0, 0);

        player.drawPlayer(batch);
       //sprite.draw(batch);
        batch.end();
        player.updatePosition(delta, spritePos);
        //sprite.setPosition(spritePos.x, spritePos.y);
        //sprite.update(delta);

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
