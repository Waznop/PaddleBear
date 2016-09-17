package com.waznop.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.waznop.gameobjects.Bear;
import com.waznop.gameobjects.Floater;
import com.waznop.gameobjects.Scrollable;
import com.waznop.paddlebear.AssetLoader;
import com.waznop.paddlebear.Constants;
import com.waznop.paddlebear.InputHandler;

import java.util.ArrayList;

/**
 * Created by Waznop on 2016-08-17.
 */
public class GameRenderer {

    private GameWorld world;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;

    private Bear bear;
    private ScrollHandler scrollHandler;
    private Scrollable river;
    private Scrollable land;
    private Scrollable sand;

    private Spawner spawner;

    private TextureRegion paddlingLeft1;
    private TextureRegion paddlingLeft2;
    private TextureRegion paddlingLeft3;
    private TextureRegion paddlingRight1;
    private TextureRegion paddlingRight2;
    private TextureRegion paddlingRight3;
    private Animation standbyLeft;
    private Animation standbyRight;

    private TextureRegion backgroundLand;
    private TextureRegion backgroundSand;
    private Animation backgroundRiver;

    private Animation shortLog;
    private Animation longLog;
    private Animation babyCub;

    private Animation dying;

    private BitmapFont font;

    public GameRenderer(GameWorld world) {
        this.world = world;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();
    }

    private void initGameObjects() {
        bear = world.getBear();
        scrollHandler = world.getScrollHandler();
        river = scrollHandler.getRiver();
        land = scrollHandler.getLand();
        sand = scrollHandler.getSand();
        spawner = world.getSpawner();
    }

    private void initAssets() {
        paddlingLeft1 = AssetLoader.paddlingLeft1;
        paddlingLeft2 = AssetLoader.paddlingLeft2;
        paddlingLeft3 = AssetLoader.paddlingLeft3;
        paddlingRight1 = AssetLoader.paddlingRight1;
        paddlingRight2 = AssetLoader.paddlingRight2;
        paddlingRight3 = AssetLoader.paddlingRight3;
        standbyLeft = AssetLoader.standbyLeftAnimation;
        standbyRight = AssetLoader.standbyRightAnimation;
        backgroundLand = AssetLoader.backgroundLand;
        backgroundSand = AssetLoader.backgroundSand;
        backgroundRiver = AssetLoader.backgroundLowAnimation;
        shortLog = AssetLoader.shortLogAnimation;
        longLog = AssetLoader.longLogAnimation;
        babyCub = AssetLoader.babyCubAnimation;
        dying = AssetLoader.dyingAnimation;
        font = AssetLoader.font;
    }

    public void render(float delta, float runTime) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(22 / 255f, 125 / 255f, 1 / 255f, 1);
        shapeRenderer.rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        shapeRenderer.end();

        batcher.begin();

        batcher.disableBlending();

        for (Vector2 position : river.getPositions()) {
            batcher.draw(backgroundRiver.getKeyFrame(runTime),
                    position.x, position.y,
                    river.getWidth(), river.getHeight());
        }

        batcher.enableBlending();

        for (Vector2 position : sand.getPositions()) {
            batcher.draw(backgroundSand,
                    position.x, position.y,
                    sand.getWidth(), sand.getHeight());
        }

        ArrayList<Floater> spawnList = spawner.getSpawnList();
        for (int i = 0; i < spawnList.size(); ++i) {
            Floater floater = spawnList.get(i);
            switch(floater.getType()) {
                case SHORTLOG:
                    batcher.draw(shortLog.getKeyFrame(runTime),
                            floater.getX(), floater.getY(),
                            floater.getWidth() / 2, floater.getHeight() / 2,
                            floater.getWidth(), floater.getHeight(),
                            1.2f, 1.2f, floater.getRotation());
                    break;
                case LONGLOG:
                    batcher.draw(longLog.getKeyFrame(runTime),
                            floater.getX(), floater.getY(),
                            floater.getWidth() / 2, floater.getHeight() / 2,
                            floater.getWidth(), floater.getHeight(),
                            1.2f, 1.2f, floater.getRotation());
                    break;
                case BABYCUB:
                    batcher.draw(babyCub.getKeyFrame(runTime),
                            floater.getX(), floater.getY(),
                            floater.getWidth(), floater.getHeight());
            }
        }

        int w = bear.getWidth();
        int h = bear.getHeight();

        TextureRegion image;
        float paddleTimer = bear.getPaddleTimer();
        boolean paddlingLeft = bear.getPaddlingLeft();
        boolean paddlingFront = bear.getPaddlingFront();

        if (! bear.getIsAlive()) {
            image = dying.getKeyFrame(bear.getDyingTimer());
        } else if (paddleTimer <= 0) {
            image = paddlingLeft ? standbyLeft.getKeyFrame(runTime) :
                    standbyRight.getKeyFrame(runTime);
        } else if (paddleTimer > Constants.PADDLE_TIMER_B) {
            image = paddlingFront ?
                    (paddlingLeft ? paddlingLeft1 : paddlingRight1) :
                    (paddlingLeft ? paddlingLeft3 : paddlingRight3);
        } else if (paddleTimer > Constants.PADDLE_TIMER_C) {
            image = paddlingLeft ? paddlingLeft2 : paddlingRight2;
        } else {
            image = paddlingFront ?
                    (paddlingLeft ? paddlingLeft3 : paddlingRight3) :
                    (paddlingLeft ? paddlingLeft1 : paddlingRight1);
        }

        batcher.draw(image, bear.getX(), bear.getY(), w / 2f, h / 2f, w, h, 1, 1, bear.getRotation());

        for (Vector2 position : land.getPositions()) {
            batcher.draw(backgroundLand,
                    position.x, position.y,
                    land.getWidth(), land.getHeight());
        }

        String score = "Score: " + world.getScore();
        font.draw(batcher, score,
                10, Constants.GAME_START_Y + 10);

        String highScore = "Best: " + world.getHighScore();
        font.draw(batcher, highScore,
                10, Constants.GAME_START_Y + 20);

        String karma = "Karma: " + world.getKarma();
        font.draw(batcher, karma,
                10, Constants.GAME_START_Y + 30);

        batcher.end();

        if (Constants.SHOW_DEBUG) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 1, 1, 1);

            // draw collisions
            for (int i = 0; i < spawnList.size(); ++i) {
                Floater floater = spawnList.get(i);
                shapeRenderer.polygon(floater.getCollider().getTransformedVertices());
            }
            shapeRenderer.polygon(bear.getCollider().getTransformedVertices());

            // draw map bounds
            shapeRenderer.rect(Constants.GAME_START_X + Constants.GAME_LEFT_BOUND, Constants.GAME_START_Y,
                    Constants.GAME_RIGHT_BOUND - Constants.GAME_LEFT_BOUND, Constants.GAME_HEIGHT);

            // draw the score line
            Vector2 tmp = scrollHandler.getScoreLine().getPositions().getFirst();
            shapeRenderer.line(Constants.GAME_START_X + Constants.GAME_LEFT_BOUND, tmp.y,
                    Constants.GAME_START_X + Constants.GAME_RIGHT_BOUND, tmp.y);

            shapeRenderer.end();
        }

        // if screen is too narrow
        shapeRenderer.begin(ShapeType.Filled);

        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(0, 0, Constants.SCREEN_WIDTH, Constants.GAME_START_Y);
        shapeRenderer.rect(0, Constants.GAME_START_Y + Constants.GAME_HEIGHT,
                Constants.SCREEN_WIDTH, Constants.GAME_START_Y);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setColor(1, 1, 1, 0.1f);
        switch(InputHandler.touchSection) {
            case TOPLEFT:
                shapeRenderer.rect(0, Constants.GAME_START_Y,
                        Constants.GAME_MID_X, Constants.GAME_MID_Y);
                break;
            case TOPRIGHT:
                shapeRenderer.rect(Constants.GAME_MID_X, Constants.GAME_START_Y,
                        Constants.SCREEN_WIDTH, Constants.GAME_MID_Y);
                break;
            case BOTLEFT:
                shapeRenderer.rect(0, Constants.GAME_MID_Y,
                        Constants.GAME_MID_X, Constants.SCREEN_HEIGHT);
                break;
            case BOTRIGHT:
                shapeRenderer.rect(Constants.GAME_MID_X, Constants.GAME_MID_Y,
                        Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
                break;
        }

        shapeRenderer.end();

    }

}
