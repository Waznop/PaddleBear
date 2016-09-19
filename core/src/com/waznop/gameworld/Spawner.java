package com.waznop.gameworld;

import com.waznop.gameobjects.Floater;
import com.waznop.paddlebear.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Waznop on 2016-09-16.
 */
public class Spawner {

    private Random rng;
    private ArrayList<Floater> spawnList;
    private float spawnTimer;
    private float spawnTime;
    private boolean spawnEnabled;

    public Spawner() {
        rng = new Random();
        spawnList = new ArrayList<Floater>();
        spawnTime = Constants.SPAWN_TIME_START;
        spawnTimer = 0;
        spawnEnabled = true;
    }

    public void reset() {
        spawnList.clear();
        spawnTime = Constants.SPAWN_TIME_START;
        spawnTimer = 0;
        spawnEnabled = true;
    }

    public void update(float delta) {
        update(delta, 0);
    }

    public void update(float delta, float extraSpeedY) {
        spawnTimer += delta;

        if (spawnTimer >= spawnTime) {
            spawnTimer = 0;
            if (spawnTime > Constants.SPAWN_TIME_CAP) {
                spawnTime -= Constants.SPAWN_TIME_PROGRESSION;
            }
            if (spawnEnabled) {
                spawnRandom();
            }
        }

        Iterator<Floater> iter = spawnList.iterator();
        while (iter.hasNext()) {
            Floater floater = iter.next();
            floater.update(delta, extraSpeedY);
            if (floater.getY() > Constants.GAME_START_Y + Constants.GAME_HEIGHT + Constants.SPAWN_MARGIN) {
                floater.die();
                iter.remove();
            }
        }
    }

    private Floater spawnRandom() {
        FloaterEnum type;
        int margin;
        float rotation;

        if (rng.nextFloat() < Constants.SPAWN_CUB_CHANCE) {
            type = FloaterEnum.BABYCUB;
            margin = Constants.BABY_CUB_SIZE;
            rotation = 0;
        } else if (rng.nextBoolean()) {
            type = FloaterEnum.SHORTLOG;
            margin = Constants.SHORT_LOG_WIDTH;
            rotation = -45 + rng.nextFloat() * 90;
        } else {
            type = FloaterEnum.LONGLOG;
            margin = Constants.LONG_LOG_WIDTH;
            rotation = -45 + rng.nextFloat() * 90;
        }

        float x = Constants.GAME_START_X + Constants.GAME_LEFT_BOUND + rng.nextFloat() *
                (Constants.GAME_RIGHT_BOUND - Constants.GAME_LEFT_BOUND - margin);
        float y = Constants.GAME_START_Y - Constants.SPAWN_MARGIN;
        float speedY = Constants.SPAWN_SCROLL_MIN_Y + rng.nextFloat() * Constants.SPAWN_SCROLL_RANGE_Y;
        return spawn(type, x, y, speedY, rotation);
    }

    private Floater spawn(FloaterEnum type, float x, float y, float speedY, float rotation) {
        int width;
        int height;
        switch(type) {
            case SHORTLOG:
                width = Constants.SHORT_LOG_WIDTH;
                height = Constants.LOG_HEIGHT;
                break;
            case LONGLOG:
                width = Constants.LONG_LOG_WIDTH;
                height = Constants.LOG_HEIGHT;
                break;
            case BABYCUB:
                width = Constants.BABY_CUB_SIZE;
                height = Constants.BABY_CUB_SIZE;
                break;
            default:
                width = Constants.BEAR_SIZE;
                height = Constants.BEAR_SIZE;
        }
        Floater floater = new Floater(type, x, y, width, height, speedY, rotation);
        spawnList.add(floater);
        return floater;
    }

    public ArrayList<Floater> getSpawnList() {
        return spawnList;
    }

    public boolean getSpawnEnabled() {
        return spawnEnabled;
    }

    public void setSpawnEnabled(boolean spawnEnabled) {
        this.spawnEnabled = spawnEnabled;
    }
}
