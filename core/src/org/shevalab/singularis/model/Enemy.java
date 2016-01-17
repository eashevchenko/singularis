package org.shevalab.singularis.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends DynamicModels {

    private boolean isAlive = true;


    public Enemy(World world, Vector2 position) {
        super(1, 1);
	    bodyDef.position.set(position);
	    modelBody = world.createBody(bodyDef);
	    modelBody.createFixture(fixtureDef);
    }

    public void move(float delta){
       getModelBody().setLinearVelocity(new Vector2(-1f*delta, getModelBody().getLinearVelocity().y));
    }

    @Override
    public void reset() {
        modelBody.setTransform(0,0,0);
        isAlive = false;
    }
}
