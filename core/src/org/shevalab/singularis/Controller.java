package org.shevalab.singularis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Controller {

    float screenHeigth;
    float screenWidth;

    FitViewport viewPort;
    Stage stage;
    boolean isUp, isLeft, isRight;

    OrthographicCamera camera;

    public Controller(SpriteBatch batch) {
        screenWidth = Gdx.graphics.getWidth();
        screenHeigth = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        viewPort = new FitViewport(screenWidth, screenHeigth, camera);
        stage = new Stage(viewPort, batch);
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode){
                    case Input.Keys.RIGHT:
                        isRight = true;
                        break;
                    case Input.Keys.LEFT:
                        isLeft = true;
                        break;
                    case Input.Keys.UP:
                        isUp = true;
                        break;
                    default:
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode){
                    case Input.Keys.RIGHT:
                        isRight = false;
                        break;
                    case Input.Keys.LEFT:
                        isLeft = false;
                        break;
                    case Input.Keys.UP:
                        isUp = false;
                        break;
                    default:
                        break;
                }
                return true;
            }


        });

        Gdx.input.setInputProcessor(stage);

        Table table  = new Table();
        table.left().bottom();

        Image rigthImg =  new Image(new Texture("in/flatDark24.png"));
        Image leftImg =  new Image(new Texture("in/flatDark23.png"));
        Image upImg =  new Image(new Texture("in/flatDark25.png"));
        rigthImg.setSize(50,50);
        leftImg.setSize(50,50);
        upImg.setSize(50,50);
        registerControlTouch(rigthImg, leftImg, upImg);

        table.add();
        table.add(upImg).size(rigthImg.getWidth(), rigthImg.getHeight());
        table.add();
        table.row().pad(5,5,5,5);
        table.add(leftImg).size(leftImg.getWidth(), rigthImg.getHeight());
        table.add();
        table.add(rigthImg).size(rigthImg.getWidth(), rigthImg.getHeight());
        stage.addActor(table);
    }

    private void registerControlTouch(Image rigthImg, Image leftImg, Image upImg) {
        rigthImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isRight = true;
                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isRight = false;
            }
        });

        leftImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isLeft = true;
                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               isLeft = false;
            }
        });

        upImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isUp = true;
                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               isUp = false;
            }
        });


    }

    public void draw(){
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height){
        viewPort.update(width, height);
    }

    public boolean isRightPressed(){
        return isRight;
    }

    public boolean isLeftPressed(){
        return isLeft;
    }

    public boolean isUpPressed(){
        return isUp;
    }
}
