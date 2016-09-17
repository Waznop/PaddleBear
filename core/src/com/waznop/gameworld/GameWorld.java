package com.waznop.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Intersector;
import com.waznop.gameobjects.Bear;
import com.waznop.gameobjects.Floater;
import com.waznop.paddlebear.AssetLoader;
import com.waznop.paddlebear.Constants;

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
    private int highScore;

    private GameState currentState;

    public GameWorld() {
        bear = new Bear(
                Constants.GAME_MID_X - Constants.BEAR_SIZE / 2,
                Constants.GAME_MID_Y - Constants.BEAR_SIZE / 2,
                Constants.BEAR_SIZE,
                Constants.BEAR_SIZE);
        scrollHandler = new ScrollHandler(this);
        score = 0;
        currentState = GameState.PLAYING;
        fastforwarding = false;
        spawner = new Spawner();

        data = AssetLoader.data;
        karma = data.getInteger("karma");
        highScore = data.getInteger("highScore");
    }

    public void reset() {
        currentState = GameState.PLAYING;
        score = 0;
        bear.reset(Constants.GAME_MID_X - Constants.BEAR_SIZE / 2,
                Constants.GAME_MID_Y - Constants.BEAR_SIZE / 2);
        scrollHandler.reset();
        fastforwarding = false;
        spawner.reset();
    }

    public void update(float delta, float runTime) {
        switch(currentState) {
            case MENU:
                break;
            case PLAYING:
                updatePlaying(delta);
                break;
            case GAMEOVER:
                updateGameover(delta);
                break;
            case HIGHSCORE:
                break;
        }
    }

    public void updateGameover(float delta) {
        bear.updateGameover(delta);
        scrollHandler.update(delta, - Constants.LAND_SCROLL_Y);
        spawner.update(delta, - Constants.LAND_SCROLL_Y);
    }

    public void updatePlaying(float delta) {
        float centerX = bear.getCenterX();
        float centerY = bear.getCenterY();

        if (centerY < Constants.GAME_START_Y + Constants.GAME_HEIGHT * Constants.FASTFORWARD_START
                && ! fastforwarding) {
            fastforwarding = true;
        } else if (centerY > Constants.GAME_START_Y + Constants.GAME_HEIGHT * Constants.FASTFORWARD_END
                && fastforwarding) {
            fastforwarding = false;
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
        currentState = GameState.GAMEOVER;
        bear.die();
        saveKarma();
        if (score > highScore) {
            setHighScore(score);
        }
    }

    public int addToScore(int increment) {
        return score += increment;
    }

    public int addToKarma(int increment) {
        return karma += increment;
    }

    public void saveKarma() {
        data.putInteger("karma", karma);
        data.flush();
    }

    public int getKarma() {
        return karma;
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

    public GameState getCurrentState() {
        return currentState;
    }

    public Spawner getSpawner() {
        return spawner;
    }

    public void toggleInvincibility() {
        Constants.IS_INVINCIBLE = ! Constants.IS_INVINCIBLE;
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
