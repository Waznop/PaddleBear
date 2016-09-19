package com.waznop.gameworld;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.waznop.gameobjects.Bear;
import com.waznop.gameobjects.Floater;
import com.waznop.paddlebear.AssetLoader;
import com.waznop.paddlebear.Constants;
import com.waznop.gameobjects.SimpleButton;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Waznop on 2016-08-17.
 */
public class GameWorld {

    private Bear bear;
    private ScrollHandler scrollHandler;
    private int score;
    private boolean fastforwarding;
    private Spawner spawner;
    private Preferences data;
    private int karma;
    private int deltaKarma;
    private int highScore;

    private ArrayList<SimpleButton> activeButtons;
    private SimpleButton playButton;
    private SimpleButton shopButton;
    private SimpleButton restartButton;
    private SimpleButton postButton;

    private GameStateEnum currentState;

    private float endGameTimer;

    public GameWorld() {
        bear = new Bear(
                Constants.GAME_MID_X - Constants.BEAR_SIZE / 2,
                Constants.GAME_MID_Y - Constants.BEAR_SIZE / 2,
                Constants.BEAR_SIZE,
                Constants.BEAR_SIZE);
        scrollHandler = new ScrollHandler(this);
        score = 0;
        currentState = GameStateEnum.MENU;
        fastforwarding = false;
        spawner = new Spawner();

        data = AssetLoader.data;
        karma = data.getInteger("karma");
        deltaKarma = 0;
        highScore = data.getInteger("highScore");

        TextureRegion playButtonUp = AssetLoader.playButtonUp;
        TextureRegion shopButtonUp = AssetLoader.shopButtonUp;
        TextureRegion restartButtonUp = AssetLoader.restartButtonUp;
        TextureRegion postButtonUp = AssetLoader.postButtonUp;

        playButton = new SimpleButton(ButtonTypeEnum.PLAY,
                Constants.GAME_MID_X - playButtonUp.getRegionWidth() * 1.5f,
                Constants.GAME_MID_Y + Constants.PLAY_BUTTON_OFFSET_Y,
                playButtonUp.getRegionWidth() * 3, playButtonUp.getRegionHeight() * 3,
                playButtonUp, AssetLoader.playButtonDown
        );

        shopButton = new SimpleButton(ButtonTypeEnum.SHOP,
                Constants.GAME_MID_X - shopButtonUp.getRegionWidth() * 1.2f,
                Constants.GAME_MID_Y + Constants.SHOP_BUTTON_OFFSET_Y,
                shopButtonUp.getRegionWidth() * 2.4f, shopButtonUp.getRegionHeight() * 2,
                shopButtonUp, AssetLoader.shopButtonDown
        );

        restartButton = new SimpleButton(ButtonTypeEnum.PLAY,
                Constants.GAME_MID_X - restartButtonUp.getRegionWidth() * 1.2f,
                Constants.GAME_MID_Y + Constants.RESTART_BUTTON_OFFSET_Y,
                restartButtonUp.getRegionWidth() * 2.4f, restartButtonUp.getRegionHeight() * 2,
                restartButtonUp, AssetLoader.restartButtonDown
        );

        postButton = new SimpleButton(ButtonTypeEnum.POST,
                Constants.GAME_MID_X - postButtonUp.getRegionWidth() * 1.2f,
                Constants.GAME_MID_Y + Constants.POST_BUTTON_OFFSET_Y,
                postButtonUp.getRegionWidth() * 2.4f, postButtonUp.getRegionHeight() * 2,
                postButtonUp, AssetLoader.postButtonDown
        );

        activeButtons = new ArrayList<SimpleButton>();
        activeButtons.add(playButton);

        endGameTimer = 0;

        /*
        startGame();
        endGame();
        showPostMenu();
        */
    }

    public void startGame() {
        currentState = GameStateEnum.PLAYING;
        score = 0;
        deltaKarma = 0;
        bear.reset(Constants.GAME_MID_X - Constants.BEAR_SIZE / 2,
                Constants.GAME_MID_Y - Constants.BEAR_SIZE / 2);
        scrollHandler.reset();
        fastforwarding = false;
        spawner.reset();
        activeButtons.clear();
    }

    public void update(float delta, float runTime) {
        switch(currentState) {
            case MENU:
                updateIdle(delta);
                break;
            case PLAYING:
                updatePlaying(delta);
                break;
            case GAMEOVER:
                updateGameover(delta);
                break;
            case POSTMENU:
                updateIdle(delta);
                bear.updateGameover(delta);
                break;
        }
    }

    public void updateIdle(float delta) {
        scrollHandler.update(delta, - Constants.LAND_SCROLL_Y);
        spawner.update(delta, - Constants.LAND_SCROLL_Y);
    }

    public void updateGameover(float delta) {
        endGameTimer -= delta;
        if (endGameTimer <= 0) {
            showPostMenu();
        }
        updateIdle(delta);
        bear.updateGameover(delta);
    }

    public void updatePlaying(float delta) {
        float centerX = bear.getCenterX();
        float centerY = bear.getCenterY();

        if (centerY < Constants.GAME_START_Y + Constants.GAME_HEIGHT * Constants.FASTFORWARD_START
                && ! fastforwarding) {
            fastforwarding = true;
            bear.getEmitter().getLife().setHigh(0);
            bear.getEmitter().setAttached(true);
        } else if (centerY > Constants.GAME_START_Y + Constants.GAME_HEIGHT * Constants.FASTFORWARD_END
                && fastforwarding) {
            fastforwarding = false;
            bear.getEmitter().getLife().setHigh(1000);
            bear.getEmitter().setAttached(false);
        }

        if (fastforwarding) {
            bear.updatePlaying(delta, Constants.FASTFORWARD_SCROLL_Y);
            scrollHandler.update(delta, Constants.FASTFORWARD_SCROLL_Y);
            spawner.update(delta, Constants.FASTFORWARD_SCROLL_Y);
        } else {
            bear.updatePlaying(delta);
            scrollHandler.update(delta);
            spawner.update(delta);
        }

        if (centerX - bear.getRadius() < Constants.GAME_START_X + Constants.GAME_LEFT_BOUND
                || centerX + bear.getRadius() > Constants.GAME_START_X + Constants.GAME_RIGHT_BOUND
                || centerY - bear.getRadius() > Constants.GAME_START_Y + Constants.GAME_HEIGHT) {
            if (! bear.getIsInvincible()) {
                endGame();
            }
        }

        ArrayList<Floater> spawnList = spawner.getSpawnList();
        Iterator<Floater> iter = spawnList.iterator();
        while (iter.hasNext()) {
            Floater floater = iter.next();
            if (Intersector.overlapConvexPolygons(floater.getCollider(), bear.getCollider())) {
                if (floater.getType() == FloaterEnum.BABYCUB) {
                    addToKarma(1);
                    floater.die();
                    iter.remove();
                } else {
                    if (! bear.getIsInvincible()) {
                        endGame();
                    }
                }
            }
        }

    }

    private void endGame() {
        endGameTimer = Constants.END_GAME_TIME;
        spawner.setSpawnEnabled(false);
        bear.die();
        saveKarma();
        if (score > highScore) {
            setHighScore(score);
        }
        currentState = GameStateEnum.GAMEOVER;
    }

    public void showPostMenu() {
        currentState = GameStateEnum.POSTMENU;
        activeButtons.add(shopButton);
        activeButtons.add(restartButton);
        activeButtons.add(postButton);
    }

    public int addToScore(int increment) {
        return score += increment;
    }

    public int addToKarma(int increment) {
        deltaKarma += increment;
        return karma += increment;
    }

    public void saveKarma() {
        data.putInteger("karma", karma);
        data.flush();
    }

    public int getKarma() {
        return karma;
    }

    public int getDeltaKarma() {
        return deltaKarma;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        data.putInteger("highScore", highScore);
        data.flush();
    }

    public int getHighScore() {
        return highScore;
    }

    public Bear getBear() {
        return bear;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public int getScore() {
        return score;
    }

    public GameStateEnum getCurrentState() {
        return currentState;
    }

    public Spawner getSpawner() {
        return spawner;
    }

    public ArrayList<SimpleButton> getActiveButtons() {
        return activeButtons;
    }

    public void toggleInvincibility() {
        bear.setIsInvincible(! bear.getIsInvincible());
    }

    public void toggleDebug() {
        Constants.SHOW_DEBUG = ! Constants.SHOW_DEBUG;
    }

    public void resetData() {
        karma = 0;
        highScore = 0;
        data.putInteger("karma", 0);
        data.putInteger("highScore", 0);
        data.flush();
    }

}
