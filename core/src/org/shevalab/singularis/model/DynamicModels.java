package org.shevalab.singularis.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Pool;

import static org.shevalab.singularis.utils.Constants.PPM;

public abstract class DynamicModels implements Pool.Poolable {

    protected Body modelBody;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
    protected PolygonShape polygonShape;

    public DynamicModels(int hx, int hy) {
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.fixtureDef = new FixtureDef();
        this.polygonShape = new PolygonShape();
        this.polygonShape.setAsBox(hx / PPM, hy / PPM);
        this.fixtureDef.shape = polygonShape;
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

	@Override
	public void reset() {}
}
