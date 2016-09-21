package com.waznop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.waznop.gameworld.GameRenderer;
import com.waznop.gameworld.GameWorld;
import com.waznop.paddlebear.InputHandler;

/**
 * Created by Waznop on 2016-08-17.
 */
public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;

    public GameScreen() {
        world = new GameWorld();
        renderer = new GameRenderer(world);
        runTime = 0;

        Gdx.input.setInputProcessor(new InputHandler(world));
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
