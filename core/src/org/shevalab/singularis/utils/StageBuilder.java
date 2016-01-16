package org.shevalab.singularis.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class StageBuilder {

	public static Stage buildStage(FitViewport viewPort, SpriteBatch spriteBatch, InputListener listener, InputListener right,InputListener left,InputListener up){
		Stage stage = new Stage(viewPort, spriteBatch);
		stage.addListener(listener);
		Table table  = new Table();
		table.left().bottom();
		Image rightImg =  new Image(new Texture("in/flatDark24.png"));
		Image leftImg =  new Image(new Texture("in/flatDark23.png"));
		Image upImg =  new Image(new Texture("in/flatDark25.png"));
		rightImg.setSize(50,50);
		leftImg.setSize(50,50);
		upImg.setSize(50,50);
		rightImg.addListener(right);
		leftImg.addListener(left);
		upImg.addListener(up);
		table.add();
		table.add(upImg).size(rightImg.getWidth(), rightImg.getHeight());
		table.add();
		table.row().pad(5,5,5,5);
		table.add(leftImg).size(leftImg.getWidth(), rightImg.getHeight());
		table.add();
		table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
		stage.addActor(table);

		return stage;
	}
}
