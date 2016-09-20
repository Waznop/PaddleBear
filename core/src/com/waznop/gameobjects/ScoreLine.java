package com.waznop.gameobjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.waznop.gameworld.GameWorld;
import com.waznop.paddlebear.AssetLoader;
import com.waznop.paddlebear.Constants;

/**
 * Created by Waznop on 2016-09-16.
 */
public class ScoreLine extends Scrollable{

    private boolean gotPoint;
    private GameWorld gameWorld;
    private Bear bear;
    private Sound scoreUp;

    public ScoreLine(float x, float y, int width, int height, float speedY, GameWorld gameWorld) {
        super(1, x, y, width, height, speedY);
        this.gameWorld = gameWorld;
        bear = gameWorld.getBear();
        gotPoint = true;
        scoreUp = AssetLoader.scoreupSound;
    }

    @Override
    public void reset() {
        super.reset();
        gotPoint = true;
    }

    @Override
    public void update(float delta, float extraSpeedY) {
        Vector2 position = positions.getFirst();
        position.mulAdd(velocity, delta);
        position.add(0, extraSpeedY * delta);

        if (position.y >= Constants.GAME_START_Y + Constants.GAME_HEIGHT) {
            position.y = Constants.GAME_START_Y;
            gotPoint = false;
        }

        if (position.y > bear.getY() && ! gotPoint) {
            gotPoint = true;
            gameWorld.addToScore(1);
            if (! gameWorld.getIsMuted()) {
                scoreUp.play(0.2f);
            }
        }
    }

}
