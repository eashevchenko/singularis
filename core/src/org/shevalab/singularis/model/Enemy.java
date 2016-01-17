package org.shevalab.singularis.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by eugeneshevchenko on 16.01.16.
 */
public class Enemy extends DynamicModels implements Pool.Poolable {

    private boolean isAlive;


    public Enemy(World world, Vector2 position) {
        super(1, 1);
        isAlive = true;
    }

    public void move(float delta){
       getModelBody().setLinearVelocity(new Vector2(-1f, getModelBody().getLinearVelocity().y));
    }

    @Override
    public void reset() {
        modelBody.setTransform(0,0,0);
        isAlive = false;
    }
}
