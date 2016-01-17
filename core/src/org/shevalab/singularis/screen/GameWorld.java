package org.shevalab.singularis.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import org.shevalab.singularis.model.Enemy;

/**
 * Created by eugeneshevchenko on 16.01.16.
 */
public class GameWorld implements Disposable{

    private World world;
    private Box2DDebugRenderer renderer;

    private Array<Enemy> enemies = new Array<Enemy>();
    private Pool<Enemy> enemyPool;


    public GameWorld(World world, Box2DDebugRenderer renderer) {
        this.world = world;
        this.renderer = renderer;

    }

    public void initEnemies() {
        enemyPool = new Pool<Enemy>() {
            @Override
            protected Enemy newObject() {
                return new Enemy(world, new Vector2(1,1));
            }
        };


    }

    public void updateEnemies(float delta){
        Enemy  enemy= enemyPool.obtain();
        enemies.add(enemy);
        enemy.move(delta);
    }


    public void draw(float delta, OrthographicCamera camera){
        world.step(1 / 60f, 6, 2);
        renderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
