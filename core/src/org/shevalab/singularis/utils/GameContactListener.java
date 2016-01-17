package org.shevalab.singularis.utils;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by eugeneshevchenko on 17.01.16.
 */
public class GameContactListener implements ContactListener {

    private World world;

    public GameContactListener(World world) {
        this.world = world;
    }

    @Override
    public void beginContact(Contact contact) {
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        WorldManifold manifold = contact.getWorldManifold();
        for(int j = 0; j < manifold.getNumberOfContactPoints(); j++){
            if(contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().equals("p"))
                contact.setEnabled(false);
            if(contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals("p"))
                contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Body body = null;
        if(contact.getFixtureA() != null && contact.getFixtureA().getUserData() != null  && contact.getFixtureA().getUserData().equals("p"))
        body = contact.getFixtureA().getBody();

        if(contact.getFixtureB() != null && contact.getFixtureB().getUserData() != null  && contact.getFixtureB().getUserData().equals("p"))
        body = contact.getFixtureB().getBody();

        if(body != null){

            body.setActive(false);
            world.destroyBody(body);
        }

    }
}
