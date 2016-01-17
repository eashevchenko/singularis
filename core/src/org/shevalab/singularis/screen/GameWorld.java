package org.shevalab.singularis.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Disposable;
import org.shevalab.singularis.model.DynamicModels;
import org.shevalab.singularis.model.StaticModel;

/**
 * Created by eugeneshevchenko on 16.01.16.
 */
public class GameWorld implements Disposable{

    private World world;
    private Box2DDebugRenderer renderer;
    private List<StaticModel> staticModels;
    private List<DynamicModels> dynamicModelsList;

    public GameWorld(World world, Box2DDebugRenderer renderer) {
        this.world = world;
        this.renderer = renderer;

    }

    public void init(){
        world = new World(new Vector2(0f, -9.8f), true);
        renderer = new Box2DDebugRenderer();

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
