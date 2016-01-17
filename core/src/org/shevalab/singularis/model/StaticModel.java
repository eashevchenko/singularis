package org.shevalab.singularis.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class StaticModel {

    private Body modelBody;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private PolygonShape polygonShape;


    public StaticModel(World world, BodyDef.BodyType bodyType, Vector2 modelBodyPosition, Vector2 shapePosition) {
        this.bodyDef = new BodyDef();
        this.bodyDef.position.set(modelBodyPosition);
        this.bodyDef.type = bodyType;
        this.modelBody = world.createBody(bodyDef);

        this.polygonShape = new PolygonShape();
        this.fixtureDef = new FixtureDef();

        this.polygonShape.setAsBox(shapePosition.x, shapePosition.y);
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
}
