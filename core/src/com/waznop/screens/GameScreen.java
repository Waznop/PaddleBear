package com.waznop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.waznop.gameworld.GameRenderer;
import com.waznop.gameworld.GameWorld;
import com.waznop.paddlebear.AssetLoader;
import com.waznop.paddlebear.Constants;
import com.waznop.paddlebear.InputHandler;

/**
 * Created by Waznop on 2016-08-17.
 */
public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;
    private OrthographicCamera cam;
    //private Viewport viewPort;

    public GameScreen() {
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        //viewPort = new FitViewport(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, cam);
        world = new GameWorld();
        renderer = new GameRenderer(world, cam);
        runTime = 0;

        Gdx.input.setInputProcessor(new InputHandler(world, cam));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }
        runTime += delta;
        world.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {
        //AssetLoader.setSizes(width, height);
        //viewPort.update(width, height, true);
        //cam.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
