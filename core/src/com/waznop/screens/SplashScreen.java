package com.waznop.screens;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.waznop.paddlebear.AssetLoader;
import com.waznop.paddlebear.Constants;
import com.waznop.paddlebear.PBGame;
import com.waznop.paddlebear.SpriteAccessor;

/**
 * Created by Waznop on 2016-09-21.
 */
public class SplashScreen implements Screen {

    private TweenManager tweenManager;
    private Sprite splashLogo;
    private PBGame game;
    private SpriteBatch batcher;

    public SplashScreen(PBGame game) {
        this.game = game;
        batcher = new SpriteBatch();
        splashLogo = new Sprite(AssetLoader.waznopGames);
        float width = Constants.SCALED_SCREEN_WIDTH;
        float height = Constants.SCALED_SCREEN_HEIGHT;
        float splashWidth = width * .8f;
        float splashHeight = splashLogo.getHeight() * splashWidth / splashLogo.getWidth();
        splashLogo.setColor(1, 1, 1, 0);
        splashLogo.setSize(splashWidth, splashHeight);
        splashLogo.setPosition((width - splashWidth) / 2, (height - splashHeight) / 2);
        setupTween();
    }


    private void setupTween() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        tweenManager = new TweenManager();

        TweenCallback callback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> baseTween) {
                game.setScreen(new GameScreen());
            }
        };

        Tween.to(splashLogo, SpriteAccessor.ALPHA, 0.8f).target(1)
                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.4f)
                .setCallback(callback).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(tweenManager);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        tweenManager.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();
        splashLogo.draw(batcher);
        batcher.end();
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
