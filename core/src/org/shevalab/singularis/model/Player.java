package org.shevalab.singularis.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

import static org.shevalab.singularis.utils.Constants.PPM;

public class Player extends  DynamicModels{



    public Player(Viewport viewport, World world, Vector2 position) {
        super(40, 60);
        bodyDef.position.set(viewport.getWorldWidth() / 2, 80 / PPM);
        modelBody = world.createBody(bodyDef);
        modelBody.createFixture(fixtureDef);


    }

    public void updatePlayerSprite(Animation animation) {
        modelBody.setUserData(new AnimatedSprite(animation));
    }

    public void drawPlayer(SpriteBatch batch){
        getSprite().draw(batch);
    }

    public void updatePosition(float delta, Vector2 spritePos){
        getSprite().setPosition(spritePos.x, spritePos.y);
        getSprite().update(delta);
    }

    public float getBodyLinearVelocityX(){
        return modelBody.getLinearVelocity().x;
    }

    public float getBodyLinearVelocityY(){
        return modelBody.getLinearVelocity().y;
    }

    public Vector2 getBodyPosition(){
        return new Vector2(getVectorAxisValue(3, true), getVectorAxisValue(2, false));
    }

    private AnimatedSprite getSprite(){
        return (AnimatedSprite) modelBody.getUserData();
    }

    private float getVectorAxisValue(int index, boolean isXAxis){
        float spriteValue = isXAxis ? getSprite().getWidth() : getSprite().getHeight();
        float position = isXAxis ? modelBody.getPosition().x : modelBody.getPosition().y;
        return position * PPM - spriteValue / index;
    }
}
