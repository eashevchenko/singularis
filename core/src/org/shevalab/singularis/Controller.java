package org.shevalab.singularis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.shevalab.singularis.enums.Navigation;
import org.shevalab.singularis.utils.StageBuilder;

public class Controller extends AbstractController {

    private FitViewport viewPort;
    private Stage stage;

    public Controller(SpriteBatch batch) {
	    super(Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), new OrthographicCamera());
        this.viewPort = new FitViewport(screenWidth, screenHeight, camera);
        this.stage = StageBuilder.buildStage(viewPort, batch, keyPressListener, buildListener(Navigation.RIGHT), buildListener(Navigation.LEFT), buildListener(Navigation.UP));
	    Gdx.input.setInputProcessor(stage);
    }
    public void draw(){
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height){
        viewPort.update(width, height);
    }

    public boolean isRightPressed(){
        return Navigation.RIGHT.equals(direction);
    }

    public boolean isLeftPressed(){
        return Navigation.LEFT.equals(direction);
    }

    public boolean isUpPressed(){
        return Navigation.UP.equals(direction);
    }
}
