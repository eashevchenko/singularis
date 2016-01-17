package org.shevalab.singularis.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.shevalab.singularis.utils.Constants;

/**
 * Created by eugeneshevchenko on 16.01.16.
 */
public class StaticModel {

    private Body modelBody;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private PolygonShape polygonShape;
    private Vector2 position;


    public StaticModel(Viewport viewport, World world, BodyDef.BodyType bodyType, Vector2 position) {
        this.bodyDef = new BodyDef();
        this.bodyDef.position.set(viewport.getWorldWidth() / 2, 0);
        this.bodyDef.type = bodyType;
        this.modelBody = world.createBody(bodyDef);

        this.polygonShape = new PolygonShape();
        this.fixtureDef = new FixtureDef();

        this.polygonShape.setAsBox(viewport.getWorldWidth() / 2, 80 / Constants.PPM);
        this.fixtureDef.shape = polygonShape;

        this.modelBody.createFixture(fixtureDef);

    }

    public Body getModelBody() {
        return modelBody;
    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public PolygonShape getPolygonShape() {
        return polygonShape;
    }

    public Vector2 getPosition() {
        return position;
    }
}
