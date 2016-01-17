package org.shevalab.singularis.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by eugeneshevchenko on 16.01.16.
 */
public class Enemy extends  DynamicModels {


    public Enemy(World world, Vector2 position) {
        super(1,1);
    }
}
